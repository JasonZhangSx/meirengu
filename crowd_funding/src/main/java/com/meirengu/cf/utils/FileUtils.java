package com.meirengu.cf.utils;

import com.aliyun.oss.OSSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-17 19:57
 */
public class FileUtils {

    private final static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI1Gqnvoh764Aq";
    private static String accessKeySecret = "AoxsjlX4iRWihQposkwNCdOVwTxxAk";
    private static String bucketName = "test-mrg-img";

    public static Map<String,String> createFile(Map<String,String> map, Iterator<String> iter,
                                                MultipartHttpServletRequest multipartHttpServletRequest, String folderName) throws IOException{
        //取得上传文件1
        List<MultipartFile> fileList = multipartHttpServletRequest.getFiles(iter.next());

        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        for (MultipartFile file : fileList){
            if (file != null) {
                //取得当前上传文件的文件名称
                String myFileName = file.getOriginalFilename();
                //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                if (!"".equals(myFileName.trim())) {
                    //重新生成文件名，生成规则：当前时间戳+随机数
                    String fileName = String.valueOf(System.currentTimeMillis() / 1000).concat(String.valueOf(new Random().nextInt(201)));
                    myFileName = myFileName.substring(myFileName.lastIndexOf("."));
//                    File localFile = new File(folderName);
//                    if (!localFile.exists()){
//                        localFile.mkdirs();
//                    }
//                    localFile=new File(folderName + fileName+myFileName);
//                    file.transferTo(localFile);
                    client.putObject(bucketName, folderName + fileName + myFileName,file.getInputStream());
                    String[] size=file.getName().split("_");
                    String key = null;
                    if (size.length!=1){
                        key=size[size.length-3];
                    }else{
                        key=size[0];
                    }
                    String value=fileName+myFileName;
                    if (map.get(key)!=null){
                        map.put(key,map.get(key).concat(","+value));
                    }else {
                        map.put(key,value);
                    }
//                map.put(size[size.length-3],fileName+myFileName);
                }
            }
        }
        client.shutdown();
        return map;
    }

}
