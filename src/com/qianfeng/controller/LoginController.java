package com.qianfeng.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.User;
import com.qianfeng.service.ILoginService;
import com.qianfeng.vo.JsonBean;

@Controller
public class LoginController {
	
	@Autowired
	private ILoginService loginService;
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public @ResponseBody JsonBean logout(HttpSession session) {
		JsonBean bean = new JsonBean();
		
		try {
			if(session.getAttribute("loginname") == null) {
				bean.setCode(0);
				RuntimeException exception =  new RuntimeException("您未登录");
				bean.setMsg(exception.getMessage());
			}else {
				session.removeAttribute("loginname");
				bean.setCode(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody JsonBean login(@RequestParam("userName") String username,  @RequestParam("passWord") String password, HttpSession session, HttpServletResponse response){
		
		JsonBean bean = new JsonBean();
		try {
			int ret = loginService.login(username, password);
			if(ret == 0) {
				bean.setCode(0);
				RuntimeException e = new RuntimeException("用户名不存在");
				bean.setMsg(e.getMessage());
			}else if(ret == 1) {
				bean.setCode(1);
				RuntimeException e = new RuntimeException("密码错误");
				bean.setMsg(e.getMessage());
			}else if(ret == 5) {
				bean.setCode(5);
				RuntimeException e = new RuntimeException("您的账户已锁定，请联系管理员解锁");
				bean.setMsg(e.getMessage());
			}else {
				
				session.setAttribute("loginname", username);
				String sessionId = session.getId();
				Cookie cookie = new Cookie("JSESSIONID", sessionId);
				cookie.setMaxAge(1800);
				response.addCookie(cookie);
				bean.setCode(2);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			bean.setCode(3);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public @ResponseBody JsonBean register(User user){
		JsonBean bean = new JsonBean();
		try {
			loginService.register(user);
			bean.setCode(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping("/check")
	public @ResponseBody JsonBean checkUser(String userName){
		JsonBean bean = new JsonBean();
		try {
			boolean ret = loginService.userIsExist(userName);
			if(ret == true){
				bean.setCode(-1);
			}else{
				bean.setCode(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setCode(0);
		}
		return bean;
	}
}






