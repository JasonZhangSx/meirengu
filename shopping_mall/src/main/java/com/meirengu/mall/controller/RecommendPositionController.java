package com.meirengu.mall.controller;

import com.meirengu.mall.common.Constants;
import com.meirengu.mall.common.StatusCode;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.model.RecommendPosition;
import com.meirengu.mall.service.RecommendPositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 推荐位controller
 * @author 建新
 * @create 2017-01-19 19:59
 */

@Controller
@RequestMapping("rp")
public class RecommendPositionController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendPositionController.class);

    @Autowired
    private RecommendPositionService recommendPositionService;

    /**
     * 插入推荐位
     * @param isUse
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> insert(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "intro", required = false) String intro,
                                      @RequestParam(value = "class", required = false) Integer rpClass,
                                      @RequestParam(value = "display", required = false) Integer display,
                                      @RequestParam(value = "is_use", required = false) Integer isUse,
                                      @RequestParam(value = "width", required = false) Integer width,
                                      @RequestParam(value = "height", required = false) Integer height,
                                      @RequestParam(value = "recomment_num", required = false) Integer recommentNum,
                                      @RequestParam(value = "click_num", required = false) Integer clickNum,
                                      @RequestParam(value = "default_content", required = false) String defaultContent){

        if(null == name){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "name"));
        }

        if(null == intro){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "intro"));
        }

        if(null == rpClass){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "class"));
        }

        if(null == display){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "display"));
        }

        if(null == isUse){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "is_use"));
        }

        if(null == width){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "width"));
        }

        if(null == height){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "height"));
        }

        if(null == recommentNum){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "recomment_num"));
        }

        if(null == clickNum){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "click_num"));
        }

        if(null == defaultContent){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "default_content"));
        }

        RecommendPosition rp = new RecommendPosition();
        rp.setName(name);
        rp.setIntro(intro);
        rp.setRpClass(rpClass);
        rp.setDisplay(display);
        rp.setIsUse(isUse);
        rp.setWidth(width);
        rp.setHeight(height);
        rp.setRecommentNum(recommentNum);
        rp.setClickNum(clickNum);
        rp.setDefaultContent(defaultContent);
        boolean addFlag = recommendPositionService.add(rp);
        if(addFlag){
            return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
        }else {
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }
    }

    /**
     * 修改推荐位信息
     * @param rpId
     * @param name
     * @param intro
     * @param rpClass
     * @param display
     * @param isUse
     * @param width
     * @param height
     * @param recommentNum
     * @param clickNum
     * @param defaultContent
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Map<String, Object> update(@RequestParam(value = "rp_id", required = false) Integer rpId,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "intro", required = false) String intro,
                                      @RequestParam(value = "class", required = false) Integer rpClass,
                                      @RequestParam(value = "display", required = false) Integer display,
                                      @RequestParam(value = "is_use", required = false) Integer isUse,
                                      @RequestParam(value = "width", required = false) Integer width,
                                      @RequestParam(value = "height", required = false) Integer height,
                                      @RequestParam(value = "recomment_num", required = false) Integer recommentNum,
                                      @RequestParam(value = "click_num", required = false) Integer clickNum,
                                      @RequestParam(value = "default_content", required = false) String defaultContent){
        if(null == rpId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "rp_id"));
        }

        if(null == name){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "name"));
        }

        if(null == intro){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "intro"));
        }

        if(null == rpClass){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "class"));
        }

        if(null == display){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "display"));
        }

        if(null == isUse){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "is_use"));
        }

        if(null == width){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "width"));
        }

        if(null == height){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "height"));
        }

        if(null == recommentNum){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "recomment_num"));
        }

        if(null == clickNum){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "click_num"));
        }

        if(null == defaultContent){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "default_content"));
        }

        RecommendPosition rp = new RecommendPosition();
        rp.setId(rpId);
        rp.setName(name);
        rp.setIntro(intro);
        rp.setRpClass(rpClass);
        rp.setDisplay(display);
        rp.setIsUse(isUse);
        rp.setWidth(width);
        rp.setHeight(height);
        rp.setRecommentNum(recommentNum);
        rp.setClickNum(clickNum);
        rp.setDefaultContent(defaultContent);
        int addRNum = recommendPositionService.update(rp);

        if(addRNum == 1){
            return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
        }else if(addRNum == 2){
            return super.setReturnMsg(StatusCode.STATUS_4213, null, StatusCode.STATUS_4213);
        }else {
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }

    }

    /**
     * 获取推荐位详情
     * @param rpId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{rp_id}",method = RequestMethod.GET)
    public Map<String, Object> insert(@PathVariable("rp_id") Integer rpId){

        if(null == rpId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "rp_id"));
        }

        RecommendPosition rp = recommendPositionService.detail(rpId);
        if(rp == null){
            return super.setReturnMsg(StatusCode.STATUS_501, null, StatusCode.STATUS_501_MSG);
        }else{
            return super.setReturnMsg(StatusCode.STATUS_200, rp, StatusCode.STATUS_200_MSG);
        }
    }

    /**
     * 获取推荐列表
     * @param rpIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Map<String, Object> list(@RequestParam(value = "rp_ids", required = false) String rpIds){

        List<Map<String, Object>> rpList = recommendPositionService.getRpList(rpIds);
        if(rpList.size() > 0){
            return super.setReturnMsg(StatusCode.STATUS_200, rpList, StatusCode.STATUS_200_MSG);
        }else {
            return super.setReturnMsg(StatusCode.STATUS_501, null, StatusCode.STATUS_501_MSG);
        }
    }
}
