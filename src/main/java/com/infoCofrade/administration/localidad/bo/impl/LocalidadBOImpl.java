package com.infoCofrade.administration.localidad.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.localidad.bo.LocalidadBO;
import com.infoCofrade.administration.localidad.dao.LocalidadDao;
import com.infoCofrade.administration.localidad.dao.impl.LocalidadDaoImpl;
import com.infoCofrade.administration.localidad.vo.LocalidadVO;
import com.infoCofrade.common.exceptions.DaoException;


public class LocalidadBOImpl implements LocalidadBO{
	private LocalidadDao localidadDao = LocalidadDaoImpl.getInstance();
	
	public LocalidadVO findByPrimaryKey(Transaction transaction, Long id){
		return localidadDao.findByPrimaryKey(transaction, id);
	}
	
	public List<LocalidadVO> findAll(Transaction transaction, String order){
		return localidadDao.findAll(transaction, order);
	}
	
	public List<LocalidadVO> findUsingTemplate(Transaction transaction,
			LocalidadVO localidad, String order) {
		return localidadDao.findUsingTemplate(transaction, localidad, order);
	}

	@Override
	public List<LocalidadVO> findUsingExactTemplate(Transaction transaction,
			LocalidadVO localidad, String order) {
		return localidadDao.findUsingExactTemplate(transaction, localidad, order);
	}
	
	public List<LocalidadVO> findAutoComplete(Transaction transaction, LocalidadVO localidad, String order){
		return localidadDao.findAutoComplete(transaction, localidad, order);
	}
	
	public void createElement(Transaction transaction, LocalidadVO localidad) throws DaoException{
		this.localidadDao.createElement(transaction, localidad);
	}
	
	public void deleteElement(Transaction transaction, LocalidadVO localidad){
		this.localidadDao.deleteElement(transaction, localidad);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public LocalidadDao getLocalidadDao() {
		return localidadDao;
	}

	public void setLocalidadDao(LocalidadDao localidadDao) {
		this.localidadDao = localidadDao;
	}
}