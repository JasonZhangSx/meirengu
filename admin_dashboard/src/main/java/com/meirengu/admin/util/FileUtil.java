package com.meirengu.admin.util;

import com.aliyun.oss.OSSClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/10 16:14.
 * 图片上传公共类
 */
public class FileUtil {
    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;

    private static String bucketName;
    public static Map<String,String> createFile(Map<String,String> map,
                                                Iterator<String> iter,
                                                MultipartHttpServletRequest multipartHttpServletRequest,
                                                String folderName) throws IOException, DocumentException {
        //取得上传文件1
        List<MultipartFile> fileList = multipartHttpServletRequest.getFiles(iter.next());
        if (endpoint==null){
            ossValue();
        }
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
//                        Thumbnails.of(folderName + fileName+myFileName).
//                                size(Integer.valueOf(size[size.length-2]),
//                                        Integer.valueOf(size[size.length-1])).
//                                toFile(folderName + fileName+"_"+size[size.length-2]+"_"+size[size.length-1]+myFileName);
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

    public static void ossValue() throws DocumentException {
        File f = new File(Thread.currentThread().getContextClassLoader().getResource("ossConfigure.xml").getPath());
        SAXReader reader = new SAXReader();
        Document doc = reader.read(f);
        Element root = doc.getRootElement();
        Element foo;
        for (Iterator i = root.elementIterator("VALUE"); i.hasNext();) {
            foo = (Element) i.next();
            endpoint=foo.elementText("endpoint");
            accessKeyId=foo.elementText("accessKeyId");
            accessKeySecret=foo.elementText("accessKeySecret");
            bucketName=foo.elementText("bucketName");
        }
    }
}
