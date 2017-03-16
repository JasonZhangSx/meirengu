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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/14/2017.
 */
@RestController
@RequestMapping("address")
public class UserAddressController extends BaseController{


    @Autowired
    private UserAddressService userAddressService;

    private static final Logger logger = LoggerFactory.getLogger(UserAddressController.class);

    @RequestMapping(value = "insert", method = RequestMethod.POST)
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

        UserAddress userAddres = userAddressService.selectDefaultAddressByUserId(userId);

        if(!isDefault){
            if(userAddres==null || StringUtil.isEmpty(userAddres.getAddressId())){
                userAddressBean.setDefault(true);
            }else{
                userAddressBean.setDefault(false);
            }
        }else{
                userAddressBean.setDefault(false);
        }
        int res = userAddressService.insert(userAddressBean);
        if(res == 0){
            userAddressBean.setAddressId(UuidUtils.getShortUuid());
            userAddressService.insert(userAddressBean);
        }
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode
                .OK));
    }



    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result updateAddress(@RequestParam(value = "mobile", required = false) String mobile,
                                @RequestParam(value = "address_id", required = true) Integer addressId,
                                @RequestParam(value = "user_id", required = true) Integer userId,
                                @RequestParam(value = "user_name", required = false) String userName,
                                @RequestParam(value = "user_address", required = false) String userAddress,
                                @RequestParam(value = "is_default", required = false) Boolean isDefault){

        //verify params
        if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
            return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        UserAddress address = new UserAddress();
        address.setAddressId(addressId);
        UserAddress userAddress1 = userAddressService.selectByUserAddress(address);
        if(StringUtil.isEmpty(userAddress1.getUserId())){
            return super.setResult(StatusCode.ADDRESS_ID_NOT_EMPTY, null, StatusCode.codeMsgMap.get(StatusCode
                    .ADDRESS_ID_NOT_EMPTY));
        }
        if(!userAddress1.getUserId().equals(userId)){
            return super.setResult(StatusCode.ADDRESS_ID_AND_USER_ID_MISMATCH, null, StatusCode.codeMsgMap.get(StatusCode
                    .ADDRESS_ID_AND_USER_ID_MISMATCH));
        }
        UserAddress userAddressBean = new UserAddress();
        userAddressBean.setUpdateTime(new Date());
        userAddressBean.setUserAddress(userAddress);
        userAddressBean.setAddressId(addressId);
        userAddressBean.setUserId(userId);
        userAddressBean.setUserPhone(mobile);
        userAddressBean.setUserName(userName);
        userAddressBean.setDelFlag(1);
        userAddressBean.setDefault(isDefault);

        int res = userAddressService.updateByAdressId(userAddressBean);
        if(res == 0){
            userAddressBean.setAddressId(UuidUtils.getShortUuid());
            userAddressService.updateByAdressId(userAddressBean);
        }
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode
                .OK));
    }

    /**
     * 逻辑删除地址
     * @param addressId
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result deleteAddress(@RequestParam(value = "address_id", required = true) Integer addressId){

        //默认地址不允许删除
        UserAddress userAddress = new UserAddress();
        userAddress.setAddressId(addressId);
        userAddressService.selectByUserAddress(userAddress);
        if(userAddress.getDefault()!=null){
            if(userAddress.getDefault()){
                return super.setResult(StatusCode.ADDREAA_IS_NOT_ALLOWED_DELETE, null, StatusCode.codeMsgMap.get(StatusCode
                        .ADDREAA_IS_NOT_ALLOWED_DELETE));
            }
        }
        userAddress.setDelFlag(1);
        userAddressService.updateByAdressId(userAddress);
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    /**
     * 分页获取地址
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = {RequestMethod.POST})
    public Result list(@RequestParam(value="page", required = false, defaultValue = "1") int pageNum,
                                    @RequestParam(value="per_page", required = false, defaultValue = "10") int pageSize,
                                    @RequestParam(value="user_id", required = true) int userId){
        Map paramMap = new HashMap<String, Object>();
        paramMap.put("userId",userId);
        Page<UserAddress> page = super.setPageParams(pageNum,pageSize);
        try{
            page = userAddressService.getListByPage(page,paramMap);
            if(page.getList().size() != 0){
                return super.setResult(StatusCode.OK, page,StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }
}
