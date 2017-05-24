package com.meirengu.erp.controller.partner;

import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.model.LimitedPartnership;
import com.meirengu.erp.service.LimitedPartnerShipService;
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
 * 有限合伙controller
 * @author 建新
 * @create 2017-05-23 12:16
 */
@Controller
@RequestMapping("partner_ship")
public class LimitedPartnerShipController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(InvestorController.class);

    @Autowired
    LimitedPartnerShipService limitedPartnerShipService;

    /**
     * 跳转到领投人信息列表页面
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view() {
        return "partner/limitedPartnerShipList";
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
            Map<String, Object> map = limitedPartnerShipService.query(page, length, true);
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
            return new ModelAndView("partner/limitedPartnerShipDetail");
        }else {
            Map<String, Object>  map = limitedPartnerShipService.detail(id);
            return new ModelAndView("partner/limitedPartnerShipDetail", map);
        }
    }

    /**
     * 保存版本信息（新增和更新）
     * @param partnerShip
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(LimitedPartnership partnerShip) {
        Map<String, Object> map ;
        //id为空 新增
        if(partnerShip.getId() == null || partnerShip.getId() == 0){
            map = limitedPartnerShipService.add(partnerShip);
        }else {
            map = limitedPartnerShipService.update(partnerShip);
        }
        return map;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> delete(int id) {
        Map<String, Object> map = limitedPartnerShipService.delete(id);
        return map;
    }
}
