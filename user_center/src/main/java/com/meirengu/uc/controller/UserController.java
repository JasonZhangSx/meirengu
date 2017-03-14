package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.utils.RedisUtil;
import com.meirengu.uc.vo.UserVO;
import com.meirengu.utils.StringUtil;
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

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public Map<String, Object> updateUserInfo(@RequestBody UserVO userVO) {

        if(!StringUtil.isEmpty(userVO.getToken())){
            RedisUtil redisUtil = new RedisUtil();
            boolean b = redisUtil.existsObject(userVO.getToken());
            if(b){
                    int result = userService.updateUserInfo(userVO);
                    logger.error("UserController.updateUserInfo result << {}, result:{}", result);
                    return super.setReturnMsg(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }
        }
        return super.setReturnMsg(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
    }
}
