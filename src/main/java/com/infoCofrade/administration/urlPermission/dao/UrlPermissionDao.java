package com.infoCofrade.administration.urlPermission.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.urlPermission.vo.UrlPermissionVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface UrlPermissionDao {
	public UrlPermissionVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<UrlPermissionVO> findAll(Transaction transaction, String order);
	
	public List<UrlPermissionVO> findUsingTemplate(Transaction transaction, UrlPermissionVO urlPermission);
	
	public List<UrlPermissionVO> findUsingExactTemplate(Transaction transaction, UrlPermissionVO urlPermission);
	
	public Long createElement(Transaction transaction, UrlPermissionVO urlPermission) throws DaoException;
	
	public void deleteElement(Transaction transaction, UrlPermissionVO urlPermission);
}