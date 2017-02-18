package com.meirengu.medical.service.impl;

import com.meirengu.common.StatusCode;
import com.meirengu.medical.dao.ItemDao;
import com.meirengu.medical.service.IItemService;
import com.meirengu.medical.util.CodeUtil;
import com.meirengu.medical.util.FileUtil;
import com.meirengu.medical.util.ResultUtil;
import com.meirengu.medical.util.UtilData;
import com.meirengu.medical.util.check.Validator;
import com.meirengu.medical.vo.ItemVo;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/12 11:59.
 * 项目业务逻辑实现
 */
@Service
public class ItemServiceImpl implements IItemService {
    private final static Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    private ItemDao itemDao;
    /**
     * 条件添加项目
     * @param itemVo 项目Model
     * @return 将添加结果返回
     */
    @Override
    public String addItemData(ItemVo itemVo, Iterator<String> iter, MultipartHttpServletRequest multipartHttpServletRequest) {
        logger.info("Request addItemData parameter:{}", itemVo.toString());
        //获取文件需要上传到的路径
        try {
//            Validator.getInstance().validate(itemVo);
            Map<String,String> map = new HashMap<>();
            itemVo.setCreateTime(new Date());
            itemDao.addItemData(itemVo);
            while(iter.hasNext()){
                map= FileUtil.createFile(map,iter,multipartHttpServletRequest, UtilData.hospitalImagePath+itemVo.getHospitalId()+"/item/"+itemVo.getItemId()+"/");
                for (String s : map.keySet()){
                    setImgValue(itemVo,s,map.get(s));
                }
                map.clear();
            }
            itemDao.updateItemData(itemVo);
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_CORRECT),null);
        } catch (Exception e) {
            logger.error("addItemData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_ERROR_INSERT),null);
        }
    }
    /**
     *
     * 条件查询项目
     * @param itemVo 项目Model
     * @return 将添加结果返回
     */
    @Override
    public String getItemData(ItemVo itemVo) {
        logger.info("Request getItemData parameter:{}", itemVo.toString());
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List list = new ArrayList<ItemVo>();
            if (itemVo.getPageSize()<=0){
                itemVo.setPageSize(3);
            }
            list = itemDao.getItemData(itemVo);
            resultMap.put("list",list);
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_CORRECT),resultMap);
        } catch (Exception e) {
            logger.error("getItemData ErrorMsg:{}",e.getMessage());
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_ERROR_SELECT),null);
        }
    }

    /**
     * 条件更新项目
     * @param itemVo 项目Model
     * @return 将添加结果返回
     */
    @Override
    public String updateItemData(ItemVo itemVo) {
        logger.info("Request updateItemData parameter:{}", itemVo.toString());
        //获取文件需要上传到的路径
        try {
            itemDao.updateItemData(itemVo);
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_CORRECT),null);
        } catch (Exception e) {
            logger.error("updateItemData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_ERROR_UPDATE),null);
        }
    }
    /**
     * 条件删除项目
     * @param itemId 项目Id
     * @return 将添加结果返回
     */
    @Override
    public String deleteItemData(Integer itemId) {
        logger.info("Request deleteItemData parameter:{}", itemId);
        try {
            ItemVo itemVo = new ItemVo();
            itemVo.setItemId(itemId);
            itemVo.setItemCommend(0);
            itemDao.updateItemData(itemVo);
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_CORRECT),null);
        } catch (Exception e) {
            logger.error("deleteItemData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_ERROR_DELETE),null);
        }
    }
    /**
     * 获取服务项目
     * @param itemVo 项目Model
     * @return 查询结果
     */
    @Override
    public String getServiceItem(ItemVo itemVo) {
        logger.info("Request getServiceItem parameter:{}", itemVo.toString());
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<ItemVo> list = new ArrayList<ItemVo>();
            if (itemVo.getPageSize()<=0){
                itemVo.setPageSize(3);
            }
            list = itemDao.getServiceItem(itemVo);
            resultMap.put("list",list);
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_CORRECT),resultMap);
        } catch (Exception e) {
            logger.error("getServiceItem ErrorMsg:{}",e.getMessage());
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_ERROR_SELECT),null);
        }
    }
    /**
     * 获取项目详情
     * @param itemVo 项目Model
     * @return 查询结果
     */
    @Override
    public String getItemDetail(ItemVo itemVo) {
        logger.info("Request getItemDetail parameter:{}", itemVo.toString());
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List list = itemDao.getItemDetail(itemVo);
            ItemVo item = new ItemVo();
            item.setDoctorId(itemVo.getDoctorId());
            resultMap.put("itemList",list);
            resultMap.put("caseList",getRelationCase(item));
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_CORRECT),resultMap);
        } catch (Exception e) {
            logger.error("getItemDetail ErrorMsg:{}",e.getMessage());
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_ERROR_SELECT),null);
        }
    }
    /**
     * 获取项目案例
     * @param itemVo 项目Model
     * @return 查询结果
     */
    @Override
    public List getRelationCase(ItemVo itemVo) {
        logger.info("Request getRelationCase parameter:{}", itemVo.toString());
        return itemDao.getRelationCase(itemVo);
    }

    /**
     * 给数据库存储图片字段赋值
     * @param itemVo
     * @param imgName
     * @param imageValue
     */
    private void setImgValue(ItemVo itemVo,String imgName,String imageValue){
        switch (imgName){
            case "itemImageOne" :
                itemVo.setItemImage(imageValue);
                break;
            default:
                if (itemVo.getItemImageMore()==null){
                    itemVo.setItemImageMore(imageValue);
                }else {
                    itemVo.setItemImageMore(itemVo.getItemImageMore()+","+imageValue);
                }
                break;
        }
    }
}
