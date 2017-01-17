package com.meirengu.medical.service.impl;

import com.meirengu.medical.dao.AreaDao;
import com.meirengu.medical.service.IAreaService;
import com.meirengu.medical.util.CodeUtil;
import com.meirengu.medical.util.ResultUtil;
import com.meirengu.medical.vo.AreaVo;
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
 * Create Date: 2017/1/11 19:05.
 * 地区业务逻辑实现
 */
@Service
public class AreaServiceImpl implements IAreaService {
    private final static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
    @Autowired
    private AreaDao areaDao;
    /**
     * 条件添加地区
     * @param areaVo 地区Model
     * @return 将查询结果返回
     */
    @Override
    public String addAreaData(AreaVo areaVo) {
        logger.info("Request addAreaData parameter:{}", areaVo.toString());
        //获取文件需要上传到的路径
        try {
            areaDao.addAreaData(areaVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("addAreaData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
    /**
     *
     * 条件查询地区
     * @param areaVo 地区Model
     * @return 将添加结果返回
     */
    @Override
    public String getAreaData(AreaVo areaVo) {
        logger.info("Request getAreaData parameter:{}", areaVo.toString());
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<AreaVo> list = new ArrayList<AreaVo>();
            list = areaDao.getAreaData(areaVo);
            List<AreaVo> itemClassVoList = tree(list,0);
            resultMap.put("list",itemClassVoList);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),resultMap);
        } catch (Exception e) {
            logger.error("getAreaData ErrorMsg:{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_SELECT.getCode(),null);
        }
    }
    /**
     * 条件更新地区
     * @param areaVo 地区Model
     * @return 将更新结果返回
     */
    @Override
    public String updateAreaData(AreaVo areaVo) {
        logger.info("Request updateAreaData parameter:{}", areaVo.toString());
        //获取文件需要上传到的路径
        try {
            areaDao.updateAreaData(areaVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("updateAreaData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }

    /**
     * 删除地区数据
     * @param areaId 地区Id
     * @return 将删除结果返回
     * TODO 逻辑删除还是物理删除
     */
    @Override
    public String deleteAreaData(Integer areaId) {
        logger.info("Request deleteAreaData parameter:{}", areaId);
        try {
//            itemClassDao.updateItemClassData(itemClassVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("deleteAreaData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }

    /**
     * 递归遍历树形
     * @param list 查询结果
     * @param icParentId 父节点
     * @return 树形结构
     */
    public List<AreaVo> tree(List<AreaVo> list,int icParentId){
        List<AreaVo> itemClassVoList = new ArrayList<>();
        for (AreaVo vo : list){
            if (vo.getAreaParentId()==icParentId){
                List<AreaVo> voList=tree(list,vo.getAreaId());
                vo.setList(voList);
                itemClassVoList.add(vo);
            }
        }
        return itemClassVoList;
    }
}
