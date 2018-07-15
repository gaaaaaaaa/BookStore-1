package com.qianfeng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IBookDao;
import com.qianfeng.entity.Books;
import com.qianfeng.service.IBookService;
import com.qianfeng.vo.PageBean;

@Service
public class BookService implements IBookService{

	@Autowired
	private IBookDao bookDao;
	
	@Override
	public PageBean<Books> findByPage(Integer page) {
		
		if(page == null || page < 1){
			throw new RuntimeException("页码数据有误");
		}
		PageBean<Books> pageBean = new PageBean<>();
		pageBean.setCurrentPage(page);
		
		//获取所有记录
		int count = 0;
		try {
			count = bookDao.countBook();
			pageBean.setCount(count);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		
		//计算总页数
		if(count % pageBean.getSize() == 0){
			pageBean.setTotalPage(count / pageBean.getSize());
			
		}else{
			pageBean.setTotalPage(count / pageBean.getSize() + 1);
		}
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("size", pageBean.getSize());
		
		int index = (page-1)*pageBean.getSize();
		map.put("index", index);
		
		try {
			List<Books> books = bookDao.findByIndex(map);
			pageBean.setPageInfos(books);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return pageBean;
	}

	@Override
	public PageBean<Books> findDelByPage(Integer page) {
		
		if(page == null || page < 1){
			throw new RuntimeException("页码数据有误");
		}
		PageBean<Books> pageBean = new PageBean<>();
		pageBean.setCurrentPage(page);
		
		//获取所有记录
		int count = 0;
		try {
			count = bookDao.countDelBook();
			pageBean.setCount(count);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		
		//计算总页数
		if(count % pageBean.getSize() == 0){
			pageBean.setTotalPage(count / pageBean.getSize());
			
		}else{
			pageBean.setTotalPage(count / pageBean.getSize() + 1);
		}
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("size", pageBean.getSize());
		
		int index = (page-1)*pageBean.getSize();
		map.put("index", index);
		
		try {
			List<Books> books = bookDao.findDelByIndex(map);
			pageBean.setPageInfos(books);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return pageBean;
	}
	
	@Override
	public int findStockById(Integer id, Integer nums) {
		
		try {
			if(bookDao.findStockById(id) < nums) {
				return 0;
			}else {
				return 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		try {
			bookDao.deleteById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateBook(Books book) {
		// TODO Auto-generated method stub
		try {
			bookDao.update(book);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Books findByName(String bookname) {
		// TODO Auto-generated method stub
		Books book = null;
		try {
			bookDao.findByName(bookname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return book;
	}

	@Override
	public List<Books> findAll() {
		// TODO Auto-generated method stub
		List<Books> infos = null;
		try {
			infos = bookDao.findAll();
			return infos;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	public void addBook(Books books) {
		// TODO Auto-generated method stub
		if (books == null) {
			throw new RuntimeException("图书不能为空");
		}

		try {
			bookDao.add(books);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

	@Override
	public void editBook(Books books) {
		// TODO Auto-generated method stub
		if (books == null) {
			throw new RuntimeException("图书信息不能为空");
		}

		try {
			bookDao.update(books);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
}
