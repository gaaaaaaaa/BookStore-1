package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.OrderItems;

public interface IOrderItemDao extends IBaseDao<OrderItems>{

	public Integer findOrderByName(String name);

	public void deleteByName(String name);

	public void deleteById(String id);

	public List<OrderItems> findByOrderId(String id);

	//前台查询订单
	public List<OrderItems> findItemByIndex(Map<String, Object> map);

	//后台查询订单明细
	public List<OrderItems> findOrderByIndex(Map<String, Object> info);
	
}
