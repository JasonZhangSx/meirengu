package com.meirengu.uc.service.impl;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.uc.contract.CreateHtmlContactFile;
import com.meirengu.uc.dao.UserAddressDao;
import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.User;
import com.meirengu.uc.model.UserAddress;
import com.meirengu.uc.po.AddressPO;
import com.meirengu.uc.service.ContactService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.JacksonUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by huoyan403 on 4/11/2017.
 */
@Service
public class ContactServiceImpl implements ContactService {


    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    private UserAddressDao userAddressDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressServiceImpl addressServiceImpl;

    @Override
    public Result CreateContactFile(Map<String,String> map) {

        HttpUtil.HttpResult hr = null;
        Result result = new Result();
        String url = ConfigUtil.getConfig("URI_GET_CONTACT_INFO");
        String urlAppend = url+"?item_id="+map.get("itemId")+"&level_id="+map.get("levelId");
        logger.info("ContactServiceImpl.CreateContactFile send get >> uri :{}, params:{}", new Object[]{url});
        try {
            hr = HttpUtil.doGet(urlAppend);
        } catch (Exception e) {
            logger.error("ContactServiceImpl.CreateContactFile send get >> params:{}, exception:{}", new Object[]{e});
        }
        if( hr!=null && hr.getStatusCode()==200){
            Map<String,Object> mapData = new HashedMap();
            mapData = JacksonUtil.readValue(hr.getContent(),Map.class);
            if(mapData!=null){
                Map data = (Map)mapData.get("data");
                data.put("itemId",map.get("itemId"));
                data.put("levelId",map.get("levelId"));
                data.put("userId",map.get("userId"));
              //获取投资人信息

                User user = userDao.retrieveByUserId(Integer.parseInt(map.get("userId")));
                UserAddress userAddress = new UserAddress();
                userAddress.setUserId(Integer.parseInt(map.get("userId")));
                userAddress.setIsDefault(1);
                userAddress = userAddressDao.selectByUserAddress(userAddress);
                AddressPO addressPO = addressServiceImpl.showAddress(userAddress.getAreaId());
                data.put("investors",user.getRealname());
                data.put("investorIdCard",user.getIdCard());
                data.put("investorArea",addressPO.getProvince() +" "+addressPO.getCity()+" "+addressPO.getArea()+" "+userAddress.getUserAddress());

                try {
                    CreateHtmlContactFile createHtmlContactFile  = new CreateHtmlContactFile();
                    Map<String,String> message = createHtmlContactFile.CreateHtmlContactFile(data);

                    if(message!=null){
                        String msg = message.get("flag");
                        if("success".equals(msg)){
                            return this.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
                        }
                        if("failed".equals(msg)){
                            return this.setResult(StatusCode.OK, message.get("msg"), StatusCode.codeMsgMap.get(StatusCode.OK));
                        }
                    }
                }catch (Exception e){
                    logger.info("ContactServiceImpl.CreateContactFile failed :{}",e.getMessage());
                }
            }else{
                logger.info("ContactServiceImpl.CreateContactFile connected refused :{}");
                return this.setResult(StatusCode.RETRIEVE_PROJECT_GET_MESSAGE_EMPTY, null, StatusCode.codeMsgMap.get(StatusCode.RETRIEVE_PROJECT_GET_MESSAGE_EMPTY));
            }
        }else{
            logger.info("ContactServiceImpl.CreateContactFile connected refused :{}");
            return this.setResult(StatusCode.RETRIEVE_PROJECT_GET_MESSAGE_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.RETRIEVE_PROJECT_GET_MESSAGE_FAILED));
        }
        return null;
    }


    public Result setResult(int code, Object data, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        if (code == 200 && (data != null && !"".equals(data))){
            result.setData(data);
        }else {
            //result.setData("");
        }
        logger.info("Request getResponse: {}", JSON.toJSON(result));
        return result;
    }
}
