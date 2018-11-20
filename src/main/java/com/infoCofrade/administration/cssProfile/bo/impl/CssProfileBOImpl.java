package com.infoCofrade.administration.cssProfile.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.cssProfile.bo.CssProfileBO;
import com.infoCofrade.administration.cssProfile.dao.CssProfileDao;
import com.infoCofrade.administration.cssProfile.dao.impl.CssProfileDaoImpl;
import com.infoCofrade.administration.cssProfile.vo.CssProfileVO;
import com.infoCofrade.common.exceptions.DaoException;


public class CssProfileBOImpl implements CssProfileBO{
	private CssProfileDao cssProfileDao = CssProfileDaoImpl.getInstance();
	
	public CssProfileVO findByPrimaryKey(Transaction transaction, Long id){
		return cssProfileDao.findByPrimaryKey(transaction, id);
	}
	
	public List<CssProfileVO> findAll(Transaction transaction, String order){
		return cssProfileDao.findAll(transaction, order);
	}
	
	public List<CssProfileVO> findUsingTemplate(Transaction transaction,
			CssProfileVO cssProfile, String order) {
		return cssProfileDao.findUsingTemplate(transaction, cssProfile, order);
	}

	@Override
	public List<CssProfileVO> findUsingExactTemplate(Transaction transaction,
			CssProfileVO cssProfile, String order) {
		return cssProfileDao.findUsingExactTemplate(transaction, cssProfile, order);
	}
	
	public void createElement(Transaction transaction, CssProfileVO cssProfile) throws DaoException{
		this.cssProfileDao.createElement(transaction, cssProfile);
	}
	
	public void deleteElement(Transaction transaction, CssProfileVO cssProfile){
		this.cssProfileDao.deleteElement(transaction, cssProfile);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public CssProfileDao getCssProfileDao() {
		return cssProfileDao;
	}

	public void setCssProfileDao(CssProfileDao cssProfileDao) {
		this.cssProfileDao = cssProfileDao;
	}
}