package com.meirengu.cf.controller;

import com.meirengu.cf.utils.FileUtils;
import com.meirengu.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 上传controller
 * @author 建新
 * @create 2017-03-21 11:12
 */
@Controller
@RequestMapping("upload")
public class UploadController extends BaseController{

    @RequestMapping(value = "test", method = RequestMethod.POST)
    public void test(MultipartHttpServletRequest request){
        String foldName = "test";

        //FileUtils.testUpload(request, fileName, foldName);
    }
}
