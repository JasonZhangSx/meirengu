package com.meirengu.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.mall.dao.ServiceCodeDao;
import com.meirengu.mall.model.ServiceCode;
import com.meirengu.mall.service.ServiceCodeService;
import com.meirengu.mall.utils.ConfigUtil;
import com.meirengu.mall.utils.OrderSNUtils;
import com.meirengu.model.Result;
import com.meirengu.utils.DateAndTime;
import com.meirengu.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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
    public String create(int hospitalId, String orderSN, String userPhone, String itemDesc,
                         double originalPrice, double orderPrice, double restPrice, int itemId) {

        ServiceCode serviceCode = new ServiceCode();
        Map<String, String> params = new HashMap<>();
        params.put("itemId", String.valueOf(itemId));
        HttpUtil.HttpResult hr = HttpUtil.doGet(ConfigUtil.getValue("item.detail.url"), params);
        int statusCode = hr.getStatusCode();

        try {
            if(statusCode == 200){
                String content = hr.getContent();
                JSONObject resultJson = (JSONObject) JSON.parseObject(content);

                int hrCode = (int)resultJson.get("code");
                //业务状态码判断
                if(hrCode == 200){
                    //获取项目的有效期
                    int validityPeriods = resultJson.get("validityPeriods") == null ? 0:(int)resultJson.get("validityPeriods");
                    Date createTime = new Date();
                    String expireTimeTemp = DateAndTime.dateAdd("dd", createTime.toString(), validityPeriods);
                    Date expireTime = new Date(expireTimeTemp);
                    serviceCode.setCreateTime(createTime);
                    serviceCode.setExpireTime(expireTime);
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

            serviceCode.setCode(Integer.parseInt(code));
            serviceCode.setItemId(itemId);
            serviceCode.setHospitalId(hospitalId);
            serviceCode.setOrderSN(orderSN);
            serviceCode.setUserPhone(userPhone);
            serviceCode.setItemDesc(itemDesc);
            serviceCode.setOriginalPrice(originalPrice);
            serviceCode.setOrderPrice(orderPrice);
            serviceCode.setRestPrice(restPrice);
            serviceCode.setIs_use(0);

            LOGGER.info("create service code params: {}", JSON.toJSON(serviceCode));

            int result = serviceCodeDao.insert(serviceCode);
            if(result > 1){
                LOGGER.info(">> create service code success......");
                return code;
            }else {
                LOGGER.info(">> create service code return: {}", result);
                return null;
            }
        } catch (Exception e){
            LOGGER.error(">> create service code throw a exception: {}", e);
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
    public int validateCode(int code, int hospitalId) {

        ServiceCode sc = getDetailByCode(code);
        Date currentTime = new Date();

        try {
            if(sc != null){
                if(sc.getIs_use() == 1){
                    //已使用
                    return 2;
                }
                if(sc.getHospitalId() != hospitalId){
                    //该医院无权限验证
                    return 5;
                }
                Date expireTime = sc.getExpireTime();
                int dateDiff = DateAndTime.dateDiff("ss", expireTime.toString(), currentTime.toString());
                if(dateDiff < 0){
                    //服务码过期
                    return 6;
                }
            }else {
                //code不存在
                return 3;
            }

            ServiceCode serviceCode = new ServiceCode();
            serviceCode.setIs_use(1);
            serviceCode.setCode(code);
            serviceCode.setUsingTime(currentTime);

            int result = serviceCodeDao.validateCode(serviceCode);
            if(result > 1){
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
    public ServiceCode getDetailByCode(int code) {
        return serviceCodeDao.getDetailByCode(code);
    }
}
