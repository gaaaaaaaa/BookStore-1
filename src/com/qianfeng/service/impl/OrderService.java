package com.qianfeng.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IBookDao;
import com.qianfeng.dao.IOrderDao;
import com.qianfeng.dao.IOrderItemDao;
import com.qianfeng.dao.IUserDao;
import com.qianfeng.entity.Books;
import com.qianfeng.entity.OrderItems;
import com.qianfeng.entity.Orders;
import com.qianfeng.entity.User;
import com.qianfeng.service.IOrderService;
import com.qianfeng.util.StringUtil;
import com.qianfeng.vo.PageBean;

@Service
public class OrderService implements IOrderService{

	@Autowired
	private IOrderDao orderDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IOrderItemDao orderItemDao;
	
	@Autowired
	private IBookDao bookDao;
	
	@Override
	public Orders addOrderInfo(String name, Double total) {
		//添加订单
		Orders order = new Orders();
		order.setTotalPrice(total);
		order.setState(0);
		order.setCreateDate(new Date());
		//使用UUID作为订单编号
		order.setOrderNum(UUID.randomUUID().toString());
		
		try {
			User user = userDao.findByName(name);
			order.setBuyer(user);
			orderDao.add(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return order;
	}

	@Override
	public void addOrderitems(String[] ids, String[] nums, Orders orders) {
		
		if(ids == null || ids.length == 0){
			throw new RuntimeException("图书数据不存在");
		}
		if(nums == null || nums.length == 0){
			throw new RuntimeException("购买数量不存在");
		}
		if(orders == null){
			throw new RuntimeException("订单数据不存在");
		}
		
		
		try {
			for (int i = 0; i < ids.length; ++i){
				OrderItems orderItems = new OrderItems();
				Books book = new Books();
				book.setId(Integer.parseInt(ids[i]));
				orderItems.setBook(book);
				orderItems.setOrder(orders);
				orderItems.setNum(Integer.parseInt(nums[i]));
				orderItemDao.add(orderItems);
				Map<String, Object> map = new HashMap<>();
				map.put("id",ids[i]);
				map.put("num", nums[i]);
				bookDao.updateStock(map);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public PageBean<OrderItems> findItemByIndex(String name, Integer page, Integer state){
		
		if(StringUtil.isNullOrEmpty(name)) {
			throw new RuntimeException("您未登录");
		}
		PageBean<OrderItems> pageInfo;
		try {
			pageInfo = new PageBean<>();
			pageInfo.setCurrentPage(page);
			int count;
			if("root".equals(name)) {
				 count = orderDao.countOrder();
			}
			else {
				 count = orderDao.countOrderByName(name);
			}
			pageInfo.setCount(count);
			
			int totalPage = 0;
			if(count % pageInfo.getSize() == 0) {
				totalPage = count / pageInfo.getSize();
				
			}else {
				totalPage = count / pageInfo.getSize() + 1;
			}
			pageInfo.setTotalPage(totalPage);
			
			Map<String, Object> map = new HashMap<>();
			map.put("index", (page-1)*pageInfo.getSize());
			map.put("size", pageInfo.getSize());
			map.put("name", name);
			if(state != 5) {
				map.put("state", state);
			}
			
			List<OrderItems> items;
			if("root".equals(name)) {
				items = orderItemDao.findOrderByIndex(map);
			}else {
				items = orderItemDao.findItemByIndex(map);
			}
			pageInfo.setPageInfos(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return pageInfo;
	}
	
	@Override
	public List<OrderItems> findItemById(String id) {
		
		List<OrderItems> list;
		try {
			list = orderItemDao.findByOrderId(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	@Override
	public boolean findOrderByName(String name) {
		// TODO Auto-generated method stub
		if(StringUtil.isNullOrEmpty(name)){
			throw new RuntimeException("书名为空");
		}else{
			try {
				Integer ret = orderItemDao.findOrderByName(name);
				if(ret != null && ret != 0) {
					return true;
				}else{
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
		}
	}

	@Override
	public void updateState(String id) {
		try {
			orderDao.updateState(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Orders findOrderByOrderNum(String id) {
		try {
			Orders orders = orderDao.findByOrderNum(id);
			if (orders == null) {
				throw new RuntimeException("订单数据不存在");
			}
			return orders;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteOrder(String oid) {
		try {
			orderItemDao.deleteById(oid);
			orderDao.deleteByOrderNum(oid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
	}
	

}
