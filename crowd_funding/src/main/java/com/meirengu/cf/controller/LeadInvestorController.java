package com.meirengu.cf.controller;

import com.meirengu.cf.model.LeadInvestor;
import com.meirengu.cf.service.LeadInvestorService;
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
 * 领投人控制层
 * @author 建新
 * @create 2017-05-08 14:21
 */
@Controller
@RequestMapping("investor")
public class LeadInvestorController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(LeadInvestorController.class);

    @Autowired
    LeadInvestorService leadInvestorService;

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
            Page<LeadInvestor> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = leadInvestorService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = leadInvestorService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    /**
     * 插入领投人
     * @param investorName
     * @param investorType
     * @param investorBusinessLicence
     * @param investorIdcard
     * @param investorAddress
     * @param investorTelphone
     * @param investorImage
     * @param operateAccount
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "investor_name", required = false) String investorName,
                         @RequestParam(value = "investor_type", required = false) Integer investorType,
                         @RequestParam(value = "principal_name", required = false) String principalName,
                         @RequestParam(value = "investor_business_licence", required = false) String investorBusinessLicence,
                         @RequestParam(value = "investor_idcard", required = false) String investorIdcard,
                         @RequestParam(value = "investor_address", required = false) String investorAddress,
                         @RequestParam(value = "investor_telphone", required = false) String investorTelphone,
                         @RequestParam(value = "investor_image", required = false) String investorImage,
                         @RequestParam(value = "investor_introduction", required = false) String investorIntroduction,
                         @RequestParam(value = "investor_company", required = false) String investorCompany,
                         @RequestParam(value = "investor_position", required = false) String investorPosition,
                         @RequestParam(value = "investor_idea", required = false) String investorIdea,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){

        LeadInvestor li = setEntity(0, investorName, investorType, investorBusinessLicence,
                investorIdcard, investorAddress, investorTelphone, investorImage, new Date(), operateAccount, principalName,
                investorIntroduction, investorCompany, investorPosition, investorIdea);
        try {
            int insertNum = leadInvestorService.insert(li);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> insert lead investor throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 获取领投人信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "id", required = false)Integer id){
        try {
            LeadInvestor p = leadInvestorService.detail(id);
            return super.setResult(StatusCode.OK, p, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get lead investor throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改领投人
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "id", required = false) Integer id,
                         @RequestParam(value = "investor_name", required = false) String investorName,
                         @RequestParam(value = "investor_type", required = false) Integer investorType,
                         @RequestParam(value = "principal_name", required = false) String principalName,
                         @RequestParam(value = "investor_business_licence", required = false) String investorBusinessLicence,
                         @RequestParam(value = "investor_idcard", required = false) String investorIdcard,
                         @RequestParam(value = "investor_address", required = false) String investorAddress,
                         @RequestParam(value = "investor_telphone", required = false) String investorTelphone,
                         @RequestParam(value = "investor_image", required = false) String investorImage,
                         @RequestParam(value = "investor_introduction", required = false) String investorIntroduction,
                         @RequestParam(value = "investor_company", required = false) String investorCompany,
                         @RequestParam(value = "investor_position", required = false) String investorPosition,
                         @RequestParam(value = "investor_idea", required = false) String investorIdea,
                         @RequestParam(value = "operate_account", required = false) String operateAccount ){

        LeadInvestor li = setEntity(id, investorName, investorType, investorBusinessLicence,
                investorIdcard, investorAddress, investorTelphone, investorImage, new Date(), operateAccount, principalName,
                investorIntroduction, investorCompany, investorPosition, investorIdea);
        try {
            int updateNum = leadInvestorService.update(li);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_UPDATE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_UPDATE));
            }
        }catch (Exception e){
            LOGGER.error(">> update lead investor throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /*
     * 删除领投人
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@RequestParam(value = "id", required = false)Integer id){
        try {
            int deleteNum = leadInvestorService.delete(id);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ALREADY_DELETE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ALREADY_DELETE));
            }
        }catch (Exception e){
            LOGGER.error(">> delete lead investor detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    private LeadInvestor setEntity(Integer id, String investorName, Integer investorType, String investorBusinessLicence,
                                    String investorIdcard, String investorAddress, String investorTelphone, String investorImage,
                                    Date createTime, String operateAccount, String principalName, String investorIntroduction,
                                   String investorCompany, String investorPosition, String investorIdea) {
        LeadInvestor li = new LeadInvestor();
        if(id == null || id == 0){
            li.setId(null);
        }else{
            li.setId(id);
        }
        li.setInvestorName(investorName == null ? "" : investorName);
        li.setInvestorType(investorType);
        li.setInvestorBusinessLicence(investorBusinessLicence == null ? "" : investorBusinessLicence);
        li.setInvestorIdcard(investorIdcard == null ? "" : investorIdcard);
        li.setInvestorAddress(investorAddress == null ? "" : investorAddress);
        li.setInvestorTelphone(investorTelphone == null ? "" : investorTelphone);
        li.setInvestorImage(investorImage == null ? "" : investorImage);
        li.setCreateTime(createTime);
        li.setOperateAccount(operateAccount == null ? "" : operateAccount);
        li.setPrincipalName(principalName == null ? "" : principalName);
        li.setInvestorIntroduction(investorIntroduction == null ? "" : investorIntroduction);
        li.setInvestorCompany(investorCompany == null ? "" : investorCompany);
        li.setInvestorPosition(investorPosition == null ? "" : investorPosition);
        li.setInvestorIdea(investorIdea == null ? "" : investorIdea);
        return li;
    }
}
