package com.infoCofrade.administration.dataBaseManage.bo;

import org.hibernate.Transaction;


public interface DataBaseManageBO{
	public void createViews(Transaction transaction, Boolean hermandadSelected);
	
	public void createInserts(Transaction transaction, Boolean hermandadSelected);
	
	public void resetDataBase(Transaction transaction);
}