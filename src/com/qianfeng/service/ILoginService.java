package com.qianfeng.service;

import com.qianfeng.entity.User;

public interface ILoginService {

	//��¼
	public int login(String name, String password);
	
	//ע��
	public void register(User user);
	
	public boolean userIsExist(String name);
}
