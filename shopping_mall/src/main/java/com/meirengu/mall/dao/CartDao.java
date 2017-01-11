package com.meirengu.mall.dao;

import com.meirengu.mall.model.Cart;

import java.util.List;
import java.util.Map;

/**
 * 购物车dao
 * @author 建新
 * @create 2017-01-10 17:15
 */
public interface CartDao {

    /**
     * 获取购物车列表
     * @param map
     * @return
     */
    List<Map<String, Object>> getCartList(Map<String, Object> map);

    /**
     * 修改购物车属性
     * @param map
     * @return
     */
    int update(Map<String, Object> map);

    /**
     * 加入购物车
     * @param cart
     * @return
     */
    int addCart(Cart cart);
}
