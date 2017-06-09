package com.meirengu.erp.controller.partner;

import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.model.LeadInvestor;
import com.meirengu.erp.service.InvestorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 领投人控制层
 *
 * @author 建新
 * @create 2017-05-17 17:15
 */
@Controller
@RequestMapping("investor")
public class InvestorController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(InvestorController.class);

    @Autowired
    InvestorService investorService;

    /**
     * 跳转到领投人信息列表页面
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view() {
        return "partner/leadInvestorList";
    }

    /**
     * 领投人信息列表数据请求
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
            Map<String, Object> map = (Map<String, Object>) investorService.query(page, length, true);
            list = (List<Map<String,Object>>) map.get("list");
            totalCount = Integer.parseInt(map.get("totalCount").toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throw exception:{}", e);
        }
        return setDataTablesOutput(input, list, totalCount);
    }

    /**
     * 跳转到领投人信息页
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(Integer id) {
        if(id == null){
            return new ModelAndView("partner/leadInvestorDetail");
        }else {
            Map<String, Object>  map = investorService.detail(id);
            return new ModelAndView("partner/leadInvestorDetail", map);
        }
    }

    /**
     * 保存版本信息（新增和更新）
     * @param investor
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(LeadInvestor investor) {
        Map<String, Object> map ;
        //id为空 新增
        if(investor.getId() == null || investor.getId() == 0){
            map = investorService.add(investor);
        }else {
            map = investorService.update(investor);
        }
        return map;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> delete(int id) {
        Map<String, Object> map = investorService.delete(id);
        return map;
    }
}
