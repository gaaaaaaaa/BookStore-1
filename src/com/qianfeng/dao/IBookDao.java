package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.Books;

public interface IBookDao extends IBaseDao<Books>{
	
	//��ҳ��ѯ
	public List<Books> findByIndex(Map<String, Object> pageInfo);
	
	//��ѯ�鼮
	public List<Books> findByIds(List<String> ids);
	
	public Integer countBook();

	public Integer findStockById(Integer id);
	
	public void deleteById(Integer id);
	
	public Books findByName(String bookname);

	public void updateStock(Map<String, Object> map);
	
	public int countDelBook();

	public List<Books> findDelByIndex(Map<String, Object> map);
	
	public void update(Books books);
}
