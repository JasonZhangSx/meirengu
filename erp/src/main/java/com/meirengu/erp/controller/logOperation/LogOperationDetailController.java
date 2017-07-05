package com.meirengu.erp.controller.logOperation;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.commons.authority.model.LogOperation;
import com.meirengu.commons.authority.model.LogOperationDetail;
import com.meirengu.commons.authority.service.LogOperationDetailService;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.vo.QueryVo;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作日志详情控制类
 * Created by maoruxin on 2017/3/30.
 */
@Controller
@RequestMapping("/logOperationDetail")
public class LogOperationDetailController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(LogOperationDetailController.class);

    @Autowired
    private LogOperationDetailService logOperationDetailService;

    /**
     * 跳转到日志详情列表页面
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/view/{logOperationId}", method = RequestMethod.GET)
    public String logOperationDetailView(@PathVariable("logOperationId") Integer logOperationId, ModelMap model) throws IOException {
        model.addAttribute("logOperationId",logOperationId);
        return "/logOperation/logOperationDetailList";
    }


    /**
     * 操作日志详细列表数据请求
     * @param input
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput logOperationDetailList(@Valid DataTablesInput input,
                                             @RequestParam(value = "logOperationId", required = false) Integer logOperationId) throws IOException {
        // 组装请求参数
        QueryVo queryVo = new QueryVo(input);
        Map<String, Object> map = new HashMap<>();
        map.put("logOperationId", logOperationId);
        Page<LogOperationDetail> page = new Page<LogOperationDetail>();
        page.setPageNow(queryVo.getPageNum());
        page.setPageSize(queryVo.getPageSize());

        page = logOperationDetailService.getListByPage(page, map);
        List<Map<String,Object>> list = page.getList();
        int totalCount = page.getTotalCount();
        return setDataTablesOutput(input, list, totalCount);
    }
}
