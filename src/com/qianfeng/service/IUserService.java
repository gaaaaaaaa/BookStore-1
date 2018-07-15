package com.qianfeng.service;

import java.util.List;

import com.qianfeng.entity.User;

public interface IUserService {
	public void deleteByName(String name);
	
	public User findByName(String name);
	
	public List<User> findByState();
	
	public void unlockByName(String name);
}
