package com.meirengu.erp.controller.crowdfunding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.model.Item;
import com.meirengu.erp.model.ItemContent;
import com.meirengu.erp.model.ItemLevel;
import com.meirengu.erp.service.ItemService;
import com.meirengu.erp.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目模块控制层
 * @author 建新
 * @create 2017-03-25 12:31
 */
@RestController
@RequestMapping("item")
public class ItemController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    /**
     * 新建list
     * @return
     */
    @RequestMapping("create_list")
    public ModelAndView itemCreateList(Integer itemId, String itemName){

        Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "1,4");

        return new ModelAndView("/item/itemCreateList", map);
    }

    /**
     * 待初审
     * @return
     */
    @RequestMapping("verify_list")
    public ModelAndView itemVerifyList(Integer itemId, String itemName){

        Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "2");

        return new ModelAndView("/item/itemVerifyList", map);
    }

    /**
     * 待合作列表
     * @return
     */
    @RequestMapping("cooperate_list")
    public ModelAndView itemCooperateList(Integer itemId, String itemName){

        Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "3");

        return new ModelAndView("/item/itemCooperateList", map);
    }

    /**
     * 待复审
     * @return
     */
    @RequestMapping("review_list")
    public ModelAndView itemReviewList(Integer itemId, String itemName){

        Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "5,6");

        return new ModelAndView("/item/itemReviewList", map);
    }

    /**
     * 待发布
     * @return
     */
    @RequestMapping("publish_list")
    public ModelAndView itemPublishList(Integer itemId, String itemName){

        Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "7,9");

        return new ModelAndView("/item/itemPublishList", map);
    }

    /**
     * 已发布
     * @return
     */
    @RequestMapping("published_list")
    public ModelAndView itemPublishedList(Integer itemId, String itemName){

        Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "10,11");

        return new ModelAndView("/item/itemPublishedList", map);
    }

    /**
     * 已完成
     * @return
     */
    @RequestMapping("completed_list")
    public ModelAndView itemCompletedList(Integer itemId, String itemName){

        Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "12");

        return new ModelAndView("/item/itemCompletedList", map);
    }

    /**
     * 跳转到项目新增页面
     * @return
     */
    @RequestMapping("to_add")
    public ModelAndView toItemAdd(){
        Map<String, Object> returnMap = new HashMap<>();
        try {
            List itemClassData = (List) httpGet(ConfigUtil.getConfig("item.class.list"));
            List typeData = (List) httpGet(ConfigUtil.getConfig("type.list"));
            List partnerData = (List) httpGet(ConfigUtil.getConfig("partner.list"));
            List provinceData = (List) httpGet(ConfigUtil.getConfig("address.province.list"));
            returnMap.put("itemClass", itemClassData);
            returnMap.put("type", typeData);
            returnMap.put("partner", partnerData);
            returnMap.put("provinces", provinceData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/item/itemAdd", returnMap);
    }

    /**
     * 项目基本信息新增
     * @param item
     * @return
     */
    @RequestMapping("save")
    public Map itemSave(Item item){
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("item_name", item.getItemName());
        params.put("item_profile", item.getItemProfile());
        params.put("type_id", String.valueOf(item.getTypeId()));
        params.put("class_id", String.valueOf(item.getClassId()));
        params.put("target_amount", String.valueOf(item.getTargetAmount()));
        params.put("preheating_days", String.valueOf(item.getPreheatingDays()));
        params.put("partner_id", String.valueOf(item.getPartnerId()));
        params.put("crowd_days", String.valueOf(item.getCrowdDays()));
        params.put("area_id", "2");
        params.put("header_image", item.getHeaderImage());
        params.put("operate_account", "jason");
        params.put("sponsor_name","jason");

        try {

            if(item.getItemId() == null || item.getItemId() == 0){
                Object itemResult = itemService.itemInsert(params);
                if(itemResult != null){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", itemResult);
                }else {
                    returnMap.put("code", StatusCode.ITEM_ERROR_INSERT);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_ERROR_INSERT));
                }
            }else {
                params.put("item_id", String.valueOf(item.getItemId()));
                if(itemService.contentUpdate(params)){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", item);
                }else {
                    returnMap.put("code", StatusCode.ITEM_ERROR_UPDATE);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_ERROR_UPDATE));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnMap;
    }

    /**
     * 项目内容回报新增/修改
     * @param itemContent
     * @return
     */
    @RequestMapping("content/save")
    public Map contentAdd(ItemContent itemContent){
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("item_id", String.valueOf(itemContent.getItemId()));
        params.put("content_title", itemContent.getContentTitle());
        params.put("content_sort", "255");
        params.put("content_subtitle", "");
        params.put("content_type", String.valueOf(itemContent.getContentType()));
        params.put("content_info", itemContent.getContentInfo());
        params.put("operate_account", "jason");

        try {
            //contentId存在则为修改，不存在则为新增
            if(itemContent.getContentId() == null || itemContent.getContentId() == 0){
                Object contentResult = itemService.contentInsert(params);
                if(contentResult != null){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", contentResult);
                }else {
                    returnMap.put("code", StatusCode.ITEM_CONTENT_ERROR_INSERT);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_CONTENT_ERROR_INSERT));
                }
            }else {
                params.put("content_id", String.valueOf(itemContent.getContentId()));
                if(itemService.contentUpdate(params)){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", itemContent);
                }else {
                    returnMap.put("code", StatusCode.ITEM_CONTENT_ERROR_UPDATE);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_CONTENT_ERROR_UPDATE));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return returnMap;
    }

    /**
     * 项目档位回报新增/修改
     * @param itemLevel
     * @return
     */
    @RequestMapping("level/save")
    public Map levelAdd(ItemLevel itemLevel){
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("item_id", String.valueOf(itemLevel.getItemId()));
        params.put("level_name", itemLevel.getLevelName());
        params.put("level_amount", String.valueOf(itemLevel.getLevelAmount()));
        params.put("level_desc", itemLevel.getLevelDesc());
        params.put("total_number", String.valueOf(itemLevel.getTotalNumber()));
        params.put("single_limit_number", String.valueOf(itemLevel.getSingleLimitNumber()));
        params.put("payback_days", String.valueOf(itemLevel.getPaybackDays()));
        params.put("is_share_bonus", String.valueOf(itemLevel.getIsShareBonus()));
        params.put("year_rate", String.valueOf(itemLevel.getYearRate()));
        params.put("investment_period", String.valueOf(itemLevel.getInvestmentPeriod()));
        params.put("revenue_model", String.valueOf(itemLevel.getRevenueModel()));
        params.put("share_bonus_period", String.valueOf(itemLevel.getShareBonusPeriod()));
        params.put("is_need_address", String.valueOf(itemLevel.getIsNeedAddress()));
        params.put("is_need_agreement", String.valueOf(itemLevel.getIsNeedAgreement()));
        params.put("operate_account", "jason");
        try {
            if(itemLevel.getLevelId() == 0 || itemLevel.getLevelId() == null){
                Object levelResult = itemService.levelInsert(params);
                if(levelResult != null){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", levelResult);
                }else {
                    returnMap.put("code", StatusCode.ITEM_LEVEL_ERROR_INSERT);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_ERROR_INSERT));
                }
            }else {
                params.put("level_id", String.valueOf(itemLevel.getLevelId()));
                if(itemService.levelUpdate(params)){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", itemLevel);
                }else {
                    returnMap.put("code", StatusCode.ITEM_LEVEL_ERROR_UPDATE);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_ERROR_UPDATE));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return returnMap;
    }

    @RequestMapping("to_edit")
    public ModelAndView toItemEdit(Integer itemId){
        Map<String, Object> returnMap = new HashMap<>();
        try {
            List itemClassData = (List) httpGet(ConfigUtil.getConfig("item.class.list"));
            List typeData = (List) httpGet(ConfigUtil.getConfig("type.list"));
            List partnerData = (List) httpGet(ConfigUtil.getConfig("partner.list"));
            List provinceData = (List) httpGet(ConfigUtil.getConfig("address.province.list"));
            StringBuffer itemUrl = new StringBuffer(ConfigUtil.getConfig("item.list"));
            itemUrl.append("/").append(itemId);
            JSONObject itemJson = (JSONObject) httpGet(itemUrl.toString());
            Item item = JSON.parseObject(itemJson.toJSONString(), Item.class);
            StringBuffer contentUrl = new StringBuffer(ConfigUtil.getConfig("item.content.list"));
            contentUrl.append("?item_id=").append(itemId);
            List contentData = (List) httpGet(contentUrl.toString());
            StringBuffer levelUrl = new StringBuffer(ConfigUtil.getConfig("item.level.list"));
            levelUrl.append("?item_id=").append(itemId);
            List levelData = (List) httpGet(levelUrl.toString());
            returnMap.put("itemClass", itemClassData);
            returnMap.put("type", typeData);
            returnMap.put("partner", partnerData);
            returnMap.put("provinces", provinceData);
            returnMap.put("item", item);
            returnMap.put("content", contentData);
            returnMap.put("level", levelData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/item/itemEdit", returnMap);
    }

    @RequestMapping(value = "verify", method = RequestMethod.POST)
    public Map verify(Integer itemId){
        Map<String, Object> returnMap = new HashMap<>();
        if(itemService.verify(itemId)){
            returnMap.put("code", StatusCode.OK);
            returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            returnMap.put("code", StatusCode.INTERNAL_SERVER_ERROR);
            returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

        return returnMap;
    }
}
