package com.meirengu.cf.controller;

import com.meirengu.cf.model.Type;
import com.meirengu.cf.service.TypeService;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 众筹类型表
 * @author 建新
 * @create 2017-03-17 12:07
 */
@Controller
@RequestMapping("type")
public class TypeController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeController.class);

    @Autowired
    TypeService typeService;

    /**
     * 获取请求列表
     * @param pageSize
     * @param pageNum
     * @param isPage
     * @param typeName
     * @param flag
     * @param sortBy
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "type_name", required = false) String typeName,
                       @RequestParam(value = "flag", required = false) Integer flag,
                       @RequestParam(value = "sortby", required = false, defaultValue = "create_time") String sortBy,
                       @RequestParam(value = "order", required = false, defaultValue = "desc") String order){

        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("typeName", typeName);
        map.put("flag", flag);
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<Type> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = typeService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = typeService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    /**
     * 新增众筹分类
     * @param typeName
     * @param typeSort
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "type_name", required = false) String typeName,
                         @RequestParam(value = "type_sort", required = false) Integer typeSort){

        Type type = setType(null, typeName, typeSort, 255);
        try {
            int insertNum = typeService.insert(type);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.TYPE_INSERT_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.TYPE_INSERT_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> insert class throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 获取众筹分类详情
     * @param typeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{type_id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "type_id", required = false)int typeId){
        try {
            Type type = typeService.detail(typeId);
            return super.setResult(StatusCode.OK, type, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get class detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改众筹分类信息
     * @param typeId
     * @param typeName
     * @param typeSort
     * @param flag
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestParam(value = "type_id", required = false)int typeId,
                         @RequestParam(value = "type_name", required = false)String typeName,
                         @RequestParam(value = "type_sort", required = false)Integer typeSort,
                         @RequestParam(value = "flag", required = false)Integer flag){

        Type type = setType(typeId, typeName, typeSort, flag);
        try {
            int updateNum = typeService.update(type);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.TYPE_UPDATE_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.TYPE_UPDATE_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> update class throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 删除众筹分类
     * @param typeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{type_id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "type_id", required = false)int typeId){
        try {
            int deleteNum = typeService.delete(typeId);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.TYPE_DELETE_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.TYPE_DELETE_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> delete class detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    private Type setType(Integer typeId, String typeName, Integer typeSort, Integer flag){
        Type type = new Type();
        type.setTypeId(typeId);
        type.setTypeName(typeName);
        type.setTypeSort(typeSort);
        type.setFlag(flag);
        return type;
    }
}
