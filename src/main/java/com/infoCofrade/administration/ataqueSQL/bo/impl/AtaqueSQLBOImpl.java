package com.infoCofrade.administration.ataqueSQL.bo.impl;

import org.hibernate.Transaction;

import com.infoCofrade.administration.ataqueSQL.bo.AtaqueSQLBO;
import com.infoCofrade.administration.ataqueSQL.dao.AtaqueSQLDao;
import com.infoCofrade.administration.ataqueSQL.dao.impl.AtaqueSQLDaoImpl;
import com.infoCofrade.administration.ataqueSQL.vo.AtaqueSQLVO;
import com.infoCofrade.common.exceptions.DaoException;


public class AtaqueSQLBOImpl implements AtaqueSQLBO{
	private AtaqueSQLDao ataqueSQLDao = AtaqueSQLDaoImpl.getInstance();
	
	public void createElement(StringBuilder sentencia) throws DaoException{
		this.ataqueSQLDao.createElement(sentencia);
	}
	
	public void deleteElement(Transaction transaction, AtaqueSQLVO ataqueSQL){
		this.ataqueSQLDao.deleteElement(transaction, ataqueSQL);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public AtaqueSQLDao getAtaqueSQLDao() {
		return ataqueSQLDao;
	}

	public void setAtaqueSQLDao(AtaqueSQLDao ataqueSQLDao) {
		this.ataqueSQLDao = ataqueSQLDao;
	}
}