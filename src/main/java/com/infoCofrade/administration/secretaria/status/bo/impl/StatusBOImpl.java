package com.infoCofrade.administration.secretaria.status.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.status.bo.StatusBO;
import com.infoCofrade.administration.secretaria.status.dao.StatusDao;
import com.infoCofrade.administration.secretaria.status.dao.impl.StatusDaoImpl;
import com.infoCofrade.administration.secretaria.status.vo.StatusVO;
import com.infoCofrade.common.exceptions.DaoException;


public class StatusBOImpl implements StatusBO{
	private StatusDao statusDao = StatusDaoImpl.getInstance();
	
	public StatusVO findByPrimaryKey(Transaction transaction, Long id){
		return statusDao.findByPrimaryKey(transaction, id);
	}
	
	public List<StatusVO> findAll(Transaction transaction, String order){
		return statusDao.findAll(transaction, order);
	}
	
	public List<StatusVO> findUsingTemplate(Transaction transaction,
			StatusVO status, String order) {
		return statusDao.findUsingTemplate(transaction, status, order);
	}

	@Override
	public List<StatusVO> findUsingExactTemplate(Transaction transaction,
			StatusVO status, String order) {
		return statusDao.findUsingExactTemplate(transaction, status, order);
	}
	
	public void createElement(Transaction transaction, StatusVO status) throws DaoException{
		this.statusDao.createElement(transaction, status);
	}
	
	public void deleteElement(Transaction transaction, StatusVO status){
		this.statusDao.deleteElement(transaction, status);
	}
	
	public void deleteElementByWhere(Transaction transaction, String where){
		this.statusDao.deleteElementByWhere(transaction, where);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public StatusDao getStatusDao() {
		return statusDao;
	}

	public void setStatusDao(StatusDao statusDao) {
		this.statusDao = statusDao;
	}
}