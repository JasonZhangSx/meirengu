package com.meirengu.cf.controller;

import com.meirengu.cf.model.LimitedPartnership;
import com.meirengu.cf.service.LimitedPartnershipService;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 有限合伙公司信息控制层
 * @author 建新
 * @create 2017-05-08 14:22
 */
@RequestMapping("partner_ship")
@Controller
public class LimitedPartnerShipController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(LimitedPartnerShipController.class);

    @Autowired
    LimitedPartnershipService limitedPartnershipService;

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
            Page<LimitedPartnership> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = limitedPartnershipService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = limitedPartnershipService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    /**
     * 插入有限合伙公司
     * @param companyName
     * @param establishmentTime
     * @param limitNum
     * @param idcard
     * @param companyAddress
     * @param imagePrincipal
     * @param imageBusinessLicence
     * @param imageBank
     * @param imageProfessionalLicense
     * @param operateAccount
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "company_name", required = false) String companyName,
                         @RequestParam(value = "establishment_time", required = false) Date establishmentTime,
                         @RequestParam(value = "limit_num", required = false) Integer limitNum,
                         @RequestParam(value = "idcard", required = false) String idcard,
                         @RequestParam(value = "company_address", required = false) String companyAddress,
                         @RequestParam(value = "image_principal", required = false) String imagePrincipal,
                         @RequestParam(value = "image_businessLicence", required = false) String imageBusinessLicence,
                         @RequestParam(value = "image_bank", required = false) String imageBank,
                         @RequestParam(value = "image_professional_license", required = false) String imageProfessionalLicense,
                         @RequestParam(value = "operate_account", required = false) String operateAccount ){

        LimitedPartnership li = setEntity(0, companyName, establishmentTime, limitNum,
                idcard, companyAddress, imagePrincipal, imageBusinessLicence,
                imageBank, imageProfessionalLicense, new Date(), operateAccount);
        try {
            int insertNum = limitedPartnershipService.insert(li);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> insert limitedPartnership throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 获取有限合伙公司
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "id", required = false)Integer id){
        try {
            LimitedPartnership p = limitedPartnershipService.detail(id);
            return super.setResult(StatusCode.OK, p, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get limitPartnerShip throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改有限合伙
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "id", required = false) Integer id,
                         @RequestParam(value = "company_name", required = false) String companyName,
                         @RequestParam(value = "establishment_time", required = false) Date establishmentTime,
                         @RequestParam(value = "limit_num", required = false) Integer limitNum,
                         @RequestParam(value = "idcard", required = false) String idcard,
                         @RequestParam(value = "company_address", required = false) String companyAddress,
                         @RequestParam(value = "image_principal", required = false) String imagePrincipal,
                         @RequestParam(value = "image_businessLicence", required = false) String imageBusinessLicence,
                         @RequestParam(value = "image_bank", required = false) String imageBank,
                         @RequestParam(value = "image_professional_license", required = false) String imageProfessionalLicense,
                         @RequestParam(value = "operate_account", required = false) String operateAccount ){

        LimitedPartnership li = setEntity(id, companyName, establishmentTime, limitNum,
                idcard, companyAddress, imagePrincipal, imageBusinessLicence,
                imageBank, imageProfessionalLicense, new Date(), operateAccount);
        try {
            int updateNum = limitedPartnershipService.update(li);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_UPDATE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_UPDATE));
            }
        }catch (Exception e){
            LOGGER.error(">> update limitedPartnership throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /*
     * 删除有限合伙公司
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@RequestParam(value = "id", required = false)Integer id){
        try {
            int deleteNum = limitedPartnershipService.delete(id);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ALREADY_DELETE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ALREADY_DELETE));
            }
        }catch (Exception e){
            LOGGER.error(">> delete limitedPartnership throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    private LimitedPartnership setEntity(Integer id, String companyName, Date establishmentTime, Integer limitNum,
                                         String idcard, String companyAddress, String imagePrincipal, String imageBusinessLicence,
                                         String imageBank, String imageProfessionalLicense, Date createTime, String operateAccount) {
        LimitedPartnership li = new LimitedPartnership();
        if(id == null || id == 0){
            li.setId(null);
        }else{
            li.setId(id);
        }
        li.setCompanyName(companyName);
        li.setEstablishmentTime(establishmentTime);
        li.setLimitNum(limitNum);
        li.setIdcard(idcard);
        li.setCompanyAddress(companyAddress);
        li.setImageProfessionalLicense(imageProfessionalLicense);
        li.setImageBank(imageBank);
        li.setImagePrincipal(imagePrincipal);
        li.setImageBusinessLicence(imageBusinessLicence);
        li.setCreateTime(createTime);
        li.setOperateAccount(operateAccount);
        return li;
    }
}
