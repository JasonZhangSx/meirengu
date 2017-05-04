package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.Inviter;
import com.meirengu.uc.service.InviterService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 4/1/2017.
 */
@RestController
@RequestMapping("inviteReward")
public class InviteRewardController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(InviteRewardController.class);

    @Autowired
    private InviterService inviterService;

    //oss配置信息  从oss读取文件
    String contractFolderName = ConfigUtil.getConfig("contractFolderName");
    String endpoint = ConfigUtil.getConfig("endpoint");
    String accessKeyId = ConfigUtil.getConfig("accessKeyId");
    String accessKeySecret = ConfigUtil.getConfig("accessKeySecret");
    String bucketName = ConfigUtil.getConfig("bucketName");
    String callback = ConfigUtil.getConfig("callbackUrl");

    @RequestMapping(value = "notify",method = RequestMethod.GET)
    public Result notify(@RequestParam(value="file_name", required = true) String fileName,
                         @RequestParam(value="file_path", required = true) String filePath) {
        try {

//            List<Map<String,String>> investInfo =  ObjectToFile.readObject(fileName);

            OSSFileUtils fileUtils = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callback);
            String order = IOUtils.toString(fileUtils.download(filePath,fileName),"UTF-8");
            List<Map<String,String>> investInfo = JacksonUtil.readValue(order,List.class);

            List<String[]> list = new ArrayList<>();
            // 根据用户id 获取邀请人信息 判断是否为空
            if(investInfo.size()!=0){
                for (Map map:investInfo){
                    String userId = String.valueOf(map.get("userId"));
                    String investMoney = String.valueOf(map.get("investMoney"));
                    String type = String.valueOf(map.get("type"));
//                    for(Object userId : map.keySet()){
//                        Object investMoney = map.get(userId);

                        Inviter inviter = new Inviter();
                        inviter.setInvitedUserId(Integer.parseInt(userId+""));
                        inviter = inviterService.detail(inviter);
                        if(inviter!=null && !StringUtil.isEmpty(inviter.getId())){
                        // 根据注册时间  投资时间 校验是否有效期
                            Date registerTime = inviter.getRegisterTime();
                            Date investTime = inviter.getInvestTime();
                            try {
                                int i  = DateAndTime.dateDiff("dd",DateAndTime.convertDateToString(registerTime,"yyyy-MM-dd HH:mm:ss"),
                                        DateAndTime.convertDateToString(investTime,"yyyy-MM-dd HH:mm:ss"));

                                // 封装入结构 写入文件
                                if(i<34){
                                    String[] arr = new String[3];
                                    arr[0] = userId+"";
                                    arr[1] = inviter.getUserId()+"";
                                    arr[2] = investMoney+"";
                                    arr[3] = type+"";
                                    list.add(arr);
                                }
                            }catch (Exception e){
                                    logger.info("InviteRewardController.notify throws Exception :{} ",e.getMessage());
                            }

                        }
                    }
                }
//            }
            //如果没有数据直接return
            if(list.size()==0){
                return setResult(StatusCode.OK,null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                //大阳写的文件格式
                StringBuffer sb = new StringBuffer();
                for(int i=0;i<list.size();i++){
                    String[] arr2 = list.get(i);
                    for (int index = 0; index < arr2.length; index++)
                    {
                        sb.append(arr2[index]);
                        if(index<arr2.length-1){
                            sb.append(",");
                        }
                    }
                    if(i<list.size()-1){
                        sb.append("|");
                    }
                }
                String foldName = "user";
                fileName = "user."+ DateUtils.getCurrentDate()+".txt";
                OSSFileUtils fileUpload = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callback);
                fileUpload.upload(sb.toString(),fileName,foldName);

                // 去通知支付系统  异步 重试
                inviterService.noticePayment();
                return setResult(StatusCode.OK,null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }
        }catch (Exception e){
            logger.info("AddressController.redis get token result:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

}
