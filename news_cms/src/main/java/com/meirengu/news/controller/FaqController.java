package com.meirengu.news.controller;

import com.meirengu.model.Result;
import com.meirengu.common.StatusCode;
import com.meirengu.news.model.Faq;
import com.meirengu.news.model.Page;
import com.meirengu.news.service.FaqService;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/10/2017.
 */
@Controller
@RequestMapping("/faq")
public class FaqController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(FaqController.class);

    @Autowired
    FaqService faqService;

    /**
     * 保存常见问题
     * @param classId
     * @param className
     * @param faqQuestion
     * @param faqAnswer
     * @param operateAccount
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insert", method = {RequestMethod.POST})
    public Result save(@RequestParam(value="class_id", required = true) Integer classId,
                       @RequestParam(value="class_name", required = true) String className,
                       @RequestParam(value="faq_question", required = true) String faqQuestion,
                       @RequestParam(value="faq_answer", required = true) String faqAnswer,
                       @RequestParam(value="operate_account", required = true) String operateAccount){
        Faq faq = new Faq();
        faq.setClassId(classId);
        faq.setClassName(className);
        faq.setFaqQuestion(faqQuestion);
        faq.setFaqAnswer(faqAnswer);
        faq.setOperateAccount(operateAccount);
        try {
            //判断问题是否重复
            int count = faqService.getFaq(faq);
            if(count == 0){
                int result = faqService.insert(faq);
                if(result == 1){
                    return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
                }else{
                    return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
                }
            }else{
                return super.setResult(StatusCode.QUESTION_IS_REPEATED, null, StatusCode.codeMsgMap.get(StatusCode.QUESTION_IS_REPEATED));
            }
        }catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 根据问题编号获取问题详情
     * @param faqId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get", method = {RequestMethod.GET})
    public Result get(@RequestParam(value="faq_id", required = true) Integer faqId){
        Faq faq = faqService.getFaqById(faqId);
        if(!StringUtil.isEmpty(faq)){
            return super.setResult(StatusCode.OK, faq, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else{
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }
    }

    /**
     * 分页获取问题详情
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = {RequestMethod.GET})
    public Result list(@RequestParam(value="page", required = false, defaultValue = "1") int pageNum,
                                    @RequestParam(value="per_page", required = false, defaultValue = "10") Integer pageSize,
                                    @RequestParam(value="class_id", required = false) Integer classId,
                                    @RequestParam(value="status", required = false) Integer status){
        Map paramMap = new HashMap<String, Object>();
        paramMap.put("classId",classId);
        paramMap.put("status",status);
        Page<Faq> page = super.setPageParams(pageNum,pageSize);
        try{
            page = faqService.getPageList(page, paramMap);
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

    @ResponseBody
    @RequestMapping(value = "update", method = {RequestMethod.PUT})
    public Result updateStatus(@RequestParam(value="faq_id", required = true) Integer faqId,
                                            @RequestParam(value="status", required = true) Byte status,
                                            @RequestParam(value="faq_question", required = true) String faqQuestion,
                                            @RequestParam(value="faq_answer", required = true) String faqAnswer,
                                            @RequestParam(value="class_id", required = true) Integer classId,
                                            @RequestParam(value="class_name", required = true) String className,
                                            @RequestParam(value="operate_account", required = true) String operateAccount){
        Faq faq = new Faq();
        faq.setFaqId(faqId);
        faq.setOperateAccount(operateAccount);
        faq.setUpdateTime(new Date());
        faq.setStatus(status);
        faq.setFaqQuestion(faqQuestion);
        faq.setFaqAnswer(faqAnswer);
        faq.setClassId(classId);
        faq.setClassName(className);

        int result = faqService.update(faq);
        if(result == 0){
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }else{
            return super.setResult(StatusCode.OK,null , StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }
}
