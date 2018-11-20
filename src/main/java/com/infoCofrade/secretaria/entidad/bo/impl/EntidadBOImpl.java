package com.infoCofrade.secretaria.entidad.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.entidad.bo.EntidadBO;
import com.infoCofrade.secretaria.entidad.dao.EntidadDao;
import com.infoCofrade.secretaria.entidad.dao.impl.EntidadDaoImpl;
import com.infoCofrade.secretaria.entidad.vo.EntidadVO;


public class EntidadBOImpl implements EntidadBO{
	private EntidadDao entidadDao = EntidadDaoImpl.getInstance();
	
	public EntidadVO findByPrimaryKey(Transaction transaction, Long id){
		return entidadDao.findByPrimaryKey(transaction, id);
	}
	
	public List<EntidadVO> findAll(Transaction transaction, String order){
		return entidadDao.findAll(transaction, order);
	}
	
	public List<EntidadVO> findUsingTemplate(Transaction transaction,
			EntidadVO entidad, String order) {
		return entidadDao.findUsingTemplate(transaction, entidad, order);
	}

	@Override
	public List<EntidadVO> findUsingExactTemplate(Transaction transaction,
			EntidadVO entidad, String order) {
		return entidadDao.findUsingExactTemplate(transaction, entidad, order);
	}
	
	public void createElement(Transaction transaction, EntidadVO entidad) throws DaoException{
		this.entidadDao.createElement(transaction, entidad);
	}
	
	public void deleteElement(Transaction transaction, EntidadVO entidad){
		this.entidadDao.deleteElement(transaction, entidad);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public EntidadDao getEntidadDao() {
		return entidadDao;
	}

	public void setEntidadDao(EntidadDao entidadDao) {
		this.entidadDao = entidadDao;
	}
}