package com.meirengu.mall.controller;

import com.meirengu.mall.common.Constants;
import com.meirengu.common.StatusCode;
import com.meirengu.mall.service.CartService;
import com.meirengu.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车controller
 *
 * @author 建新
 * @create 2017-01-10 19:35
 */
@Controller
@RequestMapping("/cart")
public class CartController extends com.meirengu.controller.BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    /**
     * 某个用户的购物车列表接口
     * @param userId 必填
     * @return
     */
    @RequestMapping(value="list", method = RequestMethod.POST)
    @ResponseBody
    public Result list(@RequestParam(value = "user_id", required = false) Integer userId){

        if(null == userId){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        //用户验证是否存在

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("state", Constants.CART_STATE_DEFAULT);
        try {
            List<Map<String, Object>> list = cartService.getCartList(map);
            if(list.size() <= 0){
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }else{
                return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
            }
        } catch (Exception e) {
            LOGGER.error("throw exception: ", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    /**
     * 修改购物车项目数量接口
     * @param itemNum 项目购买数量 必填
     * @return
     */
    @RequestMapping(value="modify-num", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyNum(@RequestParam(value = "cart_id", required = false) Integer cartId,
                            @RequestParam(value = "item_num", required = false) Integer itemNum){

        if(null == cartId || null == itemNum){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        boolean flag = cartService.modifyNum(cartId, itemNum);

        if(flag){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else{
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 删除购物车项目接口
     * @param cartId
     * @return
     */
    @RequestMapping(value="{cart_id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result delete(@PathVariable("cart_id") Integer cartId){

        if(null == cartId){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        int cartDelNum = cartService.delete(cartId);

        if(cartDelNum == 1){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else if(cartDelNum == 2){
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }else {
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 加入购物车接口
     * @param userId 用户id 必填
     * @param hospitalId 医院id 必填
     * @param itemId 项目id 必填
     * @param itemNum 项目数量 必填
     * @return
     */
    @RequestMapping(value="add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestParam(value = "user_id", required = false) Integer userId,
                                   @RequestParam(value = "hospital_id", required = false) Integer hospitalId,
                                   @RequestParam(value = "item_id", required = false) Integer itemId,
                                   @RequestParam(value = "item_num", required = false) Integer itemNum){

        if(null == userId || null == hospitalId || null == itemId || null == itemNum){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        //用户是否存在验证

        //医院是否存在验证

        //项目验证是否存在

        boolean flag = cartService.addCart(userId, hospitalId, itemId, itemNum);
        if(flag){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

}
