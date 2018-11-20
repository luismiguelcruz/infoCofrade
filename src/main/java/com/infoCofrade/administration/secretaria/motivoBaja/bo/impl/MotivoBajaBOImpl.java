package com.infoCofrade.administration.secretaria.motivoBaja.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.motivoBaja.bo.MotivoBajaBO;
import com.infoCofrade.administration.secretaria.motivoBaja.dao.MotivoBajaDao;
import com.infoCofrade.administration.secretaria.motivoBaja.dao.impl.MotivoBajaDaoImpl;
import com.infoCofrade.administration.secretaria.motivoBaja.vo.MotivoBajaVO;
import com.infoCofrade.common.exceptions.DaoException;


public class MotivoBajaBOImpl implements MotivoBajaBO{
	private MotivoBajaDao motivoBajaDao = MotivoBajaDaoImpl.getInstance();
	
	public MotivoBajaVO findByPrimaryKey(Transaction transaction, Long id){
		return motivoBajaDao.findByPrimaryKey(transaction, id);
	}
	
	public List<MotivoBajaVO> findAll(Transaction transaction, String order){
		return motivoBajaDao.findAll(transaction, order);
	}
	
	public List<MotivoBajaVO> findUsingTemplate(Transaction transaction,
			MotivoBajaVO motivoBaja, String order) {
		return motivoBajaDao.findUsingTemplate(transaction, motivoBaja, order);
	}

	@Override
	public List<MotivoBajaVO> findUsingExactTemplate(Transaction transaction,
			MotivoBajaVO motivoBaja, String order) {
		return motivoBajaDao.findUsingExactTemplate(transaction, motivoBaja, order);
	}
	
	public void createElement(Transaction transaction, MotivoBajaVO motivoBaja) throws DaoException{
		this.motivoBajaDao.createElement(transaction, motivoBaja);
	}
	
	public void deleteElement(Transaction transaction, MotivoBajaVO motivoBaja){
		this.motivoBajaDao.deleteElement(transaction, motivoBaja);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public MotivoBajaDao getMotivoBajaDao() {
		return motivoBajaDao;
	}

	public void setMotivoBajaDao(MotivoBajaDao motivoBajaDao) {
		this.motivoBajaDao = motivoBajaDao;
	}
}