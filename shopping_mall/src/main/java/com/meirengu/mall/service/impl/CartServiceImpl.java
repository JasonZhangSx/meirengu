package com.meirengu.mall.service.impl;

import com.meirengu.mall.common.Constants;
import com.meirengu.mall.dao.CartDao;
import com.meirengu.mall.model.Cart;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.service.CartService;
import com.meirengu.mall.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车业务实现类
 * @author 建新
 * @create 2017-01-10 17:15
 */
@Service
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartDao cartDao;

    @Autowired
    private PageService<Cart> pageService;

    @Override
    public List<Map<String, Object>> getCartList(Map<String, Object> map) throws Exception{
        return cartDao.getCartList(map);
    }

    @Override
    @Transactional(readOnly = false)
    /**
     * 0 抛出异常 1 修改成功  2 修改失败（要修改的记录不存在）
     */
    public int delete(int cartId) {
        Map<String, Object> map = new HashMap<>();
        map.put("cartId", cartId);
        map.put("state", Constants.CART_STATE_DEL);
        try{
            int i = cartDao.update(map);
            if(i > 0){
                return 1;
            }else{
                return 2;
            }
        } catch (RuntimeException e){
            LOGGER.error("cart delete throw exceptions:", e);
            return 0;
        }
    }

    @Override
    @Transactional(readOnly = false)
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
        } catch (RuntimeException e){
            LOGGER.error("cart modifyNum throw exceptions:", e);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = false)
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
        } catch (RuntimeException e){
            LOGGER.error("add cart throws exceptions : ", e);
            return false;
        }

    }

    @Override
    public Page<Cart> getPageList(Page<Cart> page, Map map) {
        return pageService.getListByPage(page, map, cartDao);
    }
}
