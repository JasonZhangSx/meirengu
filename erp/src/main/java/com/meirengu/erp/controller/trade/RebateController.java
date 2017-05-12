package com.meirengu.erp.controller.trade;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.common.Constants;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.vo.QueryVo;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抵扣券控制类
 */
@Controller
@RequestMapping("/rebate")
public class RebateController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RebateController.class);

    /**
     * 跳转到抵扣券列表页面
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String orderCandidateView() throws IOException {
        return "/trade/rebateList";
    }

    /**
     * 抵扣券列表数据请求
     * @param input
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput rebateBatchList(@Valid DataTablesInput input) throws IOException {
        // 组装请求参数
        QueryVo queryVo = new QueryVo(input);

        String url = ConfigUtil.getConfig("rebate.list.url") + "?" + queryVo.getParamsStr();
        Map<String,Object> httpData = null;
        List<Map<String,Object>> list = null;
        int totalCount = 0;
        try {
            httpData = (Map<String,Object>)httpGet(url);
            list = (List<Map<String,Object>>) httpData.get("list");
            totalCount = Integer.parseInt(httpData.get("totalCount").toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throw exception:{}", e);
        }
        return setDataTablesOutput(input, list, totalCount);
    }


}
