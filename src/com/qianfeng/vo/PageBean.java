package com.qianfeng.vo;

import java.util.List;

public class PageBean<T> {

	private Integer currentPage;
	private Integer totalPage;
	private Integer size = 4;//ÿҳ��ʾ��¼��
	private Integer userSize = 10;//ÿҳ��ʾ�û���
	
	private Integer count;    //�ܼ�¼��
	private List<T> pageInfos;//��ѯ���ķ�ҳ����
	
	private Integer isLog;//�Ƿ��¼
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public List<T> getPageInfos() {
		return pageInfos;
	}
	public void setPageInfos(List<T> pageInfos) {
		this.pageInfos = pageInfos;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getUserSize() {
		return userSize;
	}
	public void setUserSize(Integer userSize) {
		this.userSize = userSize;
	}
	public Integer getIsLog() {
		return isLog;
	}
	public void setIsLog(Integer isLog) {
		this.isLog = isLog;
	}
	
}
