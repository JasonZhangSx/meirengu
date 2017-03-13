package com.meirengu.news.controller;

import com.meirengu.mall.common.StatusCode;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/10/2017.
 */
@Controller
@RequestMapping("/faq")
public class FaqController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

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
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public Map<String, Object> save(@RequestParam(value="class_id", required = true) Integer classId,
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
                    return super.setReturnMsg(StatusCode.STATUS_200, null, "ok");
                }else{
                    return super.setReturnMsg(StatusCode.STATUS_500, null, "服务器添加失败");
                }
            }else{
                return super.setReturnMsg(StatusCode.STATUS_400, null, "问题不能重复");
            }
        }catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setReturnMsg(StatusCode.STATUS_500, null, e.getMessage());
        }
    }

    /**
     * 根据问题编号获取问题详情
     * @param faqId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get", method = {RequestMethod.POST})
    public Map<String, Object> get(@RequestParam(value="faq_id", required = true) Integer faqId){
        Faq faq = faqService.getFaqById(faqId);
        if(StringUtil.isEmpty(faq)){
            return super.setReturnMsg(StatusCode.STATUS_501, null, "数据为空");
        }else{
            return super.setReturnMsg(StatusCode.STATUS_200, faq, "ok");
        }
    }

    /**
     * 分页获取问题详情
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = {RequestMethod.POST})
    public Map<String, Object> list(@RequestParam(value="page", required = false, defaultValue = "1") int pageNum,
                                    @RequestParam(value="per_page", required = false, defaultValue = "10") int pageSize){
        Map paramMap = new HashMap<String, Object>();
        Page<Faq> page = super.setPageParams(pageSize,pageNum);
        try{
            page = faqService.getPageList(page, paramMap);
            if(page.getList().size() != 0){
                return super.setReturnMsg(StatusCode.STATUS_200, page, StatusCode.STATUS_200_MSG);
            }else{
                return super.setReturnMsg(StatusCode.STATUS_501, page, StatusCode.STATUS_501_MSG);
            }
        }catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setReturnMsg(StatusCode.STATUS_400, null, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "status/update", method = {RequestMethod.PUT})
    public Map<String, Object> updateStatus(@RequestParam(value="faq_id", required = true) Integer faqId,
                                            @RequestParam(value="status", required = true) Byte status,
                                            @RequestParam(value="operate_account", required = true) String operateAccount){
        Integer count = faqService.updateStatus(faqId,status,operateAccount);
        if(count == 0){
            return super.setReturnMsg(StatusCode.STATUS_501, null, "数据为空");
        }else{
            return super.setReturnMsg(StatusCode.STATUS_200, null, "ok");
        }
    }
}
