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

/**
 * 预约订单控制类
 * Created by maoruxin on 2017/3/30.
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController{

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView orderList(TradeQuery query) throws IOException {
        ModelAndView mv = new ModelAndView();
//        if (query.getOrderState() == null) {
//            query.setOrderState(4);//默认展示待支付
//        }
//        String url = ConfigUtil.getConfig("order.list.url") + "?" + query.getParamsStr();
        String url = null;
        Object data = httpGet(url);
        mv.addObject("page", data);
        mv.addObject("query", query);
        mv.setViewName("/trade/orderList");
        return mv;
    }
}
