package com.infoCofrade.administration.pais.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.pais.bo.PaisBO;
import com.infoCofrade.administration.pais.dao.PaisDao;
import com.infoCofrade.administration.pais.dao.impl.PaisDaoImpl;
import com.infoCofrade.administration.pais.vo.PaisVO;
import com.infoCofrade.common.exceptions.DaoException;


public class PaisBOImpl implements PaisBO{
	private PaisDao paisDao = PaisDaoImpl.getInstance();
	
	public PaisVO findByPrimaryKey(Transaction transaction, Long id){
		return paisDao.findByPrimaryKey(transaction, id);
	}
	
	public List<PaisVO> findAll(Transaction transaction, String order){
		return paisDao.findAll(transaction, order);
	}
	
	public List<PaisVO> findUsingTemplate(Transaction transaction,
			PaisVO pais, String order) {
		return paisDao.findUsingTemplate(transaction, pais, order);
	}

	@Override
	public List<PaisVO> findUsingExactTemplate(Transaction transaction,
			PaisVO pais, String order) {
		return paisDao.findUsingExactTemplate(transaction, pais, order);
	}
	
	public void createElement(Transaction transaction, PaisVO pais) throws DaoException{
		this.paisDao.createElement(transaction, pais);
	}
	
	public void deleteElement(Transaction transaction, PaisVO pais){
		this.paisDao.deleteElement(transaction, pais);
	}
	
	public void deleteElementByWhere(Transaction transaction, String where){
		this.paisDao.deleteElementByWhere(transaction, where);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public PaisDao getPaisDao() {
		return paisDao;
	}

	public void setPaisDao(PaisDao paisDao) {
		this.paisDao = paisDao;
	}
}