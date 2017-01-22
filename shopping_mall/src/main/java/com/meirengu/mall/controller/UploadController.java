package com.meirengu.mall.controller;

import com.meirengu.mall.utils.ConfigUtil;
import com.meirengu.mall.utils.ImageUtil;
import com.meirengu.mall.utils.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-01-22 2:56
 */
@Controller
@RequestMapping("upload")
public class UploadController extends BaseController{

    /**
     *
     * @return
     */
    @RequestMapping(value = "image", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> image(HttpServletRequest request){

        Map<String, String> map = new HashMap<String, String>();
        try {
			String absolutePath = ConfigUtil.getRecommendSavePath();
			String showPath = ConfigUtil.getRecommendShowPath();
            if (request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
                MultipartFile file = req.getFile("upfile");
                if (file != null) {
                    String originalFilename = file.getOriginalFilename();
                    long currentTime = new Date().getTime();
                    String suffix  = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String pictureName = currentTime + suffix;
                    File absoluteFile = new File(absolutePath);
                    if(!absoluteFile.exists()){
                        absoluteFile.mkdirs();
                    }
                    File savedFile = new File(absoluteFile.getPath(), pictureName);
                    boolean isSave = UploadUtil.saveFile(file, savedFile);
                    String[] specs = null;
                    if(isSave){
                        map.put("state", "SUCCESS");
                        map.put("original", originalFilename);
                        map.put("url", pictureName);
                        map.put("title", pictureName);
                        map.put("type", suffix);
                        map.put("url", showPath+pictureName);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "文件上传失败!"); //在此处写上错误提示信息，这样当错误的时候就会显示此信息
            map.put("url","");
            map.put("title", "");
            map.put("original", "");

        }
        return map;
    }
}
