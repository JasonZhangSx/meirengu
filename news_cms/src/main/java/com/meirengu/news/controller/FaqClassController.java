package com.meirengu.news.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.news.model.FaqClass;
import com.meirengu.news.model.Page;
import com.meirengu.news.po.ListAllFaqClassPo;
import com.meirengu.news.service.FaqClassService;
import com.meirengu.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 3/10/2017.
 */
@RestController
@RequestMapping("/faqclass")
public class FaqClassController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(FaqClassController.class);

    @Autowired
    FaqClassService faqClassService;

    /**
     * 分页获取所有分类内容
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET})
    public Result list(@RequestParam(value="page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value="per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value="class_id", required = false) Integer classId,
                       @RequestParam(value="status", required = false) Integer status,
                       @RequestParam(value="sortby", required = false) String sortBy,
                       @RequestParam(value="order", required = false) String order){
        Map paramMap = new HashMap<String, Object>();
        Page<FaqClass> page = super.setPageParams(pageNum,pageSize);
        paramMap.put("classId", classId);
        paramMap.put("status", status);
        paramMap.put("sortBy", sortBy);
        paramMap.put("order", order);
        try{
            page = faqClassService.getPageList(page, paramMap);
            if(page.getList().size() != 0){
                return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 保存分类
     * @param className
     * @param operateAccount
     * @return
     */
    @RequestMapping(value = "insert", method = {RequestMethod.POST})
    public Result list(@RequestParam(value="class_name", required = true) String className,
                       @RequestParam(value="operate_account", required = true) String operateAccount){
        FaqClass faqClass = new FaqClass();
        faqClass.setClassName(className);
        faqClass.setOperateAccount(operateAccount);
        try {
            //判断分类是否重复
            int count = faqClassService.getFaqClass(faqClass);
            if(count == 0){
                int result = faqClassService.insert(faqClass);
                if(result == 1){
                    return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
                }else{
                    return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
                }
            }else{
                return super.setResult(StatusCode.CLASS_IS_REPEATED, null, StatusCode.codeMsgMap.get(StatusCode.CLASS_IS_REPEATED));
            }
        }catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 获取所有分类
     */
    @RequestMapping(value = "listall", method = {RequestMethod.GET})
    public Result list( @RequestParam(value="status", required = true) Byte status){
        FaqClass faqClass = new FaqClass();
        faqClass.setStatus(status);
        List<ListAllFaqClassPo> listAllFaqClassPo = faqClassService.listAllFaqClass(faqClass);
        return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(listAllFaqClassPo,List.class,ListAllFaqClassPo.class), StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    @RequestMapping(value = "update", method = {RequestMethod.PUT})
    public Result updateStatus(@RequestParam(value="class_id", required = true) Integer classId,
                                            @RequestParam(value="status", required = false) Byte status,
                                            @RequestParam(value="class_name", required = false) String className,
                                            @RequestParam(value="operate_account", required = false) String operateAccount){
        Integer count = faqClassService.updateStatus(classId,status,operateAccount,className);
        if(count == 0){
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }else{
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }
    @RequestMapping(value = "get", method = {RequestMethod.GET})
    public Result get(@RequestParam(value="class_id", required = true) Integer classId){

        try {
            Map<String, Object> map = faqClassService.detail(classId);
            return super.setResult(StatusCode.OK, map, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }
    @RequestMapping(value = "/{classId}",method = {RequestMethod.DELETE})
    public Result delete(@PathVariable Integer classId){

        try {
            int result = faqClassService.delete(classId);
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
            }
        }catch (Exception e){
            LOGGER.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

}
