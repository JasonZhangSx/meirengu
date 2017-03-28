package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.uc.model.Inviter;
import com.meirengu.uc.service.InviterService;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        try {
            Map paramMap = new HashMap<String, Object>();
            Page<Inviter> page = super.setPageParams(pageNum,pageSize);
            if(!StringUtil.isEmpty(userId)){
                paramMap.put("userId", userId);
            }
            if(!StringUtil.isEmpty(invitedUserId)){
                paramMap.put("invitedUserId", invitedUserId);
            }
            paramMap.put("sortBy", sortBy);
            paramMap.put("order", order);

            page = inviterService.getListByPage(page, paramMap);
            if(page.getList().size() != 0){
                return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }


    @RequestMapping(method = {RequestMethod.GET})
    public Result detail(@RequestParam(value="id", required = true) Integer id){

        try {
            Inviter inviter  = inviterService.detail(id);
            if(!StringUtil.isEmpty(inviter)){
                return super.setResult(StatusCode.OK, inviter, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }


    @RequestMapping(method = {RequestMethod.PUT})
    public Result update(@RequestParam(value = "id", required = true)Integer id,
                         @RequestParam(value = "user_id", required = false)Integer userId,
                         @RequestParam(value = "invited_user_id", required = false)Integer invitedUserId,
                         @RequestParam(value = "invited_user_phone", required = false)String invitedUserPhone,
                         @RequestParam(value = "register_time", required = false)Date registerTime,
                         @RequestParam(value = "invest_time", required = false)Date investTime,
                         @RequestParam(value = "reward", required = false)BigDecimal reward){
        try {
            Inviter inviter = new Inviter();
            inviter.setId(id);
            inviter.setUserId(userId);
            inviter.setInvitedUserId(invitedUserId);
            inviter.setInvitedUserPhone(invitedUserPhone);
            inviter.setRegisterTime(registerTime);
            inviter.setInvestTime(investTime);
            inviter.setReward(reward);
            int result  = inviterService.update(inviter);
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    @RequestMapping(value = "/{id}",method = {RequestMethod.DELETE})
    public Result delete(@PathVariable Integer id){
        try {
            int result  = inviterService.delete(id);
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /*@RequestMapping(value = "insert",method = {RequestMethod.POST})
    public Result insert(@RequestParam(value = "user_id", required = true)Integer userId,
                         @RequestParam(value = "invited_user_id", required = true)Integer invitedUserId,
                         @RequestParam(value = "invited_user_phone", required = true)String invitedUserPhone,
                         @RequestParam(value = "register_time", required = true)Date registerTime,
                         @RequestParam(value = "invest_time", required = true)Date investTime,
                         @RequestParam(value = "reward", required = true)BigDecimal reward){

        Inviter inviter = new Inviter();
        inviter.setUserId(userId);
        inviter.setInvitedUserId(invitedUserId);
        inviter.setInvitedUserPhone(invitedUserPhone);
        inviter.setRegisterTime(registerTime);
        inviter.setInvestTime(investTime);
        inviter.setReward(reward);
        int result = inviterService.insert(inviter);
        try {
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }*/
}
