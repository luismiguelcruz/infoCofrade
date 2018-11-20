package com.infoCofrade.tesoreria.cuenta.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.cuenta.bo.CuentaBO;
import com.infoCofrade.tesoreria.cuenta.dao.CuentaDao;
import com.infoCofrade.tesoreria.cuenta.dao.impl.CuentaDaoImpl;
import com.infoCofrade.tesoreria.cuenta.vo.CuentaVO;


public class CuentaBOImpl implements CuentaBO{
	private CuentaDao cuentaDao = CuentaDaoImpl.getInstance();
	
	public CuentaVO findByPrimaryKey(Transaction transaction, Long id){
		return cuentaDao.findByPrimaryKey(transaction, id);
	}
	
	public List<CuentaVO> findAll(Transaction transaction, String order){
		return cuentaDao.findAll(transaction, order);
	}
	
	public List<CuentaVO> findUsingTemplate(Transaction transaction,
			CuentaVO cuenta, String order) {
		return cuentaDao.findUsingTemplate(transaction, cuenta, order);
	}
	
	public List<CuentaVO> findUsingTemplateForLevel(Transaction transaction,
			CuentaVO cuenta, String order){
		return cuentaDao.findUsingTemplateForLevel(transaction, cuenta, order);
	}

	@Override
	public List<CuentaVO> findUsingExactTemplate(Transaction transaction,
			CuentaVO cuenta, String order) {
		return cuentaDao.findUsingExactTemplate(transaction, cuenta, order);
	}
	
	public void createElement(Transaction transaction, CuentaVO cuenta) throws DaoException{
		this.cuentaDao.createElement(transaction, cuenta);
	}
	
	public void deleteElement(Transaction transaction, CuentaVO cuenta){
		this.cuentaDao.deleteElement(transaction, cuenta);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public CuentaDao getCuentaDao() {
		return cuentaDao;
	}

	public void setCuentaDao(CuentaDao cuentaDao) {
		this.cuentaDao = cuentaDao;
	}
}