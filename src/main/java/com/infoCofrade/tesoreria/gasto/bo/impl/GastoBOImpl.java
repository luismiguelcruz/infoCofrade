package com.infoCofrade.tesoreria.gasto.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.gasto.bo.GastoBO;
import com.infoCofrade.tesoreria.gasto.dao.GastoDao;
import com.infoCofrade.tesoreria.gasto.dao.impl.GastoDaoImpl;
import com.infoCofrade.tesoreria.gasto.vo.GastoVO;


public class GastoBOImpl implements GastoBO{
	private GastoDao gastoDao = GastoDaoImpl.getInstance();
	
	public GastoVO findByPrimaryKey(Transaction transaction, Long id){
		return gastoDao.findByPrimaryKey(transaction, id);
	}
	
	public List<GastoVO> findAll(Transaction transaction, String order){
		return gastoDao.findAll(transaction, order);
	}
	
	public List<GastoVO> findUsingTemplate(Transaction transaction,
			GastoVO gasto, String order) {
		return gastoDao.findUsingTemplate(transaction, gasto, order);
	}

	@Override
	public List<GastoVO> findUsingExactTemplate(Transaction transaction,
			GastoVO gasto, String order) {
		return gastoDao.findUsingExactTemplate(transaction, gasto, order);
	}
	
	public void createElement(Transaction transaction, GastoVO gasto) throws DaoException{
		this.gastoDao.createElement(transaction, gasto);
	}
	
	public void deleteElement(Transaction transaction, GastoVO gasto){
		this.gastoDao.deleteElement(transaction, gasto);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public GastoDao getGastoDao() {
		return gastoDao;
	}

	public void setGastoDao(GastoDao gastoDao) {
		this.gastoDao = gastoDao;
	}
}