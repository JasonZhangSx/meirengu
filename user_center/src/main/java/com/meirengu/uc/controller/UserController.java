package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 会员控制类
 *
 * @author Marvin
 * @create 2017-01-10 下午7:10
 */
@RestController
public class UserController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Map<String, Object> create(@RequestParam(required = true) String apikey, @RequestParam(required = true) String phone, int from, String ip) {
        logger.info("UserController.create params >> apikey:{}, phone:{}, from:{}, ip;{}", new Object[]{apikey, phone, from, ip});
        User user = userService.retrieveByPhone(phone);
        if (user == null){
            //auto register
            user = new User();
            user.setPhone(phone);
            user.setRegisterFrom(from);
            user.setLoginIp(ip);
            user.setLastLoginIp(ip);
            user.setLoginFrom(from);
            user.setLoginNum(1);
            int result = userService.create(user);
            logger.error("userService.create result << phone:{}, result:{}", new Object[]{phone, result});
            if (result > 0){
                return super.setReturnMsg(StatusCode.OK, user, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                logger.error("userService.create error, try create again >> phone:{}, result:{}", new Object[]{phone, result});
                userService.create(user);
            }
        }
        return super.setReturnMsg(StatusCode.OK, user, StatusCode.codeMsgMap.get(StatusCode.OK));

    }

    @RequestMapping(value = "user/{phone}", method = RequestMethod.GET)
    public Map<String, Object> retrieve(@PathVariable(required = true) String phone){
        logger.info("UserController.retrieve params >> phone:{}", phone);
        User user = userService.retrieveByPhone(phone);
        if (user != null){
            return super.setReturnMsg(StatusCode.OK, user, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setReturnMsg(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }
}
