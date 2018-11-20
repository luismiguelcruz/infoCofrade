package com.infoCofrade.administration.dataBaseManage.dao;

import org.hibernate.Transaction;


public interface DataBaseManageDao {
	public void createViews(Transaction transaction, Boolean hermandadSelected);
	
	public void createInserts(Transaction transaction, Boolean hermandadSelected);
	
	public void resetDataBase(Transaction transaction);
}