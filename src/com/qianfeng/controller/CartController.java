package com.qianfeng.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.Books;
import com.qianfeng.service.ICartService;
import com.qianfeng.vo.JsonBean;

@Controller
public class CartController {

	@Autowired
	private ICartService cartService;

	// 向购物车中添加数据，将数据存在cookie里
	@RequestMapping(value = "/setcarts", method = RequestMethod.POST)

	// @CookieValue读取指定cookie
	public @ResponseBody JsonBean addCart(String[] bookIds,
			@CookieValue(value = "cartIds", defaultValue = "") String cartId, HttpServletResponse response) {
		JsonBean bean = new JsonBean();
		String info;
		try {
			info = cartService.addCart(bookIds, cartId);

			Cookie cookie = new Cookie("cartIds", info);
			cookie.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie);
			bean.setCode(1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}

		return bean;
	}

	@RequestMapping(value = "/getcarts", method = RequestMethod.GET)
	public @ResponseBody JsonBean carts(@CookieValue(value="cartIds", defaultValue = "") String cartId) {
		JsonBean bean = new JsonBean();
		try {
			List<Books> infos = cartService.findCartInfo(cartId);
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
}
