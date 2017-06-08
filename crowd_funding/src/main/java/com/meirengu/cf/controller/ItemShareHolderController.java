package com.meirengu.cf.controller;

import com.meirengu.cf.model.ItemExtention;
import com.meirengu.cf.model.ItemShareholder;
import com.meirengu.cf.model.Partner;
import com.meirengu.cf.service.ItemExtentionService;
import com.meirengu.cf.service.ItemShareholderService;
import com.meirengu.cf.service.PartnerService;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 股东信息控制层
 * @author 建新
 * @create 2017-06-08 12:22
 */
@Controller
@RequestMapping("shareholder")
public class ItemShareHolderController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemExtentionController.class);

    @Autowired
    ItemShareholderService itemShareholderService;

    /**
     * 获取请求列表
     * @param pageSize
     * @param pageNum
     * @param isPage
     * @param sortBy
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "item_id", required = false) Integer itemId,
                       @RequestParam(value = "sortby", required = false, defaultValue = "create_time") String sortBy,
                       @RequestParam(value = "order", required = false, defaultValue = "desc") String order){

        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<ItemShareholder> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = itemShareholderService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = itemShareholderService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    /**
     * 新增项目股东
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "shareholder_name", required = false) String shareholderName,
                         @RequestParam(value = "shareholder_idcard", required = false) String shareholderIdcard,
                         @RequestParam(value = "shareholder_address", required = false) String shareholderAddress,
                         @RequestParam(value = "shareholder_telphone", required = false) String shareholderTelphone,
                         @RequestParam(value = "shareholder_amount", required = false) Integer shareholderAmount,
                         @RequestParam(value = "before_invest_rate", required = false) BigDecimal beforeInvestRate,
                         @RequestParam(value = "after_invest_rate", required = false) BigDecimal afterInvestRate){

        ItemShareholder itemShareholder = setShareholder(null, itemId, shareholderName, shareholderIdcard, shareholderAddress,
                shareholderTelphone, shareholderAmount, beforeInvestRate, afterInvestRate);
        try {
            int insertNum = itemShareholderService.insert(itemShareholder);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.SHAREHOLDER_INSERT_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.SHAREHOLDER_INSERT_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> insert shareholder throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 获取项目股东信息
     */
    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "id", required = false)int id){
        try {
            ItemShareholder p = itemShareholderService.detail(id);
            return super.setResult(StatusCode.OK, p, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get item extention throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改项目扩展信息
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "id", required = false) Integer id,
                         @RequestParam(value = "item_id", required = false) Integer itemId,
                         @RequestParam(value = "shareholder_name", required = false) String shareholderName,
                         @RequestParam(value = "shareholder_idcard", required = false) String shareholderIdcard,
                         @RequestParam(value = "shareholder_address", required = false) String shareholderAddress,
                         @RequestParam(value = "shareholder_telphone", required = false) String shareholderTelphone,
                         @RequestParam(value = "shareholder_amount", required = false) Integer shareholderAmount,
                         @RequestParam(value = "before_invest_rate", required = false) BigDecimal beforeInvestRate,
                         @RequestParam(value = "after_invest_rate", required = false) BigDecimal afterInvestRate){

        ItemShareholder itemShareholder = setShareholder(id, itemId, shareholderName, shareholderIdcard, shareholderAddress,
                shareholderTelphone, shareholderAmount, beforeInvestRate, afterInvestRate);
        try {
            int updateNum = itemShareholderService.update(itemShareholder);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.SHAREHOLDER_UPDATE_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.SHAREHOLDER_UPDATE_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> update shareholder extention throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /*
     * 删除
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@PathVariable(value = "id", required = false)Integer id){
        try {
            int deleteNum = itemShareholderService.delete(id);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.SHAREHOLDER_DELETE_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.SHAREHOLDER_DELETE_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> delete shareholder throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private ItemShareholder setShareholder(Integer id, Integer itemId, String shareholderName, String shareholderIdcard, String
                                       shareholderAddress, String shareholderTelphone, Integer shareholderAmount, BigDecimal beforeInvestRate,
                                       BigDecimal afterInvestRate) {

        ItemShareholder itemShareholder = new ItemShareholder();
        itemShareholder.setId(id);
        itemShareholder.setItemId(itemId);
        itemShareholder.setShareholderName(shareholderName == null ? "" : shareholderName);
        itemShareholder.setShareholderIdcard(shareholderIdcard == null ? "" : shareholderIdcard);
        itemShareholder.setShareholderAddress(shareholderAddress == null ? "" : shareholderAddress);
        itemShareholder.setShareholderTelphone(shareholderTelphone == null ? "" : shareholderTelphone);
        itemShareholder.setShareholderAmount(shareholderAmount);
        itemShareholder.setBeforeInvestRate(beforeInvestRate);
        itemShareholder.setAfterInvestRate(afterInvestRate);

        return itemShareholder;
    }
}
