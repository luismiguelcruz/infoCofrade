package com.infoCofrade.administration.urlPermission.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.urlPermission.bo.UrlPermissionBO;
import com.infoCofrade.administration.urlPermission.dao.UrlPermissionDao;
import com.infoCofrade.administration.urlPermission.dao.impl.UrlPermissionDaoImpl;
import com.infoCofrade.administration.urlPermission.vo.UrlPermissionVO;
import com.infoCofrade.common.exceptions.DaoException;


public class UrlPermissionBOImpl implements UrlPermissionBO{
	private UrlPermissionDao urlPermissionDao = UrlPermissionDaoImpl.getInstance();
	
	public UrlPermissionVO findByPrimaryKey(Transaction transaction, Long id){
		return urlPermissionDao.findByPrimaryKey(transaction, id);
	}
	
	public List<UrlPermissionVO> findAll(Transaction transaction, String order){
		return urlPermissionDao.findAll(transaction, order);
	}
	
	public List<UrlPermissionVO> findUsingTemplate(Transaction transaction, UrlPermissionVO urlPermission){
		return urlPermissionDao.findUsingTemplate(transaction, urlPermission);
	}
	
	public List<UrlPermissionVO> findUsingExactTemplate(Transaction transaction, UrlPermissionVO urlPermission){
		return urlPermissionDao.findUsingExactTemplate(transaction, urlPermission);
	}
	
	public Long createElement(Transaction transaction, UrlPermissionVO urlPermission)
			throws DaoException{
		return this.urlPermissionDao.createElement(transaction, urlPermission);
	}
	
	public void deleteElement(Transaction transaction, UrlPermissionVO labResultType){
		this.urlPermissionDao.deleteElement(transaction, labResultType);
	}
}