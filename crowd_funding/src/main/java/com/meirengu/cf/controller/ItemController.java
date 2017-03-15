package com.meirengu.cf.controller;

import com.meirengu.cf.model.Item;
import com.meirengu.cf.model.ItemClass;
import com.meirengu.cf.service.ItemClassService;
import com.meirengu.cf.service.ItemService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目控制层
 *
 * @author 建新
 * @create 2017-03-13 16:09
 */
@Controller
@RequestMapping("item")
public class ItemController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    /**
     * 获取项目列表
     * @param pageSize
     * @param pageNum
     * @param isPage
     * @param flag
     * @param sortBy
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "item_name", required = false) String itemName,
                       @RequestParam(value = "flag", required = false) Integer flag,
                       @RequestParam(value = "item_status", required = false) String itemStatus,
                       @RequestParam(value = "sortby", required = false, defaultValue = "create_time") String sortBy,
                       @RequestParam(value = "order", required = false, defaultValue = "desc") String order){

        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("itemName", itemName);
        map.put("itemStatus", itemStatus);
        map.put("flag", flag);
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<Item> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = itemService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = itemService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    /**
     * 新增项目
     * @param item
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(Item item){
        try {
            int insertNum = itemService.insert(item);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> insert item throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 获取项目详情
     * @param itemId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{item_id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "item_id", required = false)int itemId){
        try {
            Item item = itemService.detail(itemId);
            return super.setResult(StatusCode.OK, item, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get item detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改项目信息
     * @param item
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(Item item){
        try {
            int updateNum = itemService.update(item);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_ERROR_UPDATE, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_ERROR_UPDATE));
            }
        }catch (Exception e){
            LOGGER.error(">> update item throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
}
