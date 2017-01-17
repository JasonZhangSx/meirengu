package com.meirengu.medical.service.impl;

import com.meirengu.medical.dao.HospitalDao;
import com.meirengu.medical.service.IHospitalService;
import com.meirengu.medical.util.CodeUtil;
import com.meirengu.medical.util.FileUtil;
import com.meirengu.medical.util.ResultUtil;
import com.meirengu.medical.util.UtilData;
import com.meirengu.medical.util.check.Validator;
import com.meirengu.medical.vo.HospitalVo;
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
 * Create Date: 2016/12/29 18:16.
 * 医院业务逻辑实现
 */
@Service
public class HospitalServiceImpl implements IHospitalService {
    private final static Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);
    @Autowired
    private HospitalDao hospitalDao;

    /**
     * 根据对应条件查询医院数据
     * @param hospitalVo
     * @return 将查询结果已json格式返回
     */
    @Override
    public String getHospitalData(HospitalVo hospitalVo) {
        logger.info("Request getHospitalData parameter:{}", hospitalVo.toString());
        String json = null;
        List<HospitalVo> list = new ArrayList<>();
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Validator.getInstance().validate(hospitalVo);
            list= hospitalDao.conditionQuery(hospitalVo);
            resultMap.put("list",list);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),resultMap);
        }catch (Exception e){
            logger.error("getDoctorData ErrorMsg:{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_SELECT.getCode(),null);
        }
    }

    /**
     * 插入医院数据
     * @param hospitalVo
     * @return
     * TODO　图片上传格式及图片名称待定
     */
    @Override
    public String insertSelective(HospitalVo hospitalVo,Iterator<String> iter, MultipartHttpServletRequest multipartHttpServletRequest) {
        logger.info("Request insertSelective parameter:{}", hospitalVo.toString());
        try {
            Validator.getInstance().validate(hospitalVo);
            Map<String,String> map = new HashMap<>();
            hospitalVo.setCreatetime(new Date());
            hospitalDao.insertSelective(hospitalVo);
            while(iter.hasNext()){
                map= FileUtil.createFile(map,iter,multipartHttpServletRequest,UtilData.hospitalImagePath+hospitalVo.getHospitalId()+"/");
                for (String s : map.keySet()){
                    setImgValue(hospitalVo,s,map.get(s));
                }
                map.clear();
            }
            hospitalDao.conditionUpdate(hospitalVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        }catch (Exception e){
            logger.error("insertSelective ErrorMsg:{}",e.getMessage());
            e.printStackTrace();
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
    /**
     * 甄选医院页面
     * @param hospitalVo
     * @return 对应数据
     *  TODO 新增人如何获取未定
     */
    @Override
    public String selectionHospital(HospitalVo hospitalVo) {
        logger.info("Request selectionHospital parameter:{}", hospitalVo.toString());
        List list = new ArrayList<>();
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Validator.getInstance().validate(hospitalVo);
            list= hospitalDao.selectionHospital(hospitalVo);
            resultMap.put("list",list);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),resultMap);
        }catch (Exception e){
            logger.error("selectionHospital ErrorMsg:{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_SELECT.getCode(),null);
        }
    }
    /**
     * 修改医院数据
     * @param hospitalVo
     * @return
     * TODO 修改人如何获取未定
     */
    @Override
    public String conditionUpdate(HospitalVo hospitalVo) {
        logger.info("Request conditionUpdate parameter:{}", hospitalVo.toString());
        try {
            Validator.getInstance().validate(hospitalVo);
            hospitalDao.conditionUpdate(hospitalVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        }catch (Exception e){
            logger.error("conditionUpdate ErrorMsg:{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_UPDATE.getCode(),null);
        }
    }

    /**
     * 逻辑删除医院数据
     * @param hospitalId
     * @return
     *  TODO 修改人如何获取未定
     */
    @Override
    public String conditionDelete(int hospitalId) {
        logger.info("Request conditionDelete parameter:{}", hospitalId);
        try {
            HospitalVo hospitalVo = new HospitalVo();
            hospitalVo.setHospitalId(hospitalId);
            hospitalVo.setHospitalStatus(UtilData.delete_yes);
            Validator.getInstance().validate(hospitalVo);
            hospitalDao.conditionUpdate(hospitalVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        }catch (Exception e){
            logger.error("conditionDelete ErrorMsg:{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_DELETE.getCode(),null);
        }
    }

    /**
     * 给数据库存储图片字段赋值
     * @param hospitalVo
     * @param imgName
     * @param imageValue
     */
    private void setImgValue(HospitalVo hospitalVo,String imgName,String imageValue){
        switch (imgName){
            case "hospitalSmallLogoImg" :
                hospitalVo.setHospitalSmallLogo(imageValue);
                break;
            case "hospitalLogoImg" :
                hospitalVo.setHospitalLogo(imageValue);
                break;
//            case "hospitalPicImg" :
//                hospitalVo.setHospitalPic(imageValue);
//                break;
            case "certificatePicOne" :
                hospitalVo.setCertificatePic(imageValue);
                break;
            case "certificatePicTwo" :
                hospitalVo.setCertificatePic(hospitalVo.getCertificatePic()+","+imageValue);
                break;
            default:
                if (hospitalVo.getHospitalPic()!=null){
                    hospitalVo.setHospitalPic(hospitalVo.getHospitalPic()+","+imageValue);
                }else {
                    hospitalVo.setHospitalPic(imageValue);
                }
                break;
        }

    }
}
