package com.infoCofrade.administration.hermandad.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.hermandad.bo.HermandadBO;
import com.infoCofrade.administration.hermandad.dao.HermandadDao;
import com.infoCofrade.administration.hermandad.dao.impl.HermandadDaoImpl;
import com.infoCofrade.administration.hermandad.vo.HermandadVO;
import com.infoCofrade.common.exceptions.DaoException;


public class HermandadBOImpl implements HermandadBO{
	private HermandadDao hermandadDao = HermandadDaoImpl.getInstance();
	
	public HermandadVO findByPrimaryKey(Transaction transaction, Long id){
		return hermandadDao.findByPrimaryKey(transaction, id);
	}
	
	public List<HermandadVO> findAll(Transaction transaction, String order){
		return hermandadDao.findAll(transaction, order);
	}
	
	public List<HermandadVO> findUsingTemplate(Transaction transaction,
			HermandadVO hermandad, String order) {
		return hermandadDao.findUsingTemplate(transaction, hermandad, order);
	}

	@Override
	public List<HermandadVO> findUsingExactTemplate(Transaction transaction,
			HermandadVO hermandad, String order) {
		return hermandadDao.findUsingExactTemplate(transaction, hermandad, order);
	}
	
	public void createElement(Transaction transaction, HermandadVO hermandad) throws DaoException{
		this.hermandadDao.createElement(transaction, hermandad);
	}
	
	public void deleteElement(Transaction transaction, HermandadVO hermandad){
		this.hermandadDao.deleteElement(transaction, hermandad);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public HermandadDao getHermandadDao() {
		return hermandadDao;
	}

	public void setHermandadDao(HermandadDao hermandadDao) {
		this.hermandadDao = hermandadDao;
	}
}