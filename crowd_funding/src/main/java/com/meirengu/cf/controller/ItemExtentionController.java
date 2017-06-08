package com.meirengu.cf.controller;

import com.meirengu.cf.model.ItemExtention;
import com.meirengu.cf.service.ItemExtentionService;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 项目扩展controller
 * @author 建新
 * @create 2017-06-08 12:21
 */
@Controller
@RequestMapping("item_extention")
public class ItemExtentionController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemExtentionController.class);

    @Autowired
    ItemExtentionService itemExtentionService;

    /**
     * 获取请求列表
     * @param pageSize
     * @param pageNum
     * @param isPage
     * @param sortBy
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "sortby", required = false, defaultValue = "create_time") String sortBy,
                       @RequestParam(value = "order", required = false, defaultValue = "desc") String order){

        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<ItemExtention> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = itemExtentionService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = itemExtentionService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    /**
     * 新增合作伙伴
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "sell_share", required = false) BigDecimal sellShare,
                         @RequestParam(value = "finance_amount", required = false) Integer financeAmount,
                         @RequestParam(value = "register_capital", required = false) Integer registerCapital,
                         @RequestParam(value = "captital_reserve", required = false) Integer captitalReserve,
                         @RequestParam(value = "after_register_capital", required = false) Integer afterRegisterCapital,
                         @RequestParam(value = "lead_investor_id", required = false) Integer leadInvestorId,
                         @RequestParam(value = "lead_investor_amount", required = false) Integer leadInvestorAmount,
                         @RequestParam(value = "executive_partner", required = false) String executivePartner,
                         @RequestParam(value = "lead_investor_reason", required = false) String leadInvestorReason,
                         @RequestParam(value = "limited_partnership_id1", required = false) Integer limitedPartnershipId1,
                         @RequestParam(value = "limited_partnership_id2", required = false) Integer limitedPartnershipId2,
                         @RequestParam(value = "limited_partnership_id3", required = false) Integer limitedPartnershipId3,
                         @RequestParam(value = "limited_partnership_id4", required = false) Integer limitedPartnershipId4,
                         @RequestParam(value = "equity_template", required = false) String equityTemplate,
                         @RequestParam(value = "partnership_template", required = false) String partnershipTemplate){

        ItemExtention itemExtention = this.setExtention(itemId, sellShare, financeAmount, registerCapital,
                captitalReserve, afterRegisterCapital, leadInvestorId,
                leadInvestorAmount, executivePartner, leadInvestorReason,
                limitedPartnershipId1, limitedPartnershipId2, limitedPartnershipId3,
                limitedPartnershipId4, equityTemplate, partnershipTemplate);
        try {
            int insertNum = itemExtentionService.insert(itemExtention);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.EXTENTION_INSERT_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.EXTENTION_INSERT_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> insert partner class throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 获取项目扩展信息
     */
    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "id", required = false)int id){
        try {
            ItemExtention p = itemExtentionService.detail(id);
            return super.setResult(StatusCode.OK, p, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get item extention throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改项目扩展信息
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "sell_share", required = false) BigDecimal sellShare,
                         @RequestParam(value = "finance_amount", required = false) Integer financeAmount,
                         @RequestParam(value = "register_capital", required = false) Integer registerCapital,
                         @RequestParam(value = "captital_reserve", required = false) Integer captitalReserve,
                         @RequestParam(value = "after_register_capital", required = false) Integer afterRegisterCapital,
                         @RequestParam(value = "lead_investor_id", required = false) Integer leadInvestorId,
                         @RequestParam(value = "lead_investor_amount", required = false) Integer leadInvestorAmount,
                         @RequestParam(value = "executive_partner", required = false) String executivePartner,
                         @RequestParam(value = "lead_investor_reason", required = false) String leadInvestorReason,
                         @RequestParam(value = "limited_partnership_id1", required = false) Integer limitedPartnershipId1,
                         @RequestParam(value = "limited_partnership_id2", required = false) Integer limitedPartnershipId2,
                         @RequestParam(value = "limited_partnership_id3", required = false) Integer limitedPartnershipId3,
                         @RequestParam(value = "limited_partnership_id4", required = false) Integer limitedPartnershipId4,
                         @RequestParam(value = "equity_template", required = false) String equityTemplate,
                         @RequestParam(value = "partnership_template", required = false) String partnershipTemplate){

        ItemExtention itemExtention = this.setExtention(itemId, sellShare, financeAmount, registerCapital,
                captitalReserve, afterRegisterCapital, leadInvestorId,
                leadInvestorAmount, executivePartner, leadInvestorReason,
                limitedPartnershipId1, limitedPartnershipId2, limitedPartnershipId3,
                limitedPartnershipId4, equityTemplate, partnershipTemplate);
        try {
            int updateNum = itemExtentionService.update(itemExtention);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.EXTENTION_UPDATE_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.EXTENTION_UPDATE_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> update item extention throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /*
     * 删除
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@PathVariable(value = "id", required = false)Integer id){
        try {
            int deleteNum = itemExtentionService.delete(id);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.EXTENTION_DELETE_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.EXTENTION_DELETE_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> delete item extention throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private ItemExtention setExtention(Integer itemId, BigDecimal sellShare, Integer financeAmount, Integer registerCapital,
                               Integer captitalReserve, Integer afterRegisterCapital, Integer leadInvestorId, Integer
                                       leadInvestorAmount, String executivePartner, String leadInvestorReason, Integer
                                       limitedPartnershipId1, Integer limitedPartnershipId2, Integer limitedPartnershipId3,
                               Integer limitedPartnershipId4, String equityTemplate, String partnershipTemplate) {

        ItemExtention itemExtention = new ItemExtention();
        itemExtention.setItemId(itemId);
        itemExtention.setSellShare(sellShare);
        itemExtention.setFinanceAmount(financeAmount);
        itemExtention.setRegisterCapital(registerCapital);
        itemExtention.setCaptitalReserve(captitalReserve);
        itemExtention.setAfterRegisterCapital(afterRegisterCapital);
        itemExtention.setLeadInvestorId(leadInvestorId);
        itemExtention.setLeadInvestorAmount(leadInvestorAmount);
        itemExtention.setExecutivePartner(executivePartner == null ? "" : executivePartner);
        itemExtention.setLeadInvestorReason(leadInvestorReason == null ? "" : leadInvestorReason);
        itemExtention.setLimitedPartnershipId1(limitedPartnershipId1);
        itemExtention.setLimitedPartnershipId2(limitedPartnershipId2);
        itemExtention.setLimitedPartnershipId3(limitedPartnershipId3);
        itemExtention.setLimitedPartnershipId4(limitedPartnershipId4);
        itemExtention.setEquityTemplate(equityTemplate == null ? "" : equityTemplate);
        itemExtention.setPartnershipTemplate(partnershipTemplate);

        return itemExtention;
    }
}
