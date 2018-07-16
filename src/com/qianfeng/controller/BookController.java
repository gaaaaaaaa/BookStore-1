package com.qianfeng.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qianfeng.entity.Books;
import com.qianfeng.service.IBookService;
import com.qianfeng.service.IOrderService;
import com.qianfeng.util.StringUtil;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class BookController {
	
	@Autowired
	private IBookService bookService;
	
	@RequestMapping(value="/books/page/{page}", method=RequestMethod.GET)
	//参数注解表示从路径中取{}中的值
	public @ResponseBody JsonBean findByPage(@PathVariable("page") Integer page, Integer state, HttpSession session){
		
		JsonBean bean = new JsonBean();
		try {
			PageBean<Books> infos = bookService.findByPage(page);
			if((String) session.getAttribute("loginname") == null) {
				infos.setIsLog(0);
			}else {
				infos.setIsLog(1);
			}
			bean.setCode(1);
			bean.setMsg(infos);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping(value="/stock", method=RequestMethod.POST)
	public @ResponseBody JsonBean findStockById(Integer id, Integer nums) {
		
		JsonBean bean = new JsonBean(); 
		try {
			if(bookService.findStockById(id, nums) == 1) {
				bean.setCode(1);
			}else {
				bean.setCode(0);
				RuntimeException exception = new RuntimeException("库存不足");
				bean.setMsg(exception.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	
	//查一本书
	@RequestMapping(value="/searchBookManage/{bookname}", method=RequestMethod.GET)
	public @ResponseBody JsonBean findByName(@PathVariable("bookname") String bookname) {
		JsonBean bean = new JsonBean();
		try {
			Books info = bookService.findByName(bookname);
			bean.setCode(1);
			bean.setMsg(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		} 
		return bean;
	}
	
	//删除书
	@RequestMapping(value = "/deleteBookManage/{bid}", method=RequestMethod.DELETE)
	public @ResponseBody JsonBean deleteBook(@PathVariable("bid") Integer bid){
		System.out.println(bid);
		JsonBean bean = new JsonBean();
		Books book = new Books();
		book.setId(bid);
		if(bid == null) {
			RuntimeException e =  new RuntimeException("书的编号为空");
			bean.setCode(0);
			bean.setMsg(e.getMessage());
			return bean;
		}
		
		try {
//			if(orderService.findOrderById(bid)){
//				bean.setCode(0);
//				bean.setMsg("存在相关联订单");
//				return bean;
//			}
			bookService.deleteById(bid);
			bean.setCode(1);
			bean.setMsg(book);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return bean;
	}

	@RequestMapping(value="/delbooks/page/{page}", method=RequestMethod.GET)
	public @ResponseBody JsonBean findDelPage(@PathVariable("page") Integer page){
		
		JsonBean bean = new JsonBean();
		try {
			PageBean<Books> infos = bookService.findDelByPage(page);
			bean.setCode(1);
			bean.setMsg(infos);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	
	@RequestMapping(value="/addBook", method=RequestMethod.POST)
	public @ResponseBody JsonBean addBook(@RequestParam MultipartFile imgfile, Books book){
		
		JsonBean bean = new JsonBean();
		String fileName = imgfile.getOriginalFilename();
		
		String path = "D:/upload";
		File d = new File(path);
		if(!d.exists()){
			d.mkdir();
		}
		
		File file = new File(path, fileName);
		
		try {
			imgfile.transferTo(file);
			book.setImg("images/book/" + fileName);
			bookService.addBook(book);
			bean.setCode(1);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
		
	}
	
	@RequestMapping(value="/editBook", method=RequestMethod.POST)
	public @ResponseBody JsonBean editBook(@RequestParam("bookId")String bookId, @RequestParam MultipartFile imgfile, Books book){
		int id = Integer.parseInt(bookId);
		book.setId(id);
		System.out.println(book.getId());
		JsonBean bean = new JsonBean();
		String fileName = imgfile.getOriginalFilename();
		
		String path = "D:/upload";
		File d = new File(path);
		if(!d.exists()){
			d.mkdir();
		}
		
		File file = new File(path, fileName);
		
		try {
			imgfile.transferTo(file);
			book.setImg("images/book/" + fileName);
			bookService.editBook(book);
			bean.setCode(1);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
		
	}
}
