package com.meirengu.erp.controller.crowdfunding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.model.Item;
import com.meirengu.erp.model.ItemContent;
import com.meirengu.erp.model.ItemLevel;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * 新建list
     * @return
     */
    @RequestMapping("create_list")
    public ModelAndView itemCreateList(){
        Map<String, Object> map = new HashMap<>();

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.list"));
        url.append("?item_status=1,4&is_page=true");
        try {
            HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null){
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    map = data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/item/itemCreateList", map);
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
            /*for (String key: provinceData.keySet()){
                Map<String, Object> obj = (Map<String, Object>) provinceData.get(key);
                String provinceId = (String) obj.get("areaId");
            }*/
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
    public ModelAndView itemAdd(Item item){
        Map<String, Object> returnMap = new HashMap<>();
        return new ModelAndView("redirect:/item/to_add", returnMap);
    }

    /**
     * 项目内容回报新增
     * @param itemContent
     * @return
     */
    public ModelAndView contentAdd(ItemContent itemContent){
        Map<String, Object> returnMap = new HashMap<>();
        return new ModelAndView("redirect:/item/to_add", returnMap);
    }

    /**
     * 项目档位回报新增
     * @param itemLevel
     * @return
     */
    public ModelAndView levelAdd(ItemLevel itemLevel){
        Map<String, Object> returnMap = new HashMap<>();
        return new ModelAndView("redirect:/item/to_add", returnMap);
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
            itemUrl.append("?item_id=").append(itemId);
            List contentData = (List) httpGet(contentUrl.toString());
            StringBuffer levelUrl = new StringBuffer(ConfigUtil.getConfig("item.level.list"));
            itemUrl.append("?item_id=").append(itemId);
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

    /**
     * 待初审
     * @return
     */
    @RequestMapping("verify_list")
    public ModelAndView itemVerifyList(){
        Map<String, Object> map = new HashMap<>();

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.list"));
        url.append("?item_status=2&is_page=true");
        try {
            HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null){
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    map = data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/item/itemVerifyList", map);
    }

    /**
     * 待合作列表
     * @return
     */
    @RequestMapping("cooperate_list")
    public ModelAndView itemCooperateList(){
        Map<String, Object> map = new HashMap<>();

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.list"));
        url.append("?item_status=3&is_page=true");
        try {
            HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null){
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    map = data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/item/itemCooperateList", map);
    }

    /**
     * 待复审
     * @return
     */
    @RequestMapping("review_list")
    public ModelAndView itemReviewList(){
        Map<String, Object> map = new HashMap<>();

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.list"));
        url.append("?item_status=5,6&is_page=true");
        try {
            HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null){
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    map = data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/item/itemReviewList", map);
    }

    /**
     * 待发布
     * @return
     */
    @RequestMapping("publish_list")
    public ModelAndView itemPublishList(){
        Map<String, Object> map = new HashMap<>();

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.list"));
        url.append("?item_status=7,9&is_page=true");
        try {
            HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null){
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    map = data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/item/itemPublishList", map);
    }

    /**
     * 已发布
     * @return
     */
    @RequestMapping("published_list")
    public ModelAndView itemPublishedList(){
        Map<String, Object> map = new HashMap<>();

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.list"));
        url.append("?item_status=10,11&is_page=true");
        try {
            HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null){
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    map = data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/item/itemPublishedList", map);
    }

    /**
     * 已完成
     * @return
     */
    @RequestMapping("completed_list")
    public ModelAndView itemCompletedList(){
        Map<String, Object> map = new HashMap<>();

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.list"));
        url.append("?item_status=12&is_page=true");
        try {
            HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null){
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    map = data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/item/itemCompletedList", map);
    }

}
