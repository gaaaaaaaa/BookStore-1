package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.User;

public interface IUserDao extends IBaseDao<User> {
	
	// �����û�����ѯ�û���Ϣ
	public User findByName(String name);

	public void setIsLock(String name);
	
	public void clearIsLock(String name);
	
	public void deleteByName(String name);
	
	public List<User> findByState();

	public Integer countUser();

	public List<User> findByIndex(Map<String, Object> map);
}
