package com.infoCofrade.secretaria.statusPorHermano.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.statusPorHermano.bo.StatusPorHermanoBO;
import com.infoCofrade.secretaria.statusPorHermano.dao.StatusPorHermanoDao;
import com.infoCofrade.secretaria.statusPorHermano.dao.impl.StatusPorHermanoDaoImpl;
import com.infoCofrade.secretaria.statusPorHermano.vo.StatusPorHermanoVO;


public class StatusPorHermanoBOImpl implements StatusPorHermanoBO{
	private StatusPorHermanoDao statusPorHermanoDao = StatusPorHermanoDaoImpl.getInstance();
	
	public StatusPorHermanoVO findByPrimaryKey(Transaction transaction, Long id){
		return statusPorHermanoDao.findByPrimaryKey(transaction, id);
	}
	
	public List<StatusPorHermanoVO> findAll(Transaction transaction, String order){
		return statusPorHermanoDao.findAll(transaction, order);
	}
	
	public List<StatusPorHermanoVO> findUsingTemplate(Transaction transaction,
			StatusPorHermanoVO statusPorHermano, String order) {
		return statusPorHermanoDao.findUsingTemplate(transaction, statusPorHermano, order);
	}

	@Override
	public List<StatusPorHermanoVO> findUsingExactTemplate(Transaction transaction,
			StatusPorHermanoVO statusPorHermano, String order) {
		return statusPorHermanoDao.findUsingExactTemplate(transaction, statusPorHermano, order);
	}
	
	public void createElement(Transaction transaction, StatusPorHermanoVO statusPorHermano) throws DaoException{
		this.statusPorHermanoDao.createElement(transaction, statusPorHermano);
	}
	
	public void deleteElement(Transaction transaction, StatusPorHermanoVO statusPorHermano){
		this.statusPorHermanoDao.deleteElement(transaction, statusPorHermano);
	}
	
	public void deleteElementByWhere(Transaction transaction, String where){
		this.statusPorHermanoDao.deleteElementByWhere(transaction, where);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public StatusPorHermanoDao getStatusPorHermanoDao() {
		return statusPorHermanoDao;
	}

	public void setStatusPorHermanoDao(StatusPorHermanoDao statusPorHermanoDao) {
		this.statusPorHermanoDao = statusPorHermanoDao;
	}
}