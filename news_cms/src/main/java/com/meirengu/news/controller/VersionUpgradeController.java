package com.meirengu.news.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.*;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.news.model.VersionUpgrade;
import com.meirengu.news.service.VersionUpgradeService;
import com.meirengu.news.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * APP版本升级表
 * @author 建新
 * @create 2017-03-27 11:08
 */
@RestController
@RequestMapping("version")
public class VersionUpgradeController extends com.meirengu.controller.BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionUpgradeController.class);

    @Autowired
    private VersionUpgradeService versionUpgradeService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "sortby", required = false) String sortBy,
                       @RequestParam(value = "order", required = false) String order,
                       @RequestParam(value = "app_channel", required = false) Integer appChannel,
                       @RequestParam(value = "app_id", required = false) Integer appId,
                       @RequestParam(value = "version_code", required = false) Integer versionCode,
                       @RequestParam(value = "status", required = false) Integer status){
        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("versionCode", versionCode);
        map.put("status", status);
        map.put("appId", appId);
        map.put("appChannel", appChannel);
        map.put("sortBy", sortBy);
        map.put("order", order);

        try {
            if(isPage){
                Page<VersionUpgrade> page = new Page<>();
                page.setPageNow(pageNum);
                page.setPageSize(pageSize);
                page = versionUpgradeService.getListByPage(page, map);
                return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                List<Map<String, Object>> list = versionUpgradeService.getList(map);
                return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
            }
        }catch (Exception e){
            LOGGER.error(">> VersionUpgradeController.list throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    /**
     * 获取最新版本
     * @param appChannel
     * @param appId
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "newest", method = RequestMethod.POST)
    public Result newest(@RequestParam(value = "app_channel", required = false) Integer appChannel,
                         @RequestParam(value = "app_id", required = false) Integer appId,
                         @RequestParam(value = "status", required = false) Integer status){

        if(appChannel == null || appId == null || status == null || appChannel == 0 || appId == 0){
            return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        try {
            VersionUpgrade vu = versionUpgradeService.getLastVersion(appId, appChannel, status);
            return super.setResult(StatusCode.OK, vu, StatusCode.codeMsgMap.get(StatusCode.OK));
        } catch (Exception e) {
            LOGGER.error(">> VersionUpgradeController.newest throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "id", required = false) int id){

        try {
            VersionUpgrade result = versionUpgradeService.detail(id);
            return super.setResult(StatusCode.OK, result, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> VersionUpgradeController.detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "app_id", required = false) Integer appId,
                         @RequestParam(value = "app_code", required = false) String appCode,
                         @RequestParam(value = "app_name", required = false) String appName,
                         @RequestParam(value = "app_channel", required = false) Integer appChannel,
                         @RequestParam(value = "version_milepost", required = false) Integer versionMilepost,
                         @RequestParam(value = "version_code", required = false) String versionCode,
                         @RequestParam(value = "version_code_before", required = false) String versionCodeBefore,
                         @RequestParam(value = "version_size", required = false) String versionSize,
                         @RequestParam(value = "download_url", required = false) String downloadUrl,
                         @RequestParam(value = "update_title", required = false) String updateTitle,
                         @RequestParam(value = "update_content", required = false) String updateContent,
                         @RequestParam(value = "update_type", required = false) Integer updateType,
                         @RequestParam(value = "status", required = false) Integer status){

        if(StringUtil.isEmpty(appId) || StringUtil.isEmpty(appCode) || StringUtil.isEmpty(appName) || StringUtil.isEmpty(appChannel) ||
                StringUtil.isEmpty(versionCode) || StringUtil.isEmpty(versionSize) ||
                StringUtil.isEmpty(downloadUrl) || StringUtil.isEmpty(updateTitle) || StringUtil.isEmpty(updateContent) ||
                StringUtil.isEmpty(updateType) || StringUtil.isEmpty(status)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        VersionUpgrade params = setEntity(null, appId, appCode, appName, appChannel,
                versionMilepost, versionCode, versionCodeBefore, versionSize,
                downloadUrl, updateTitle, updateContent, updateType,
                status, new Date());

        try {
            if(versionUpgradeService.updateVersion(params)){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.VERSION_INSERT_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.VERSION_INSERT_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> VersionUpgradeController.insert throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "id", required = false) Integer id,
                         @RequestParam(value = "app_id", required = false) Integer appId,
                         @RequestParam(value = "app_code", required = false) String appCode,
                         @RequestParam(value = "app_name", required = false) String appName,
                         @RequestParam(value = "app_channel", required = false) Integer appChannel,
                         @RequestParam(value = "version_milepost", required = false) Integer versionMilepost,
                         @RequestParam(value = "version_code", required = false) String versionCode,
                         @RequestParam(value = "version_code_before", required = false) String versionCodeBefore,
                         @RequestParam(value = "version_size", required = false) String versionSize,
                         @RequestParam(value = "download_url", required = false) String downloadUrl,
                         @RequestParam(value = "update_title", required = false) String updateTitle,
                         @RequestParam(value = "update_content", required = false) String updateContent,
                         @RequestParam(value = "update_type", required = false) Integer updateType,
                         @RequestParam(value = "status", required = false) Integer status){

        if(StringUtil.isEmpty(id)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        VersionUpgrade params = setEntity(id, appId, appCode, appName, appChannel,
                versionMilepost, versionCode, versionCodeBefore, versionSize,
                downloadUrl, updateTitle, updateContent, updateType,
                status, null);

        try {
            int updateNum = versionUpgradeService.update(params);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.VERSION_UPDATE_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.VERSION_UPDATE_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> VersionUpgradeController.update throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id")int id){

        try {
            int deleteNum = versionUpgradeService.delete(id);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.VERSION_DELETE_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.VERSION_DELETE_ERROR));
            }
        }catch (Exception e){
            LOGGER.error(">> VersionUpgradeController.delete throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    private VersionUpgrade setEntity(Integer id, Integer appId, String appCode, String appName, Integer appChannel,
                                     Integer versionMilepost, String versionCode, String versionCodeBefore, String versionSize,
                                     String downloadUrl, String updateTitle, String updateContent, Integer updateType,
                                     Integer status, Date createTime){
        VersionUpgrade entity = new VersionUpgrade();
        entity.setId(id);
        entity.setAppId(appId);
        entity.setAppCode(appCode);
        entity.setAppName(appName);
        entity.setAppChannel(appChannel);
        entity.setVersionMilepost(versionMilepost);
        entity.setVersionCode(versionCode);
        entity.setVersionCodeBefore(versionCodeBefore);
        entity.setVersionSize(versionSize);
        entity.setDownloadUrl(downloadUrl);
        entity.setUpdateTitle(updateTitle);
        entity.setUpdateContent(updateContent);
        entity.setUpdateType(updateType);
        entity.setStatus(status);
        entity.setCreateTime(createTime);

        return entity;
    }
}
