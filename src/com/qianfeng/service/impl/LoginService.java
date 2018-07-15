package com.qianfeng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IUserDao;
import com.qianfeng.entity.User;
import com.qianfeng.service.ILoginService;

@Service
public class LoginService implements ILoginService {

	@Autowired
	private IUserDao userDao;

	@Override
	public int login(String name, String password) {

		User user;
		try {
			user = userDao.findByName(name);
		} catch (Exception e) {

			throw e;
		}
		if (user == null) {
//			throw new RuntimeException("用户名不存在");
			return 0;
		}
		if (user.getIsLock() > 2) {
			// 用户锁定
			return 5;
		}
		if (!user.getPassword().equals(password)) {
			// throw new RuntimeException("密码错误");
			userDao.setIsLock(name);
			return 1;
		}
		userDao.clearIsLock(name);
		return 2;
	}

	@Override
	public void register(User user) {
		// TODO Auto-generated method stub

		if (user == null) {
			throw new RuntimeException("用户信息不能为空");
		}

		if (!user.getPassword().equals(user.getRePassword())) {
			throw new RuntimeException("两次输入的密码不一致");
		}

		try {
			userDao.add(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}

	}

	public boolean userIsExist(String name) {
		try {
			User user = userDao.findByName(name);
			if (user == null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

}
