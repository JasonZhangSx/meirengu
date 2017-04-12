package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/** 电子合同controller
 * Created by huoyan403 on 4/11/2017.
 */
@RestController
@RequestMapping("contact")
public class ContactController extends BaseController{

//    CreateHtmlContactFile

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    /**
     * @param itemId  项目id
     * @param levelId  档位id
     * @param userId   投资人id
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public Result CreateContactFile(@RequestParam(value = "item_id",required = true) String itemId,
                                    @RequestParam(value = "level_id",required = true) String levelId,
                                    @RequestParam(value = "user_id",required = true) String userId) {
        try {
            Map map = new HashMap();
            map.put("itemId",itemId);
            map.put("levelId",levelId);
            map.put("userId",userId);
            return contactService.CreateContactFile(map);
        }catch (Exception e){
            logger.info("AddressController.showCityListByPid:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

}
