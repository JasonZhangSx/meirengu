package com.meirengu.admin.service.impl;

import com.meirengu.admin.dao.TypeDao;
import com.meirengu.admin.service.ITypeService;
import com.meirengu.admin.util.ResultUtil;
import com.meirengu.admin.vo.TypeVo;
import com.meirengu.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/11 20:27.
 * 商品类型业务逻辑实现
 */
@Service
public class TypeServiceImpl implements ITypeService {
    private final static Logger logger = LoggerFactory.getLogger(TypeServiceImpl.class);
    @Autowired
    private TypeDao areaDao;
    /**
     * 条件添加商品类型
     * @param areaVo 商品类型Model
     * @return 将查询结果返回
     */
    @Override
    public Map addTypeData(TypeVo areaVo) {
        logger.info("Request addTypeData parameter:{}", areaVo.toString());
        //获取文件需要上传到的路径
        try {
            areaDao.addTypeData(areaVo);
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_CORRECT),null);
        } catch (Exception e) {
            logger.error("addTypeData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_ERROR_INSERT),null);
        }
    }
    /**
     *
     * 条件查询商品类型
     * @param areaVo 商品类型Model
     * @return 将添加结果返回
     */
    @Override
    public Map getTypeData(TypeVo areaVo) {
        logger.info("Request getTypeData parameter:{}", areaVo.toString());
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<TypeVo> list = new ArrayList<TypeVo>();
            list = areaDao.getTypeData(areaVo);
            resultMap.put("list",list);
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_CORRECT),resultMap);
        } catch (Exception e) {
            logger.error("getTypeData ErrorMsg:{}",e.getMessage());
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_ERROR_SELECT),null);
        }
    }
    /**
     * 条件更新商品类型
     * @param areaVo 商品类型Model
     * @return 将更新结果返回
     */
    @Override
    public Map updateTypeData(TypeVo areaVo) {
        logger.info("Request updateTypeData parameter:{}", areaVo.toString());
        //获取文件需要上传到的路径
        try {
            areaDao.updateTypeData(areaVo);
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_CORRECT),null);
        } catch (Exception e) {
            logger.error("updateTypeData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_ERROR_INSERT),null);
        }
    }
    /**
     * 删除商品类型数据
     * @param areaId 商品类型Id
     * @return 将删除结果返回
     * TODO 逻辑删除还是物理删除
     */
    @Override
    public Map deleteTypeData(Integer areaId) {
        logger.info("Request deleteTypeData parameter:{}", areaId);
        try {
//            itemClassDao.updateItemClassData(itemClassVo);
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_CORRECT),null);
        } catch (Exception e) {
            logger.error("deleteTypeData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(String.valueOf(StatusCode.MB_ERROR_INSERT),null);
        }
    }
}
