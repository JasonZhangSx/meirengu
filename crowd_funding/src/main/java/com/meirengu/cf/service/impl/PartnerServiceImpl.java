package com.meirengu.cf.service.impl;
import com.meirengu.cf.common.Constants;
import com.meirengu.cf.dao.PartnerDao;
import com.meirengu.cf.model.Item;
import com.meirengu.cf.model.ItemCooperation;
import com.meirengu.cf.model.ItemLevel;
import com.meirengu.cf.model.Partner;
import com.meirengu.cf.service.*;
import com.meirengu.exception.BusinessException;
import com.meirengu.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Partner服务实现层 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
@Service
public class PartnerServiceImpl extends BaseServiceImpl<Partner> implements PartnerService{

    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerServiceImpl.class);

    @Autowired
    ItemService itemService;
    @Autowired
    PartnerDao partnerDao;
    @Autowired
    ItemCooperationService itemCooperationService;
    @Autowired
    ItemLevelService itemLevelService;

    @Override
    public Map<String, Object> getAgreementContent(int itemId, int levelId) {

        LOGGER.info(">> PartnerServiceImpl.getAgreementContent params is itemId={}", itemId);
        Map<String, Object> returnMap = new HashMap<>();

        try {
            Item item = itemService.detail(itemId);
            if(item == null){
                LOGGER.warn(">> PartnerServiceImpl.getAgreementContent item is null");
                return null;
            }

            ItemLevel itemLevel = itemLevelService.detail(levelId);
            if(itemLevel == null){
                LOGGER.warn(">> PartnerServiceImpl.getAgreementContent level is null");
                return null;
            }

            returnMap.put("investmentStalls", itemLevel.getLevelName());
            returnMap.put("investmentAmount", itemLevel.getLevelAmount());
            returnMap.put("investmentTerm", itemLevel.getInvestmentPeriod());
            returnMap.put("projectBonusAndReturnStatement", itemLevel.getLevelDesc());
            returnMap.put("flag", itemLevel.getRevenueModel());
            if(itemLevel.getRevenueModel() == Constants.LEVEL_PROFIT_ONCE){
                returnMap.put("exitAndRepurchaseDate", itemLevel.getInvestmentPeriod());
            }else if(itemLevel.getRevenueModel() == Constants.LEVEL_PROFIT_MULTIPLE){
                returnMap.put("exitAndRepurchaseDate", itemLevel.getShareBonusPeriod());
                returnMap.put("exitAndRepurchaseDateNum", itemLevel.getInvestmentPeriod());
            }else {
                LOGGER.warn(">> PartnerServiceImpl.getAgreementContent level revenueMode is false..");
                return null;
            }

            int partnerId = item.getPartnerId();
            Partner partner = partnerDao.detail(partnerId);
            if(partner == null){
                LOGGER.warn(">> PartnerServiceImpl.getAgreementContent partner is null");
                return null;
            }

            Date partnerCreateDay = partner.getPartnerCreateDay();
            returnMap.put("projectOwner", partner.getPartnerName());
            returnMap.put("projectCompany", partner.getPartnerName());
            returnMap.put("manageMoney", partner.getPartnerValuation());
            returnMap.put("manageYear", partnerCreateDay.getYear()+1900);
            returnMap.put("manageMonth", partnerCreateDay.getMonth()+1);
            returnMap.put("manageDay", partnerCreateDay.getDate());

            ItemCooperation itemCooperation = itemCooperationService.getByItemId(itemId);
            if(itemCooperation == null){
                LOGGER.warn(">> PartnerServiceImpl.getAgreementContent itemCooperation is null");
                return null;
            }

            returnMap.put("platformServiceFee", itemCooperation.getCommissionRate());
            returnMap.put("shareholder", itemCooperation.getShareholderName());
            returnMap.put("shareholderIdCard", itemCooperation.getShareholderIdcard());
            returnMap.put("shareholderArea", itemCooperation.getShareholderAddress());

            returnMap.put("guarantor", itemCooperation.getGuaranteeName());
            returnMap.put("guarantorIdCard", itemCooperation.getGuaranteeIdcard());
            returnMap.put("guarantorArea", itemCooperation.getGuaranteeAddress());

            return returnMap;
        }catch (Exception e){
            throw new BusinessException("PartnerServiceImpl.getAgreementContent throw exception", e);
        }
    }
}
