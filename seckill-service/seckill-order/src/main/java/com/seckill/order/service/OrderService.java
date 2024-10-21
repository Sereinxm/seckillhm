package com.seckill.order.service;

import com.github.pagehelper.PageInfo;
import com.seckill.order.pojo.Order;

import java.util.List;
import java.util.Map;

/****
 * @Author:www.itheima.com
 * @Description:Order业务层接口
 * @Date  0:16
 *****/
public interface OrderService {

    /***
     * Order多条件分页查询
     * @param order
     * @param page
     * @param size
     * @return
     */
    PageInfo<Order> findPage(Order order, int page, int size);

}
