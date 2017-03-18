package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.uc.model.UserAddress;
import com.meirengu.uc.service.UserAddressService;
import com.meirengu.uc.utils.ObjectUtils;
import com.meirengu.uc.utils.RedisUtil;
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
import java.util.List;
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
                                @RequestParam(value = "token", required = true) String token,
                                @RequestParam(value = "user_id", required = true) Integer userId,
                                @RequestParam(value = "user_name", required = false) String userName,
                                @RequestParam(value = "user_address", required = false) String userAddress,
                                @RequestParam(value = "pid", required = true) Integer pid,
                                @RequestParam(value = "city_id", required = true) Integer cityId,
                                @RequestParam(value = "area_id", required = true) Integer areaId,
                                @RequestParam(value = "is_default", required = false) Boolean isDefault){

        try{
            RedisUtil redisUtil = new RedisUtil();
            Object userRedis =   redisUtil.getObject(token);
            if(!StringUtil.isEmpty(userRedis)){
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
                userAddressBean.setProvinceId(pid);
                userAddressBean.setCityId(cityId);
                userAddressBean.setAreaId(areaId);
                userAddressBean.setDefault(isDefault);
                int res = userAddressService.insert(userAddressBean);
                if(res == 0){
                    userAddressBean.setAddressId(UuidUtils.getShortUuid());
                    userAddressService.insert(userAddressBean);
                }
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode
                        .OK));
            }else{
                //无效token返回登陆
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
        }catch (Exception e){
            logger.info("LoginController.redis get token result:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }



    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result updateAddress(@RequestParam(value = "mobile", required = false) String mobile,
                                @RequestParam(value = "token", required = true) String token,
                                @RequestParam(value = "address_id", required = true) Integer addressId,
                                @RequestParam(value = "user_id", required = true) Integer userId,
                                @RequestParam(value = "user_name", required = false) String userName,
                                @RequestParam(value = "user_address", required = false) String userAddress,
                                @RequestParam(value = "pid", required = true) Integer pid,
                                @RequestParam(value = "city_id", required = true) Integer cityId,
                                @RequestParam(value = "area_id", required = true) Integer areaId,
                                @RequestParam(value = "is_default", required = false) Boolean isDefault){

        try{
            RedisUtil redisUtil = new RedisUtil();
            Object userRedis =   redisUtil.getObject(token);
            if(!StringUtil.isEmpty(userRedis)){
                //verify params
                if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
                    return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                            .MOBILE_FORMAT_ERROR));
                }
                UserAddress address = new UserAddress();
                address.setAddressId(addressId);
                UserAddress userAddress1 = userAddressService.selectByUserAddress(address);
                if(userAddress1 !=null && StringUtil.isEmpty(userAddress1.getUserId())){
                    return super.setResult(StatusCode.ADDRESS_ID_NOT_EMPTY, null, StatusCode.codeMsgMap.get(StatusCode
                            .ADDRESS_ID_NOT_EMPTY));
                }
                if(userAddress1 !=null && !userAddress1.getUserId().equals(userId)){
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
                userAddressBean.setProvinceId(pid);
                userAddressBean.setCityId(cityId);
                userAddressBean.setAreaId(areaId);
                int res = userAddressService.updateByAdressId(userAddressBean);
                if(res == 0){
                    userAddressBean.setAddressId(UuidUtils.getShortUuid());
                    userAddressService.updateByAdressId(userAddressBean);
                }
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                //无效token返回登陆
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
        }catch (Exception e){
            logger.info("LoginController.redis get token result:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 逻辑删除地址
     * @param addressId
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result deleteAddress(@RequestParam(value = "address_id", required = true) Integer addressId,
                                @RequestParam(value = "token", required = true) String token){

        try{
            RedisUtil redisUtil = new RedisUtil();
            Object userRedis =   redisUtil.getObject(token);
            if(!StringUtil.isEmpty(userRedis)){
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
            }else{
                //无效token返回登陆
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
        }catch (Exception e){
            logger.info("LoginController.redis get token result:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
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
                       @RequestParam(value="user_id", required = true) int userId,
                       @RequestParam(value = "token", required = true) String token){
        try{
            RedisUtil redisUtil = new RedisUtil();
            Object userRedis =   redisUtil.getObject(token);
            if(!StringUtil.isEmpty(userRedis)){
                Map paramMap = new HashMap<String, Object>();
                paramMap.put("userId",userId);
                Page<UserAddress> page = super.setPageParams(pageNum,pageSize);
                page = userAddressService.getListByPage(page,paramMap);
                if(page.getList().size() != 0){
                    return super.setResult(StatusCode.OK, page,StatusCode.codeMsgMap.get(StatusCode.OK));
                }else{
                    return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
                }
            }else{
                //无效token返回登陆
                Page<UserAddress> page = new Page<>();
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, page, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
        }catch (Exception e){
            logger.info("LoginController.redis get token result:{}",e.getMessage());
            Page<UserAddress> page = new Page<>();
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, page, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    @ResponseBody
    @RequestMapping(value = "get", method = {RequestMethod.POST})
    public Result list(@RequestParam(value="address_id", required = true) int addressId,
                       @RequestParam(value="user_id", required = true) int userId,
                       @RequestParam(value = "token", required = true) String token){

        try{
            RedisUtil redisUtil = new RedisUtil();
            Object userRedis =   redisUtil.getObject(token);
            if(!StringUtil.isEmpty(userRedis)){
                UserAddress userAddress = new UserAddress();
                userAddress.setUserId(userId);
                userAddress.setAddressId(addressId);
                UserAddress userAddressPO = userAddressService.selectByUserAddress(userAddress);
                if(userAddressPO != null){
                    return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(userAddressPO,UserAddress.class),StatusCode.codeMsgMap.get(StatusCode.OK));
                }else{
                    return super.setResult(StatusCode.ADDRESS_IS_NOT_EXITS, null,StatusCode.codeMsgMap.get(StatusCode.ADDRESS_IS_NOT_EXITS));
                }
            }else{
                //无效token返回登陆
                Page<UserAddress> page = new Page<>();
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, page, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
        }catch (Exception e){
            logger.info("LoginController.redis get token result:{}",e.getMessage());
            Page<UserAddress> page = new Page<>();
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, page, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 提供给订单系统查询地址接口
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listAddress", method = {RequestMethod.POST})
    public Result listAddress(@RequestParam(value="address_id", required = true) String addressId){
        List<UserAddress> userAddressPO = userAddressService.selectByAddIdArray(addressId);
        if(userAddressPO != null){
            return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(userAddressPO,List.class),StatusCode.codeMsgMap.get(StatusCode.OK));
        }else{
            return super.setResult(StatusCode.ADDRESS_IS_NOT_EXITS, null,StatusCode.codeMsgMap.get(StatusCode.ADDRESS_IS_NOT_EXITS));
        }
    }
}
