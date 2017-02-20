package com.meirengu.mall.controller;

import com.meirengu.controller.BaseController;
import com.meirengu.mall.common.Constants;
import com.meirengu.common.StatusCode;
import com.meirengu.mall.model.Case;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.service.CaseService;
import com.meirengu.mall.utils.ConfigUtil;
import com.meirengu.mall.utils.UploadUtil;
import com.meirengu.model.Result;
import com.meirengu.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 案例controller
 *
 * @author 建新
 * @create 2017-01-19 11:33
 */
@Controller
@RequestMapping("case")
public class CaseController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CaseService caseService;


    /**
     * 新增案例
     * @param itemId
     * @param doctorId
     * @param hospitalId
     * @param icId
     * @param name
     * @param desc
     * @param sort
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "doctor_id", required = false) Integer doctorId,
                         @RequestParam(value = "hospital_id", required = false) Integer hospitalId,
                         @RequestParam(value = "ic_id", required = false) Integer icId,
                         @RequestParam(value = "case_name", required = false) String name,
                         @RequestParam(value = "case_desc", required = false) String desc,
                                      /*@RequestParam(value = "before_pic", required = false) String beforePic,
                                      @RequestParam(value = "after_pic", required = false) String afterPic,*/
                                      @RequestParam(value = "case_sort", required = false) Integer sort,
                         HttpServletRequest request){



        if(null == itemId || null == doctorId || null == hospitalId || null == icId || null == sort || StringUtil.isEmpty(name)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        Map map = UploadUtil.uploadPic(request, "before_pic",  ConfigUtil.getCaseSavePath()+hospitalId+"/"+doctorId+"/", ConfigUtil.getCaseShowPath(), "375x480");
        Map map2 = UploadUtil.uploadPic(request, "after_pic",  ConfigUtil.getCaseSavePath()+hospitalId+"/"+doctorId+"/", ConfigUtil.getCaseShowPath(), "375x480");

        String beforePic = map.get("path").toString();
        String afterPic = map2.get("path").toString();

        Case c = new Case();
        c.setItemId(itemId);
        c.setDoctorId(doctorId);
        c.setHospitalId(hospitalId);
        c.setIcId(icId);
        c.setName(name);
        c.setDesc(desc);
        c.setBeforePic(beforePic);
        c.setAfterPic(afterPic);
        c.setTime(new Date());
        c.setState(1);
        c.setSort(sort);
        boolean b = caseService.add(c);
        if(b){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    /**
     * 修改案例
     * @param caseId
     * @param itemId
     * @param doctorId
     * @param hospitalId
     * @param icId
     * @param name
     * @param desc
     * @param beforePic
     * @param afterPic
     * @param sort
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestParam(value = "case_id", required = false) Integer caseId,
                                      @RequestParam(value = "item_id", required = false) Integer itemId,
                                      @RequestParam(value = "doctor_id", required = false) Integer doctorId,
                                      @RequestParam(value = "hospital_id", required = false) Integer hospitalId,
                                      @RequestParam(value = "ic_id", required = false) Integer icId,
                                      @RequestParam(value = "case_name", required = false) String name,
                                      @RequestParam(value = "case_desc", required = false) String desc,
                                      @RequestParam(value = "before_pic", required = false) String beforePic,
                                      @RequestParam(value = "after_pic", required = false) String afterPic,
                                      @RequestParam(value = "case_sort", required = false) Integer sort){

        if(null == caseId || null == itemId || null == doctorId || null == hospitalId || null == icId || null == sort){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

       /* if(StringUtils.isEmpty(name)){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "case_name"));
        }

        if(StringUtils.isEmpty(beforePic)){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "before_pic"));
        }

        if(StringUtils.isEmpty(afterPic)){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "after_pic"));
        }
        */

        Case c = new Case();
        c.setId(caseId);
        c.setItemId(itemId);
        c.setDoctorId(doctorId);
        c.setHospitalId(hospitalId);
        c.setIcId(icId);
        c.setName(name);
        c.setDesc(desc);
        c.setBeforePic(beforePic);
        c.setAfterPic(afterPic);
        c.setState(1);
        c.setSort(sort);
        int caseUpdateNum = caseService.update(c);
        if(caseUpdateNum == 1){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else if(caseUpdateNum == 2){
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }else {
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 案例详情
     * @param caseId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{case_id}", method = RequestMethod.GET)
    public Result detail(@PathVariable("case_id") Integer caseId){
        Case c = new Case();
        c.setId(caseId);
        Case cas = caseService.detail(c);
        if(cas == null){
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }else {
            return super.setResult(StatusCode.OK, cas, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    /**
     * 获取列表
     * @param pageNow
     * @param pageSize
     * @param hospitalId
     * @param doctorId
     * @param itemId
     * @param acId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false) Integer pageSize,
                                    @RequestParam(value = "page", required = false) Integer pageNow,
                                    @RequestParam(value = "hospital_id", required = false, defaultValue = "0") Integer hospitalId,
                                    @RequestParam(value = "doctor_id", required = false, defaultValue = "0") Integer doctorId,
                                    @RequestParam(value = "item_id", required = false, defaultValue = "0") Integer itemId,
                                    @RequestParam(value = "ac_id", required = false, defaultValue = "0") Integer acId){

        if(pageNow == null){
            pageNow = Constants.PAGE_NUM_DEFAULT;
        }

        if(pageSize == null){
            pageSize = Constants.PAGE_SIZE_DEFAULT;
        }

        Page page = new Page<Case>();
        page.setPageNow(pageNow);
        page.setPageSize(pageSize);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hospitalId", hospitalId);
        paramMap.put("doctorId", doctorId);
        paramMap.put("itemId", itemId);
        paramMap.put("acId", acId);
        page = caseService.getPageList(page, paramMap);
        if(page.getList().size() > 0){
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }

    }


}
