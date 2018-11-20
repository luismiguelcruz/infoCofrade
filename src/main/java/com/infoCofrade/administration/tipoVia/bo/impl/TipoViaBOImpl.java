package com.infoCofrade.administration.tipoVia.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.tipoVia.bo.TipoViaBO;
import com.infoCofrade.administration.tipoVia.dao.TipoViaDao;
import com.infoCofrade.administration.tipoVia.dao.impl.TipoViaDaoImpl;
import com.infoCofrade.administration.tipoVia.vo.TipoViaVO;
import com.infoCofrade.common.exceptions.DaoException;


public class TipoViaBOImpl implements TipoViaBO{
	private TipoViaDao tipoViaDao = TipoViaDaoImpl.getInstance();
	
	public TipoViaVO findByPrimaryKey(Transaction transaction, Long id){
		return tipoViaDao.findByPrimaryKey(transaction, id);
	}
	
	public List<TipoViaVO> findAll(Transaction transaction, String order){
		return tipoViaDao.findAll(transaction, order);
	}
	
	public List<TipoViaVO> findUsingTemplate(Transaction transaction,
			TipoViaVO tipoVia, String order) {
		return tipoViaDao.findUsingTemplate(transaction, tipoVia, order);
	}

	@Override
	public List<TipoViaVO> findUsingExactTemplate(Transaction transaction,
			TipoViaVO tipoVia, String order) {
		return tipoViaDao.findUsingExactTemplate(transaction, tipoVia, order);
	}
	
	public void createElement(Transaction transaction, TipoViaVO tipoVia) throws DaoException{
		this.tipoViaDao.createElement(transaction, tipoVia);
	}
	
	public void deleteElement(Transaction transaction, TipoViaVO tipoVia){
		this.tipoViaDao.deleteElement(transaction, tipoVia);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public TipoViaDao getTipoViaDao() {
		return tipoViaDao;
	}

	public void setTipoViaDao(TipoViaDao tipoViaDao) {
		this.tipoViaDao = tipoViaDao;
	}
}