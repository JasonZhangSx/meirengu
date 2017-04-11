package com.meirengu.cf.controller;

import com.meirengu.cf.common.Constants;
import com.meirengu.cf.model.Item;
import com.meirengu.cf.service.ItemInterestedService;
import com.meirengu.cf.service.ItemOperateRecordService;
import com.meirengu.cf.service.ItemService;
import com.meirengu.cf.service.PartnerService;
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
import java.util.*;

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
    @Autowired
    ItemOperateRecordService itemOperateRecordService;
    @Autowired
    PartnerService partnerService;

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
                       @RequestParam(value = "item_id", required = false) String itemId,
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

        try {
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
        }catch (Exception e){
            LOGGER.error(">> ItemController.list throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    /**
     * 新增项目
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "item_name", required = false) String itemName,
                         @RequestParam(value = "item_profile", required = false) String itemProfile,
                         @RequestParam(value = "type_id", required = false) Integer typeId,
                         @RequestParam(value = "class_id", required = false) Integer classId,
                         @RequestParam(value = "target_amount", required = false) BigDecimal targetAmount,
                         @RequestParam(value = "preheating_days", required = false) Integer preheatingDays,
                         @RequestParam(value = "partner_id", required = false) Integer partnerId,
                         @RequestParam(value = "crowd_days", required = false) Integer crowdDays,
                         @RequestParam(value = "area_id", required = false) Integer areaId,
                         @RequestParam(value = "header_image", required = false) String headerImage,
                         @RequestParam(value = "sponsor_name", required = false) String sponsorName,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){
        try {
            Item item = setEntity(null, itemName, itemProfile, typeId, classId,
                    targetAmount, new BigDecimal(0), new BigDecimal(0),
                    preheatingDays, null, null, crowdDays,
                    null, null, partnerId, areaId, headerImage,
                    Constants.ITEM_BUILDING, Constants.STATUS_YES, 255, new Date(), null,
                    operateAccount, sponsorName);
            int insertNum = itemService.insert(item);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, item, StatusCode.codeMsgMap.get(StatusCode.OK));
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
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "item_name", required = false) String itemName,
                         @RequestParam(value = "item_profile", required = false) String itemProfile,
                         @RequestParam(value = "type_id", required = false) Integer typeId,
                         @RequestParam(value = "class_id", required = false) Integer classId,
                         @RequestParam(value = "target_amount", required = false) BigDecimal targetAmount,
                         @RequestParam(value = "appoint_amount", required = false) BigDecimal appointAmount,
                         @RequestParam(value = "completed_amount", required = false) BigDecimal completedAmount,
                         @RequestParam(value = "preheating_days", required = false) Integer preheatingDays,
                         @RequestParam(value = "preheating_start_time", required = false) Date preheatingStartTime,
                         @RequestParam(value = "preheating_end_time", required = false) Date preheatingEndTime,
                         @RequestParam(value = "partner_id", required = false) Integer partnerId,
                         @RequestParam(value = "crowd_days", required = false) Integer crowdDays,
                         @RequestParam(value = "crowd_start_time", required = false) Date crowdStartTime,
                         @RequestParam(value = "crowd_end_time", required = false) Date crowdEndTime,
                         @RequestParam(value = "area_id", required = false) Integer areaId,
                         @RequestParam(value = "header_image", required = false) String headerImage,
                         @RequestParam(value = "sponsor_name", required = false) String sponsorName,
                         @RequestParam(value = "item_sort", required = false) Integer itemSort,
                         @RequestParam(value = "item_status", required = false) Integer itemStatus,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){
        try {
            if(StringUtil.isEmpty(itemId)){
                return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
            Item item = setEntity(itemId, itemName, itemProfile, typeId, classId,
                    targetAmount, appointAmount, completedAmount,
                    preheatingDays, preheatingStartTime, preheatingEndTime, crowdDays,
                    crowdStartTime, crowdEndTime, partnerId, areaId, headerImage,
                    itemStatus, null, itemSort, null, null,
                    operateAccount, sponsorName);
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


    /**
     * 失效订单将已筹集金额减回去
     * @param itemId
     * @param levelId
     * @param levelAmount
     * @param itemNum
     * @param totalAmount
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "level_rollback", method = RequestMethod.POST)
    public Result levelRollback(@RequestParam(value = "item_id", required = false) Integer itemId,
                           @RequestParam(value = "level_id", required = false) Integer levelId,
                           @RequestParam(value = "level_amount", required = false) BigDecimal levelAmount,
                           @RequestParam(value = "item_num", required = false) Integer itemNum,
                           @RequestParam(value = "total_amount", required = false) BigDecimal totalAmount){
        try {
            int code = itemService.levelRollback(itemId, levelAmount, levelId, itemNum, totalAmount);
            return super.setResult(code, null, StatusCode.codeMsgMap.get(code));
        }catch (Exception e){
            LOGGER.error(">>ItemController.levelRollback throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 初审
     * @param itemId
     * @param operateStatus
     * @param operateRemark
     * @param operateAccount
     * @return
     */
    @RequestMapping(value = "verify", method = RequestMethod.POST)
    @ResponseBody
    public Result verify(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "operate_status", required = false) Integer operateStatus,
                         @RequestParam(value = "operate_remark", required = false) String operateRemark,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){
        if(itemId == null || operateStatus == null ||
                StringUtil.isEmpty(operateRemark) || StringUtil.isEmpty(operateAccount)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        if(itemId == 0 || (operateStatus != 1 && operateStatus != 0)){
            return super.setResult(StatusCode.INVALID_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }

        try {
            itemService.verify(itemId, operateStatus, operateRemark, operateAccount);
            return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">>ItemOperateRecordController.verify throw exception:{}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 复审
     * @param itemId
     * @param operateStatus
     * @param operateRemark
     * @param operateAccount
     * @return
     */
    @RequestMapping(value = "review", method = RequestMethod.POST)
    @ResponseBody
    public Result review(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "operate_status", required = false) Integer operateStatus,
                         @RequestParam(value = "operate_remark", required = false) String operateRemark,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){
        if(itemId == null || operateStatus == null ||
                StringUtil.isEmpty(operateRemark) || StringUtil.isEmpty(operateAccount)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        if(itemId == 0 || (operateStatus != 1 && operateStatus != 0)){
            return super.setResult(StatusCode.INVALID_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }

        try {
            itemService.review(itemId, operateStatus, operateRemark, operateAccount);
            return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">>ItemOperateRecordController.review throw exception:{}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 根据项目id返回收益权转让协议内容
     * @param itemId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "agreement", method = RequestMethod.GET)
    public Result getAgreementContent(@RequestParam(value = "item_id", required = false) Integer itemId,
                                      @RequestParam(value = "level_id", required = false) Integer levelId){

        try {
            Map<String, Object> map = partnerService.getAgreementContent(itemId, levelId);
            return super.setResult(StatusCode.OK, map, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> PartnerController.getAgreementContent throw exception:{}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    public Item setEntity(Integer itemId, String itemName, String itemProfile, Integer typeId, Integer classId,
                          BigDecimal targetAmount, BigDecimal appointAmount, BigDecimal completedAmount,
                          Integer preheatingDays, Date preheatingStartTime, Date preheatingEndTime, Integer crowdDays,
                          Date crowdStartTime, Date crowdEndTime, Integer partnerId, Integer areaId, String headerImage,
                          Integer itemStatus, Integer flag, Integer itemSort, Date createTime, Date updateTime,
                          String operateAccount, String sponsorName) {
        Item item = new Item();

        item.setItemId(itemId);
        item.setItemName(itemName);
        item.setItemProfile(itemProfile);
        item.setTypeId(typeId);
        item.setClassId(classId);
        item.setTargetAmount(targetAmount);
        item.setAppointAmount(appointAmount);
        item.setCompletedAmount(completedAmount);
        item.setPreheatingDays(preheatingDays);
        item.setPreheatingStartTime(preheatingStartTime);
        item.setPreheatingEndTime(preheatingEndTime);
        item.setCrowdDays(crowdDays);
        item.setCrowdStartTime(crowdStartTime);
        item.setCrowdEndTime(crowdEndTime);
        item.setPartnerId(partnerId);
        item.setAreaId(areaId);
        item.setHeaderImage(headerImage);
        item.setItemStatus(itemStatus);
        item.setFlag(flag);
        item.setItemSort(itemSort);
        item.setCreateTime(createTime);
        item.setUpdateTime(updateTime);
        item.setOperateAccount(operateAccount);
        item.setSponsorName(sponsorName);

        return item;
    }

}
