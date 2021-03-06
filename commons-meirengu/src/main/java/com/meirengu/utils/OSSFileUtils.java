package com.meirengu.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

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

    /**
     * 单张图片上传
     * @param request
     * @param fileName
     * @param folderName
     * @return
     * @throws IOException
     */
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

    /**
     * 多张图片上传
     * @param request
     * @param fileName
     * @param folderName
     * @return
     * @throws IOException
     */
    public List<String> uploadMultiple(HttpServletRequest request, String fileName, String folderName) throws IOException {
        List<String> list = new ArrayList<String>();
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
            List<MultipartFile> fileList = req.getFiles(fileName);
            for (MultipartFile file: fileList) {
                if (file != null) {
                    String originalFilename = file.getOriginalFilename();
                    long alias = new Date().getTime() + new Random().nextInt(10000);
                    String suffix  = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String pictureName = alias + suffix;
                    ossClient.putObject(bucketName, folderName+"/"+pictureName, file.getInputStream());
                    list.add(folderName + "/" + pictureName);
                }
            }
        }
        return list;
    }

    /**
     * inputstream 上传oss
     * @param inputStream
     * @param fileName
     * @param folderName
     * @throws IOException
     */
    public void upload(InputStream inputStream, String fileName, String folderName) throws IOException {
        ossClient.putObject(bucketName, folderName+"/"+fileName, inputStream);
        inputStream.close();
    }

    /**
     * String类型上传 oss
     * @param content
     * @param fileName
     * @param folderName
     * @throws IOException
     */
    public void upload(String content, String fileName, String folderName) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        ossClient.putObject(bucketName, folderName+"/"+fileName, byteArrayInputStream);
        byteArrayInputStream.close();
    }

    /**
     * url格式上传oss
     * @param url
     * @param folderName
     * @param fileName
     * @throws IOException
     */
    public void uploadUrl(String url, String folderName, String fileName) throws IOException {
        InputStream inputStream = new URL(url).openStream();
        ossClient.putObject(bucketName, folderName+"/"+fileName, inputStream);
        inputStream.close();
    }

    public InputStream download(String filePath,String fileName){
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, filePath+"/"+fileName);
        return ossObject.getObjectContent();
    }
}
