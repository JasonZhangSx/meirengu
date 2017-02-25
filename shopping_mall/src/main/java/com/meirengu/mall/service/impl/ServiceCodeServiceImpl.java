package com.meirengu.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.mall.dao.ServiceCodeDao;
import com.meirengu.mall.model.ServiceCode;
import com.meirengu.mall.service.ServiceCodeService;
import com.meirengu.mall.utils.ConfigUtil;
import com.meirengu.mall.utils.OrderSNUtils;
import com.meirengu.model.Result;
import com.meirengu.utils.DateAndTime;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务码业务
 * @author 建新
 * @create 2017-02-23 18:04
 */
@Service
public class ServiceCodeServiceImpl implements ServiceCodeService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCodeServiceImpl.class);

    @Autowired
    private ServiceCodeDao serviceCodeDao;

    @Override
    public Map<String, Object> generate(int hospitalId, String orderSN, String userPhone, int itemId) {

        Map<String, Object> resultMap = new HashMap<>();
        ServiceCode serviceCode = new ServiceCode();
        Map<String, String> params = new HashMap<>();
        params.put("itemId", String.valueOf(itemId));
        HttpUtil.HttpResult hr = HttpUtil.doGet(ConfigUtil.getValue("item.detail.url")+"?itemId="+itemId, params);
        int statusCode = hr.getStatusCode();

        try {
            if(statusCode == 200){
                String content = hr.getContent();
                JSONObject resultJson = JSON.parseObject(content);

                int hrCode = Integer.parseInt(resultJson.get("code").toString());
                //业务状态码判断
                if(hrCode == 40300){
                    JSONObject data = resultJson.get("data") == null ? null : JSONObject.parseObject(resultJson.get("data").toString());
                    if(StringUtil.isEmpty(data)){
                        return null;
                    }
                    String itemList = data.get("itemList") == null ? null : data.get("itemList").toString();
                    JSONArray array = JSONArray.parseArray(itemList);
                    if(array.size() <= 0){
                        return null;
                    }
                    JSONObject item = (JSONObject) array.get(0);
                    if(item == null){
                        return null;
                    }
                    //获取项目的有效期
                    int validityPeriods = item.get("validityPeriods") == null ? 0:(int)item.get("validityPeriods");
                    Date createTime = new Date();
                    String expireTimeTemp = DateAndTime.dateAdd("dd", createTime.toLocaleString(), validityPeriods);
                    Date expireTime = DateAndTime.convertStringToDate(expireTimeTemp);
                    serviceCode.setCreateTime(createTime);
                    serviceCode.setExpireTime(expireTime);
                    BigDecimal originalPrice = item.get("originalPrice") == null ? null :new BigDecimal(item.get("originalPrice").toString());
                    serviceCode.setOriginalPrice(originalPrice);
                    BigDecimal appointmentPrice = item.get("appointmentPrice") == null ? null : new BigDecimal(item.get("appointmentPrice").toString());
                    serviceCode.setOrderPrice(appointmentPrice);
                    BigDecimal sellingPrice = item.get("sellingPrice") == null ? null : new BigDecimal(item.get("sellingPrice").toString());
                    BigDecimal restPrice = sellingPrice.subtract(appointmentPrice);
                    serviceCode.setRestPrice(restPrice);
                    serviceCode.setOrderPrice(appointmentPrice);
                    String itemName = item.get("itemName") == null ? "" : item.get("itemName").toString();
                    serviceCode.setItemDesc(itemName);
                    String hospitalTel = item.get("hospitalTel") == null ? "" : item.get("hospitalTel").toString();
                    resultMap.put("hospitalTel", hospitalTel);
                    resultMap.put("expireTime", expireTime);
                }else {
                    LOGGER.info(">> get item detail fail. itemId is {}, return code is {}", new Object[]{itemId, hrCode});
                    return null;
                }
            }else {
                LOGGER.info(">> get item detail fail......");
                return null;
            }
            //生成服务码
            String code = OrderSNUtils.getServiceCode();

            serviceCode.setCode(code);
            serviceCode.setItemId(itemId);
            serviceCode.setHospitalId(hospitalId);
            serviceCode.setOrderSN(orderSN);
            serviceCode.setUserPhone(userPhone);
            serviceCode.setIsUse(0);

            LOGGER.info("generate service code params: {}", JSON.toJSON(serviceCode));

            int result = serviceCodeDao.insert(serviceCode);
            if(result > 0){
                resultMap.put("serviceCode", code);
                LOGGER.info(">> generate service code success......");
                return resultMap;
            }else {
                LOGGER.info(">> generate service code return: {}", result);
                return null;
            }
        } catch (Exception e){
            LOGGER.error(">> generate service code throw a exception: {}", e);
            return null;
        }

    }

    @Override
    public ServiceCode query(Map map) {
        return null;
    }

    /**
     *
     * @param code
     * @return 0抛出异常 1验证通过 2code已使用 3code不存在 4未使用的code不存在 5该医院无权限验证
     */
    @Override
    public int validateCode(String code, int hospitalId) {

        ServiceCode sc = getDetailByCode(code);
        Date currentTime = new Date();

        try {
            if(sc != null){
                if(sc.getIsUse() == 1){
                    //已使用
                    return 2;
                }
                if(sc.getHospitalId() != hospitalId){
                    //该医院无权限验证
                    return 5;
                }
                Date expireTime = sc.getExpireTime();
                int dateDiff = DateAndTime.dateDiff("ss", currentTime.toLocaleString(), expireTime.toLocaleString());
                if(dateDiff < 0){
                    //服务码过期
                    return 6;
                }
            }else {
                //code不存在
                return 3;
            }

            ServiceCode serviceCode = new ServiceCode();
            serviceCode.setIsUse(1);
            serviceCode.setCode(code);
            serviceCode.setUsingTime(currentTime);

            int result = serviceCodeDao.validateCode(serviceCode);
            if(result > 0){
                return 1;
            }else {
                //未使用的code不存在
                return 4;
            }
        }catch (Exception e){
            LOGGER.error(">> validate code throw a exception: {}", e);
            return 0;
        }

    }

    @Override
    public ServiceCode getDetailByCode(String code) {
        return serviceCodeDao.getDetailByCode(code);
    }
}
