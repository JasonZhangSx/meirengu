package com.meirengu.news.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.news.service.SysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-04-06 16:02
 */
@RestController
@RequestMapping("sys")
public class SysController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysController.class);

    @Autowired
    SysService sysService;

    @ResponseBody
    @RequestMapping("app_init")
    public Result appInit(@RequestParam(value = "app_id", required = false) Integer appId,
                          @RequestParam(value = "app_channel", required = false) Integer appChannel,
                          @RequestParam(value = "status", required = false) Integer status){

        if(appChannel == null || appId == null || status == null || appChannel == 0 || appId == 0){
            return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        try {
            Map<String, Object> map = sysService.initApp(appId, appChannel, status);
            return super.setResult(StatusCode.OK, map, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">>SysController.appInit throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
}
