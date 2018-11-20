package com.infoCofrade.administration.rolUrlPermissionView.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.rolUrlPermissionView.bo.RolUrlPermissionViewBO;
import com.infoCofrade.administration.rolUrlPermissionView.dao.RolUrlPermissionViewDao;
import com.infoCofrade.administration.rolUrlPermissionView.dao.impl.RolUrlPermissionViewDaoImpl;
import com.infoCofrade.administration.rolUrlPermissionView.vo.RolUrlPermissionViewVO;
import com.infoCofrade.common.exceptions.DaoException;


public class RolUrlPermissionViewBOImpl implements RolUrlPermissionViewBO{
	private RolUrlPermissionViewDao rolUrlPermissionViewDao = RolUrlPermissionViewDaoImpl.getInstance();
	
	public RolUrlPermissionViewVO findByPrimaryKey(Transaction transaction, Long id){
		return rolUrlPermissionViewDao.findByPrimaryKey(transaction, id);
	}
	
	public List<RolUrlPermissionViewVO> findAll(Transaction transaction, String order){
		return rolUrlPermissionViewDao.findAll(transaction, order);
	}
	
	public List<RolUrlPermissionViewVO> findUsingTemplate(Transaction transaction, RolUrlPermissionViewVO rolUrlPermissionView){
		return rolUrlPermissionViewDao.findUsingTemplate(transaction, rolUrlPermissionView);
	}
	
	public List<RolUrlPermissionViewVO> findUsingExactTemplate(Transaction transaction, RolUrlPermissionViewVO rolUrlPermissionView){
		return rolUrlPermissionViewDao.findUsingExactTemplate(transaction, rolUrlPermissionView);
	}
	
	public Long createElement(Transaction transaction, RolUrlPermissionViewVO rolUrlPermissionView)
			throws DaoException{
		return this.rolUrlPermissionViewDao.createElement(transaction, rolUrlPermissionView);
	}
	
	public void deleteElement(Transaction transaction, RolUrlPermissionViewVO labResultType){
		this.rolUrlPermissionViewDao.deleteElement(transaction, labResultType);
	}
}