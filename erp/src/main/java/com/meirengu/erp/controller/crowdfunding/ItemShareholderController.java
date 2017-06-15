package com.meirengu.erp.controller.crowdfunding;

import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.model.ItemShareholder;
import com.meirengu.erp.service.ItemShareHolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 项目股东控制层
 * @author 建新
 * @create 2017-06-08 18:42
 */
@RequestMapping("item/shareholder")
@Controller
public class ItemShareholderController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(ItemShareholderController.class);

    @Autowired
    ItemShareHolderService itemShareHolderService;

    @RequestMapping("query")
    @ResponseBody
    public DataTablesOutput query(@Valid DataTablesInput input, Integer itemId) throws IOException {

        List<Map<String,Object>> list = null;
        int totalCount = 0;
        int start = input.getStart();
        int length = input.getLength();
        int page = start / length + 1;
        try {
            Map<String, Object> map = (Map<String, Object>) itemShareHolderService.query(1, 100, false, itemId);
            list = (List<Map<String,Object>>) map.get("list");
            totalCount = Integer.parseInt(map.get("totalCount").toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throw exception:{}", e);
        }
        return setDataTablesOutput(input, list, totalCount);
    }

    @RequestMapping("save")
    @ResponseBody
    public Map<String, Object> save(ItemShareholder shareholder){
        Map<String, Object> map;
        if(shareholder.getId() == null || shareholder.getId() == 0){
            map = itemShareHolderService.add(shareholder);
        }else{
            map = itemShareHolderService.update(shareholder);
        }
        return map;
    }
}
