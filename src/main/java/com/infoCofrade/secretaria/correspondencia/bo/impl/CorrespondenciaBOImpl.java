package com.infoCofrade.secretaria.correspondencia.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.correspondencia.bo.CorrespondenciaBO;
import com.infoCofrade.secretaria.correspondencia.dao.CorrespondenciaDao;
import com.infoCofrade.secretaria.correspondencia.dao.impl.CorrespondenciaDaoImpl;
import com.infoCofrade.secretaria.correspondencia.vo.CorrespondenciaVO;


public class CorrespondenciaBOImpl implements CorrespondenciaBO{
	private CorrespondenciaDao correspondenciaDao = CorrespondenciaDaoImpl.getInstance();
	
	public CorrespondenciaVO findByPrimaryKey(Transaction transaction, Long id){
		return correspondenciaDao.findByPrimaryKey(transaction, id);
	}
	
	public List<CorrespondenciaVO> findAll(Transaction transaction, String order){
		return correspondenciaDao.findAll(transaction, order);
	}
	
	public List<CorrespondenciaVO> findUsingTemplate(Transaction transaction,
			CorrespondenciaVO correspondencia, String order) {
		return correspondenciaDao.findUsingTemplate(transaction, correspondencia, order);
	}

	@Override
	public List<CorrespondenciaVO> findUsingExactTemplate(Transaction transaction,
			CorrespondenciaVO correspondencia, String order) {
		return correspondenciaDao.findUsingExactTemplate(transaction, correspondencia, order);
	}
	
	public void createElement(Transaction transaction, CorrespondenciaVO correspondencia) throws DaoException{
		this.correspondenciaDao.createElement(transaction, correspondencia);
	}
	
	public void deleteElement(Transaction transaction, CorrespondenciaVO correspondencia){
		this.correspondenciaDao.deleteElement(transaction, correspondencia);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public CorrespondenciaDao getCorrespondenciaDao() {
		return correspondenciaDao;
	}

	public void setCorrespondenciaDao(CorrespondenciaDao correspondenciaDao) {
		this.correspondenciaDao = correspondenciaDao;
	}
}