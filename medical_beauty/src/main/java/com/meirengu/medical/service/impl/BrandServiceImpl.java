package com.meirengu.medical.service.impl;

import com.meirengu.medical.dao.BrandDao;
import com.meirengu.medical.service.IBrandService;
import com.meirengu.medical.util.CodeUtil;
import com.meirengu.medical.util.FileUtil;
import com.meirengu.medical.util.ResultUtil;
import com.meirengu.medical.util.UtilData;
import com.meirengu.medical.vo.BrandVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/10 14:44.
 * 品牌业务逻辑实现
 */
@Service
public class BrandServiceImpl implements IBrandService {
    private final static Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);
    @Autowired
    private BrandDao brandDao;
    /**
     * 条件查询品牌列表
     * @param brandVo 对应Model
     * @return 查询结果
     */
    @Override
    public String getBrandData(BrandVo brandVo) {
        logger.info("Request getBrandData parameter:{}", brandVo.toString());
        List<BrandVo> list = new ArrayList<>();
        Map<String,Object> resultMap = new HashMap<>();
        try {
            list = brandDao.selectByPrimaryKey(brandVo);
            resultMap.put("list",list);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),resultMap);
        } catch (Exception e) {
            logger.error("getBrandData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_SELECT.getCode(),null);
        }
    }
    /**
     * 条件添加品牌数据
     * @param brandVo 对应Model
     * @return 添加结果
     */
    @Override
    public String addBrandData(BrandVo brandVo, Iterator<String> iter, MultipartHttpServletRequest multipartHttpServletRequest) {
        logger.info("Request addBrandData parameter:{}", brandVo.toString());
        //获取文件需要上传到的路径
        try {
            Map<String,String> map = new HashMap<>();
            brandDao.insertSelective(brandVo);
            while(iter.hasNext()) {
                map= FileUtil.createFile(map,iter,multipartHttpServletRequest, UtilData.brandImagePath+brandVo.getBrandId()+"/");
                for (String s : map.keySet()){
                    setImgValue(brandVo,s,map.get(s));
                }
                map.clear();
                continue;
            }
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("addBrandData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
    /**
     * 条件更新品牌数据
     * @param brandVo 对应Model
     * @return 更新结果
     */
    @Override
    public String updateBrandData(BrandVo brandVo) {
        logger.info("Request updateBrandData parameter:{}", brandVo.toString());
        //获取文件需要上传到的路径
        try {
            brandDao.updateByPrimaryKeySelective(brandVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("updateBrandData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }

    /**
     * 条件删除品牌数据
     * @param brandId 品牌id
     * @return 删除结果
     * TODO　采用物理删除还是逻辑删除
     */
    @Override
    public String deleteBrandData(Integer brandId) {
        logger.info("Request deleteBrandData parameter:{}", brandId);
        //获取文件需要上传到的路径
        try {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(brandId);
            brandDao.updateByPrimaryKeySelective(brandVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("deleteBrandData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }

    /**
     * 给数据库存储图片字段赋值
     * @param brandVo 品牌Model
     * @param imgName 图片名称
     * @param imageValue 图片值
     */
    private void setImgValue(BrandVo brandVo,String imgName,String imageValue){
        switch (imgName){
            case "brandPic" :
                brandVo.setBrandPic(imageValue);
                break;
        }
    }
}
