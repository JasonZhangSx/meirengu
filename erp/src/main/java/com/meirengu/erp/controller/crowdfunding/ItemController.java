package com.meirengu.erp.controller.crowdfunding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.model.*;
import com.meirengu.erp.service.AddressService;
import com.meirengu.erp.service.ItemService;
import com.meirengu.erp.service.PartnerService;
import com.meirengu.erp.service.TypeService;
import com.meirengu.erp.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
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

    @Autowired
    ItemService itemService;
    @Autowired
    TypeService typeService;
    @Autowired
    PartnerService partnerService;
    @Autowired
    AddressService addressService;

    /**
     * 新建list
     * @return
     */
    @RequestMapping("create_list")
    public ModelAndView itemCreateList(Integer itemId, String itemName){
        Map<String, Object> map = new HashMap<>();
        //Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "1,4");

        return new ModelAndView("/item/itemCreateList", map);
    }

    /**
     * 待初审
     * @return
     */
    @RequestMapping("verify_list")
    public ModelAndView itemVerifyList(Integer itemId, String itemName){
        Map<String, Object> map = new HashMap<>();
        //Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "2");

        return new ModelAndView("/item/itemVerifyList", map);
    }

    /**
     * 待合作列表
     * @return
     */
    @RequestMapping("cooperate_list")
    public ModelAndView itemCooperateList(Integer itemId, String itemName){
        Map<String, Object> map = new HashMap<>();
        //Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "3");

        return new ModelAndView("/item/itemCooperateList", map);
    }

    /**
     * 待复审
     * @return
     */
    @RequestMapping("review_list")
    public ModelAndView itemReviewList(Integer itemId, String itemName){
        Map<String, Object> map = new HashMap<>();
        //Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "5,6");

        return new ModelAndView("/item/itemReviewList", map);
    }

    /**
     * 待发布
     * @return
     */
    @RequestMapping("publish_list")
    public ModelAndView itemPublishList(Integer itemId, String itemName){
        Map<String, Object> map = new HashMap<>();
        //Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "7,9");

        return new ModelAndView("/item/itemPublishList", map);
    }

    /**
     * 已发布
     * @return
     */
    @RequestMapping("published_list")
    public ModelAndView itemPublishedList(Integer itemId, String itemName){
        Map<String, Object> map = new HashMap<>();
        //Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "10,11");

        return new ModelAndView("/item/itemPublishedList", map);
    }

    /**
     * 已完成
     * @return
     */
    @RequestMapping("completed_list")
    public ModelAndView itemCompletedList(Integer itemId, String itemName){
        Map<String, Object> map = new HashMap<>();
        //Map<String, Object> map = itemService.getItemListByPage(true, itemId, itemName, "12");

        return new ModelAndView("/item/itemCompletedList", map);
    }

    /**
     * 项目列表数据请求
     * @param input
     * @return
     */
    @RequestMapping(value = "query", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput query(@Valid DataTablesInput input, Integer itemId, String itemName, String status) throws IOException {

        List<Map<String,Object>> list = null;
        int totalCount = 0;
        int start = input.getStart();
        int length = input.getLength();
        int page = start / length + 1;
        try {
            Map<String, Object> map = itemService.getItemListByPage(page, length, true, itemId, itemName, status);
            list = (List<Map<String,Object>>) map.get("list");
            totalCount = Integer.parseInt(map.get("totalCount").toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throw exception:{}", e);
        }
        return setDataTablesOutput(input, list, totalCount);
    }

    /**
     * 跳转到项目新增页面
     * @return
     */
    @RequestMapping("to_add")
    public ModelAndView toItemAdd(){
        Map<String, Object> returnMap = new HashMap<>();
        try {
            List itemClassData = itemService.getItemClassList();
            List typeData = typeService.getTypeList();
            List partnerData = partnerService.getPartnerList();
            List provinceData = (List) httpGet(ConfigUtil.getConfig("address.province.list"));
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
    @RequestMapping("save")
    public Map itemSave(Item item){
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("item_name", item.getItemName());
        params.put("item_profile", item.getItemProfile());
        params.put("type_id", String.valueOf(item.getTypeId()));
        params.put("class_id", String.valueOf(item.getClassId()));
        params.put("target_amount", String.valueOf(item.getTargetAmount()));
        params.put("preheating_days", String.valueOf(item.getPreheatingDays()));
        params.put("partner_id", String.valueOf(item.getPartnerId()));
        params.put("crowd_days", String.valueOf(item.getCrowdDays()));
        params.put("area_id", String.valueOf(item.getAreaId()));
        params.put("header_image", item.getHeaderImage());
        params.put("operate_account", "jason");
        params.put("sponsor_name","jason");
        params.put("lead_investor_name", item.getLeadInvestorName());
        params.put("lead_investor_amount", String.valueOf(item.getLeadInvestorAmount()));
        params.put("lead_investor_header", item.getLeadInvestorHeader());

        try {
            if(item.getItemId() == null || item.getItemId() == 0){
                Object itemResult = itemService.itemInsert(params);
                if(itemResult != null){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", itemResult);
                }else {
                    returnMap.put("code", StatusCode.ITEM_ERROR_INSERT);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_ERROR_INSERT));
                }
            }else {
                params.put("item_id", String.valueOf(item.getItemId()));
                if(itemService.itemUpdate(params)){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", item);
                }else {
                    returnMap.put("code", StatusCode.ITEM_ERROR_UPDATE);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_ERROR_UPDATE));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnMap;
    }

    /**
     * 项目内容回报新增/修改
     * @param itemContent
     * @return
     */
    @RequestMapping("content/save")
    public Map contentAdd(ItemContent itemContent){
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("item_id", String.valueOf(itemContent.getItemId()));
        params.put("content_title", itemContent.getContentTitle());
        params.put("content_sort", "255");
        params.put("content_subtitle", "");
        params.put("content_type", String.valueOf(itemContent.getContentType()));
        params.put("content_info", itemContent.getContentInfo());
        params.put("operate_account", "jason");

        try {
            //contentId存在则为修改，不存在则为新增
            if(itemContent.getContentId() == null || itemContent.getContentId() == 0){
                Object contentResult = itemService.contentInsert(params);
                if(contentResult != null){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", contentResult);
                }else {
                    returnMap.put("code", StatusCode.ITEM_CONTENT_ERROR_INSERT);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_CONTENT_ERROR_INSERT));
                }
            }else {
                params.put("content_id", String.valueOf(itemContent.getContentId()));
                if(itemService.contentUpdate(params)){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", itemContent);
                }else {
                    returnMap.put("code", StatusCode.ITEM_CONTENT_ERROR_UPDATE);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_CONTENT_ERROR_UPDATE));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return returnMap;
    }

    /**
     * 项目档位回报新增/修改
     * @param itemLevel
     * @return
     */
    @RequestMapping("level/save")
    public Map levelAdd(ItemLevel itemLevel){
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("item_id", String.valueOf(itemLevel.getItemId()));
        params.put("level_name", itemLevel.getLevelName());
        params.put("level_amount", String.valueOf(itemLevel.getLevelAmount()));
        params.put("level_desc", itemLevel.getLevelDesc());
        params.put("total_number", String.valueOf(itemLevel.getTotalNumber()));
        params.put("single_limit_number", String.valueOf(itemLevel.getSingleLimitNumber()));
        params.put("payback_days", String.valueOf(itemLevel.getPaybackDays()));
        params.put("is_share_bonus", String.valueOf(itemLevel.getIsShareBonus()));
        params.put("year_rate", String.valueOf(itemLevel.getYearRate()));
        params.put("investment_period", String.valueOf(itemLevel.getInvestmentPeriod()));
        params.put("revenue_model", String.valueOf(itemLevel.getRevenueModel()));
        params.put("share_bonus_period", String.valueOf(itemLevel.getShareBonusPeriod()));
        params.put("is_need_address", String.valueOf(itemLevel.getIsNeedAddress()));
        params.put("is_need_agreement", String.valueOf(itemLevel.getIsNeedAgreement()));
        params.put("operate_account", "jason");
        try {
            if(itemLevel.getLevelId() == 0 || itemLevel.getLevelId() == null){
                Object levelResult = itemService.levelInsert(params);
                if(levelResult != null){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", levelResult);
                }else {
                    returnMap.put("code", StatusCode.ITEM_LEVEL_ERROR_INSERT);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_ERROR_INSERT));
                }
            }else {
                params.put("level_id", String.valueOf(itemLevel.getLevelId()));
                if(itemService.levelUpdate(params)){
                    returnMap.put("code", StatusCode.OK);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
                    returnMap.put("data", itemLevel);
                }else {
                    returnMap.put("code", StatusCode.ITEM_LEVEL_ERROR_UPDATE);
                    returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_ERROR_UPDATE));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return returnMap;
    }

    @RequestMapping("to_edit")
    public ModelAndView toItemEdit(Integer itemId){
        Map<String, Object> returnMap = null;
        try {
            returnMap = getItemInfo(itemId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/item/itemEdit", returnMap);
    }

    /**
     * 提交初审
     * @param itemId
     * @return
     */
    @RequestMapping(value = "submit_verify", method = RequestMethod.POST)
    public Map submit_verify(Integer itemId){
        Map<String, Object> returnMap = new HashMap<>();
        if(itemService.submitVerify(itemId)){
            returnMap.put("code", StatusCode.OK);
            returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            returnMap.put("code", StatusCode.INTERNAL_SERVER_ERROR);
            returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

        return returnMap;
    }

    @RequestMapping(value = "to_verify")
    public ModelAndView toVerify(Integer itemId){
        Map<String, Object> returnMap = null;

        try {
            returnMap = getItemInfo(itemId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ModelAndView("/item/itemVerify", returnMap);
    }

    @RequestMapping(value = "verify")
    public Map verify(Integer itemId, Integer operateStatus, String operateRemark){
        Map<String, Object> returnMap = new HashMap<>();
        if(itemService.verify(itemId, operateStatus, operateRemark)){
            returnMap.put("code", StatusCode.OK);
            returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            returnMap.put("code", StatusCode.INTERNAL_SERVER_ERROR);
            returnMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

        return returnMap;
    }

    @RequestMapping(value = "to_cooperate")
    public ModelAndView toOperation(Integer itemId){
        Map<String, Object> returnMap = null;
        try {
            returnMap = getItemInfo(itemId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("/item/itemCooperate", returnMap);
    }

    @RequestMapping(value = "cooperate")
    public ModelAndView cooperate(ItemCooperation cooperation){
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("item_id", String.valueOf(cooperation.getItemId()));
        params.put("commission_rate", cooperation.getCommissionRate() == null ? "0" : String.valueOf(cooperation.getCommissionRate()));
        params.put("guarantee_rate", cooperation.getGuaranteeRate() == null ? "0" : String.valueOf(cooperation.getGuaranteeRate()));
        params.put("prepaid_bonus", cooperation.getPrepaidBonus() == null ? "0" : String.valueOf(cooperation.getPrepaidBonus()));
        params.put("loan_mode", cooperation.getLoanMode() == null ? "1" : String.valueOf(cooperation.getLoanMode()));
        params.put("first_ratio", cooperation.getFirstRatio() == null ? "0" : String.valueOf(cooperation.getFirstRatio()));
        params.put("sponsor_idcard", cooperation.getSponsorIdcard());
        params.put("sponsor_credit", cooperation.getSponsorCredit());
        params.put("principal_idcard", cooperation.getPrincipalIdcard());
        params.put("principal_credit", cooperation.getPrincipalCredit());
        params.put("business_licence", cooperation.getBusinessLicence());
        params.put("venue_rental_agreement", cooperation.getVenueRentalAgreement());
        params.put("store_renderings", cooperation.getStoreRenderings());
        params.put("corporate_articles", cooperation.getCorporateArticles());
        params.put("asset_water_liabilities", cooperation.getAssetWaterLiabilities());
        params.put("operate_account", cooperation.getOperateAccount());
        params.put("shareholder_name", cooperation.getShareholderName());
        params.put("shareholder_idcard", cooperation.getShareholderIdcard());
        params.put("shareholder_address", cooperation.getShareholderAddress());
        params.put("guarantee_name", cooperation.getGuaranteeAddress());
        params.put("guarantee_idcard", cooperation.getGuaranteeIdcard());
        params.put("guarantee_address", cooperation.getGuaranteeAddress());
        params.put("pledged_shares", String.valueOf(cooperation.getPledgedShares()));
        params.put("partner_seal", cooperation.getPartnerSeal());
        params.put("escrow_agreement", cooperation.getEscrowAgreement());
        params.put("finance_service", cooperation.getFinanceService());
        params.put("finance_manage", cooperation.getFinanceManage());
        params.put("guaranty_agreement", cooperation.getGuarantyAgreement());

        itemService.setCooperate(params);
        return new ModelAndView("redirect:/item/to_review");
    }

    @RequestMapping(value = "to_review")
    public ModelAndView toReview(Integer itemId){
        Map<String, Object> returnMap = null;
        try {
            returnMap = getItemInfo(itemId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("/item/itemReview", returnMap);
    }

    @RequestMapping(value = "review")
    public ModelAndView review(Integer itemId, Integer operateStatus, String operateRemark){
        itemService.review(itemId, operateStatus, operateRemark);
        return new ModelAndView("redirect:/item/publish_list");
    }

    @RequestMapping(value = "to_publish")
    public ModelAndView toPublish(Integer itemId){
        Map<String, Object> returnMap = null;
        try {
            returnMap = getItemInfo(itemId);
            Map<String, Object> cooperateMap = getCooperateInfo(itemId);
            returnMap.put("cooperate", cooperateMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("/item/itemPublish", returnMap);
    }

    @RequestMapping(value = "to_published")
    public ModelAndView toPublished(Integer itemId){
        Map<String, Object> returnMap = null;
        try {
            returnMap = getItemInfo(itemId);
            Map<String, Object> cooperateMap = getCooperateInfo(itemId);
            returnMap.put("cooperate", cooperateMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("/item/itemPublishedDetail", returnMap);
    }

    @RequestMapping(value = "publish")
    public ModelAndView publish(Integer itemId, Integer publishType, Date publishTime){
        itemService.publish(publishTime, publishType, itemId, "admin");
        return new ModelAndView("redirect:/item/published_list");
    }

    private Map getCooperateInfo(Integer itemId){
        return itemService.getCooperateInfo(itemId);
    }


    private Map getItemInfo(Integer itemId) throws IOException {

        Map<String, Object> returnMap = new HashMap<>();
        List itemClassData = itemService.getItemClassList();
        List typeData = typeService.getTypeList();
        List partnerData = partnerService.getPartnerList();
        List provinceData = addressService.getProvinceList();
        Map item = itemService.itemDetail(itemId);
        List contentData = itemService.getContentList(itemId, null);
        List levelData = itemService.getLevelList(itemId);
        List recordData = itemService.getOperateRecordList(itemId);
        returnMap.put("itemClass", itemClassData);
        returnMap.put("type", typeData);
        returnMap.put("partner", partnerData);
        returnMap.put("provinces", provinceData);
        returnMap.put("item", item);
        returnMap.put("content", contentData);
        returnMap.put("level", levelData);
        returnMap.put("imageUrl", ConfigUtil.getConfig("image.show.url"));
        returnMap.put("record", recordData);
        return returnMap;
    }
}
