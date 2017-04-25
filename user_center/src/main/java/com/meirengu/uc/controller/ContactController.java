package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.service.ContactService;
import com.meirengu.uc.utils.GetIPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 电子合同controller
 * Created by huoyan403 on 4/11/2017.
 */
@RestController
@RequestMapping("contact")
public class ContactController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    /**生成盖章合同 并创建保全  并把保全文本下载到本地一份
     * @param itemId  项目id
     * @param levelId  档位id
     * @param userId   投资人id
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public Result CreateContactFile(@RequestParam(value = "item_id",required = true) String itemId,
                                    @RequestParam(value = "level_id",required = true) String levelId,
                                    @RequestParam(value = "user_id",required = true) String userId) {
        logger.info("ContactController createContactFile itemId :{} levelId:{} userId:{}",itemId,levelId,userId);
        try {
            Map<String,String> map = new HashMap();
            map.put("itemId",itemId);
            map.put("levelId",levelId);
            map.put("userId",userId);
            return contactService.CreateContactFile(map);
        }catch (Exception e){
            logger.error("ContactController createContactFile throws Exception :{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 查看合同文件
     * @param itemId
     * @param levelId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/view",method = RequestMethod.GET)
    public Result ViewContactFile(@RequestParam(value = "item_id",required = true) String itemId,
                                    @RequestParam(value = "level_id",required = true) String levelId,
                                    @RequestParam(value = "user_id",required = true) String userId) {
        logger.info("ContactController ViewContactFile itemId :{} levelId:{} userId:{}",itemId,levelId,userId);
        try {
            Map<String,String> map = new HashMap();
            map.put("itemId",itemId);
            map.put("levelId",levelId);
            map.put("userId",userId);
            List<String> viewUrl =  contactService.ViewContactFile(map);
            return super.setResult(StatusCode.OK, viewUrl, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("ContactController ViewContactFile throws Exception :{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 下载合同文件
     * @param itemId
     * @param levelId
     * @param userId
     * @return
     */
     @RequestMapping(value = "/down",method = RequestMethod.GET)
        public Result DownContactFile(@RequestParam(value = "item_id",required = true) String itemId,
                                        @RequestParam(value = "level_id",required = true) String levelId,
                                        @RequestParam(value = "user_id",required = true) String userId) {
         logger.info("ContactController DownContactFile itemId :{} levelId:{} userId:{}",itemId,levelId,userId);
            try {
                Map<String,String> map = new HashMap();
                map.put("itemId",itemId);
                map.put("levelId",levelId);
                map.put("userId",userId);
                List<String> downUrl =  contactService.DownContactFile(map);
                return super.setResult(StatusCode.OK, downUrl, StatusCode.codeMsgMap.get(StatusCode.OK));
            }catch (Exception e){
                logger.info("ContactController DownContactFile throws Exception :{}",e.getMessage());
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        }
}
