package com.qianfeng.entity;

public class OrderItems {

	private Integer id;
	private Orders orders;
	private Books books;
	private Integer num;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Orders getOrder() {
		return orders;
	}
	public void setOrder(Orders order) {
		this.orders = order;
	}
	public Books getBook() {
		return books;
	}
	public void setBook(Books books) {
		this.books = books;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
}
