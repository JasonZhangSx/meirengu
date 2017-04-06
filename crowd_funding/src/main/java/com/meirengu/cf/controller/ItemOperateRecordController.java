package com.meirengu.cf.controller;

import com.meirengu.cf.common.Constants;
import com.meirengu.cf.model.ItemOperateRecord;
import com.meirengu.cf.service.ItemOperateRecordService;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.utils.StringUtil;
import com.sun.org.apache.bcel.internal.classfile.Constant;
import com.sun.org.apache.regexp.internal.REUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作记录控制层
 * @author 建新
 * @create 2017-03-31 21:49
 */
@RestController
@RequestMapping("operate_record")
public class ItemOperateRecordController extends BaseController{

    private static Logger LOGGER = LoggerFactory.getLogger(ItemOperateRecordController.class);

    @Autowired
    ItemOperateRecordService itemOperateRecordService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam(value = "item_id", required = false) Integer itemId){
        if(itemId == null || itemId == 0){
            return super.setResult(StatusCode.INVALID_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }
        Map<String, Object> params = new HashMap<>();
        params.put("item_id", itemId);

        try {
            List list = itemOperateRecordService.getList(params);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error("ItemOperateRecordController.list throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Result insert(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "operate_type", required = false) Integer operateType,
                         @RequestParam(value = "operate_status", required = false) Integer operateStatus,
                         @RequestParam(value = "operate_remark", required = false) String operateRemark,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){
        if(itemId == null || operateType == null || operateStatus == null ||
                StringUtil.isEmpty(operateRemark) || StringUtil.isEmpty(operateAccount)){
            return super.setResult(StatusCode.INVALID_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }

        ItemOperateRecord params = new ItemOperateRecord();
        params.setItemId(itemId);
        params.setOperateType(operateType);
        params.setOperateStatus(operateStatus);
        params.setOperateRemark(operateRemark);
        params.setOperateTime(new Date());
        params.setOperateAccount(operateAccount);

        try {
            int insertNum = itemOperateRecordService.insert(params);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.OPERATE_RECORD_INSERT_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.OPERATE_RECORD_INSERT_ERROR));
            }
        }catch (Exception e){
            LOGGER.error("ItemOperateRecordController.insert throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
}
