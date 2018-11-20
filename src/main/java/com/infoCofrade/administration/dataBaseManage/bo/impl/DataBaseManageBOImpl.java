package com.infoCofrade.administration.dataBaseManage.bo.impl;

import org.hibernate.Transaction;

import com.infoCofrade.administration.dataBaseManage.bo.DataBaseManageBO;
import com.infoCofrade.administration.dataBaseManage.dao.DataBaseManageDao;
import com.infoCofrade.administration.dataBaseManage.dao.impl.DataBaseManageDaoImpl;


public class DataBaseManageBOImpl implements DataBaseManageBO{
	private DataBaseManageDao dataBaseManageDao = DataBaseManageDaoImpl.getInstance();
	
	public void createViews(Transaction transaction, Boolean hermandadSelected){
		this.dataBaseManageDao.createViews(transaction, hermandadSelected);
	}
	
	public void createInserts(Transaction transaction, Boolean hermandadSelected){
		this.dataBaseManageDao.createInserts(transaction, hermandadSelected);
	}
	
	public void resetDataBase(Transaction transaction){
		this.dataBaseManageDao.resetDataBase(transaction);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public DataBaseManageDao getDataBaseManageDao() {
		return dataBaseManageDao;
	}

	public void setDataBaseManageDao(DataBaseManageDao dataBaseManageDao) {
		this.dataBaseManageDao = dataBaseManageDao;
	}
}