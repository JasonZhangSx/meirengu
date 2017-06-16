package com.meirengu.cf.controller;

import com.meirengu.cf.model.Partner;
import com.meirengu.cf.model.PartnerClass;
import com.meirengu.cf.service.PartnerService;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-15 20:52
 */
@Controller
@RequestMapping("partner")
public class PartnerController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    PartnerService partnerService;

    /**
     * 获取请求列表
     * @param pageSize
     * @param pageNum
     * @param isPage
     * @param partnerName
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
                       @RequestParam(value = "partner_name", required = false) String partnerName,
                       @RequestParam(value = "class_id", required = false) String classId,
                       @RequestParam(value = "flag", required = false) Integer flag,
                       @RequestParam(value = "sortby", required = false, defaultValue = "create_time") String sortBy,
                       @RequestParam(value = "order", required = false, defaultValue = "desc") String order){

        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("partnerName", partnerName);
        map.put("classId", classId);
        map.put("flag", flag);
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<Partner> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = partnerService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = partnerService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }


    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum){

        Map<String, Object> map = new HashMap<>();
        map.put("fields", "partner_name as partnerName, partner_label as partnerLabel, partner_img as partnerImg, partner_telphone as partnerTelphone, enterprise_address as enterpriseAddress, partner_id as partnerId");

        Page<Partner> page = new Page<>();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        page = partnerService.getListByPage(page, map);
        return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    /**
     * 新增合作伙伴
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "type_id", required = false) Integer typeId,
                         @RequestParam(value = "partner_name", required = false) String partnerName,
                         @RequestParam(value = "partner_label", required = false) String partnerLabel,
                         @RequestParam(value = "partner_telphone", required = false) String partnerTelphone,
                         @RequestParam(value = "partner_img", required = false) String partnerImg,
                         @RequestParam(value = "partner_create_day", required = false) Date partnerCreateDay,
                         @RequestParam(value = "partner_regist_capital", required = false) Integer partnerRegistCapital,
                         @RequestParam(value = "partner_valuation", required = false) Integer partnerValuation,
                         @RequestParam(value = "account_id", required = false) Integer accountId,
                         @RequestParam(value = "enterprise_name", required = false) String enterpriseName,
                         @RequestParam(value = "id_number", required = false) String idNumber,
                         @RequestParam(value = "enterprise_address", required = false) String enterpriseAddress,
                         @RequestParam(value = "principal_name", required = false) String principalName,
                         @RequestParam(value = "principal_idcard", required = false) String principalIdcard,
                         @RequestParam(value = "principal_telephone", required = false) String principalTelephone,
                         @RequestParam(value = "principal_fax", required = false) String principalFax,
                         @RequestParam(value = "principal_address", required = false) String principalAddress,
                         @RequestParam(value = "contacts_name", required = false) String contactsName,
                         @RequestParam(value = "contacts_idcard", required = false) String contactsIdcard,
                         @RequestParam(value = "contacts_telephone", required = false) String contactsTelephone,
                         @RequestParam(value = "contacts_fax", required = false) String contactsFax,
                         @RequestParam(value = "bank_name", required = false) String bankName,
                         @RequestParam(value = "bank_account", required = false) String bankAccount,
                         @RequestParam(value = "bank_card", required = false) String bankCard,
                         @RequestParam(value = "image_principal", required = false) String imagePrincipal,
                         @RequestParam(value = "image_businessLicence", required = false) String imageBusinessLicence,
                         @RequestParam(value = "image_bank", required = false) String imageBank,
                         @RequestParam(value = "image_professional_license", required = false) String imageProfessionalLicense,
                         @RequestParam(value = "contacts_address", required = false) String contactsAddress,
                         @RequestParam(value = "operate_account", required = false) String operateAccount
                         ){

        Partner partner = this.setPartner(0, typeId, partnerName, partnerCreateDay, partnerRegistCapital,
                                            partnerValuation, accountId, enterpriseName, idNumber,
                                            enterpriseAddress, principalName, principalIdcard,
                                            principalTelephone, principalFax, principalAddress, contactsName,
                                            contactsIdcard, contactsTelephone, contactsFax, contactsAddress,
                                            bankName, bankAccount, bankCard, imagePrincipal,
                                            imageBusinessLicence, imageBank, imageProfessionalLicense, 1,
                                            new Date(), operateAccount, partnerLabel, partnerTelphone, partnerImg);
        try {
            int insertNum = partnerService.insert(partner);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> insert partner class throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 获取行业分类详情
     */
    @ResponseBody
    @RequestMapping(value = "{partner_id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "partner_id", required = false)int partnerId){
        try {
            Partner p = partnerService.detail(partnerId);
            return super.setResult(StatusCode.OK, p, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get partner detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改合作伙伴信息
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "partner_id", required = false) Integer partnerId,
                         @RequestParam(value = "type_id", required = false) Integer typeId,
                         @RequestParam(value = "partner_name", required = false) String partnerName,
                         @RequestParam(value = "partner_label", required = false) String partnerLabel,
                         @RequestParam(value = "partner_telphone", required = false) String partnerTelphone,
                         @RequestParam(value = "partner_img", required = false) String partnerImg,
                         @RequestParam(value = "partner_create_day", required = false) Date partnerCreateDay,
                         @RequestParam(value = "partner_regist_capital", required = false) Integer partnerRegistCapital,
                         @RequestParam(value = "partner_valuation", required = false) Integer partnerValuation,
                         @RequestParam(value = "account_id", required = false) Integer accountId,
                         @RequestParam(value = "enterprise_name", required = false) String enterpriseName,
                         @RequestParam(value = "id_number", required = false) String idNumber,
                         @RequestParam(value = "enterprise_address", required = false) String enterpriseAddress,
                         @RequestParam(value = "principal_name", required = false) String principalName,
                         @RequestParam(value = "principal_idcard", required = false) String principalIdcard,
                         @RequestParam(value = "principal_telephone", required = false) String principalTelephone,
                         @RequestParam(value = "principal_fax", required = false) String principalFax,
                         @RequestParam(value = "principal_address", required = false) String principalAddress,
                         @RequestParam(value = "contacts_name", required = false) String contactsName,
                         @RequestParam(value = "contacts_idcard", required = false) String contactsIdcard,
                         @RequestParam(value = "contacts_telephone", required = false) String contactsTelephone,
                         @RequestParam(value = "contacts_fax", required = false) String contactsFax,
                         @RequestParam(value = "bank_name", required = false) String bankName,
                         @RequestParam(value = "bank_account", required = false) String bankAccount,
                         @RequestParam(value = "bank_card", required = false) String bankCard,
                         @RequestParam(value = "image_principal", required = false) String imagePrincipal,
                         @RequestParam(value = "image_businessLicence", required = false) String imageBusinessLicence,
                         @RequestParam(value = "image_bank", required = false) String imageBank,
                         @RequestParam(value = "image_professional_license", required = false) String imageProfessionalLicense,
                         @RequestParam(value = "contacts_address", required = false) String contactsAddress,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){

        Partner partner = this.setPartner(partnerId, typeId, partnerName, partnerCreateDay, partnerRegistCapital,
                partnerValuation, accountId, enterpriseName, idNumber,
                enterpriseAddress, principalName, principalIdcard,
                principalTelephone, principalFax, principalAddress, contactsName,
                contactsIdcard, contactsTelephone, contactsFax, contactsAddress,
                bankName, bankAccount, bankCard, imagePrincipal,
                imageBusinessLicence, imageBank, imageProfessionalLicense, 1,
                new Date(), operateAccount, partnerLabel, partnerTelphone, partnerImg);
        try {
            int updateNum = partnerService.update(partner);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_UPDATE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_UPDATE));
            }
        }catch (Exception e){
            LOGGER.error(">> update partner class throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /*
     * 删除
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{partner_id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "partner_id", required = false)Integer partnerId){
        try {
            int deleteNum = partnerService.delete(partnerId);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ALREADY_DELETE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ALREADY_DELETE));
            }
        }catch (Exception e){
            LOGGER.error(">> delete partner class detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private Partner setPartner(Integer partnerId, int typeId, String partnerName, Date partnerCreateDay, int partnerRegistCapital,
                   int partnerValuation, int accountId, String enterpriseName, String idNumber, String
                           enterpriseAddress, String principalName, String principalIdcard, String
                           principalTelephone, String principalFax, String principalAddress, String contactsName, String
                           contactsIdcard, String contactsTelephone, String contactsFax, String contactsAddress, String
                           bankName, String bankAccount, String bankCard, String imagePrincipal, String
                           imageBusinessLicence, String imageBank, String imageProfessionalLicense, int flag, Date
                           createTime, String operateAccount, String partnerLabel, String partnerTelphone, String partnerImg) {
        Partner partner = new Partner();
        partner.setPartnerId(partnerId);
        partner.setTypeId(typeId);
        partner.setPartnerName(partnerName == null ? "" : partnerName);
        partner.setImageBusinessLicence(imageBusinessLicence == null ? "" : imageBusinessLicence);
        partner.setImageBank(imageBank == null ? "" : imageBank);
        partner.setImageProfessionalLicense(imageProfessionalLicense == null ? "" : imageProfessionalLicense);
        partner.setFlag(flag);
        partner.setCreateTime(createTime);
        partner.setOperateAccount(operateAccount == null ? "": operateAccount);
        partner.setPartnerCreateDay(partnerCreateDay);
        partner.setPartnerValuation(partnerValuation);
        partner.setAccountId(accountId);
        partner.setEnterpriseName(enterpriseName == null ? "" : enterpriseName);
        partner.setEnterpriseAddress(enterpriseAddress == null ? "" : enterpriseAddress);
        partner.setIdNumber(idNumber);
        partner.setPartnerRegistCapital(partnerRegistCapital);
        partner.setPrincipalName(principalName == null ? "": principalName);
        partner.setPrincipalIdcard(principalIdcard == null ? "": principalIdcard);
        partner.setPrincipalTelephone(principalTelephone == null ? "": principalTelephone);
        partner.setPrincipalFax(principalFax);
        partner.setPrincipalAddress(principalAddress);
        partner.setContactsName(contactsName == null ? "": contactsName);
        partner.setContactsIdcard(contactsIdcard == null ? "": contactsIdcard);
        partner.setContactsTelephone(contactsTelephone == null ? "": contactsTelephone);
        partner.setContactsFax(contactsFax);
        partner.setContactsAddress(contactsAddress == null ? "":contactsAddress);
        partner.setBankName(bankName == null ? "": bankName);
        partner.setBankAccount(bankAccount == null ? "":bankAccount);
        partner.setBankCard(bankCard);
        partner.setImagePrincipal(imagePrincipal == null ? "" : imagePrincipal);
        partner.setPartnerLabel(partnerLabel == null ? "" : partnerLabel);
        partner.setPartnerTelphone(partnerTelphone == null ? "" : partnerTelphone);
        partner.setPartnerImg(partnerImg == null ? "" :partnerImg);

        return partner;
    }
}
