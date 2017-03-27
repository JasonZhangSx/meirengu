package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * app需要的系统控制层
 *
 * @author 建新
 * @create 2017-03-27 16:52
 */
@RestController
@RequestMapping("sys")
public class SystemController extends BaseController{

    private static Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @ResponseBody
    @RequestMapping("time")
    public Result getLocalTime(){
        return super.setResult(StatusCode.OK, System.currentTimeMillis(), StatusCode.codeMsgMap.get(StatusCode.OK));
    }
}
