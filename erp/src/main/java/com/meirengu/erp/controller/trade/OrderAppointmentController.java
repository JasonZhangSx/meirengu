package com.meirengu.erp.controller.trade;

import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.vo.TradeQuery;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * 预约订单控制类
 * Created by maoruxin on 2017/3/30.
 */
@RestController
@RequestMapping("/order_appointment")
public class OrderAppointmentController extends BaseController{

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView orderAppointmentList(TradeQuery query) throws IOException {
        ModelAndView mv = new ModelAndView();
        query.setOrderState(1);
        String url = ConfigUtil.getConfig("order.list.url") + "?" + query.getParamsStr();
        Object data = httpGet(url);
        mv.addObject("page", data);
        mv.addObject("query", query);
        mv.setViewName("/trade/orderAppointmentList");
        return mv;
    }
}
