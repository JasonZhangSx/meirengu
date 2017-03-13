package com.meirengu.news.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.news.model.Bulletin;
import com.meirengu.news.model.Page;
import com.meirengu.news.service.BulletinService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 公告控制类
 * Created by maoruxin on 2017/3/10.
 */
@RestController
@RequestMapping("/bulletin")
public class BulletinController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BulletinController.class);

    @Autowired
    private BulletinService blletinService;

    /**
     * 新增公告
     * @param bulletin_title    公告标题
     * @param bulletin_content  公告内容
     * @param operate_account   操作人账号
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert (@RequestParam(value = "bulletin_title", required = false) String bulletinTitle,
                                      @RequestParam(value = "bulletin_content", required = false) String bulletinContent,
                                      @RequestParam(value = "operate_account", required = false) Integer operateAccount
                                      ){
        if(StringUtils.isEmpty(bulletinTitle)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        if(StringUtils.isEmpty(bulletinContent)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        if(operateAccount==null && operateAccount==0){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        try{
            int i = blletinService.insert(bulletinTitle,bulletinContent,operateAccount);
            if(i > 0){
                return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return setResult(StatusCode.BULLETIN_ERROR_INSERT, null, StatusCode.codeMsgMap.get(StatusCode.BULLETIN_ERROR_INSERT));
            }
        }catch (Exception e){
            logger.error("throw exception:", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 获取公告列表
     * @param page
     * @param per_page
     * @param sortby
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "sortby", required = false) String sortBy,
                       @RequestParam(value = "order", required = false) String order){
        Map paramMap = new HashMap<String, Object>();
        Page<Bulletin> page = super.setPageParams(pageNum, pageSize);
        paramMap.put("sortBy", sortBy);
        paramMap.put("order", order);
        try {
            page = blletinService.getPageList(page, paramMap);
            if(page.getList().size() != 0){
                return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return setResult(StatusCode.BULLETIN_ERROR_LIST, null, StatusCode.codeMsgMap.get(StatusCode.BULLETIN_ERROR_LIST));
            }
        }catch (Exception e){
            logger.error("throw exception:", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

}
