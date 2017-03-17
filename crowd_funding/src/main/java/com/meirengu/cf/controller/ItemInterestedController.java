package com.meirengu.cf.controller;

import com.meirengu.cf.common.Constants;
import com.meirengu.cf.model.ItemInterested;
import com.meirengu.cf.service.ItemInterestedService;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
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
 * 感兴趣项目列表
 * @author 建新
 * @create 2017-03-17 12:05
 */
@Controller
@RequestMapping("item_interested")
public class ItemInterestedController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemInterestedController.class);

    @Autowired
    private ItemInterestedService itemInterestedService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "item_id", required = false) Integer itemId,
                       @RequestParam(value = "user_id", required = false) Integer userId,
                       @RequestParam(value = "user_phone", required = false) String userPhone,
                       @RequestParam(value = "status", required = false) Integer status,
                       @RequestParam(value = "sortby", required = false) String sortBy,
                       @RequestParam(value = "order", required = false) String order){

        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("userId", userId);
        map.put("userPhone", userPhone);
        map.put("status", status);
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<ItemInterested> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = itemInterestedService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = itemInterestedService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    @ResponseBody
    @RequestMapping(value = "be", method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "user_id", required = false) Integer userId,
                         @RequestParam(value = "user_name", required = false) String userName,
                         @RequestParam(value = "user_phone", required = false) String userPhone){
        if(itemId == null || itemId == 0 || userId == null || userId == 0 || StringUtil.isEmpty(userName) || StringUtil.isEmpty(userPhone)){
            return super.setResult(StatusCode.INVALID_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }
        try {
            boolean isInterested = itemInterestedService.isBeInterested(itemId, userId);
            if(isInterested){
                return super.setResult(StatusCode.ITEM_BE_INTERESTED, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_BE_INTERESTED));
            }
            ItemInterested itemInterested = this.setEntity(null, itemId, userId, userName, userPhone, Constants.BE_INTERESTED, new Date());
            int insertNum = itemInterestedService.insert(itemInterested);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_INTERESTED_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_INTERESTED_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> insert item interested throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "id")Integer id){
        try {
            ItemInterested itemInterested = itemInterestedService.detail(id);
            return super.setResult(StatusCode.OK, itemInterested, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get item interested throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "cancle", method = RequestMethod.POST)
    public Result delete(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "user_id", required = false) Integer userId){
        if(itemId == null || itemId == 0 || userId == null || userId == 0){
            return super.setResult(StatusCode.INVALID_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }
        try {
            boolean isInterested = itemInterestedService.isBeInterested(itemId, userId);
            if(!isInterested){
                return super.setResult(StatusCode.ITEM_NOT_BE_INTERESTED, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_NOT_BE_INTERESTED));
            }
            ItemInterested itemInterested = this.setEntity(null, itemId, userId, null, null, null, null);
            int deleteNum = itemInterestedService.cancle(itemInterested);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_INTERESTED_ERROR_DELETE, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_INTERESTED_ERROR_DELETE));
            }
        }catch (Exception e){
            LOGGER.error(">> set item not interested throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    private ItemInterested setEntity(Integer id, Integer itemId, Integer userId, String userName, String userPhone, Integer status, Date createTime){
        ItemInterested entity = new ItemInterested();
        entity.setId(id);
        entity.setItemId(itemId);
        entity.setUserId(userId);
        entity.setUserPhone(userPhone);
        entity.setUserName(userName);
        entity.setStatus(status);
        entity.setCreateTime(createTime);
        return entity;
    }

}
