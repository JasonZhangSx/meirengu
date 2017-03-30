package com.meirengu.erp.controller.trade;

import com.meirengu.erp.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView orderCandidateList(@RequestParam(value = "page_num", required = false,  defaultValue = "1") String pageNum,
                                            @RequestParam(value = "page_size", required = false, defaultValue = "10") String pageSize,
                                            @RequestParam(value = "sort_by", required = false) String sortBy,
                                            @RequestParam(value = "order", required = false) String order,
                                            @RequestParam(value = "user_id", required = false) String userId,
                                            @RequestParam(value = "user_phone", required = false) String userPhone,
                                            @RequestParam(value = "item_name", required = false) String itemName,
                                            @RequestParam(value = "status", required = false) String status) {
        String url = "192.168.0.135:8085/trade/order_candidate";
        Map<String, String> params = new HashMap<String, String>();
        params.put("page_num", pageNum);
        params.put("page_size", pageSize);
        params.put("sort_by", sortBy);
        params.put("order", order);
        params.put("user_id", userId);
        params.put("user_phone", userPhone);
        params.put("item_name", itemName);
        params.put("status", status);
        Object data = httpPost(url, params);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/trade/OrderCandidateList");
        mv.addObject(data);
        return mv;
    }
}
