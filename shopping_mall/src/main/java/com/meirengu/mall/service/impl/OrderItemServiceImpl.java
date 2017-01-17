package com.meirengu.mall.service.impl;

import com.meirengu.mall.dao.OrderDao;
import com.meirengu.mall.dao.OrderItemDao;
import com.meirengu.mall.model.Order;
import com.meirengu.mall.model.OrderItem;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.service.OrderItemService;
import com.meirengu.mall.service.OrderService;
import com.meirengu.mall.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 订单分类业务实现类
 * @author 建新
 * @create 2017-01-10 17:15
 */
@Service
@Transactional(readOnly = true)
public class OrderItemServiceImpl implements OrderItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private PageService<OrderItem> pageService;

    @Override
    public Page<OrderItem> getPageList(Page<OrderItem> page, Map map) {
        return pageService.getListByPage(page, map, orderItemDao);
    }

    @Override
    @Transactional(readOnly = false)
    public int genOrdersItem(List<OrderItem> orderList) {
        return 0;
    }
}
