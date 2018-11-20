package com.infoCofrade.secretaria.acto.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.acto.bo.ActoBO;
import com.infoCofrade.secretaria.acto.dao.ActoDao;
import com.infoCofrade.secretaria.acto.dao.impl.ActoDaoImpl;
import com.infoCofrade.secretaria.acto.vo.ActoVO;


public class ActoBOImpl implements ActoBO{
	private ActoDao actoDao = ActoDaoImpl.getInstance();
	
	public ActoVO findByPrimaryKey(Transaction transaction, Long id){
		return actoDao.findByPrimaryKey(transaction, id);
	}
	
	public List<ActoVO> findAll(Transaction transaction, String order){
		return actoDao.findAll(transaction, order);
	}
	
	public List<ActoVO> findActoByYear(Transaction transaction, Integer anyo, String order){
		return actoDao.findActoByYear(transaction, anyo, order);
	}
	
	public List<ActoVO> findUsingTemplate(Transaction transaction,
			ActoVO acto, String order) {
		return actoDao.findUsingTemplate(transaction, acto, order);
	}

	@Override
	public List<ActoVO> findUsingExactTemplate(Transaction transaction,
			ActoVO acto, String order) {
		return actoDao.findUsingExactTemplate(transaction, acto, order);
	}
	
	public void createElement(Transaction transaction, ActoVO acto) throws DaoException{
		this.actoDao.createElement(transaction, acto);
	}
	
	public void deleteElement(Transaction transaction, ActoVO acto){
		this.actoDao.deleteElement(transaction, acto);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public ActoDao getActoDao() {
		return actoDao;
	}

	public void setActoDao(ActoDao actoDao) {
		this.actoDao = actoDao;
	}
}