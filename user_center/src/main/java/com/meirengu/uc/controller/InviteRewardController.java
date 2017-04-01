package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huoyan403 on 4/1/2017.
 */
@RestController
@RequestMapping("inviteReward")
public class InviteRewardController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(InviteRewardController.class);

    @RequestMapping(value = "notify",method = RequestMethod.GET)
    public Result showProvinceList(HttpServletRequest request, HttpServletResponse respons) {
        try {
            return setResult(StatusCode.OK,null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.info("AddressController.redis get token result:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

}
