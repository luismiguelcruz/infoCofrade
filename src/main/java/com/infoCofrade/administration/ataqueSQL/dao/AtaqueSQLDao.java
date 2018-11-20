package com.infoCofrade.administration.ataqueSQL.dao;

import org.hibernate.Transaction;

import com.infoCofrade.administration.ataqueSQL.vo.AtaqueSQLVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface AtaqueSQLDao {
	public void createElement(StringBuilder sentencia) throws DaoException;
	
	public void deleteElement(Transaction transaction, AtaqueSQLVO ataqueSQL);
}