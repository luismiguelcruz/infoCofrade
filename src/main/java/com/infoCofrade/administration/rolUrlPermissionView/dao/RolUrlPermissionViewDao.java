package com.infoCofrade.administration.rolUrlPermissionView.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.rolUrlPermissionView.vo.RolUrlPermissionViewVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface RolUrlPermissionViewDao {
	public RolUrlPermissionViewVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<RolUrlPermissionViewVO> findAll(Transaction transaction, String order);
	
	public List<RolUrlPermissionViewVO> findUsingTemplate(Transaction transaction, RolUrlPermissionViewVO rolUrlPermissionView);
	
	public List<RolUrlPermissionViewVO> findUsingExactTemplate(Transaction transaction, RolUrlPermissionViewVO rolUrlPermissionView);
	
	public Long createElement(Transaction transaction, RolUrlPermissionViewVO rolUrlPermissionView) throws DaoException;
	
	public void deleteElement(Transaction transaction, RolUrlPermissionViewVO rolUrlPermissionView);
}