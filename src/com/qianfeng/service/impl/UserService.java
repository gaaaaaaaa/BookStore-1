package com.qianfeng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IOrderDao;
import com.qianfeng.dao.IOrderItemDao;
import com.qianfeng.dao.IUserDao;
import com.qianfeng.entity.User;
import com.qianfeng.service.IUserService;
import com.qianfeng.util.StringUtil;
import com.qianfeng.vo.PageBean;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IOrderItemDao orderItemDao;
	
	@Autowired
	private IOrderDao orderDao;
	
	@Override
	public void deleteByName(String name) {
		// TODO Auto-generated method stub
		if(StringUtil.isNullOrEmpty(name)){
			throw new RuntimeException("用户名为空");
		}
		try {
		
			orderItemDao.deleteByName(name);
			orderDao.deleteByName(name);
			userDao.deleteByName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public User findByName(String name) {
		// TODO Auto-generated method stub
		if(StringUtil.isNullOrEmpty(name)){
			throw new RuntimeException("用户名为空");
		}
		try {
			User user = userDao.findByName(name);
			return user;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<User> findByState() {
		// TODO Auto-generated method stub
		try {
			List<User> list = userDao.findByState();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void unlockByName(String name) {
		// TODO Auto-generated method stub
		try {
			userDao.clearIsLock(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public PageBean<User> findByPage(Integer page) {
		if(page == null || page < 1){
			throw new RuntimeException("页码数据有误");
		}
		PageBean<User> pageBean = new PageBean<>();
		pageBean.setCurrentPage(page);
		
		//获取所有记录
		int count = 0;
		try {
			count = userDao.countUser();
			pageBean.setCount(count);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		
		//计算总页数
		if(count % pageBean.getUserSize() == 0){
			pageBean.setTotalPage(count / pageBean.getUserSize());
			
		}else{
			pageBean.setTotalPage(count / pageBean.getUserSize() + 1);
		}
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("size", pageBean.getUserSize());
		
		int index = (page-1)*pageBean.getUserSize();
		map.put("index", index);
		
		try {
			List<User> users = userDao.findByIndex(map);
			pageBean.setPageInfos(users);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return pageBean;
	}

}
