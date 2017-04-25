package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.OSSFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Map;

/**
 * 上传controller
 * @author huoyan403
 * @create 2017-03-21 11:12
 */
@Controller
@RequestMapping("upload")
public class UploadController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @RequestMapping(value = "portrait", method = RequestMethod.POST)
    @ResponseBody
    public Result portrait(MultipartHttpServletRequest request){
        //文件夹名
        String foldName = ConfigUtil.getConfig("foldName");
        //file组件的name
        String file = "file";
        String endpoint = ConfigUtil.getConfig("endpoint");
        String accessKeyId = ConfigUtil.getConfig("accessKeyId");
        String accessKeySecret = ConfigUtil.getConfig("accessKeySecret");
        String bucketName = ConfigUtil.getConfig("bucketName");
        String callbackUrl = ConfigUtil.getConfig("callbackUrl");
        String fileSize = ConfigUtil.getConfig("fileSize");
        logger.info("uc upload init :{}");
        try {
            if( request.getFile("file").getSize() > Integer.parseInt(fileSize)){
                return super.setResult(StatusCode.UPLOAD_SIZE_ERROR,null, StatusCode.codeMsgMap.get(StatusCode.UPLOAD_SIZE_ERROR));
            }

            OSSFileUtils fileUtils = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callbackUrl);
            Map<String, String> map = fileUtils.upload(request, file, foldName);
            String pictureName = "";
            if(map != null){
                pictureName = map.get("pictureName");
            }
            return super.setResult(StatusCode.OK, foldName+"/"+pictureName, StatusCode.codeMsgMap.get(StatusCode.OK));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));

    }
}
