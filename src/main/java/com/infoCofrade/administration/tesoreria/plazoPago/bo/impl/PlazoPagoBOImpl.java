package com.infoCofrade.administration.tesoreria.plazoPago.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.tesoreria.plazoPago.bo.PlazoPagoBO;
import com.infoCofrade.administration.tesoreria.plazoPago.dao.PlazoPagoDao;
import com.infoCofrade.administration.tesoreria.plazoPago.dao.impl.PlazoPagoDaoImpl;
import com.infoCofrade.administration.tesoreria.plazoPago.vo.PlazoPagoVO;
import com.infoCofrade.common.exceptions.DaoException;


public class PlazoPagoBOImpl implements PlazoPagoBO{
	private PlazoPagoDao plazoPagoDao = PlazoPagoDaoImpl.getInstance();
	
	public PlazoPagoVO findByPrimaryKey(Transaction transaction, Long id){
		return plazoPagoDao.findByPrimaryKey(transaction, id);
	}
	
	public List<PlazoPagoVO> findAll(Transaction transaction, String order){
		return plazoPagoDao.findAll(transaction, order);
	}
	
	public List<PlazoPagoVO> findUsingTemplate(Transaction transaction,
			PlazoPagoVO plazoPago, String order) {
		return plazoPagoDao.findUsingTemplate(transaction, plazoPago, order);
	}

	@Override
	public List<PlazoPagoVO> findUsingExactTemplate(Transaction transaction,
			PlazoPagoVO plazoPago, String order) {
		return plazoPagoDao.findUsingExactTemplate(transaction, plazoPago, order);
	}
	
	public void createAllElement(Transaction transaction, List<PlazoPagoVO> listPlazoPago) throws DaoException{
		this.plazoPagoDao.createAllElement(transaction, listPlazoPago);
	}
	
	public void createElement(Transaction transaction, PlazoPagoVO plazoPago) throws DaoException{
		this.plazoPagoDao.createElement(transaction, plazoPago);
	}
	
	public void deleteElement(Transaction transaction, PlazoPagoVO plazoPago){
		this.plazoPagoDao.deleteElement(transaction, plazoPago);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public PlazoPagoDao getPlazoPagoDao() {
		return plazoPagoDao;
	}

	public void setPlazoPagoDao(PlazoPagoDao plazoPagoDao) {
		this.plazoPagoDao = plazoPagoDao;
	}
}