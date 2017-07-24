package com.meirengu.news.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.news.model.IpWhiteList;
import com.meirengu.news.service.IpWhiteListService;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ip白名单控制层
 * @author 建新
 * @create 2017-07-17 15:49
 */
@Controller
@RequestMapping("ip_white")
public class IpWhiteListController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(IpWhiteListController.class);

    @Autowired
    IpWhiteListService ipWhiteListService;

    @RequestMapping(value = "cache/set", method = RequestMethod.GET)
    @ResponseBody
    public Result setCache(){
        ipWhiteListService.setCache();
        return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Result getAll(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                         @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                         @RequestParam(value = "is_page", required = false) boolean isPage,
                         @RequestParam(value = "status", required = false) Integer status,
                         @RequestParam(value = "sortby", required = false, defaultValue = "create_time") String sortBy,
                         @RequestParam(value = "order", required = false, defaultValue = "desc") String order){
        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sortBy", sortBy);
        map.put("order", order);
        map.put("status", status);

        if(isPage){
            Page<IpWhiteList> page = new Page<IpWhiteList>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = ipWhiteListService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = ipWhiteListService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "ip", required = false) String ip,
                         @RequestParam(value = "description", required = false) String description,
                         @RequestParam(value = "type", required = false) Integer type,
                         @RequestParam(value = "url", required = false) String url){
        IpWhiteList ipWhiteList = setEntity(null, ip, description, type, url, 1, new Date());
        try {
            int insertNum = ipWhiteListService.insert(ipWhiteList);
            if(insertNum == 1){
                ipWhiteListService.setCache();
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(com.meirengu.common.StatusCode.OK));
            }else {
                return super.setResult(StatusCode.IP_WHITE_INSERT_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.IP_WHITE_INSERT_ERROR));
            }
        }catch (Exception e){
            logger.error(">> insert ip into ip_white throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "id", required = false) Integer id,
                         @RequestParam(value = "ip", required = false) String ip,
                         @RequestParam(value = "description", required = false) String description,
                         @RequestParam(value = "type", required = false) Integer type,
                         @RequestParam(value = "url", required = false) String url,
                         @RequestParam(value = "status", required = false) Integer status){
        IpWhiteList ipWhiteList = setEntity(id, ip, description, type, url, status, null);
        try {
            int insertNum = ipWhiteListService.update(ipWhiteList);
            if(insertNum == 1){
                ipWhiteListService.setCache();
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(com.meirengu.common.StatusCode.OK));
            }else {
                return super.setResult(StatusCode.IP_WHITE_UPDATE_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.IP_WHITE_UPDATE_ERROR));
            }
        }catch (Exception e){
            logger.error(">> update ip into ip_white throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "id") Integer id){
        IpWhiteList ipWhiteList = ipWhiteListService.detail(id);
        return super.setResult(StatusCode.OK, ipWhiteList, StatusCode.codeMsgMap.get(StatusCode.OK));
    }


    private IpWhiteList setEntity(Integer id, String ip, String description, Integer type, String url, Integer status, Date createTime){

        IpWhiteList ipWhiteList = new IpWhiteList();
        ipWhiteList.setId(id);
        ipWhiteList.setIp(ip);
        ipWhiteList.setDescription(description);
        ipWhiteList.setType(type);
        ipWhiteList.setUrl(url);
        ipWhiteList.setStatus(status);
        ipWhiteList.setCreateTime(createTime);

        return ipWhiteList;
    }
}
