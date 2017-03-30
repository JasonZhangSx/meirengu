package com.meirengu.cf.controller;

import com.meirengu.cf.model.Partner;
import com.meirengu.cf.model.PartnerClass;
import com.meirengu.cf.service.PartnerService;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-15 20:52
 */
@Controller
@RequestMapping("partner")
public class PartnerController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    PartnerService partnerService;

    /**
     * 获取请求列表
     * @param pageSize
     * @param pageNum
     * @param isPage
     * @param partnerName
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
                       @RequestParam(value = "partner_name", required = false) String partnerName,
                       @RequestParam(value = "class_id", required = false) String classId,
                       @RequestParam(value = "flag", required = false) Integer flag,
                       @RequestParam(value = "sortby", required = false, defaultValue = "create_time") String sortBy,
                       @RequestParam(value = "order", required = false, defaultValue = "desc") String order){

        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("partnerName", partnerName);
        map.put("classId", classId);
        map.put("flag", flag);
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<Partner> page = new Page<>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = partnerService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = partnerService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    /**
     * 新增行业分类
     * @param className
     * @param classDescription
     * @return
     */
    /*@ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "class_name", required = false) String className,
                         @RequestParam(value = "class_description", required = false) String classDescription){

        PartnerClass pc = this.setPartnerClass(0, className, classDescription, 255, 0, 1, new Date());
        try {
            int insertNum = partnerService.insert(pc);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> insert partner class throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }*/

    /**
     * 获取行业分类详情
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{class_id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "class_id", required = false)int classId){
        try {
            Partner p = partnerService.detail(classId);
            return super.setResult(StatusCode.OK, p, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get partner detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改行业分类信息
     * @param classId
     * @param className
     * @param classDescription
     * @param classSort
     * @param partnerNum
     * @param flag
     * @return
     */
    /*@ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestParam(value = "class_id", required = false)int classId,
                         @RequestParam(value = "class_name", required = false)String className,
                         @RequestParam(value = "class_description", required = false)String classDescription,
                         @RequestParam(value = "class_sort", required = false)Integer classSort,
                         @RequestParam(value = "partner_num", required = false)Integer partnerNum,
                         @RequestParam(value = "flag", required = false)Integer flag){

        PartnerClass pc = this.setPartnerClass(classId, className, classDescription, classSort, partnerNum, flag, null);
        try {
            int updateNum = partnerService.update(pc);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_UPDATE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_UPDATE));
            }
        }catch (Exception e){
            LOGGER.error(">> update partner class throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }*/

    /*
     * 删除行业分类
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{class_id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "class_id", required = false)int classId){
        try {
            int deleteNum = partnerService.delete(classId);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ALREADY_DELETE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ALREADY_DELETE));
            }
        }catch (Exception e){
            LOGGER.error(">> delete partner class detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    private PartnerClass setPartnerClass(int classId, String className, String classDescription, int classSort, int partnerNum, int flag, Date createTime){
        PartnerClass pc = new PartnerClass();
        if(classId != 0){
            pc.setClassId(classId);
        }
        pc.setClassName(className);
        pc.setClassDescription(classDescription);
        pc.setClassSort(classSort);
        pc.setPartnerNum(partnerNum);
        pc.setFlag(flag);
        pc.setCreateTime(new Date());

        return pc;
    }
}
