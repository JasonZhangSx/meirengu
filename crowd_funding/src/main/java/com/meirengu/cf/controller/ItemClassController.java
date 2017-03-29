package com.meirengu.cf.controller;

import com.meirengu.cf.common.Constants;
import com.meirengu.cf.model.Item;
import com.meirengu.cf.model.ItemClass;
import com.meirengu.cf.model.Type;
import com.meirengu.cf.service.ItemClassService;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目分类
 * @author 建新
 * @create 2017-03-13 11:32
 */
@Controller
@RequestMapping("item_class")
public class ItemClassController extends BaseController{

    private static Logger LOGGER = LoggerFactory.getLogger(ItemClassController.class);

    @Autowired
    ItemClassService itemClassService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "class_name", required = false) String className,
                       @RequestParam(value = "flag", required = false) Integer flag,
                       @RequestParam(value = "sortby", required = false) String sortBy,
                       @RequestParam(value = "order", required = false) String order){
        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("className", className);
        map.put("flag", flag);
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<ItemClass> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = itemClassService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = itemClassService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    /**
     * 新增众筹项目分类
     * @param className
     * @param classSort
     * @param classParentId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "class_name", required = false) String className,
                         @RequestParam(value = "class_sort", required = false) Integer classSort,
                         @RequestParam(value = "class_parent_id", required = false) Integer classParentId){

        ItemClass ic = setItemClass(null, className, classParentId, classSort, Constants.STATUS_YES);
        try {
            int insertNum = itemClassService.insert(ic);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_TYPE_INSERT_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_TYPE_INSERT_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> insert item class throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 获取众筹项目分类详情
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{class_id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "class_id", required = false)int classId){
        try {
            ItemClass ic = itemClassService.detail(classId);
            return super.setResult(StatusCode.OK, ic, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get item class detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改众筹项目分类信息
     * @param classId
     * @param className
     * @param classSort
     * @param flag
     * @param classParentId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestParam(value = "class_id", required = false)int classId,
                         @RequestParam(value = "class_name", required = false)String className,
                         @RequestParam(value = "class_sort", required = false)Integer classSort,
                         @RequestParam(value = "flag", required = false)Integer flag,
                         @RequestParam(value = "class_parent_id", required = false)Integer classParentId){

        ItemClass ic = setItemClass(classId, className, classParentId, classSort, flag);
        try {
            int updateNum = itemClassService.update(ic);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_TYPE_UPDATE_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_TYPE_UPDATE_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> update item class throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 删除众筹项目分类
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{class_id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "class_id", required = false)int classId){
        try {
            int deleteNum = itemClassService.delete(classId);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.ITEM_TYPE_DELETE_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.ITEM_TYPE_DELETE_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> delete item class detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    private ItemClass setItemClass(Integer classId, String className, Integer classParentId, Integer classSort, Integer flag){
        ItemClass ic = new ItemClass();
        ic.setClassId(classId);
        ic.setClassName(className);
        ic.setClassParentId(classParentId);
        ic.setClassSort(classSort);
        ic.setFlag(flag);
        return ic;
    }
}
