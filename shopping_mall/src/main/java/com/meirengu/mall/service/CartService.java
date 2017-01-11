package com.meirengu.mall.service;

import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2017/1/9.
 */
public interface CartService {

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
    boolean delete(int cartId);

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
