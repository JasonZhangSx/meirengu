package com.meirengu.cf.controller;

import com.meirengu.cf.model.ItemLevel;
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
 * 项目回报档位控制层
 *
 * @author 建新
 * @create 2017-03-16 12:13
 */
@Controller
@RequestMapping("item_level")
public class ItemLevelController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemLevelController.class);

    @Autowired
    private ItemLevelService itemLevelService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "item_id", required = false) Integer itemId,
                       @RequestParam(value = "level_id", required = false) String levelId,
                       @RequestParam(value = "sortby", required = false) String sortBy,
                       @RequestParam(value = "order", required = false) String order){

        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("levelId", levelId);
        map.put("itemId", itemId);
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<ItemLevel> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = itemLevelService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = itemLevelService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "item_id", required = false) int itemId,
                         @RequestParam(value = "level_name", required = false) String levelName,
                         @RequestParam(value = "level_amount", required = false) BigDecimal levelAmount,
                         @RequestParam(value = "level_desc", required = false) String levelDesc,
                         @RequestParam(value = "total_number", required = false) int totalNumber,
                         @RequestParam(value = "single_limit_number", required = false) int singleLimitNumber,
                         @RequestParam(value = "payback_days", required = false) int paybackDays,
                         @RequestParam(value = "is_share_bonus", required = false) int isShareBonus,
                         @RequestParam(value = "year_rate", required = false) double yearRate,
                         @RequestParam(value = "investment_period", required = false) int investmentPeriod,
                         @RequestParam(value = "revenue_model", required = false) int revenueModel,
                         @RequestParam(value = "share_bonus_period", required = false) int shareBonusPeriod,
                         @RequestParam(value = "is_need_address", required = false) int isNeedAddress,
                         @RequestParam(value = "is_need_agreement", required = false) int isNeedAgreement,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){

        ItemLevel itemLevel = this.setEntity(0, itemId, levelName, levelAmount, levelDesc,
                totalNumber, singleLimitNumber, 0, 0, paybackDays,
                null, isShareBonus, yearRate, investmentPeriod, revenueModel,
                shareBonusPeriod, isNeedAddress, isNeedAgreement, 1, 255,
                new Date(), operateAccount);
        try {
            int insertNum = itemLevelService.insert(itemLevel);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_LEVEL_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> insert item level throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "{level_id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "level_id")int levelId){
        try {
            ItemLevel itemLevel = itemLevelService.detail(levelId);
            return super.setResult(StatusCode.OK, itemLevel, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get item detail detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "{level_id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "level_id")int levelId){
        try {
            int deleteNum = itemLevelService.delete(levelId);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_LEVEL_ERROR_DELETE, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_ERROR_DELETE));
            }
        }catch (Exception e){
            LOGGER.error(">> delete partner class detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{level_id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "level_id", required = false) int levelId,
                         @RequestParam(value = "level_name", required = false) String levelName,
                         @RequestParam(value = "level_amount", required = false) BigDecimal levelAmount,
                         @RequestParam(value = "level_desc", required = false) String levelDesc,
                         @RequestParam(value = "total_number", required = false) Integer totalNumber,
                         @RequestParam(value = "single_limit_number", required = false) Integer singleLimitNumber,
                         @RequestParam(value = "payback_days", required = false) Integer paybackDays,
                         @RequestParam(value = "is_share_bonus", required = false) Integer isShareBonus,
                         @RequestParam(value = "year_rate", required = false) Double yearRate,
                         @RequestParam(value = "investment_period", required = false) Integer investmentPeriod,
                         @RequestParam(value = "revenue_model", required = false) Integer revenueModel,
                         @RequestParam(value = "share_bonus_period", required = false) Integer shareBonusPeriod,
                         @RequestParam(value = "is_need_address", required = false) Integer isNeedAddress,
                         @RequestParam(value = "is_need_agreement", required = false) Integer isNeedAgreement,
                         @RequestParam(value = "operate_account", required = false) String operateAccount,
                         @RequestParam(value = "book_number", required = false) Integer bookNumber,
                         @RequestParam(value = "completed_number", required = false) Integer completedNumber,
                         @RequestParam(value = "level_status", required = false) Integer levelStatus,
                         @RequestParam(value = "level_sort", required = false) Integer levelSort){

       /* ItemLevel itemLevel = this.setEntity(levelId, 0, levelName, levelAmount, levelDesc,
                totalNumber, singleLimitNumber, bookNumber, completedNumber, paybackDays,
                null, isShareBonus, yearRate, investmentPeriod, revenueModel,
                shareBonusPeriod, isNeedAddress, isNeedAgreement, levelStatus, levelSort,
                new Date(), operateAccount);
        try {
            int updateNum = itemLevelService.update(itemLevel);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_LEVEL_ERROR_UPDATE, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_ERROR_UPDATE));
            }
        }catch (Exception e){
            LOGGER.error(">> update item level throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }*/

        return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));

    }

    private ItemLevel setEntity(Integer levelId, Integer itemId, String levelName, BigDecimal levelAmount, String levelDesc,
                                Integer totalNumber, Integer singleLimitNumber, Integer bookNumber, Integer completedNumber, Integer paybackDays,
                                Date paybackTime, Integer isShareBonus, double yearRate, Integer investmentPeriod, Integer revenueModel,
                                Integer shareBonusPeriod, Integer isNeedAddress, Integer isNeedAgreement, Integer levelStatus, Integer levelSort,
                                Date createTime, String operateAccount){
        ItemLevel entity = new ItemLevel();
        entity.setLevelId(levelId);
        entity.setItemId(itemId);
        entity.setLevelName(levelName);
        entity.setLevelAmount(levelAmount);
        entity.setLevelDesc(levelDesc);
        entity.setTotalNumber(totalNumber);
        entity.setSingleLimitNumber(singleLimitNumber);
        entity.setBookNumber(bookNumber);
        entity.setCompletedNumber(completedNumber);
        entity.setPaybackDays(paybackDays);
        entity.setPaybackTime(paybackTime);
        entity.setIsShareBonus(isShareBonus);
        entity.setYearRate(yearRate);
        entity.setInvestmentPeriod(investmentPeriod);
        entity.setRevenueModel(revenueModel);
        entity.setShareBonusPeriod(shareBonusPeriod);
        entity.setIsNeedAddress(isNeedAddress);
        entity.setIsNeedAgreement(isNeedAgreement);
        entity.setLevelStatus(levelStatus);
        entity.setLevelSort(levelSort);
        entity.setCreateTime(createTime);
        entity.setOperateAccount(operateAccount);
        return entity;
    }
}
