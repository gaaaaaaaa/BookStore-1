package com.qianfeng.service;

import java.util.List;

import com.qianfeng.entity.OrderItems;
import com.qianfeng.entity.Orders;
import com.qianfeng.vo.PageBean;

public interface IOrderService {

	//添加订单
	public Orders addOrderInfo(String name, Double total);

	//添加订单明细
	public void addOrderitems(String[] ids, String[] nums, Orders orders);

	public PageBean<OrderItems> findItemByIndex(String name, Integer page, Integer state);

	public boolean findOrderByName(String name);

	public void updateState(String oid);

	public Orders findOrderByOrderNum(String oid);

	public void deleteOrder(String oid);

	public List<OrderItems> findItemById(String id);
}
