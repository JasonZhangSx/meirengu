
package com.meirengu.cf.controller;

import com.meirengu.cf.model.ItemContent;
import com.meirengu.cf.model.ItemCooperation;
import com.meirengu.cf.service.ItemContentService;
import com.meirengu.cf.service.ItemCooperationService;
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
 * 众筹项目合作补充表
 * @author 建新
 * @create 2017-03-17 12:03
 */
@Controller
@RequestMapping("item_cooperation")
public class ItemCooperationController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemCooperationController.class);

    @Autowired
    private ItemCooperationService itemCooperationService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "commission_rate", required = false) BigDecimal commissionRate,
                         @RequestParam(value = "guarantee_rate", required = false) BigDecimal guaranteeRate,
                         @RequestParam(value = "prepaid_bonus", required = false) Integer prepaidBonus,
                         @RequestParam(value = "loan_mode", required = false) Integer loanMode,
                         @RequestParam(value = "first_ratio", required = false) Integer firstRatio,
                         @RequestParam(value = "sponsor_idcard", required = false) String sponsorIdcard,
                         @RequestParam(value = "sponsor_credit", required = false) String sponsorCredit,
                         @RequestParam(value = "principal_idcard", required = false) String principalIdcard,
                         @RequestParam(value = "principal_credit", required = false) String principalCredit,
                         @RequestParam(value = "business_licence", required = false) String businessLicence,
                         @RequestParam(value = "venue_rental_agreement", required = false) String venueRentalAgreement,
                         @RequestParam(value = "store_renderings", required = false) String storeRenderings,
                         @RequestParam(value = "corporate_articles", required = false) String corporateArticles,
                         @RequestParam(value = "asset_water_liabilities", required = false) String assetWaterLiabilities,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){

        ItemCooperation itemCooperation = setEntity(itemId, commissionRate, guaranteeRate,
                prepaidBonus, loanMode, firstRatio, sponsorIdcard,
                sponsorCredit, principalIdcard, principalCredit,
                businessLicence, venueRentalAgreement,
                storeRenderings, corporateArticles, assetWaterLiabilities,
                new Date(), operateAccount);
        try {
            int insertNum = itemCooperationService.insert(itemCooperation);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_COOPERATION_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_COOPERATION_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> insert item cooperation throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "{item_id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "item_id")Integer itemId){
        try {
            ItemCooperation itemCooperation = itemCooperationService.detail(itemId);
            return super.setResult(StatusCode.OK, itemCooperation, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get item cooperation throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "{item_id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "item_id")Integer itemId){
        try {
            int deleteNum = itemCooperationService.delete(itemId);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_COOPERATION_ERROR_DELETE, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_COOPERATION_ERROR_DELETE));
            }
        }catch (Exception e){
            LOGGER.error(">> delete item content throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{content_id}", method = RequestMethod.PUT)
    public Result update(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "commission_rate", required = false) BigDecimal commissionRate,
                         @RequestParam(value = "guarantee_rate", required = false) BigDecimal guaranteeRate,
                         @RequestParam(value = "prepaid_bonus", required = false) Integer prepaidBonus,
                         @RequestParam(value = "loan_mode", required = false) Integer loanMode,
                         @RequestParam(value = "first_ratio", required = false) Integer firstRatio,
                         @RequestParam(value = "sponsor_idcard", required = false) String sponsorIdcard,
                         @RequestParam(value = "sponsor_credit", required = false) String sponsorCredit,
                         @RequestParam(value = "principal_idcard", required = false) String principalIdcard,
                         @RequestParam(value = "principal_credit", required = false) String principalCredit,
                         @RequestParam(value = "business_licence", required = false) String businessLicence,
                         @RequestParam(value = "venue_rental_agreement", required = false) String venueRentalAgreement,
                         @RequestParam(value = "store_renderings", required = false) String storeRenderings,
                         @RequestParam(value = "corporate_articles", required = false) String corporateArticles,
                         @RequestParam(value = "asset_water_liabilities", required = false) String assetWaterLiabilities,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){

        ItemCooperation itemCooperation = setEntity(itemId, commissionRate, guaranteeRate,
                prepaidBonus, loanMode, firstRatio, sponsorIdcard,
                sponsorCredit, principalIdcard, principalCredit,
                businessLicence, venueRentalAgreement,
                storeRenderings, corporateArticles, assetWaterLiabilities,
                null, operateAccount);
        try {
            int updateNum = itemCooperationService.update(itemCooperation);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_COOPERATION_ERROR_UPDATE, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_COOPERATION_ERROR_UPDATE));
            }
        }catch (Exception e){
            LOGGER.error(">> update item content throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    private ItemCooperation setEntity(Integer itemId, BigDecimal commissionRate, BigDecimal guaranteeRate,
                                      Integer prepaidBonus, Integer loanMode, Integer firstRatio, String sponsorIdcard,
                                      String sponsorCredit, String principalIdcard, String principalCredit,
                                      String businessLicence, String venueRentalAgreement,
                                      String storeRenderings, String corporateArticles, String assetWaterLiabilities,
                                      Date createTime, String operateAccount){
        ItemCooperation entity = new ItemCooperation();
        entity.setItemId(itemId);
        entity.setCommissionRate(commissionRate);
        entity.setGuaranteeRate(guaranteeRate);
        entity.setPrepaidBonus(prepaidBonus);
        entity.setLoanMode(loanMode);
        entity.setFirstRatio(firstRatio);
        entity.setSponsorIdcard(sponsorIdcard);
        entity.setSponsorCredit(sponsorCredit);
        entity.setPrincipalIdcard(principalIdcard);
        entity.setPrincipalCredit(principalCredit);
        entity.setBusinessLicence(businessLicence);
        entity.setVenueRentalAgreement(venueRentalAgreement);
        entity.setStoreRenderings(storeRenderings);
        entity.setCorporateArticles(corporateArticles);
        entity.setAssetWaterLiabilities(assetWaterLiabilities);
        entity.setCreateTime(createTime);
        entity.setOperateAccount(operateAccount);
        return entity;
    }
}
