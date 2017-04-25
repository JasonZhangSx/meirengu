package com.meirengu.news.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.news.common.Constants;
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
     * @param bulletinTitle
     * @param bulletinContent
     * @param operateAccount
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert (@RequestParam(value = "bulletin_title", required = false) String bulletinTitle,
                                      @RequestParam(value = "bulletin_content", required = false) String bulletinContent,
                                      @RequestParam(value = "operate_account", required = false) String operateAccount
                                      ){
        if(StringUtils.isEmpty(bulletinTitle) || StringUtils.isEmpty(bulletinContent) || StringUtils.isEmpty(operateAccount)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        Bulletin bulletin = new Bulletin();
        bulletin.setBulletinTitle(bulletinTitle);
        bulletin.setBulletinContent(bulletinContent);
        bulletin.setOperateAccount(operateAccount);
        bulletin.setStatus(Constants.BULLETIN_HIDE);//默认下架
        try{
            int i = blletinService.insert(bulletin);
            if(i > 0){
                return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return setResult(StatusCode.BULLETIN_ERROR_INSERT, null, StatusCode.codeMsgMap.get(StatusCode.BULLETIN_ERROR_INSERT));
            }
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改公告
     * @param bulletinTitle
     * @param bulletinContent
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{bulletin_id}", method = RequestMethod.POST)
    public Result insert (@PathVariable("bulletin_id") Integer bulletinId ,
                          @RequestParam(value = "bulletin_title", required = false) String bulletinTitle,
                          @RequestParam(value = "bulletin_content", required = false) String bulletinContent,
                          @RequestParam(value = "status", required = false) Integer status){
        if(StringUtils.isEmpty(bulletinTitle) && StringUtils.isEmpty(bulletinContent) && status == null){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        Bulletin bulletin = new Bulletin();
        bulletin.setBulletinId(bulletinId);
        if (StringUtils.isNotBlank(bulletinTitle)) {
            bulletin.setBulletinTitle(bulletinTitle);
        }
        if (StringUtils.isNotBlank(bulletinContent)) {
            bulletin.setBulletinContent(bulletinContent);
        }
        if (status != null) {
            bulletin.setStatus(status);
        }
        try{
            int i = blletinService.update(bulletin);
            if(i > 0){
                return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return setResult(StatusCode.BULLETIN_ERROR_INSERT, null, StatusCode.codeMsgMap.get(StatusCode.BULLETIN_ERROR_INSERT));
            }
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }



    /**
     * 获取公告列表
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "sortby", required = false) String sortBy,
                       @RequestParam(value = "order", required = false) String order,
                       @RequestParam(value = "status", required = false) Integer status){
        Map paramMap = new HashMap<String, Object>();
        Page<Bulletin> page = super.setPageParams(pageNum, pageSize);
        paramMap.put("sortBy", sortBy);
        paramMap.put("order", order);
        paramMap.put("status", status);
        try {
            page = blletinService.getPageList(page, paramMap);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @RequestMapping(value = "/{bulletin_id}")
    public Result detail(@PathVariable("bulletin_id") int bulletinId) {
        try {
            Map<String, Object> bulletinMap = blletinService.detail(bulletinId);
            return setResult(StatusCode.OK, bulletinMap, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

}
