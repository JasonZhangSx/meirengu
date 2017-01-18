package com.meirengu.mall.service;

import com.meirengu.mall.model.Cart;

import java.util.List;
import java.util.Map;

/**
 * 购物车 service
 * @author 建新
 * @create 2017-01-10 17:15
 */
public interface CartService extends PageBaseService<Cart>{

    /**
     * 获取购物车列表
     * @param map 参数map
     * @return
     */
    List<Map<String, Object>> getCartList(Map<String, Object> map) throws Exception;

    /**
     * 删除购物车中的某一项目
     * @param cartId 购物车项目id
     * @return
     */
    int delete(int cartId);

    /**
     * 修改购物车中某一项目的数量
     * @param cartId 购物车项目id
     * @param itemNum 购物车项目数量
     * @return
     */
    boolean modifyNum(int cartId, int itemNum);

    /**
     * 将项目加入购物车
     * @param userId 用户id
     * @param hospitalId 医院id
     * @param itemId 项目id
     * @param itemNum 项目数量
     * @return
     */
    boolean addCart(int userId, int hospitalId, int itemId, int itemNum);
}
