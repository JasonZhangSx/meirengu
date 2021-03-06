package com.meirengu.uc.controller;

import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.uc.model.UserAddress;
import com.meirengu.uc.vo.request.AddressVO;
import com.meirengu.uc.service.AddressService;
import com.meirengu.uc.service.UserAddressService;
import com.meirengu.utils.ObjectUtils;
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
 * 用户地址增、该、删、分页查询、地址详情、订单查询地址
 */
@RestController
@RequestMapping("address")
public class UserAddressController extends BaseController{


    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    AddressService service;
    @Autowired
    private RedisClient redisClient;
    private static final Logger logger = LoggerFactory.getLogger(UserAddressController.class);

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Result insertAddress(@RequestParam(value = "mobile", required = true) String mobile,
                                @RequestParam(value = "token", required = true) String token,
                                @RequestParam(value = "user_id", required = true) Integer userId,
                                @RequestParam(value = "user_name", required = true) String userName,
                                @RequestParam(value = "user_address", required = true) String userAddress,
                                @RequestParam(value = "area_id", required = true) Integer areaId,
                                @RequestParam(value = "is_default", defaultValue = "0",required = false) Integer isDefault){
        logger.info("UserAddressController insert UserAddress ,mobile:{},token:{},userAddress:{},userId:{},areaId:{},isDefault:{}"
                ,mobile,token,userAddress,userId,areaId,isDefault);
        try{
            if(redisClient.existsObject(token)){
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
                userAddressBean.setAreaId(areaId);
                userAddressBean.setIsDefault(isDefault);
                int res = userAddressService.insert(userAddressBean);
                if(res == 0){
                    userAddressBean.setAddressId(UuidUtils.getShortUuid());
                    userAddressService.insert(userAddressBean);
                }
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                //无效token返回登陆
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
        }catch (Exception e){
            logger.error("UserAddressController. insert UserAddress throws Exception:{} ",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result updateAddress(@RequestParam(value = "mobile", required = true) String mobile,
                                @RequestParam(value = "token", required = true) String token,
                                @RequestParam(value = "address_id", required = true) Integer addressId,
                                @RequestParam(value = "user_id", required = true) Integer userId,
                                @RequestParam(value = "user_name", required = false) String userName,
                                @RequestParam(value = "user_address", required = false) String userAddress,
                                @RequestParam(value = "area_id", required = false) Integer areaId,
                                @RequestParam(value = "is_default", required = false) Integer isDefault){
        logger.info("UserAddressController update UserAddress ,mobile:{},token:{},userAddress:{},userId:{},addressId:{},areaId:{},isDefault:{}"
                ,mobile,token,userAddress,userId,addressId,areaId,isDefault);
        try{
            if(StringUtil.isEmpty(token) || !redisClient.existsObject(token)){
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
            //verify params
            if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.MOBILE_FORMAT_ERROR));
            }
            UserAddress address = new UserAddress();
            address.setAddressId(addressId);
            UserAddress userAddress1 = userAddressService.selectByUserAddress(address);
            if(userAddress1 !=null && StringUtil.isEmpty(userAddress1.getUserId())){
                return super.setResult(StatusCode.ADDRESS_ID_NOT_EMPTY, null, StatusCode.codeMsgMap.get(StatusCode.ADDRESS_ID_NOT_EMPTY));
            }
            if(userAddress1 !=null && !userAddress1.getUserId().equals(userId)){
                return super.setResult(StatusCode.ADDRESS_ID_AND_USER_ID_MISMATCH, null, StatusCode.codeMsgMap.get(StatusCode.ADDRESS_ID_AND_USER_ID_MISMATCH));
            }
            UserAddress userAddressBean = new UserAddress();
            userAddressBean.setUpdateTime(new Date());
            userAddressBean.setUserAddress(userAddress);
            userAddressBean.setAddressId(addressId);
            userAddressBean.setUserId(userId);
            userAddressBean.setUserPhone(mobile);
            userAddressBean.setUserName(userName);
            userAddressBean.setIsDefault(isDefault);
            userAddressBean.setAreaId(areaId);
            int res = userAddressService.updateByAdressId(userAddressBean);
            if(res == 0){//重试一次
                userAddressBean.setAddressId(UuidUtils.getShortUuid());
                res = userAddressService.updateByAdressId(userAddressBean);
            }
            if(res==1){//结果返回逻辑
                logger.info("UserAddressController.update result：{} ",res);
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                logger.error("UserAddressController.update result:{} ",res);
                return super.setResult(StatusCode.UPDATE_ADDRESS_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.UPDATE_ADDRESS_FAILED));
            }
        }catch (Exception e){
            logger.error("UserAddressController.update throws Exception ",e.getMessage());
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
        logger.info("delete address params addressId:{}",addressId);
        try{
            if(StringUtil.isEmpty(token) || !redisClient.existsObject(token)){
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
            //默认地址不允许删除
            UserAddress userAddress = new UserAddress();
            userAddress.setAddressId(addressId);
            userAddressService.selectByUserAddress(userAddress);
            if(!StringUtil.isEmpty(userAddress.getIsDefault())){
                return super.setResult(StatusCode.ADDREAA_IS_NOT_ALLOWED_DELETE, null, StatusCode.codeMsgMap.get(StatusCode
                    .ADDREAA_IS_NOT_ALLOWED_DELETE));
            }
            userAddress.setDelFlag(1);
            int result = userAddressService.updateByAdressId(userAddress);
            if(result == 0){//重试一次
                result = userAddressService.updateByAdressId(userAddress);
            }
            if(result==1){//结果返回逻辑
                logger.info("UserAddressController.delete result：{} ",result);
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                logger.error("UserAddressController.delete result:{} ",result);
                return super.setResult(StatusCode.DELETE_ADDRESS_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.DELETE_ADDRESS_FAILED));
            }
        }catch (Exception e){
            logger.info("UserAddressController.delete throws Exception:{}",e.getMessage());
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
                       @RequestParam(value="user_id", required = true) int userId){
        logger.info("list address params userId:{}",userId);
        try{
            Map paramMap = new HashMap<String, Object>();
            paramMap.put("userId",userId);
            Page<UserAddress> page = super.setPageParams(pageNum,pageSize);
            page = userAddressService.getListByPage(page,paramMap);
            List<Map<String, Object>> list = page.getList();
            for (Map<String, Object> list1:list){
                if(!StringUtil.isEmpty(list1.get("areaId"))){
                    AddressVO addressVO = service.showAddress(Integer.parseInt(list1.get("areaId")+""));
                    list1.put("province", addressVO.getProvince()+"");//加空字符串 非空不报错
                    list1.put("provinceId", addressVO.getProvinceId()+"");//加空字符串 非空不报错
                    list1.put("city", addressVO.getCity()+"");
                    list1.put("cityId", addressVO.getCityId()+"");
                    list1.put("areaId", addressVO.getAreaId()+"");
                    list1.put("area", addressVO.getArea()+"");
                }
            }
            if(page.getList().size() != 0){
                return super.setResult(StatusCode.OK, page,StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.error("UserAddressController.listUseraddress throws Exception:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "detail", method = {RequestMethod.POST})
    public Result detail(@RequestParam(value="address_id", required = true) int addressId,
                       @RequestParam(value="user_id", required = true) int userId){
        logger.info("address detail params addressId:{} userId:{}",addressId,userId);
        try{
            UserAddress userAddress = new UserAddress();
            userAddress.setUserId(userId);
            userAddress.setAddressId(addressId);
            UserAddress userAddressPO = userAddressService.selectByUserAddress(userAddress);

            if(userAddressPO != null){
                AddressVO addressVO = service.showAddress(userAddressPO.getAreaId());
                if(addressVO !=null){
                    userAddressPO.setProvince(addressVO.getProvince()+"");//加空字符串 非空不报错
                    userAddressPO.setCity(addressVO.getCity()+"");
                    userAddressPO.setAreas(addressVO.getArea()+"");
                }
                return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(userAddressPO,UserAddress.class),StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.ADDRESS_IS_NOT_EXITS, null,StatusCode.codeMsgMap.get(StatusCode.ADDRESS_IS_NOT_EXITS));
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
    @RequestMapping(value = "listAddress", method = {RequestMethod.GET})
    public Result listAddress(@RequestParam(value="address_id", required = true) String addressId){
        logger.info("订单系统查询地址 addressId:{}",addressId);
        try {
            List<Map<String, Object>> userAddressList= userAddressService.selectByAddIdArray(addressId);
            if(userAddressList != null && userAddressList.size() > 0){
               return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(userAddressList,List.class,Map.class),StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.ADDRESS_IS_NOT_EXITS, null,StatusCode.codeMsgMap.get(StatusCode.ADDRESS_IS_NOT_EXITS));
            }
        }catch (Exception e){
            logger.error("订单系统查询地址 throws exception:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
}
