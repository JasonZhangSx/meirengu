package com.meirengu.mall.service.impl;

import com.meirengu.mall.common.Constants;
import com.meirengu.mall.common.StatusCode;
import com.meirengu.mall.dao.CartDao;
import com.meirengu.mall.model.Cart;
import com.meirengu.mall.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2017/1/9.
 */
@Service
@Transactional
public class CartServiceImpl implements CartService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartDao cartDao;

    @Override
    public List<Map<String, Object>> getCartList(Map<String, Object> map) throws Exception{
        return cartDao.getCartList(map);
    }

    @Override
    public boolean delete(int cartId) {
        Map<String, Object> map = new HashMap<>();
        map.put("cartId", cartId);
        map.put("state", Constants.CART_STATE_DEL);
        try{
            int i = cartDao.update(map);
            if(i > 0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e){
            LOGGER.error("cart delete throw exceptions:", e);
            return false;
        }
    }

    @Override
    public boolean modifyNum(int cartId, int itemNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("cartId", cartId);
        map.put("itemNum", itemNum);
        map.put("state", Constants.CART_STATE_DEFAULT);
        try{
            int i = cartDao.update(map);
            if(i > 0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e){
            LOGGER.error("cart modifyNum throw exceptions:", e);
            return false;
        }
    }

    @Override
    public boolean addCart(int userId, int hospitalId, int itemId, int itemNum) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("hospitalId", hospitalId);
        paramMap.put("itemId", itemId);
        paramMap.put("itemNumAdd", itemNum);

        try {
            //先进行更新，如果更新成功，则直接返回true，
            //如果更新失败（除异常外），则说明表中没有此项目信息，需要新增
            int i = cartDao.update(paramMap);
            if(i  >0){
                return true;
            }   else {
                //需要加入购物车（新增记录）
                Cart cart = new Cart();
                cart.setUserId(userId);
                cart.setHospitalId(hospitalId);
                cart.setItemId(itemId);
                cart.setItemNum(itemNum);
                cart.setState(Constants.CART_STATE_DEFAULT);
                int j = cartDao.addCart(cart);
                if( j > 0){
                    return true;
                }else{
                    return false;
                }
            }
        } catch (Exception e){
            LOGGER.error("add cart throws exceptions : ", e);
            return false;
        }

    }
}
