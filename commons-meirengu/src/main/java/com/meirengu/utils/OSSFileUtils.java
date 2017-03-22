package com.meirengu.utils;

import com.aliyun.oss.OSSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * 文件上传工具类
 * @author 建新
 * @create 2017-03-17 19:57
 */
public class OSSFileUtils {

    private final static Logger logger = LoggerFactory.getLogger(OSSFileUtils.class);

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String callbackUrl;

    private static OSSClient ossClient ;

    public OSSFileUtils(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String callbackUrl){
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
        this.callbackUrl = callbackUrl;
        init();
    }

    public void init(){
        if(ossClient == null){
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
    }

    public void upload(HttpServletRequest request, String fileName, String folderName) throws IOException {

        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
            MultipartFile file = req.getFile(fileName);
            if (file != null) {
                String originalFilename = file.getOriginalFilename();
                long alias = new Date().getTime() + new Random().nextInt(10000);
                String suffix  = originalFilename.substring(originalFilename.lastIndexOf("."));
                String pictureName = alias + suffix;
                ossClient.putObject(bucketName, folderName+"/"+pictureName, file.getInputStream());
            }
        }
    }

}
