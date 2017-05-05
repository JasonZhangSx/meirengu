package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @param type 1:收益众筹 2:股权众筹
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public Result CreateContactFile(@RequestParam(value = "item_id",required = true) String itemId,
                                    @RequestParam(value = "level_id",required = true) String levelId,
                                    @RequestParam(value = "order_id",required = true) String orderId,
                                    @RequestParam(value = "user_id",required = true) String userId,
                                    @RequestParam(value = "type",required = true) Integer type) {
        logger.info("ContactController createContactFile itemId :{} levelId:{} userId:{} type:{}",itemId,levelId,userId,type);
        try {

            if(type != 1 && type != 2){
                return this.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
            }
            if(type == 1){
                Map<String,String> map = new HashMap();
                map.put("itemId",itemId);
                map.put("levelId",levelId);
                map.put("userId",userId);
                map.put("orderId",orderId);
                Result result = contactService.CreateIncomeContactFile(map);
                if(result.getCode() == 200){
                    return this.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
                }else{
                    return contactService.CreateIncomeContactFile(map);
                }
            }
            if(type == 2){
                Map<String,String> map = new HashMap();
                map.put("itemId",itemId);
                map.put("levelId",levelId);
                map.put("userId",userId);
                map.put("orderId",orderId);
                return contactService.CreateEquityContactFile(map);
            }
        }catch (Exception e){
            logger.error("ContactController createContactFile throws Exception :{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
        return null;
    }

    /**
     * 查看合同文件
     * @param type 1:收益众筹 2:股权众筹
     * @return
     */
    @RequestMapping(value = "/view",method = RequestMethod.GET)
    public Result ViewContactFile(@RequestParam(value = "order_id",required = true) String orderId,
                                  @RequestParam(value = "type",required = true) Integer type) {
        logger.info("ContactController ViewContactFile orderId:{} type:{}",orderId,type);
        try {
            Map<String,String> map = new HashMap();
            map.put("orderId",orderId);
            List<Map<String,String>>  viewUrl=  contactService.ViewContactFile(map);
            if(viewUrl.size() == 0){
                if(type == 1){
                    Map<String,String> urlMap = new HashMap<String,String>();
                    urlMap.put("contractName","收益转让协议");
                    urlMap.put("url","https://api.meirenguvip.com/webview/html/usufruct_transfer.html");
                    viewUrl.add(urlMap);
                }else if(type == 2){
                    Map<String,String> urlMap = new HashMap<String,String>();
                    urlMap.put("contractName","合伙协议(美人谷)");
                    urlMap.put("url","https://api.meirenguvip.com/webview/html/usufruct_transfer.html");
                    Map<String,String> urlMap1 = new HashMap<String,String>();
                    urlMap1.put("contractName","股权收益权投资协议");
                    urlMap1.put("url","https://api.meirenguvip.com/webview/html/usufruct_transfer.html");
                    viewUrl.add(urlMap);
                    viewUrl.add(urlMap1);
                }else{

                }
            }
            return super.setResult(StatusCode.OK, viewUrl, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("ContactController ViewContactFile throws Exception :{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 下载合同文件
     * order_id
     * @return
     */
     @RequestMapping(value = "/down",method = RequestMethod.GET)
        public Result DownContactFile(@RequestParam(value = "order_id",required = true) String orderId) {
         logger.info("ContactController DownContactFile order_id :{}",orderId);
            try {

                Map<String,String> map = new HashMap();
                map.put("orderId",orderId);

                List<Map<String,String>> downUrl =  contactService.DownContactFile(map);
                return super.setResult(StatusCode.OK, downUrl, StatusCode.codeMsgMap.get(StatusCode.OK));
            }catch (Exception e){
                logger.info("ContactController DownContactFile throws Exception :{}",e.getMessage());
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        }
}
