package com.meirengu.erp.controller.cms;

import com.meirengu.common.StatusCode;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.controller.UploadController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/4/19 16:40.
 */
@RestController
@RequestMapping("slideshow")
public class SlideshowController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(SlideshowController.class);
    protected static StringBuffer url;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getSlideshow(String status,String slideshowPosition){
        StringBuffer stringBuffer = new StringBuffer();
        if (status!=null&&!status.isEmpty()){
            stringBuffer.append("status="+status);
        }
        if (slideshowPosition!=null&&!slideshowPosition.isEmpty()){
            if (stringBuffer.length()<=0){
                stringBuffer.append("slideshowPosition="+slideshowPosition);
            }else {
                stringBuffer.append("&slideshowPosition="+slideshowPosition);
            }
        }
        return new ModelAndView("/cms/slideshow", slideshowList(String.valueOf(stringBuffer)));
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String updateHospital(String id,String status){
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("id",id);
        paramsMap.put("status",status);
        return post(url.toString()+"/Slideshow/update",paramsMap);
    }
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView add(String id,String status){
        return new ModelAndView("/cms/slideshowAdd");
    }
    @RequestMapping(value = "/addData",method = RequestMethod.POST)
    @ResponseBody
    public Object addData(String slideshowName,String slideshowLink,String slideshowPosition,MultipartHttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        UploadController uploadController = new UploadController();
        Result result = uploadController.upload(request,"slideshow");
        if (result.getData()==null || result.getData().toString() == ""){
            return "";
        }
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("slideshowName",slideshowName);
        paramsMap.put("slideshowLink",slideshowLink);
        paramsMap.put("slideshowPosition",slideshowPosition);
        paramsMap.put("slideshowImage","slideshow/"+result.getData().toString());
//        return new ModelAndView("/cms/slideshowAdd").addObject("result",post(url.toString()+"/Slideshow",paramsMap));
        return post(url.toString()+"/Slideshow",paramsMap);
    }
    protected Map<String,Object> slideshowList(String condition){
        if (url==null){
            setUrl();
        }
        try {
            return  (Map<String, Object>) super.httpGet(url.toString()+"/Slideshow?"+condition);
        } catch (IOException e) {
            logger.error("slideshowList Error Msg : {}",e.getMessage());
            return null;
        }
    }
    public String post(String url, Map<String, String> params){
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, params);
        String content = hr.getContent();
        return content;
    }
    protected void setUrl(){
        url = new StringBuffer(ConfigUtil.getConfig("news.url"));
    }
}
