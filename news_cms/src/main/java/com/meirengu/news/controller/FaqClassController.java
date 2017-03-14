package com.meirengu.news.controller;

import com.meirengu.news.common.StatusCode;
import com.meirengu.news.model.FaqClass;
import com.meirengu.news.model.Page;
import com.meirengu.news.po.ListAllFaqClassPo;
import com.meirengu.news.service.FaqClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 3/10/2017.
 */
@Controller
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
    @ResponseBody
    @RequestMapping(value = "list", method = {RequestMethod.POST})
    public Map<String, Object> list(@RequestParam(value="page", required = false, defaultValue = "1") int pageNum,
                                    @RequestParam(value="per_page", required = false, defaultValue = "10") int pageSize){
        Map paramMap = new HashMap<String, Object>();
        Page<FaqClass> page = super.setPageParams(pageSize,pageNum);
        try{
            page = faqClassService.getPageList(page, paramMap);
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

    /**
     * 保存分类
     * @param className
     * @param operateAccount
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public Map<String, Object> list(@RequestParam(value="class_name", required = true) String className,
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
                    return super.setReturnMsg(StatusCode.STATUS_200, null, "ok");
                }else{
                    return super.setReturnMsg(StatusCode.STATUS_500, null, "服务器添加失败");
                }
            }else{
                return super.setReturnMsg(StatusCode.STATUS_400, null, "分类不能重复");
            }
        }catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setReturnMsg(StatusCode.STATUS_500, null, e.getMessage());
        }
    }

    /**
     * 获取所有分类
     */
    @ResponseBody
    @RequestMapping(value = "listfaqclass", method = {RequestMethod.POST})
    public Map<String, Object> list(){
        List<ListAllFaqClassPo> listAllFaqClassPo = faqClassService.listAllFaqClass();
        return super.setReturnMsg(StatusCode.STATUS_200, listAllFaqClassPo, "ok");
    }

    @ResponseBody
    @RequestMapping(value = "status/update", method = {RequestMethod.PUT})
    public Map<String, Object> updateStatus(@RequestParam(value="class_id", required = true) Integer classId,
                                            @RequestParam(value="status", required = true) Byte status,
                                            @RequestParam(value="operate_account", required = true) String operateAccount){
        Integer count = faqClassService.updateStatus(classId,status,operateAccount);
        if(count == 0){
            return super.setReturnMsg(StatusCode.STATUS_501, null, "数据为空");
        }else{
            return super.setReturnMsg(StatusCode.STATUS_200, null, "ok");
        }
    }
}
