package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.rocketmq.RocketmqEvent;
import com.meirengu.uc.common.Constants;
import com.meirengu.uc.model.Contract;
import com.meirengu.uc.service.ContactService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 电子合同controller
 * Created by huoyan403 on 4/11/2017.
 */
@RestController
@RequestMapping("contract")
public class ContractController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private ContactService contactService;


    /** rocketMQ接收消息
     * 生成盖章合同 并创建保全  并把保全文本下载到本地一份
     */
    @EventListener(condition = "#event.topic=='user' && #event.tag=='createContract'")
    public void listenCreateContactFile(RocketmqEvent event) throws IOException {
        logger.info("ContactController listenCreateContactFile event :{} ",event.getMsg());
        String message = event.getMsg();
        Map<String,Object> map = (Map<String,Object>)JacksonUtil.readValue(message,Map.class);

        List<Contract> contractList=  contactService.selectContactFile(String.valueOf(map.get("order_id")));
        if(contractList.size() != 0){
            logger.info("消息重复消费:{}",event.getMsg());
        }else{
            String itemId = String.valueOf(map.get("item_id"));
            String levelId = String.valueOf(map.get("level_id"));
            String userId = String.valueOf(map.get("user_id"));
            String orderId = String.valueOf(map.get("order_id"));
            Integer type = Integer.parseInt(String.valueOf(map.get("type")));

            Result result = this.createContactFile(itemId,levelId,userId,orderId,type);
            if(result.getCode() != StatusCode.OK){
                this.createContactFile(itemId,levelId,userId,orderId,type);
            }
        }
    }


    /**生成盖章合同 并创建保全  并把保全文本下载到本地一份
     * @param itemId  项目id
     * @param levelId  档位id
     * @param userId   投资人id
     * @param itemId
     * @param type 1:产品众筹    2:收益众筹   3:股权众筹
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public Result createContactFile(@RequestParam(value = "item_id",required = true) String itemId,
                                    @RequestParam(value = "level_id",required = true) String levelId,
                                    @RequestParam(value = "order_id",required = true) String orderId,
                                    @RequestParam(value = "user_id",required = true) String userId,
                                    @RequestParam(value = "type",required = true) Integer type) {
        logger.info("ContactController createContactFile itemId :{} levelId:{} userId:{} type:{}",itemId,levelId,userId,type);
        try {

            if(type != 1 && type != 2  && type != 3  ){
                return this.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
            }
            if(type == 2){
                Map<String,String> map = new HashMap();
                map.put("itemId",itemId);
                map.put("levelId",levelId);
                map.put("userId",userId);
                map.put("orderId",orderId);
                return contactService.CreateIncomeContactFile(map);
            }
            if(type == 3){
                Map<String,String> map = new HashMap();
                map.put("itemId",itemId);
                map.put("levelId",levelId);
                map.put("userId",userId);
                map.put("orderId",orderId);


                Result result1 = contactService.CreateHHXYContactFile(map);//合伙协议


                Result result = contactService.CreateEquityContactFile(map);//股权协议
//                if(result.getCode() == StatusCode.OK){
//                    return this.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
//                }else{
//                    return contactService.CreateIncomeContactFile(map);
//                }
            }
        }catch (Exception e){
            logger.error("ContactController createContactFile throws Exception :{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
        return null;
    }

    /**
     * 查看合同文件
     * @param type type 1:产品众筹    2:收益众筹   3:股权众筹
     * @return generate 1已生成 0未生成
     */
    @RequestMapping(value = "/view",method = RequestMethod.GET)
    public Result viewContactFile(@RequestParam(value = "item_id",required = false) String itemId,
                                  @RequestParam(value = "order_id",required = true) String orderId,
                                  @RequestParam(value = "type",required = true) Integer type) {
        logger.info("ContactController ViewContactFile orderId:{} type:{}",orderId,type);
        try {
            Map<String,String> map = new HashMap();
            map.put("orderId",orderId);
            List<Map<String,String>>  viewUrl=  contactService.ViewContactFile(map);
            if(viewUrl.size() == 0){
                if(type == 1){

                }else if(type == 2){
                    Map<String,String> urlMap = new HashMap<String,String>();
                    urlMap.put("contractName",ConfigUtil.getConfig("SYZR_FULLNAME"));
                    urlMap.put("generate", Constants.ZERO_STRING);
                    urlMap.put("url", ConfigUtil.getConfig("USUFRUCT_TRANSFER"));
                    viewUrl.add(urlMap);
                }else if(type == 3){
                    Map<String,String> urlMap = new HashMap<String,String>();
                    urlMap.put("contractName",ConfigUtil.getConfig("HHXY_FULLNAME"));
                    urlMap.put("generate",Constants.ZERO_STRING);
                    urlMap.put("url",ConfigUtil.getConfig("PARTNERSHIP_AGREEMENT"));
                    Map<String,String> urlMap1 = new HashMap<String,String>();
                    urlMap1.put("contractName",ConfigUtil.getConfig("GQZR_FULLNAME"));
                    urlMap1.put("generate",Constants.ZERO_STRING);
                    urlMap1.put("url",ConfigUtil.getConfig("EQUITY_INVESTMENT"));
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
        public Result downContactFile(@RequestParam(value = "order_id",required = true) String orderId) {
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
