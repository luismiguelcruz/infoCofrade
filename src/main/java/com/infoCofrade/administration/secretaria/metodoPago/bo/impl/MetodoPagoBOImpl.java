package com.infoCofrade.administration.secretaria.metodoPago.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.metodoPago.bo.MetodoPagoBO;
import com.infoCofrade.administration.secretaria.metodoPago.dao.MetodoPagoDao;
import com.infoCofrade.administration.secretaria.metodoPago.dao.impl.MetodoPagoDaoImpl;
import com.infoCofrade.administration.secretaria.metodoPago.vo.MetodoPagoVO;
import com.infoCofrade.common.exceptions.DaoException;


public class MetodoPagoBOImpl implements MetodoPagoBO{
	private MetodoPagoDao metodoPagoDao = MetodoPagoDaoImpl.getInstance();
	
	public MetodoPagoVO findByPrimaryKey(Transaction transaction, Long id){
		return metodoPagoDao.findByPrimaryKey(transaction, id);
	}
	
	public List<MetodoPagoVO> findAll(Transaction transaction, String order){
		return metodoPagoDao.findAll(transaction, order);
	}
	
	public List<MetodoPagoVO> findUsingTemplate(Transaction transaction,
			MetodoPagoVO metodoPago, String order) {
		return metodoPagoDao.findUsingTemplate(transaction, metodoPago, order);
	}

	@Override
	public List<MetodoPagoVO> findUsingExactTemplate(Transaction transaction,
			MetodoPagoVO metodoPago, String order) {
		return metodoPagoDao.findUsingExactTemplate(transaction, metodoPago, order);
	}
	
	public void createAllElement(Transaction transaction, List<MetodoPagoVO> listMetodoPago) throws DaoException{
		this.metodoPagoDao.createAllElement(transaction, listMetodoPago);
	}
	
	public void createElement(Transaction transaction, MetodoPagoVO metodoPago) throws DaoException{
		this.metodoPagoDao.createElement(transaction, metodoPago);
	}
	
	public void deleteElement(Transaction transaction, MetodoPagoVO metodoPago){
		this.metodoPagoDao.deleteElement(transaction, metodoPago);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public MetodoPagoDao getMetodoPagoDao() {
		return metodoPagoDao;
	}

	public void setMetodoPagoDao(MetodoPagoDao metodoPagoDao) {
		this.metodoPagoDao = metodoPagoDao;
	}
}