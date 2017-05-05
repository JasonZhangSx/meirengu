package com.meirengu.erp.controller.user;

import com.meirengu.erp.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-05-02 19:37
 */
@RequestMapping("/address")
@Controller
public class AddressController {

    @Autowired
    AddressService addressService;

    @RequestMapping("/city")
    @ResponseBody
    public Map<String, Object> getCityList(Integer pid){

        Map<String, Object> map = new HashMap();
        List list = addressService.getCityList(pid);
        map.put("data", list);
        return map;
    }

    @RequestMapping("/area")
    @ResponseBody
    public Map<String, Object> getAreaList(Integer cid){

        Map<String, Object> map = new HashMap();
        List list = addressService.getAreaList(cid);
        map.put("data", list);
        return map;
    }
}
