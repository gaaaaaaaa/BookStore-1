package com.qianfeng.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qianfeng.entity.Books;

@Service
public interface ICartService {

	public String addCart(String[] bookIds, String cartId);

	public List<Books> findCartInfo(String ids);

}
