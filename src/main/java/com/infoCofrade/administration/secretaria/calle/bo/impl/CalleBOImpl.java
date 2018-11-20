package com.infoCofrade.administration.secretaria.calle.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.calle.bo.CalleBO;
import com.infoCofrade.administration.secretaria.calle.dao.CalleDao;
import com.infoCofrade.administration.secretaria.calle.dao.impl.CalleDaoImpl;
import com.infoCofrade.administration.secretaria.calle.vo.CalleVO;
import com.infoCofrade.common.exceptions.DaoException;


public class CalleBOImpl implements CalleBO{
	private CalleDao calleDao = CalleDaoImpl.getInstance();
	
	public CalleVO findByPrimaryKey(Transaction transaction, Long id){
		return calleDao.findByPrimaryKey(transaction, id);
	}
	
	public List<CalleVO> findAll(Transaction transaction, String order){
		return calleDao.findAll(transaction, order);
	}
	
	public List<CalleVO> findUsingTemplate(Transaction transaction,
			CalleVO calle, String order) {
		return calleDao.findUsingTemplate(transaction, calle, order);
	}

	@Override
	public List<CalleVO> findUsingExactTemplate(Transaction transaction,
			CalleVO calle, String order) {
		return calleDao.findUsingExactTemplate(transaction, calle, order);
	}
	
	public void createElement(Transaction transaction, CalleVO calle) throws DaoException{
		this.calleDao.createElement(transaction, calle);
	}
	
	public void deleteElement(Transaction transaction, CalleVO calle){
		this.calleDao.deleteElement(transaction, calle);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public CalleDao getCalleDao() {
		return calleDao;
	}

	public void setCalleDao(CalleDao calleDao) {
		this.calleDao = calleDao;
	}
}