package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.service.VerityService;
import com.meirengu.uc.utils.RedisUtil;
import com.meirengu.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by huoyan403 on 3/24/2017.
 */
@RestController
public class VerifyController extends BaseController {

    @Autowired
    private VerityService verityService;
    @Autowired
    UserService userService;

    //验证实名绑定卡到哪一阶段了
    @RequestMapping(method = {RequestMethod.PUT})
    public Result update(@RequestParam(value = "user_id", required = false)Integer userId,
                         @RequestParam(value = "token", required = false)String token) {
        //判断有无token
        if(!StringUtil.isEmpty(token)){
            //判断token是否有效
                RedisUtil redisUtil = new RedisUtil();
                Object userRedis =   redisUtil.getObject(token);
                if(StringUtil.isEmpty(userRedis)){
                    return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
                }
        }
        User user = userService.retrieveByUserId(userId);
        if(user==null){
            return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
        }
        if(StringUtil.isEmpty(user.getIdCard())){
            return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
        }
        if(StringUtil.isEmpty(user.getBankIdCard())){
            return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
        }
        Map<String,Object> map = verityService.selectPayAccountByUserId(userId);
        if(!StringUtil.isEmpty(map) && StringUtil.isEmpty(map.get("password"))){
            return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
        }
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
    }


    }
