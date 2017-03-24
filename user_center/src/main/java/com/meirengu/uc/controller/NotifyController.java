package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.uc.model.Notify;
import com.meirengu.uc.service.NotifyService;
import com.meirengu.uc.service.UserNotifyService;
import com.meirengu.uc.utils.MsgTemplateConfigUtil;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 消息模块
 * @author 建新
 * @create 2017-03-23 12:10
 */
@Controller
@RequestMapping("notify")
public class NotifyController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyController.class);

    @Autowired
    NotifyService notifyService;
    @Autowired
    UserNotifyService userNotifyService;

    /**
     * 获取消息列表
     *
     * @param pageSize
     * @param pageNum
     * @param isPage
     * @param sortBy
     * @param order
     * @param sender
     * @param type
     * @param isRead
     * @param receiver
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "sortby", required = false) String sortBy,
                       @RequestParam(value = "order", required = false) String order,
                       @RequestParam(value = "sender", required = false) Integer sender,
                       @RequestParam(value = "type", required = false) Integer type,
                       @RequestParam(value = "is_read", required = false) Integer isRead,
                       @RequestParam(value = "receiver", required = false) Integer receiver) {
        try {
            //默认不分页
            if (StringUtil.isEmpty(isPage)) {
                isPage = false;
            }

            Map<String, Object> map = new HashMap<>();
            map.put("sender", sender);
            map.put("type", type);
            map.put("isRead", isRead);
            map.put("receiver", receiver);
            map.put("sortBy", sortBy);
            map.put("order", order);

            if (isPage) {
                Page<Notify> page = new Page<>();
                page.setPageNow(pageNum);
                page.setPageSize(pageSize);
                page = notifyService.getListByPage(page, map);
                return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                List<Map<String, Object>> list = notifyService.getList(map);
                return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
            }
        } catch (Exception e) {
            LOGGER.error(">> NotifyController.list throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    /**
     * 生成消息模板
     *
     * @param sender    发送者
     * @param tplId     模板id
     * @param type
     * @param target
     * @param targetype
     * @param action
     * @param receiver
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "tpl_send", method = RequestMethod.POST)
    public Result tplSend(@RequestParam(value = "sender", required = false) Integer sender,
                          @RequestParam(value = "tpl_id", required = false) Long tplId,
                          @RequestParam(value = "type", required = false) Integer type,
                          @RequestParam(value = "target", required = false) Integer target,
                          @RequestParam(value = "target_type", required = false) String targetype,
                          @RequestParam(value = "action", required = false) String action,
                          @RequestParam(value = "receiver", required = false) Integer receiver,
                          HttpServletRequest request) {
        try {
            if (sender == null || tplId == null || receiver == null || type == null) {
                return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }

            String templateStr = MsgTemplateConfigUtil.getConfig(String.valueOf(tplId));

            if (StringUtil.isEmpty(templateStr)) {
                return super.setResult(StatusCode.MSG_TEMPLATE_INVALID, "", StatusCode.codeMsgMap.get(StatusCode.MSG_TEMPLATE_INVALID));
            }

            List<String> list = extractMessageByRegular(templateStr);
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                String key = list.get(i);
                String value = request.getParameter(list.get(i));
                if (StringUtil.isEmpty(value)) {
                    return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
                }
                if (!map.containsKey(key)) {
                    map.put(key, value);
                }
            }

            for (String key : map.keySet()) {
                String value = map.get(key);
                templateStr = templateStr.replace("#" + key + "#", value);
            }

            if (notifyService.genNotify(sender, templateStr,
                    type, target, targetype, action, receiver)) {
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                return super.setResult(StatusCode.NOTIFY_GENERATE_FAIL, "", StatusCode.codeMsgMap.get(StatusCode.NOTIFY_GENERATE_FAIL));
            }
        } catch (Exception e) {
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "id", required = false) Integer id){
        try {
            Map<String,Object> map = notifyService.getDetail(id);
            return super.setResult(StatusCode.OK, map, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> NotifyController.detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    public List<String> extractMessageByRegular(String msg){
        List<String> list=new ArrayList<String>();
        Pattern p = Pattern.compile("(\\#[^\\#]*\\#)");
        Matcher m = p.matcher(msg);
        while(m.find()){
            list.add(m.group().substring(1, m.group().length()-1));
        }
        return list;
    }


}
