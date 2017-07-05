package com.meirengu.erp.controller.logOperation;

import com.meirengu.commons.authority.model.LogOperation;
import com.meirengu.commons.authority.service.LogOperationService;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.vo.QueryVo;
import com.meirengu.model.Page;
import com.meirengu.utils.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作日志控制类
 * Created by maoruxin on 2017/3/30.
 */
@Controller
@RequestMapping("/logOperation")
public class LogOperationController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(LogOperationController.class);

    @Autowired
    private LogOperationService logOperationService;

    /**
     * 跳转到日志列表页面
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String logOperationView() throws IOException {
        return "/logOperation/logOperationList";
    }


    /**
     * 操作日志列表数据请求
     * @param input
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput logOperationList(@Valid DataTablesInput input) throws IOException {
        // 组装请求参数
        QueryVo queryVo = new QueryVo(input);
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(queryVo.getBusinessName())) {
            map.put("businessName", queryVo.getBusinessName());
        }
        if (StringUtils.isNotBlank(queryVo.getUserName())) {
            map.put("userName", queryVo.getUserName());
        }
        if (StringUtils.isNotBlank(queryVo.getSortColumn())) {
            map.put("sortBy", queryVo.getSortColumn());
        }
        if (StringUtils.isNotBlank(queryVo.getOrder())) {
            map.put("order", queryVo.getOrder());
        }
        Page<LogOperation> page = new Page<LogOperation>();
        page.setPageNow(queryVo.getPageNum());
        page.setPageSize(queryVo.getPageSize());

        page = logOperationService.getListByPage(page, map);
        List<Map<String,Object>> list = page.getList();
        int totalCount = page.getTotalCount();
        return setDataTablesOutput(input, list, totalCount);
    }

}
