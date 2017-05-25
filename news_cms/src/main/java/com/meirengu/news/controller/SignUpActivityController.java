package com.meirengu.news.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.news.model.SignUpActivity;
import com.meirengu.news.model.VersionUpgrade;
import com.meirengu.news.service.SignUpActivityService;
import com.meirengu.news.service.VersionUpgradeService;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报名活动controller
 * @author 建新
 * @create 2017-05-24 14:50
 */
@Controller
@RequestMapping("activity/signup")
public class SignUpActivityController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(SignUpActivityController.class);

    @Autowired
    private SignUpActivityService signUpActivityService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "telphone", required = false) String telphone,
                       @RequestParam(value = "city", required = false) String city,
                       @RequestParam(value = "type", required = false) Integer type){
        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("telphone", telphone);
        map.put("city", city);
        map.put("type", type);

        try {
            if(isPage){
                Page<SignUpActivity> page = new Page<>();
                page.setPageNow(pageNum);
                page.setPageSize(pageSize);
                page = signUpActivityService.getListByPage(page, map);
                return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                List<Map<String, Object>> list = signUpActivityService.getList(map);
                return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
            }
        }catch (Exception e){
            logger.error(">> SignUpActivityController.list throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "telphone", required = false) String telphone,
                         @RequestParam(value = "city", required = false) String city,
                         @RequestParam(value = "type", required = false) Integer type){

        SignUpActivity activity = new SignUpActivity();
        activity.setTelphone(telphone);
        activity.setName(name);
        activity.setCity(city);
        activity.setType(type);

        try {
            //先查重
            int repeatNum = signUpActivityService.check(telphone, type);
            if(repeatNum > 0){
                return super.setResult(StatusCode.TELPHONE_EXIST, "", StatusCode.codeMsgMap.get(StatusCode.TELPHONE_EXIST));
            }

            signUpActivityService.insert(activity);
            return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error(">> SignUpActivityController.insert throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

}
