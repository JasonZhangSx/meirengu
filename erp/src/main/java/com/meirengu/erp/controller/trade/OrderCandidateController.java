package com.meirengu.erp.controller.trade;

import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.vo.TradeQuery;
import org.springframework.beans.propertyeditors.URIEditor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 候补预约控制类
 * Created by maoruxin on 2017/3/30.
 */
@RestController
@RequestMapping("/order_candidate")
public class OrderCandidateController extends BaseController{

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView orderCandidateList(TradeQuery query) throws IOException {
        ModelAndView mv = new ModelAndView();
        String url = ConfigUtil.getConfig("order.candidate.list.url") + "?" + URLEncoder.encode(query.getParamsStr(), "UTF-8");
        Object data = httpGet(url);
        mv.addObject("page", data);
        mv.addObject("query", query);
        mv.setViewName("/trade/orderCandidateList");
        return mv;
    }
}
