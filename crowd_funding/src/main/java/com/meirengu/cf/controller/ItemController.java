package com.meirengu.cf.controller;

import com.meirengu.cf.model.Item;
import com.meirengu.cf.service.ItemInterestedService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Autowired
    ItemInterestedService itemInterestedService;

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
                       @RequestParam(value = "item_id", required = false) Integer itemId,
                       @RequestParam(value = "item_name", required = false) String itemName,
                       @RequestParam(value = "flag", required = false) Integer flag,
                       @RequestParam(value = "item_status", required = false) String itemStatus,
                       @RequestParam(value = "sortby", required = false) String sortBy,
                       @RequestParam(value = "order", required = false) String order){
        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("itemName", itemName);
        map.put("itemId", itemId);
        map.put("itemStatus", itemStatus);
        map.put("flag", flag);
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<Item> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = itemService.getListByPage(page, map);
            List<Map<String, Object>> list = page.getList();
            List<Map<String, Object>> resultList = new ArrayList<>();
            for (Map<String, Object> resultMap : list){
                resultMap.put("privince", "北京市");
                resultMap.put("city", "朝阳区");
                resultList.add(resultMap);
            }
            page.setList(resultList);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = itemService.getList(map);
            List<Map<String, Object>> resultList = new ArrayList<>();
            for (Map<String, Object> resultMap : list){
                resultMap.put("privince", "北京市");
                resultMap.put("city", "朝阳区");
                resultList.add(resultMap);
            }
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
    public Result detail(@PathVariable(value = "item_id", required = false)int itemId,
                         @RequestParam(value = "user_id", required = false)Integer userId){
        try {
            Map<String, Object> beanMap = itemService.moreDetail(itemId);
            if(!StringUtil.isEmpty(userId) && userId != 0 && beanMap != null){
                //是否感兴趣
                boolean b = itemInterestedService.isBeInterested(itemId, userId);
                beanMap.put("isInterested", b);
            }else {
                beanMap.put("isInterested", null);
            }
            if(beanMap != null){
                beanMap.put("privince", "北京市");
                beanMap.put("city", "朝阳区");
            }

            return super.setResult(StatusCode.OK, beanMap, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get item cooperation detail throw exception: {}", e);
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
            LOGGER.error(">> update item cooperation throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{item_id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "item_id") int itemId){
        try {
            int deleteNum = itemService.delete(itemId);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_ERROR_DELETE, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_ERROR_DELETE));
            }
        }catch (Exception e){
            LOGGER.error(">> delete item cooperation throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改已筹总金额/预约总金额
     * @param type  1已筹 2预约
     * @param itemId
     * @param levelId
     * @param levelAmount
     * @param itemNum
     * @param totalAmount
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "change_amount", method = RequestMethod.POST)
    public Result changeAmount(@RequestParam(value = "type", required = false) Integer type,
                    @RequestParam(value = "item_id", required = false) Integer itemId,
                    @RequestParam(value = "level_id", required = false) Integer levelId,
                    @RequestParam(value = "level_amount", required = false) BigDecimal levelAmount,
                    @RequestParam(value = "item_num", required = false) Integer itemNum,
                    @RequestParam(value = "total_amount", required = false) BigDecimal totalAmount){
        //0 失败  1 成功  2  此时状态为已约满  3 此时状态为已完成  4 档位信息为空  5 参数金额和档位金额不同
        //6 itemNum大于可预约数  7 itemNum大于可购买数  8 预约金额和购买金额不为空  9 总金额不等于单项金额和项目数量的乘积
        if(type == null || itemId == null || levelId == null ||
                levelAmount == null || itemNum == null || totalAmount == null){
            return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        int flag = 0;
        try {
            if(type == 1){
                flag = itemService.changeAmount(itemId, levelAmount, levelId, itemNum, null, totalAmount);
            }else if(type == 2){
                flag = itemService.changeAmount(itemId, levelAmount, levelId, itemNum, totalAmount, null);
            }else {
                return super.setResult(StatusCode.INVALID_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
            }

            switch (flag) {
                case 1:
                    return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
                case 2:
                    return super.setResult(StatusCode.ITEM_LEVEL_APPOINT_FULL, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_APPOINT_FULL));
                case 3:
                    return super.setResult(StatusCode.ITEM_LEVEL_COMPLETED, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_COMPLETED));
                case 4:
                    return super.setResult(StatusCode.ITEM_LEVEL_NULL, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_NULL));
                case 5:
                    return super.setResult(StatusCode.ITEM_LEVEL_AMOUNT_NOT_MATCH, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_AMOUNT_NOT_MATCH));
                case 6:
                    return super.setResult(StatusCode.ITEM_APPOINT_NUM_NOT_ENOUGH, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_APPOINT_NUM_NOT_ENOUGH));
                case 7:
                    return super.setResult(StatusCode.ITEM_BUY_NUM_NOT_ENOUGH, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_BUY_NUM_NOT_ENOUGH));
                case 8:
                    return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
                case 9:
                    return super.setResult(StatusCode.ITEM_LEVEL_TOTAL_AMOUNT_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_TOTAL_AMOUNT_ERROR));
                default:
                    return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }

        }catch (Exception e){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
}
