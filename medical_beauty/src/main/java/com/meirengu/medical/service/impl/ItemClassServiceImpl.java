package com.meirengu.medical.service.impl;

import com.meirengu.medical.dao.ItemClassDao;
import com.meirengu.medical.service.IItemClassService;
import com.meirengu.medical.util.CodeUtil;
import com.meirengu.medical.util.ResultUtil;
import com.meirengu.medical.vo.ItemClassVo;
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
 * Create Date: 2017/1/11 11:32.
 * 项目分类业务逻辑实现
 */
@Service
public class ItemClassServiceImpl implements IItemClassService {
    private final static Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);
    @Autowired
    private ItemClassDao itemClassDao;
    /**
     * 条件添加项目分类
     * @param itemClassVo 项目分类Model
     * @return 将添加结果返回
     */
    @Override
    public String addItemClassData(ItemClassVo itemClassVo) {
        logger.info("Request addItemClassData parameter:{}", itemClassVo.toString());
        //获取文件需要上传到的路径
        try {
            itemClassDao.addItemClassData(itemClassVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("addItemClassData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
    /**
     *
     * 条件查询项目分类
     * @param itemClassVo 项目分类Model
     * @return 将添加结果返回
     */
    @Override
    public String getItemClassData(ItemClassVo itemClassVo) {
        logger.info("Request getItemClassData parameter:{}", itemClassVo.toString());
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<ItemClassVo> list = new ArrayList<ItemClassVo>();
            list = itemClassDao.getItemClassData(itemClassVo);
            List<ItemClassVo> itemClassVoList = tree(list,0);
            resultMap.put("list",itemClassVoList);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),resultMap);
        } catch (Exception e) {
            logger.error("getItemClassData ErrorMsg:{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_SELECT.getCode(),null);
        }
    }
    /**
     * 条件更新项目分类
     * @param itemClassVo 项目分类Model
     * @return 将添加结果返回
     */
    @Override
    public String updateItemClassData(ItemClassVo itemClassVo) {
        logger.info("Request updateItemClassData parameter:{}", itemClassVo.toString());
        //获取文件需要上传到的路径
        try {
            itemClassDao.updateItemClassData(itemClassVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("updateItemClassData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
    /**
     * 条件删除项目分类
     * @param icId 项目分类Id
     * @return 将添加结果返回
     */
    @Override
    public String deleteItemClassData(Integer icId) {
        logger.info("Request deleteItemClassData parameter:{}", icId);
        try {
            ItemClassVo itemClassVo = new ItemClassVo();
            itemClassVo.setIcId(icId);
            itemClassVo.setIcShow(0);
            itemClassDao.updateItemClassData(itemClassVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("deleteItemClassData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }

    /**
     * 递归遍历树形
     * @param list 查询结果
     * @param icParentId 父节点
     * @return 树形结构
     */
    public List<ItemClassVo> tree(List<ItemClassVo> list,int icParentId){
        List<ItemClassVo> itemClassVoList = new ArrayList<>();
        for (ItemClassVo vo : list){
            if (vo.getIcParentId()==icParentId){
                List<ItemClassVo> voList=tree(list,vo.getIcId());
                vo.setList(voList);
                itemClassVoList.add(vo);
            }
        }
        return itemClassVoList;
    }
}
