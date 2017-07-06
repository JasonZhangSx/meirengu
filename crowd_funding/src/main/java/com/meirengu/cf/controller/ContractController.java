package com.meirengu.cf.controller;

import com.meirengu.cf.model.*;
import com.meirengu.cf.service.*;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成合同返回数据控制台
 * @author 建新
 * @create 2017-06-22 10:35
 */
@Controller
@RequestMapping("contract")
public class ContractController extends BaseController{

    Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    ItemService itemService;
    @Autowired
    ItemLevelService itemLevelService;
    @Autowired
    ItemCooperationService itemCooperationService;
    @Autowired
    ItemExtentionService itemExtentionService;
    @Autowired
    ItemShareholderService itemShareholderService;
    @Autowired
    LeadInvestorService leadInvestorService;
    @Autowired
    LimitedPartnershipService limitedPartnershipService;
    @Autowired
    PartnerService partnerService;



    @RequestMapping("shares")
    @ResponseBody
    public Map<String, Object> shares(Integer itemId, Integer levelId){

        Map<String, Object> map = new HashMap<>();
        //项目信息
        Item item = itemService.detail(itemId);
        if(item == null){
            return super.setReturnMsg(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }
        //项目扩展信息
        ItemExtention itemExtention = itemExtentionService.detail(itemId);
        //项目设置合作
        ItemCooperation itemCooperation = itemCooperationService.detail(itemId);
        //项目档位信息
        ItemLevel itemLevel = itemLevelService.detail(levelId);
        Map<String, Object> params = new HashMap<>();
        params.put("itemId", itemId);
        //项目股东
        List<Map<String, Object>> shareHolders = itemShareholderService.getList(params);
        if(itemExtention != null){
            Integer leadInvestorId = itemExtention.getLeadInvestorId();
            LeadInvestor leadInvestor = leadInvestorService.detail(leadInvestorId);
        }
        Integer partnerId = item.getPartnerId();
        //合作方信息
        Partner partner = partnerService.detail(partnerId);

        if(partner != null){
            map.put("financingCompany", partner.getEnterpriseName());
            map.put("legalRepresentativeOfFinancingCompany", partner.getPrincipalName());
            map.put("financingCompanyAddress", partner.getPrincipalAddress());
            map.put("financingCompanyPhone", partner.getPrincipalTelephone());
            map.put("financingCompanySocialCreditCode", partner.getIdNumber());
            map.put("registeredCapitalOfFinancingCompany", partner.getPartnerRegistCapital());
        }
        if(itemExtention != null){
            map.put("valuationOfTheFinancingCompany","");
            map.put("financingCompaniesToIncreaseTheirInvestment", itemExtention.getSellShare());
            map.put("financingCompanyToIncreaseThePrice", itemExtention.getFinanceAmount());
            map.put("enterTheRegisteredCapital", itemExtention.getRegisterCapital());
            map.put("intoTheCapitalReserveFund", itemExtention.getCaptitalReserve());
            map.put("financingCompanyRecruitsTheRegisteredCapital", itemExtention.getAfterRegisterCapital());
            map.put("theSharesHeldByNewShareholders", itemExtention.getSellShare());
        }
        if(shareHolders.size() > 0){
            map.put("shareHoldersList", shareHolders);
        }else{
            map.put("shareHoldersList", null);
        }


        return map;
    }


}
