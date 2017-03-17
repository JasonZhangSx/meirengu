package com.meirengu.cf.controller;

import com.meirengu.cf.model.Item;
import com.meirengu.cf.model.ItemContent;
import com.meirengu.cf.model.ItemLevel;
import com.meirengu.cf.service.ItemContentService;
import com.meirengu.cf.service.ItemLevelService;
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-16 17:54
 */
@Controller
@RequestMapping("item_content")
public class ItemContentController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemContentController.class);

    @Autowired
    private ItemContentService itemContentService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "item_id", required = false) Integer itemId,
                       @RequestParam(value = "content_type", required = false) Integer contentType,
                       @RequestParam(value = "sortby", required = false) String sortBy,
                       @RequestParam(value = "order", required = false) String order){

        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("contentType", contentType);
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<ItemContent> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = itemContentService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = itemContentService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "content_type", required = false) Integer contentType,
                         @RequestParam(value = "content_title", required = false) String contentTitle,
                         @RequestParam(value = "content_subtitle", required = false) String contentSubtitle,
                         @RequestParam(value = "content_info", required = false) String contentInfo,
                         @RequestParam(value = "content_sort", required = false) Integer contentSort,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){

        ItemContent itemContent = this.setEntity(null, itemId, contentType, contentTitle,
                contentSubtitle, contentInfo, contentSort, operateAccount, new Date());
        try {
            int insertNum = itemContentService.insert(itemContent);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_CONTENT_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_CONTENT_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> insert item content throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "{content_id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "content_id")Integer contentId){
        try {
            ItemContent itemContent = itemContentService.detail(contentId);
            return super.setResult(StatusCode.OK, itemContent, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get item content throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "{content_id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "content_id")Integer contentId){
        try {
            int deleteNum = itemContentService.delete(contentId);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_CONTENT_ERROR_DELETE, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_CONTENT_ERROR_DELETE));
            }
        }catch (Exception e){
            LOGGER.error(">> delete item content throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{content_id}", method = RequestMethod.PUT)
    public Result update(@RequestParam(value = "content_id", required = false) Integer contentId,
                         @RequestParam(value = "content_type", required = false) Integer contentType,
                         @RequestParam(value = "content_title", required = false) String contentTitle,
                         @RequestParam(value = "content_subtitle", required = false) String contentSubtitle,
                         @RequestParam(value = "content_info", required = false) String contentInfo,
                         @RequestParam(value = "content_sort", required = false) Integer contentSort,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){

        ItemContent itemContent = this.setEntity(contentId, null, contentType, contentTitle,
                contentSubtitle, contentInfo, contentSort, operateAccount, new Date());
        try {
            int updateNum = itemContentService.insert(itemContent);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_CONTENT_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_CONTENT_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> update item content throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    private ItemContent setEntity(Integer contentId, Integer itemId, Integer contentType, String contentTitle,
                                  String contentSubtitle, String contentInfo, Integer contentSort, String operateAccount, Date createTime){
        ItemContent entity = new ItemContent();
        entity.setContentId(contentId);
        entity.setItemId(itemId);
        entity.setContentType(contentType);
        entity.setContentTitle(contentTitle);
        entity.setContentSubtitle(contentSubtitle);
        entity.setContentInfo(contentInfo);
        entity.setContentSort(contentSort);
        entity.setOperateAccount(operateAccount);
        entity.setCreateTime(createTime);
        return entity;
    }
}
