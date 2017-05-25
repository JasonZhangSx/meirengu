package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.rocketmq.RocketmqEvent;
import com.meirengu.uc.model.Inviter;
import com.meirengu.uc.service.InviterService;
import com.meirengu.utils.JacksonUtil;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/23/2017.
 */
@RestController
@RequestMapping("inviter")
public class InviterController extends BaseController{

    @Autowired
    private InviterService inviterService;

    private static final Logger logger = LoggerFactory.getLogger(InviterController.class);

    /**
     * @param pageNum 当前页
     * @param pageSize 每页显示的条数
     * @param sortBy 排序字段
     * @param order 升序/降序
     * @return
     */
    @RequestMapping(value = "list",method = {RequestMethod.POST})
    public Result list(@RequestParam(value="page", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value="per_page", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value="user_id", required = false) Integer userId,
                       @RequestParam(value="invited_user_id", required = false) Integer invitedUserId,
                       @RequestParam(value="sortby", required = false) String sortBy,
                       @RequestParam(value="order", required = false) String order){
        logger.info("邀请人奖励分页查询 userId:{} invitedUserId:{}" ,userId,invitedUserId);
        try {
            Map paramMap = new HashMap<String, Object>();
            paramMap.put("sortBy", sortBy);
            if(order != null){
                if(order.contains("desc") || order.contains("DESC")){
                    paramMap.put("order", "DESC");
                }
                if(order.contains("asc") || order.contains("ASC")){
                    paramMap.put("order", "ASC");
                }
            }
            if(!StringUtil.isEmpty(userId)){
                paramMap.put("userId", userId);
            }
            if(!StringUtil.isEmpty(invitedUserId)){
                paramMap.put("invitedUserId", invitedUserId);
            }
            Page<Inviter> page = super.setPageParams(pageNum,pageSize);
            page = inviterService.getListByPage(page, paramMap);
            if(page.getList().size() != 0){
                // 查看邀请人邀请分红
                inviterService.getReward(page.getList());
                return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.error("InviterController list throw exception:{}", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 获取邀请人详情
     * @param id
     * @param invitedUserId
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET})
    public Result detail(@RequestParam(value="id", required = false) Integer id,
                         @RequestParam(value="invited_user_id", required = false) Integer invitedUserId){
        logger.info("邀请人详情查询 userId:{} invitedUserId:{}" ,id,invitedUserId);
        try {
            if(StringUtil.isEmpty(id) && StringUtil.isEmpty(invitedUserId)){
                return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
            Inviter inviter = new Inviter();
            inviter.setId(id);
            inviter.setInvitedUserId(invitedUserId);
            inviter  = inviterService.detail(inviter);
            if(!StringUtil.isEmpty(inviter)){
                return super.setResult(StatusCode.OK, inviter, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.error("InviterController detail throw exception:{}", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 修改邀请信息
     * @param id
     * @param invitedUserId
     * @param invitedUserPhone
     * @param investTime
     * @param reward
     * @return
     */
    @RequestMapping(method = {RequestMethod.PUT})
    public Result update(@RequestParam(value = "id", required = false)Integer id,
                         @RequestParam(value = "invited_user_id", required = false)Integer invitedUserId,
                         @RequestParam(value = "invited_user_phone", required = false)String invitedUserPhone,
                         @RequestParam(value = "invest_time", required = false)Date investTime,
                         @RequestParam(value = "reward", required = false)BigDecimal reward){
        logger.info("邀请人信息修改 id:{} invitedUserId:{} invitedUserPhone：{} investTime:{}" ,id,invitedUserId,invitedUserPhone,investTime);
        try {
            if(StringUtil.isEmpty(id)  && StringUtil.isEmpty(invitedUserId) && StringUtil.isEmpty(invitedUserPhone)){
                return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
            Inviter inviter = new Inviter();
            inviter.setId(id);
            inviter.setInvitedUserId(invitedUserId);
            inviter.setInvitedUserPhone(invitedUserPhone);
            inviter.setInvestTime(investTime);
            inviter.setReward(reward);
            int result  = inviterService.update(inviter);
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.error("inviterController update throw exception:{}", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    @RequestMapping(value = "/{id}",method = {RequestMethod.DELETE})
    public Result delete(@PathVariable Integer id){
        logger.info("邀请人信息删除 id:{}" ,id);
        try {
            int result  = inviterService.delete(id);
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
            }
        }catch (Exception e){
            logger.error("inviterController delete throw exception:", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
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


    /** rocketMQ接收消息
     * 修改邀请人投资时间
     */
    @EventListener(condition = "#event.topic=='user' && #event.tag=='editInviter'")
    public void listeneditInviter(RocketmqEvent event) throws IOException {
        logger.info("ContactController listeneditInviter event :{} ",event.getMsg());
        String message = event.getMsg();
        Map<String,Object> map = (Map<String,Object>) JacksonUtil.readValue(message,Map.class);

        String invitedUserPhone = String.valueOf(map.get("invited_user_phone"));
        Integer invitedUserId = Integer.parseInt(String.valueOf(map.get("invited_user_id")));
        Date investTime = new Date((Long) map.get("invest_time"));

        Inviter inviter = new Inviter();
        inviter.setInvitedUserId(invitedUserId);
        inviter.setInvitedUserPhone(invitedUserPhone);
        inviter.setInvestTime(investTime);
        inviterService.update(inviter);
    }
}
