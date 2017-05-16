package com.meirengu.cf.controller;

import com.meirengu.cf.common.Constants;
import com.meirengu.cf.model.Item;
import com.meirengu.cf.model.ItemLevel;
import com.meirengu.cf.service.ItemLevelService;
import com.meirengu.cf.service.ItemService;
import com.meirengu.cf.utils.ConfigUtil;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.utils.ObjectUtils;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

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
    @Autowired
    private ItemService itemService;

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
                return super.setResult(StatusCode.OK, itemLevel, StatusCode.codeMsgMap.get(StatusCode.OK));
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
        Map<String, Object> map;
        try {
            ItemLevel itemLevel = itemLevelService.detail(levelId);
            if(itemLevel == null){
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, "", StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
            map = ObjectUtils.bean2Map(itemLevel);
            int itemId = itemLevel.getItemId();
            Item item = itemService.detail(itemId);
            if(item != null){
                map.put("partnerId", item.getPartnerId());
                map.put("itemName", item.getItemName());
                map.put("typeId", item.getTypeId());
                String agreementNames = "";
                String agreementUrls = "";
                //根据不同的行业分类获取不同的协议地址
                if(item.getTypeId() == Constants.TYPE_PRODUCT){
                    map.put("agreement1", ConfigUtil.getConfig("type.product.agreement1"));
                    map.put("agreement2", ConfigUtil.getConfig("type.product.agreement2"));
                    agreementNames = ConfigUtil.getConfig("type.product.agreement.name");
                    agreementUrls = ConfigUtil.getConfig("type.product.agreement.url");
                }else if(item.getTypeId() == Constants.TYPE_PROFIT){
                    map.put("agreement1", ConfigUtil.getConfig("type.profile.agreement1"));
                    map.put("agreement2", ConfigUtil.getConfig("type.profile.agreement2"));
                    agreementNames = ConfigUtil.getConfig("type.profile.agreement.name");
                    agreementUrls = ConfigUtil.getConfig("type.profile.agreement.url");
                }else if(item.getTypeId() == Constants.TYPE_SHARES){
                    map.put("agreement1", ConfigUtil.getConfig("type.shares.agreement1"));
                    map.put("agreement2", ConfigUtil.getConfig("type.shares.agreement2"));
                    agreementNames = ConfigUtil.getConfig("type.shares.agreement.name");
                    agreementUrls = ConfigUtil.getConfig("type.shares.agreement.url");
                }

                List<Map<String, Object>> agreementList = new ArrayList<>();
                if(!StringUtil.isEmpty(agreementNames) && !StringUtil.isEmpty(agreementUrls)){
                    String[] agreementName = agreementNames.split(",");
                    String[] agreementUrl = agreementUrls.split(",");
                    for (int i = 0 ; i < agreementName.length; i++){
                        Map<String, Object> agreementMap = new HashMap<>();
                        agreementMap.put("agreementName",agreementName[i]);
                        agreementMap.put("agreementUrl", agreementUrl[i]);
                        agreementList.add(agreementMap);
                    }
                }

                map.put("agreements", agreementList);
            }



            return super.setResult(StatusCode.OK, map, StatusCode.codeMsgMap.get(StatusCode.OK));
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
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "level_id", required = false) int levelId,
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

       ItemLevel itemLevel = this.setEntity(levelId, 0, levelName, levelAmount, levelDesc,
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
        }

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
