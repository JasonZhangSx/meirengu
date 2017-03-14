package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.uc.model.UserAddress;
import com.meirengu.uc.service.UserAddressService;
import com.meirengu.utils.StringUtil;
import com.meirengu.utils.UuidUtils;
import com.meirengu.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/14/2017.
 */
public class UserAddressController extends BaseController{


    @Autowired
    private UserAddressService userAddressService;

    private static final Logger logger = LoggerFactory.getLogger(UserAddressController.class);

    @RequestMapping(value = "address/insert", method = RequestMethod.POST)
    public Result insertAddress(@RequestParam(value = "mobile", required = false) String mobile,
                                @RequestParam(value = "user_id", required = true) Integer userId,
                                @RequestParam(value = "user_name", required = false) String userName,
                                @RequestParam(value = "user_address", required = false) String userAddress,
                                @RequestParam(value = "is_default", required = false) Boolean isDefault){

        //verify params
        if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
            return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        UserAddress userAddressBean = new UserAddress();
        userAddressBean.setAddressId(UuidUtils.getShortUuid());
        userAddressBean.setCreateTime(new Date());
        userAddressBean.setUpdateTime(new Date());
        userAddressBean.setUserAddress(userAddress);
        userAddressBean.setUserId(userId);
        userAddressBean.setUserName(userName);
        userAddressBean.setUserPhone(mobile);
        userAddressBean.setDelFlag(1);

        UserAddress userAddres = userAddressService.selectDefaultAddressByPhone(userId);

        if(!isDefault){
            if(!StringUtil.isEmpty(userAddres.getAddressId())){
                userAddressBean.setDefault(true);
            }else{
                userAddressBean.setDefault(false);
            }
        }
        int res = userAddressService.insert(userAddressBean);
        if(res == 0){
            userAddressBean.setAddressId(UuidUtils.getShortUuid());
            userAddressService.insert(userAddressBean);
        }
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode
                .OK));
    }



    @RequestMapping(value = "address/update", method = RequestMethod.POST)
    public Result updateAddress(@RequestParam(value = "mobile", required = false) String mobile,
                                @RequestParam(value = "user_id", required = true) Integer userId,
                                @RequestParam(value = "user_name", required = false) String userName,
                                @RequestParam(value = "user_address", required = false) String userAddress,
                                @RequestParam(value = "is_default", required = false) Boolean isDefault){

        //verify params
        if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
            return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        UserAddress userAddressBean = new UserAddress();
        userAddressBean.setUpdateTime(new Date());
        userAddressBean.setUserAddress(userAddress);
        userAddressBean.setUserId(userId);
        userAddressBean.setUserPhone(mobile);
        userAddressBean.setUserName(userName);
        userAddressBean.setDelFlag(1);
        userAddressBean.setDefault(isDefault);

        int res = userAddressService.updateByAdressId(userAddressBean);
        if(res == 0){
            userAddressBean.setAddressId(UuidUtils.getShortUuid());
            userAddressService.insert(userAddressBean);
        }
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode
                .OK));
    }

    /**
     * 逻辑删除地址
     * @param addressId
     * @return
     */
    @RequestMapping(value = "address/delete", method = RequestMethod.POST)
    public Result deleteAddress(@RequestParam(value = "address_id", required = true) Integer addressId){
        UserAddress userAddress = new UserAddress();
        userAddress.setAddressId(addressId);
        userAddress.setDelFlag(1);
        userAddressService.updateByAdressId(userAddress);
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode
                .OK));
    }

    /**
     * 分页获取地址
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = {RequestMethod.POST})
    public Map<String, Object> list(@RequestParam(value="page", required = false, defaultValue = "1") int pageNum,
                                    @RequestParam(value="per_page", required = false, defaultValue = "10") int pageSize){
        Map paramMap = new HashMap<String, Object>();
        Page<UserAddress> page = super.setPageParams(pageSize,pageNum);
        try{
            page = userAddressService.getPageList(page,paramMap);
            if(page.getList().size() != 0){
                return super.setReturnMsg(StatusCode.OK, page, StatusCode.OK+"");
            }else{
                return super.setReturnMsg(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.RECORD_NOT_EXISTED+"");
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setReturnMsg(StatusCode.INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }
}
