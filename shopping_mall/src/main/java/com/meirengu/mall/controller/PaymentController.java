package com.meirengu.mall.controller;

import com.meirengu.mall.common.StatusCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 支付相关接口
 *
 * @author 建新
 * @create 2017-01-13 19:01
 */
public class PaymentController extends BaseController{

    /**
     * 微信支付回调
     * @param response
     * @param request
     */
    @RequestMapping("wx_notify")
    public void wxPayNotify(HttpServletResponse response, HttpServletRequest request){

        /** 成功返回信息 **/
        StringBuffer returnSuccessMsg = new StringBuffer();
        returnSuccessMsg.append("<xml>");
        returnSuccessMsg.append("<return_code>SUCCESS</return_code>");
        returnSuccessMsg.append("<return_msg>OK</return_msg>");
        returnSuccessMsg.append("</xml>");

        /** 失败返回信息 **/
        StringBuffer returnFailMsg = new StringBuffer();
        returnFailMsg.append("<xml>");
        returnFailMsg.append("<return_code>FAIL</return_code>");
        returnFailMsg.append("<return_msg>ERROR</return_msg>");
        returnFailMsg.append("</xml>");

        response.setCharacterEncoding("utf-8");

        try {
            PrintWriter out = response.getWriter();
            out.write(returnFailMsg.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 支付接口
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="pay", method = RequestMethod.POST)
    public Map<String, Object> pay(@RequestParam(value = "order_id", required = false) Integer orderId){

        if(null == orderId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "order_id"));
        }



        return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
    }

}
