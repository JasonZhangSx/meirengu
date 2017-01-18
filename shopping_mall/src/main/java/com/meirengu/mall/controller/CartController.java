package com.meirengu.mall.controller;

import com.meirengu.mall.common.Constants;
import com.meirengu.mall.common.StatusCode;
import com.meirengu.mall.service.CartService;
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
public class CartController extends BaseController{

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
    public Map<String, Object> list(@RequestParam(value = "user_id", required = false) Integer userId){

        if(null == userId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "user_id"));
        }

        //用户验证是否存在

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("state", Constants.CART_STATE_DEFAULT);
        try {
            List<Map<String, Object>> list = cartService.getCartList(map);
            if(list.size() <= 0){
                return super.setReturnMsg(StatusCode.STATUS_501, null, StatusCode.STATUS_501_MSG);
            }else{
                return super.setReturnMsg(StatusCode.STATUS_200, list, StatusCode.STATUS_200_MSG);
            }
        } catch (Exception e) {
            LOGGER.error("throw exception: ", e);
            return super.setReturnMsg(StatusCode.STATUS_504, null, StatusCode.STATUS_504_MSG);
        }

    }

    /**
     * 修改购物车项目数量接口
     * @param itemNum 项目购买数量 必填
     * @return
     */
    @RequestMapping(value="modify-num", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyNum(@RequestParam(value = "cart_id", required = false) Integer cartId,
                                         @RequestParam(value = "item_num", required = false) Integer itemNum){

        if(null == cartId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "cart_id"));
        }

        if(null == itemNum){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "item_num"));
        }

        boolean flag = cartService.modifyNum(cartId, itemNum);

        if(flag){
            return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
        }else{
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }

    }

    /**
     * 删除购物车项目接口
     * @param cartId
     * @return
     */
    @RequestMapping(value="{cart_id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("cart_id") Integer cartId){

        if(null == cartId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "cart_id"));
        }

        int cartDelNum = cartService.delete(cartId);

        if(cartDelNum == 1){
            return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
        }else if(cartDelNum == 2){
            return super.setReturnMsg(StatusCode.STATUS_4213, null, StatusCode.STATUS_4213_MSG);
        }else {
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
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
    public Map<String, Object> add(@RequestParam(value = "user_id", required = false) Integer userId,
                                   @RequestParam(value = "hospital_id", required = false) Integer hospitalId,
                                   @RequestParam(value = "item_id", required = false) Integer itemId,
                                   @RequestParam(value = "item_num", required = false) Integer itemNum){

        if(null == userId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "user_id"));
        }

        if(null == hospitalId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "hospital_id"));
        }

        if(null == itemId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "item_id"));
        }

        if(null == itemNum){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "item_num"));
        }
        //用户是否存在验证

        //医院是否存在验证

        //项目验证是否存在

        boolean flag = cartService.addCart(userId, hospitalId, itemId, itemNum);
        if(flag){
            return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
        }else {
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }

    }

}
