package com.meirengu.erp.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.DatatablesViewPage;
import com.meirengu.common.StatusCode;
import com.meirengu.commons.authority.common.enums.OperationTypeEnum;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.model.User;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.utils.ExportExcel;
import com.meirengu.erp.utils.InfoProcessUtil;
import com.meirengu.model.Result;
import com.meirengu.utils.ApacheBeanUtils;
import com.meirengu.utils.DateUtils;
import com.meirengu.utils.HttpUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 3/29/2017.
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @RequestMapping("tolist")
    public ModelAndView userList(){
        return new ModelAndView("/user/userList");
    }

    /**
     * 分页查看用户信息数据
     * @param request
     * @param phone
     * @param realname
     * @param idcard
     * @return
     */
    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseBody
    public DatatablesViewPage<Map<String,Object>> list(HttpServletRequest request,
                                    @RequestParam(value="draw", required = false ,defaultValue = "") Integer draw,
                                    @RequestParam(value="phone", required = false ,defaultValue = "") String phone,
                                    @RequestParam(value="realname", required = false ,defaultValue = "") String realname,
                                    @RequestParam(value="idcard", required = false ,defaultValue = "") String idcard,
                                    @RequestParam(value="is_auth", required = false ,defaultValue = "") String isAuth){

        addLogOperation("用户信息查看", OperationTypeEnum.SELECT.getIndex(),phone,"");

        Map<String, Object> map = new HashMap<>();
        //封装返回集合
        DatatablesViewPage<Map<String,Object>> view = new DatatablesViewPage<Map<String,Object>>();
        List<Map<String,Object>> userList = new ArrayList<Map<String, Object>>();
        if(draw != 1) { //首次不加载数据
            String url = ConfigUtil.getConfig("user.list");
            //查询参数
            Map<String, String> paramsMap = new HashedMap();
            paramsMap.put("phone", phone);
            paramsMap.put("realname", realname);
            paramsMap.put("idcard", idcard);
            paramsMap.put("is_auth", isAuth);
            /*配置分页数据 datatables传递过来的是 从第几条开始 以及要查看的数据长度*/
            int page = Integer.parseInt(request.getParameter("start")) / Integer.parseInt(request.getParameter("length")) + 1;
            paramsMap.put("page", page + "");
            paramsMap.put("per_page", request.getParameter("length"));

            map = (Map<String, Object>) super.httpPost(url, paramsMap);
            if(map == null){
                //保存给datatabls 分页数据
                view.setiTotalDisplayRecords(0);//显示总记录
                view.setiTotalRecords(0);//数据库总记录
            }else{
                userList = (List<Map<String,Object>>) map.get("list");
                //后台处理数据 保存编号 没有编号的不需要这一步
                for (int i = 0;i<userList.size();i++){
                    userList.get(i).put("id",i+1);
                }
                //保存给datatabls 分页数据
                view.setiTotalDisplayRecords(Integer.valueOf(map.get("totalCount")+""));//显示总记录
                view.setiTotalRecords(Integer.valueOf(map.get("totalCount")+""));//数据库总记录
            }
        }else{
            //保存给datatabls 分页数据
            view.setiTotalDisplayRecords(0);//显示总记录
            view.setiTotalRecords(0);//数据库总记录
        }
        view.setAaData(userList);
        return view;
    }

    /**
     * 用户权限信息修改
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "state/update", method = RequestMethod.GET)
    public Result updateUserState(@RequestParam(value = "state", required = false) String state,
                                  @RequestParam(value = "user_id", required = false) String userId,
                                  @RequestParam(value = "is_auth", required = false) String isAuth,
                                  @RequestParam(value = "is_buy", required = false) String isBuy,
                                  @RequestParam(value = "is_allow_inform", required = false) String isAllowInform,
                                  @RequestParam(value = "is_allow_talk", required = false) String isAllowTalk) {
        logger.info("UserController updateUserState userId :{} state :{} isAuth :{} ,isBuy :{} ,isAllowInform :{} ,isAllowTalk :{} ",
                userId,state,isAuth,isBuy,isAllowInform,isAllowTalk);
        addLogOperation("用户权限信息修改", OperationTypeEnum.UPDATE.getIndex(),userId,"" +
                "state||"+state+",isAuth||"+isAuth+",isBuy||"+isBuy+",isAllowInform||"+isAllowInform+",isAllowTalk||"+isAllowTalk+"");

        try {
            Map<String,String> paramsMap = new HashedMap();
            paramsMap.put("user_id",userId);
            paramsMap.put("state",state);

            String url = ConfigUtil.getConfig("user.state.update");
            HttpUtil.HttpResult hr = HttpUtil.doPut(url, paramsMap);
            logger.debug("Request: {} getResponse: {}", url, hr);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Integer code = jsonObject.getIntValue("code");
                if(code != null && code == StatusCode.OK){
                    return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
                }else {
                    return setResult(code, null, StatusCode.codeMsgMap.get(code));
                }
            } else {
                return setResult(statusCode, null, StatusCode.codeMsgMap.get(statusCode));
            }
        } catch (Exception e) {
            logger.error("throw exception:{}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    /**
     * 查看个人信息详情
     * @param phone
     * @return
     */
    @RequestMapping("detail")
    public ModelAndView userDetail( @RequestParam(value="phone", required = true) String phone){
        addLogOperation("用户详情信息", OperationTypeEnum.SELECT.getIndex(),phone,"");

        Map<String, Object> map = new HashMap<>();
        String urlForGetUser = ConfigUtil.getConfig("user.list");
        String urlForGetUserAddress = ConfigUtil.getConfig("user.address.list");
        try {
            Map<String,String> paramsMap = new HashedMap();
            Map<String,Object> mapUserInfo = new HashedMap();
            paramsMap.put("phone",phone);
            mapUserInfo = ( Map<String, Object>)super.httpPost(urlForGetUser,paramsMap);
            JSONArray objects =(JSONArray) mapUserInfo.get("list");
            Map<String,Object> user  = ( Map<String,Object>)objects.get(0);
            user.put("idCard", InfoProcessUtil.generateDefaultIdCard(String.valueOf(user.get("idCard"))));
            user.put("bankIdCard", InfoProcessUtil.generateDefaultBankIdCard(String.valueOf(user.get("bankIdCard"))));

            Map<String,String> paramsForAddress = new HashedMap();
            paramsForAddress.put("user_id",user.get("userId")+"");

            Map<String,Object> mapAddress = ( Map<String, Object>)super.httpPost(urlForGetUserAddress,paramsForAddress);

            // TODO: 3/30/2017 可能会有exception
            map.put("userInfo", user);
            map.put("mapAddress",mapAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/user/userDetail", map);
    }

    @RequestMapping(value="/export", method= RequestMethod.GET)
    public ModelAndView userExport(HttpServletResponse response){
        addLogOperation("用户信息导出", OperationTypeEnum.EXPORT.getIndex(),"","");

        try {
            Map<String,String> paramsMap = new HashedMap();
            Map<String, Object> map = new HashMap<>();
            String url = ConfigUtil.getConfig("user.list");
            //查询参数
            paramsMap.put("is_page","0");

            map = (Map<String,Object>)super.httpPost(url,paramsMap);
            String fileName = "userInfo"+ DateUtils.getFormatDate("yyyyMMddHHmmss")+".xlsx";
            List<Map<String, String>> userList = (List<Map<String, String>>) map.get("list");
            List<User> list = new ArrayList<User>();
            for(Map userMap:userList){
                User user = (User)ApacheBeanUtils.mapToObject(userMap,User.class);
                list.add(user);
            }
            new ExportExcel("", User.class).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/user/userList");
    }
}
