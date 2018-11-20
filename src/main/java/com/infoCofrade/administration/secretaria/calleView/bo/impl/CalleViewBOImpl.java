package com.infoCofrade.administration.secretaria.calleView.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.calleView.bo.CalleViewBO;
import com.infoCofrade.administration.secretaria.calleView.dao.CalleViewDao;
import com.infoCofrade.administration.secretaria.calleView.dao.impl.CalleViewDaoImpl;
import com.infoCofrade.administration.secretaria.calleView.vo.CalleViewVO;


public class CalleViewBOImpl implements CalleViewBO{
	private CalleViewDao calleViewDao = CalleViewDaoImpl.getInstance();
	
	public CalleViewVO findByPrimaryKey(Transaction transaction, Long id){
		return calleViewDao.findByPrimaryKey(transaction, id);
	}
	
	public List<CalleViewVO> findAll(Transaction transaction, String order){
		return calleViewDao.findAll(transaction, order);
	}
	
	public List<CalleViewVO> findUsingTemplate(Transaction transaction,
			CalleViewVO calleView, String order) {
		return calleViewDao.findUsingTemplate(transaction, calleView, order);
	}

	@Override
	public List<CalleViewVO> findUsingExactTemplate(Transaction transaction,
			CalleViewVO calleView, String order) {
		return calleViewDao.findUsingExactTemplate(transaction, calleView, order);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public CalleViewDao getCalleViewDao() {
		return calleViewDao;
	}

	public void setCalleViewDao(CalleViewDao calleViewDao) {
		this.calleViewDao = calleViewDao;
	}
}