package com.meirengu.mall.controller;

import com.meirengu.mall.common.StatusCode;
import com.meirengu.mall.model.Recommend;
import com.meirengu.mall.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 推荐位controller
 *
 * @author 建新
 * @create 2017-01-19 11:33
 */
@Controller
@RequestMapping("recommend")
public class RecommendController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendController.class);

    @Autowired
    RecommendService recommendService;


    /**
     * 插入推荐内容
     * @param rpId
     * @param title
     * @param contentId
     * @param type
     * @param slideSort
     * @param clickNum
     * @param isUse
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> insert(@RequestParam(value = "rp_id", required = false) Integer rpId,
                                      @RequestParam(value = "title", required = false) String title,
                                      @RequestParam(value = "content_id", required = false) Integer contentId,
                                      @RequestParam(value = "type", required = false) Integer type,
                                      @RequestParam(value = "slide_sort", required = false) Integer slideSort,
                                      @RequestParam(value = "click_num", required = false) Integer clickNum,
                                      @RequestParam(value = "is_use", required = false) Integer isUse){

        if(null == rpId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "rp_id"));
        }

        if(null == title){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "title"));
        }

        if(null == contentId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "content_id"));
        }

        if(null == type){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "type"));
        }

        if(null == slideSort){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "slide_sort"));
        }

        if(null == clickNum){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "click_num"));
        }

        if(null == isUse){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "is_use"));
        }

        Recommend recommend = new Recommend();
        recommend.setRpId(rpId);
        recommend.setTitle(title);
        recommend.setContentId(contentId);
        recommend.setType(type);
        recommend.setCreateTime(new Date());
        recommend.setSlideSort(slideSort);
        recommend.setClickNum(clickNum);
        recommend.setIsUse(isUse);
        boolean addFlag = recommendService.add(recommend);
        if(addFlag){
            return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
        }else {
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }
    }

    /**
     * 修改推荐内容
     * @param recommendId
     * @param rpId
     * @param title
     * @param contentId
     * @param type
     * @param slideSort
     * @param clickNum
     * @param isUse
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Map<String, Object> update(@RequestParam(value = "recommend_id", required = false) Integer recommendId,
                                      @RequestParam(value = "rp_id", required = false) Integer rpId,
                                      @RequestParam(value = "title", required = false) String title,
                                      @RequestParam(value = "content_id", required = false) Integer contentId,
                                      @RequestParam(value = "type", required = false) Integer type,
                                      @RequestParam(value = "slide_sort", required = false) Integer slideSort,
                                      @RequestParam(value = "click_num", required = false) Integer clickNum,
                                      @RequestParam(value = "is_use", required = false) Integer isUse){
        if(null == recommendId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "recommend_id"));
        }

        if(null == rpId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "rp_id"));
        }

        if(null == title){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "title"));
        }

        if(null == contentId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "content_id"));
        }

        if(null == type){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "type"));
        }

        if(null == slideSort){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "slide_sort"));
        }

        if(null == clickNum){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "click_num"));
        }

        if(null == isUse){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "is_use"));
        }

        Recommend recommend = new Recommend();
        recommend.setId(recommendId);
        recommend.setRpId(rpId);
        recommend.setTitle(title);
        recommend.setContentId(contentId);
        recommend.setType(type);
        recommend.setCreateTime(new Date());
        recommend.setSlideSort(slideSort);
        recommend.setClickNum(clickNum);
        recommend.setIsUse(isUse);
        int addRNum = recommendService.update(recommend);

        if(addRNum == 1){
            return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
        }else if(addRNum == 2){
            return super.setReturnMsg(StatusCode.STATUS_4213, null, StatusCode.STATUS_4213);
        }else {
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }

    }

    /**
     * 获取推荐内容详情
     * @param recommendId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{recommend_id}",method = RequestMethod.GET)
    public Map<String, Object> insert(@PathVariable("recommend_id") Integer recommendId){

        if(null == recommendId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "recommend_id"));
        }

        Recommend recommend = recommendService.detail(recommendId);
        if(recommend == null){
            return super.setReturnMsg(StatusCode.STATUS_501, null, StatusCode.STATUS_501_MSG);
        }else{
            return super.setReturnMsg(StatusCode.STATUS_200, recommend, StatusCode.STATUS_200_MSG);
        }
    }

    /**
     * 获取推荐内容
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> list(){

        /*if(null == sort){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "case_sort"));
        }*/

        return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);

    }


}
