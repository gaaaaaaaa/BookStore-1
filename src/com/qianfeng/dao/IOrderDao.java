package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.OrderItems;
import com.qianfeng.entity.Orders;

public interface IOrderDao extends IBaseDao<Orders>{

	public int countOrder();
	
	public int countOrderByName(String name);

	public void deleteByName(String name);

	public List<OrderItems> findByIndex(Map<String, Object> map);

	public Orders findByOrderNum(String id);

	public void updateState(String id);

	public void deleteByOrderNum(String id);

}
