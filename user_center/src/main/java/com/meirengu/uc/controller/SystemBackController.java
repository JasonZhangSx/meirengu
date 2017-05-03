package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.Inviter;
import com.meirengu.uc.service.ContactService;
import com.meirengu.uc.service.InviterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 5/2/2017.
 */
@RequestMapping
@RestController
public class SystemBackController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(SystemBackController.class);

    @Autowired
    private ContactService contactService;
    @Autowired
    private InviterService inviterService;

    @RequestMapping(value = "paysuccess/callback",method = RequestMethod.POST)
    public Result CreateContactFile(@RequestParam(value = "item_id",required = true) String itemId,
                                    @RequestParam(value = "level_id",required = true) String levelId,
                                    @RequestParam(value = "order_id",required = true) String orderId,
                                    @RequestParam(value = "invited_user_id", required = true)String invitedUserId,
                                    @RequestParam(value = "invited_user_phone", required = true)String invitedUserPhone,
                                    @RequestParam(value = "invest_time", required = true)Date investTime) {
        logger.info("ContactController createContactFile itemId :{} levelId:{} userId:{}",itemId,levelId);
        try {

            Inviter inviter = new Inviter();
            inviter.setInvitedUserId(Integer.parseInt(invitedUserId));
            inviter.setInvitedUserPhone(invitedUserPhone);
            inviter.setInvestTime(investTime);
            inviterService.update(inviter);

            Map<String,String> map = new HashMap();
            map.put("itemId",itemId);
            map.put("levelId",levelId);
            map.put("userId",invitedUserId);
            map.put("orderId",orderId);
            Result result = contactService.CreateContactFile(map);
            if(result.getCode() == 200){
                return this.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return contactService.CreateContactFile(map);
            }
        }catch (Exception e){
            logger.error("ContactController createContactFile throws Exception :{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 格式化string类型时间
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
