package com.qianfeng.service;

import java.util.List;

import com.qianfeng.entity.Books;
import com.qianfeng.vo.PageBean;

public interface IBookService {

	public PageBean<Books> findByPage(Integer page);

	public int findStockById(Integer id, Integer nums);
	
	
	public void deleteById(Integer id);
	
	public void updateBook(Books book);
	
	public Books findByName(String bookname);
	
	public List<Books> findAll();

	PageBean<Books> findDelByPage(Integer page);
	
	
	public void addBook(Books books);

	public void editBook(Books book);
}

