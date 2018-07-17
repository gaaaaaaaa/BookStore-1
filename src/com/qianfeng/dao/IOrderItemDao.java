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

	//ǰ̨��ѯ����
	public List<OrderItems> findItemByIndex(Map<String, Object> map);

	//��̨��ѯ������ϸ
	public List<OrderItems> findOrderByIndex(Map<String, Object> info);
	
}
