package com.infoCofrade.administration.secretaria.calleView.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.calleView.vo.CalleViewVO;


public interface CalleViewBO{
	public CalleViewVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<CalleViewVO> findAll(Transaction transaction, String order);
	
	public List<CalleViewVO> findUsingTemplate(Transaction transaction, CalleViewVO CalleView, String order);
	
	public List<CalleViewVO> findUsingExactTemplate(Transaction transaction, CalleViewVO CalleView, String order);
}