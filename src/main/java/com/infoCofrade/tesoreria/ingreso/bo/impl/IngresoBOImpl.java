package com.infoCofrade.tesoreria.ingreso.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.ingreso.bo.IngresoBO;
import com.infoCofrade.tesoreria.ingreso.dao.IngresoDao;
import com.infoCofrade.tesoreria.ingreso.dao.impl.IngresoDaoImpl;
import com.infoCofrade.tesoreria.ingreso.vo.IngresoVO;


public class IngresoBOImpl implements IngresoBO{
	private IngresoDao ingresoDao = IngresoDaoImpl.getInstance();
	
	public IngresoVO findByPrimaryKey(Transaction transaction, Long id){
		return ingresoDao.findByPrimaryKey(transaction, id);
	}
	
	public List<IngresoVO> findAll(Transaction transaction, String order){
		return ingresoDao.findAll(transaction, order);
	}
	
	public List<IngresoVO> findUsingTemplate(Transaction transaction,
			IngresoVO ingreso, String order) {
		return ingresoDao.findUsingTemplate(transaction, ingreso, order);
	}

	@Override
	public List<IngresoVO> findUsingExactTemplate(Transaction transaction,
			IngresoVO ingreso, String order) {
		return ingresoDao.findUsingExactTemplate(transaction, ingreso, order);
	}
	
	public void createElement(Transaction transaction, IngresoVO ingreso) throws DaoException{
		this.ingresoDao.createElement(transaction, ingreso);
	}
	
	public void deleteElement(Transaction transaction, IngresoVO ingreso){
		this.ingresoDao.deleteElement(transaction, ingreso);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public IngresoDao getIngresoDao() {
		return ingresoDao;
	}

	public void setIngresoDao(IngresoDao ingresoDao) {
		this.ingresoDao = ingresoDao;
	}
}