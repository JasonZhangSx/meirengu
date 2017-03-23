package com.meirengu.cf.controller;

import com.meirengu.cf.utils.ConfigUtil;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.utils.OSSFileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Map;

/**
 * 上传controller
 * @author 建新
 * @create 2017-03-21 11:12
 */
@Controller
@RequestMapping("upload")
public class UploadController extends BaseController{

    @RequestMapping("to_test")
    public String toTest(){
        return "testUpload";
    }

    @RequestMapping(value = "test", method = RequestMethod.POST)
    @ResponseBody
    public Result test(MultipartHttpServletRequest request){
        //文件夹名
        String foldName = "test";
        //file组件的name
        String file = "file";
        String endpoint = ConfigUtil.getConfig("endpoint");
        String accessKeyId = ConfigUtil.getConfig("accessKeyId");
        String accessKeySecret = ConfigUtil.getConfig("accessKeySecret");
        String bucketName = ConfigUtil.getConfig("bucketName");
        String callbackUrl = ConfigUtil.getConfig("callbackUrl");

        try {
            OSSFileUtils fileUtils = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callbackUrl);
            Map<String, String> map = fileUtils.upload(request, file, foldName);
            String pictureName = "";
            if(map != null){
                pictureName = map.get("pictureName");
            }
            return super.setResult(StatusCode.OK, pictureName, StatusCode.codeMsgMap.get(StatusCode.OK));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));

    }
}
