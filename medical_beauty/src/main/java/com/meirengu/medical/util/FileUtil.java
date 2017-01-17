package com.meirengu.medical.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/10 16:14.
 * 图片上传公共类
 */
public class FileUtil {
    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static Map<String,String> createFile(Map<String,String> map,
                                                Iterator<String> iter,
                                                MultipartHttpServletRequest multipartHttpServletRequest,
                                                String folderName) throws IOException {
        //取得上传文件1
        MultipartFile file = multipartHttpServletRequest.getFile(iter.next());
        if (file != null) {
            //取得当前上传文件的文件名称
            String myFileName = file.getOriginalFilename();
            //如果名称不为“”,说明该文件存在，否则说明该文件不存在
            if (!"".equals(myFileName.trim())) {
                //重新生成文件名，生成规则：当前时间戳+随机数
                String fileName = String.valueOf(System.currentTimeMillis() / 1000).concat(String.valueOf(new Random().nextInt(201)));
                myFileName = myFileName.substring(myFileName.lastIndexOf("."));
                File localFile = new File(folderName);
                if (!localFile.exists()){
                    localFile.mkdirs();
                }
                localFile=new File(folderName + fileName+myFileName);
                file.transferTo(localFile);
                String[] size=file.getName().split("_");
                Thumbnails.of(folderName + fileName+myFileName).
                        size(Integer.valueOf(size[size.length-2]),
                                Integer.valueOf(size[size.length-1])).
                        toFile(folderName + fileName+"_"+size[size.length-2]+"_"+size[size.length-1]+myFileName);
                map.put(size[size.length-3],fileName+myFileName);
            }
        }
        return map;
    }
}
