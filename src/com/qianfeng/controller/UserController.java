package com.qianfeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.User;
import com.qianfeng.service.impl.UserService;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	//ɾ���û�        ����������  
	@RequestMapping(value="/deleteUser/{username}", method=RequestMethod.DELETE)
	public @ResponseBody JsonBean deleteUser(@PathVariable("username") String username) {
		JsonBean bean = new JsonBean();
		try {
			User user = userService.findByName(username);
			if(user == null) {
				bean.setCode(0);
				bean.setMsg("�û�������");
				throw new RuntimeException("�û�������");
			}
			try {
				
				userService.deleteByName(username);
				bean.setCode(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				bean.setCode(0);
				bean.setMsg(e.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	//�������������û�  ���� ����
	@RequestMapping(value="/searchUser/{username}", method=RequestMethod.GET)
	public @ResponseBody JsonBean findUser(@PathVariable("username") String username) {
		JsonBean bean = new JsonBean();
		try {
			User user = userService.findByName(username);
			if(user == null) {
				bean.setCode(0);
				bean.setMsg("�û�������");
				throw new RuntimeException("�û�������");
			}
			bean.setCode(1);
			bean.setMsg(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	//�����û��������û�         ���� �û���
	@RequestMapping(value="/unLockUser/{username}", method=RequestMethod.PUT)
	public @ResponseBody JsonBean unlockUser(@PathVariable("username") String username){
		JsonBean bean = new JsonBean();
		User uesr = userService.findByName(username);
		if(uesr == null) {
			bean.setCode(0);
			bean.setMsg("�û�������");
		}
		try {
			userService.unlockByName(username);
			bean.setCode(1);
			bean.setMsg(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	
//	//������б������û�       ����ֵList<User> �޲���
//	@RequestMapping(value="/getLockedUser", method=RequestMethod.GET)
//	public @ResponseBody JsonBean getLockUser(){
//		JsonBean bean = new JsonBean();
//		try {
//			List<User> infos = userService.findByState();
//			if(infos == null || infos.isEmpty()){
//				//û�������û�
//				bean.setCode(2);
//				bean.setMsg("û�������û�");
//			}
//			bean.setCode(1);
//			bean.setMsg(infos);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			bean.setCode(0);
//			bean.setMsg(e.getMessage());
//			
//		}
//		return bean;
//	}
	
	@RequestMapping(value="/users/page/{page}", method=RequestMethod.GET)
	public @ResponseBody JsonBean findByPage(@PathVariable("page") Integer page) {
		JsonBean bean = new JsonBean();
		
		try {
			PageBean<User> users = userService.findByPage(page);
			bean.setCode(1);
			bean.setMsg(users);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
		
	}
}
