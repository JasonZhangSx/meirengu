package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.Inviter;
import com.meirengu.uc.service.InviterService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 4/1/2017.
 */
@RestController
@RequestMapping("inviteReward")
public class InviteRewardController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(InviteRewardController.class);

    @Autowired
    private InviterService inviterService;

    @RequestMapping(value = "notify",method = RequestMethod.GET)
    public Result notify(@RequestParam(value="file_name", required = true) String filePath) {
        try {

            List<Map<String,String>> investInfo =  ObjectToFile.readObject(filePath);
            // 根据用户id 获取邀请人信息 判断是否为空
            List<String[]> file = new ArrayList<>();
            for (Map map:investInfo){
                for(Object userId : map.keySet()){
                    Object investMoney = map.get(userId);

                    Inviter inviter = new Inviter();
                    inviter.setInvitedUserId(Integer.parseInt(userId+""));
                    inviter = inviterService.detail(inviter);
                    if(inviter!=null && !StringUtil.isEmpty(inviter.getId())){
                    // 根据注册时间  投资时间 校验是否有效期
                        Date registerTime = inviter.getRegisterTime();
                        Date investTime = inviter.getInvestTime();
                        try {
                            int i  = DateAndTime.dateDiff("dd",DateAndTime.convertDateToString(registerTime,"yyyy-MM-dd HH:mm:ss"),
                                    DateAndTime.convertDateToString(investTime,"yyyy-MM-dd HH:mm:ss"));

                            // 封装入结构 写入文件
                            if(i<34){
                                String[] arr = new String[3];
                                arr[0] = userId+"";
                                arr[1] = inviter.getUserId()+"";
                                arr[2] = investMoney+"";
                                file.add(arr);
                            }
                        }catch (Exception e){
                                logger.info("InviteRewardController.notify throws Exception :{} ",e.getMessage());
                        }

                    }
                }
            }
            ObjectToFile.writeArray(file, ConfigUtil.getConfig("INVITE.INVESTMONEY.FILEPATH")+"user."+ DateUtils.getCurrentDate()+".txt");

            // 去通知支付系统  异步 重试
            inviterService.noticePayment();
            return setResult(StatusCode.OK,null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.info("AddressController.redis get token result:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

}
