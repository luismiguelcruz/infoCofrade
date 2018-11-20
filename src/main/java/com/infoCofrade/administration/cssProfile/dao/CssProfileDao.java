package com.infoCofrade.administration.cssProfile.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.cssProfile.vo.CssProfileVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface CssProfileDao {
	public CssProfileVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<CssProfileVO> findAll(Transaction transaction, String order);
	
	public List<CssProfileVO> findUsingTemplate(Transaction transaction, CssProfileVO cssProfile, String order);
	
	public List<CssProfileVO> findUsingExactTemplate(Transaction transaction, CssProfileVO cssProfile, String order);
	
	public void createElement(Transaction transaction, CssProfileVO cssProfile) throws DaoException;
	
	public void deleteElement(Transaction transaction, CssProfileVO cssProfile);
}