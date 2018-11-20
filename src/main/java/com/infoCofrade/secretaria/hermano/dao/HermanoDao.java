package com.infoCofrade.secretaria.hermano.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.status.vo.StatusVO;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.hermano.vo.HermanoVO;


public interface HermanoDao {
	public HermanoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public Long findLastBrother(Transaction transaction);
	
	public List<HermanoVO> findAll(Transaction transaction, String order, Boolean onlyActive);
	
	public List<HermanoVO> findUsingTemplate(Transaction transaction, HermanoVO hermano, String order, Boolean onlyActive);
	
	public List<HermanoVO> findUsingExactTemplate(
			Transaction transaction, HermanoVO hermano, String order, Boolean onlyActive);
	
	public void updateBrotherNumber(Transaction transaction , HermanoVO hermano, String order);
	
	public void createElement(Transaction transaction, HermanoVO hermano) throws DaoException;
	
	public void createElement(Transaction transaction, HermanoVO hermano, List<StatusVO> listStatusPorHermano)
			throws DaoException;
	
	public void deleteElement(Transaction transaction, HermanoVO hermano);
	
	public void cancelElement(Transaction transaction, HermanoVO hermano) throws DaoException;
}