package com.qianfeng.controller;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.apache.catalina.ant.FindLeaksTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.OrderItems;
import com.qianfeng.entity.Orders;
import com.qianfeng.service.IOrderService;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class OrderController {

	@Autowired
	private IOrderService orderService;

	// 购物车请求购买
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public @ResponseBody JsonBean addorder(String[] ids, String[] nums, Double total, HttpSession session,
			HttpServletResponse response) {
		JsonBean bean = new JsonBean();
		String name = (String) session.getAttribute("loginname");

		try {
			Orders order = orderService.addOrderInfo(name, total);
			orderService.addOrderitems(ids, nums, order);

			// 添加订单成功，清空购物车
			Cookie cookie = new Cookie("cartIds", "");
			cookie.setMaxAge(0);
			response.addCookie(cookie);

			bean.setCode(1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}

	// 订单页请求显示订单
	@RequestMapping(value = "/orders/page/{page}", method = RequestMethod.GET)
	public @ResponseBody JsonBean findOrderInfo(@PathVariable("page") Integer page, Integer state,
			HttpSession session) {
		JsonBean bean = new JsonBean();
		String name = (String) session.getAttribute("loginname");
		try {
			PageBean<OrderItems> pageBean = orderService.findItemByIndex(name, page, state);
			bean.setCode(1);
			bean.setMsg(pageBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}

	//后台订单页请求显示订单明细
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public @ResponseBody JsonBean findOrderByOrderNum(@RequestParam("id")String id) {
		JsonBean bean = new JsonBean();
		try {
			List<OrderItems> list = orderService.findItemById(id);
			bean.setCode(1);
			bean.setMsg(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	// 后台订单页请求修改订单状态
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public @ResponseBody JsonBean updateOrderState(@RequestParam("oid") String oid) {
		JsonBean bean = new JsonBean();
		try {
			if (orderService.findOrderByOrderNum(oid) == null) {
				bean.setCode(0);
				bean.setMsg("订单信息不存在");
				throw new RuntimeException("订单信息不存在");
			}
			orderService.updateState(oid);
			bean.setCode(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	//后台订单页请求删除订单
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public @ResponseBody JsonBean deleteOrder(@RequestParam("oid")String oid) {
		JsonBean bean = new JsonBean();
		try {
			if (orderService.findOrderByOrderNum(oid) == null) {
				bean.setCode(0);
				bean.setMsg("订单信息不存在");
				throw new RuntimeException("订单信息不存在");
			}
			orderService.deleteOrder(oid);
			bean.setCode(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
}
