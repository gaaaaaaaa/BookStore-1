package com.qianfeng.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IBookDao;
import com.qianfeng.entity.Books;
import com.qianfeng.service.ICartService;
import com.qianfeng.util.StringUtil;

@Service
public class CartService implements ICartService {

	@Autowired
	private IBookDao bookDao;

	@Override
	public String addCart(String[] bookIds, String cartId) {
		Set<String> set = new HashSet<>();
		if (!StringUtil.isNullOrEmpty(cartId)) {
			String[] split = cartId.split("#");
			for (String v : split) {
				set.add(v);
			}

		}
		if (bookIds == null || bookIds.length == 0) {
			throw new RuntimeException("没有选择相关图书");
		}

		// 1,2,3
		String info = "";
		for (int i = 0; i < bookIds.length; i++) {
			if (!set.contains(bookIds[i])) {
				set.add(bookIds[i]);
			}
		}

		for (String v : set) {
			info += v + "#";
		}
		info = info.substring(0, info.length() - 1);
		return info;
	}

	@Override
	public List<Books> findCartInfo(String ids) {
		if (StringUtil.isNullOrEmpty(ids)) {
			throw new RuntimeException("购物车中没有数据");
		}

		try {
			List<String> list = new ArrayList<>();
			String[] split = ids.split("#");
			for(String info : split){
				list.add(info);
			}
			List<Books> books = bookDao.findByIds(list);
			return books;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

}
