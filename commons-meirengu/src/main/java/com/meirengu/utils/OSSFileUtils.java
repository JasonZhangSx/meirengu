package com.meirengu.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    public Map<String, String> upload(HttpServletRequest request, String fileName, String folderName) throws IOException {
        Map<String, String> map = new HashMap<>();
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
            MultipartFile file = req.getFile(fileName);
            if (file != null) {
                String originalFilename = file.getOriginalFilename();
                long alias = new Date().getTime() + new Random().nextInt(10000);
                String suffix  = originalFilename.substring(originalFilename.lastIndexOf("."));
                String pictureName = alias + suffix;
                ossClient.putObject(bucketName, folderName+"/"+pictureName, file.getInputStream());
                map.put("pictureName", pictureName);
            }
        }

        return map;
    }
    public void upload(InputStream inputStream, String fileName, String folderName) throws IOException {

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName+"/"+fileName,inputStream);
        // 上传回调参数
        Callback callback = new Callback();
        callback.setCallbackUrl(callbackUrl);
        callback.setCallbackBody("{\\\"mimeType\\\":${mimeType},\\\"size\\\":${size}}");
        callback.setCalbackBodyType(Callback.CalbackBodyType.URL);
        callback.addCallbackVar("x:var1", "value1");
        callback.addCallbackVar("x:var2", "value2");
        putObjectRequest.setCallback(callback);
//        ossClient.putObject(bucketName, folderName+"/"+fileName, inputStream);
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        // 读取上传回调返回的消息内容
        byte[] buffer = new byte[1024];
        logger.info("callbackMessage :{} ", putObjectResult.getCallbackResponseBody().read(buffer));
        // 一定要close，否则会造成连接资源泄漏
        putObjectResult.getCallbackResponseBody().close();
    }
}
