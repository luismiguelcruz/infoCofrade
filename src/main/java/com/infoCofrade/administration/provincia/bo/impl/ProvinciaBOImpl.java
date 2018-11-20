package com.infoCofrade.administration.provincia.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.provincia.bo.ProvinciaBO;
import com.infoCofrade.administration.provincia.dao.ProvinciaDao;
import com.infoCofrade.administration.provincia.dao.impl.ProvinciaDaoImpl;
import com.infoCofrade.administration.provincia.vo.ProvinciaVO;
import com.infoCofrade.common.exceptions.DaoException;


public class ProvinciaBOImpl implements ProvinciaBO{
	private ProvinciaDao provinciaDao = ProvinciaDaoImpl.getInstance();
	
	public ProvinciaVO findByPrimaryKey(Transaction transaction, Long id){
		return provinciaDao.findByPrimaryKey(transaction, id);
	}
	
	public List<ProvinciaVO> findAll(Transaction transaction, String order){
		return provinciaDao.findAll(transaction, order);
	}
	
	public List<ProvinciaVO> findUsingTemplate(Transaction transaction,
			ProvinciaVO provincia, String order) {
		return provinciaDao.findUsingTemplate(transaction, provincia, order);
	}

	@Override
	public List<ProvinciaVO> findUsingExactTemplate(Transaction transaction,
			ProvinciaVO provincia, String order) {
		return provinciaDao.findUsingExactTemplate(transaction, provincia, order);
	}
	
	public List<ProvinciaVO> findAutoComplete(Transaction transaction, ProvinciaVO provincia, String order){
		return provinciaDao.findAutoComplete(transaction, provincia, order);
	}
	
	public void createElement(Transaction transaction, ProvinciaVO provincia) throws DaoException{
		this.provinciaDao.createElement(transaction, provincia);
	}
	
	public void deleteElement(Transaction transaction, ProvinciaVO provincia){
		this.provinciaDao.deleteElement(transaction, provincia);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public ProvinciaDao getProvinciaDao() {
		return provinciaDao;
	}

	public void setProvinciaDao(ProvinciaDao provinciaDao) {
		this.provinciaDao = provinciaDao;
	}
}