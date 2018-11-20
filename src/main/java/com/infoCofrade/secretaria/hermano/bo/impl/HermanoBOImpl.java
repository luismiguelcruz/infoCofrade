package com.infoCofrade.secretaria.hermano.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.status.vo.StatusVO;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.hermano.bo.HermanoBO;
import com.infoCofrade.secretaria.hermano.dao.HermanoDao;
import com.infoCofrade.secretaria.hermano.dao.impl.HermanoDaoImpl;
import com.infoCofrade.secretaria.hermano.vo.HermanoVO;


public class HermanoBOImpl implements HermanoBO{
	private HermanoDao hermanoDao = HermanoDaoImpl.getInstance();
	
	public HermanoVO findByPrimaryKey(Transaction transaction, Long id){
		return hermanoDao.findByPrimaryKey(transaction, id);
	}
	
	public Long findLastBrother(Transaction transaction){
		return hermanoDao.findLastBrother(transaction);
	}
	
	public List<HermanoVO> findAll(Transaction transaction, String order, Boolean onlyActive){
		return hermanoDao.findAll(transaction, order, onlyActive);
	}
	
	public List<HermanoVO> findUsingTemplate(Transaction transaction,
			HermanoVO hermano, String order, Boolean onlyActive) {
		return hermanoDao.findUsingTemplate(transaction, hermano, order, onlyActive);
	}

	@Override
	public List<HermanoVO> findUsingExactTemplate(Transaction transaction,
			HermanoVO hermano, String order, Boolean onlyActive) {
		return hermanoDao.findUsingExactTemplate(transaction, hermano, order, onlyActive);
	}
	
	public void updateBrotherNumber(Transaction transaction , HermanoVO hermano, String order){
		this.hermanoDao.updateBrotherNumber(transaction, hermano, order);
	}
	
	public void createElement(Transaction transaction, HermanoVO hermano) throws DaoException{
		this.hermanoDao.createElement(transaction, hermano);
	}
	
	public void createElement(Transaction transaction, HermanoVO hermano, List<StatusVO> listStatusPorHermano)
			throws DaoException{
		this.hermanoDao.createElement(transaction, hermano, listStatusPorHermano);
	}
	
	public void deleteElement(Transaction transaction, HermanoVO hermano){
		this.hermanoDao.deleteElement(transaction, hermano);
	}
	
	public void cancelElement(Transaction transaction, HermanoVO hermano) throws DaoException{
		this.hermanoDao.cancelElement(transaction, hermano);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public HermanoDao getHermanoDao() {
		return hermanoDao;
	}

	public void setHermanoDao(HermanoDao hermanoDao) {
		this.hermanoDao = hermanoDao;
	}
}