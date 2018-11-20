package com.infoCofrade.administration.secretaria.calleView.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.calleView.vo.CalleViewVO;


public interface CalleViewDao {
	public CalleViewVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<CalleViewVO> findAll(Transaction transaction, String order);
	
	public List<CalleViewVO> findUsingTemplate(Transaction transaction, CalleViewVO calleView, String order);
	
	public List<CalleViewVO> findUsingExactTemplate(Transaction transaction, CalleViewVO calleView, String order);
}