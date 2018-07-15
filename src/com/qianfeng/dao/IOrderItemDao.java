package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.OrderItems;

public interface IOrderItemDao extends IBaseDao<OrderItems>{

	//²éÑ¯¶©µ¥Ã÷Ï¸
	public List<OrderItems> findByIndex(Map<String, Object> info);
	
	public Integer findOrderByName(String name);

	public void deleteByName(String name);

	public void deleteById(String id);

	public List<OrderItems> findByOrderId(String id);

}
