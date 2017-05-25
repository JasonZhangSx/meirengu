package com.meirengu.erp.controller.cms;

import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.service.SignupActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-05-24 15:58
 */
@Controller
@RequestMapping("activity/signup")
public class SignupActivityController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(VersionController.class);

    @Autowired
    SignupActivityService signupActivityService;

    /**
     * 跳转到版本信息列表页面
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view() {
        return "cms/signupList";
    }

    /**
     * 版本信息列表数据请求
     * @param input
     * @return
     */
    @RequestMapping(value = "query", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput query(@Valid DataTablesInput input) throws IOException {

        List<Map<String,Object>> list = null;
        int totalCount = 0;
        int start = input.getStart();
        int length = input.getLength();
        int page = start / length + 1;
        try {
            Map<String, Object> map = signupActivityService.query(page, length, true, null, "", "", "");
            list = (List<Map<String,Object>>) map.get("list");
            totalCount = Integer.parseInt(map.get("totalCount").toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throw exception:{}", e);
        }
        return setDataTablesOutput(input, list, totalCount);
    }
}
