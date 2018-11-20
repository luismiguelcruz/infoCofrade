package com.infoCofrade.administration.rolUrlPermission.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.rolUrlPermission.bo.RolUrlPermissionBO;
import com.infoCofrade.administration.rolUrlPermission.dao.RolUrlPermissionDao;
import com.infoCofrade.administration.rolUrlPermission.dao.impl.RolUrlPermissionDaoImpl;
import com.infoCofrade.administration.rolUrlPermission.vo.RolUrlPermissionVO;
import com.infoCofrade.administration.rolUrlPermissionModel.vo.RolUrlPermissionModelVO;
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.common.exceptions.DaoException;


public class RolUrlPermissionBOImpl implements RolUrlPermissionBO{
	private RolUrlPermissionDao rolUrlPermissionDao = RolUrlPermissionDaoImpl.getInstance();
	
	public RolUrlPermissionVO findByPrimaryKey(Transaction transaction, Long id){
		return rolUrlPermissionDao.findByPrimaryKey(transaction, id);
	}
	
	public List<RolUrlPermissionVO> findAll(Transaction transaction, String order){
		return rolUrlPermissionDao.findAll(transaction, order);
	}
	
	public List<RolUrlPermissionVO> findUsingTemplate(Transaction transaction, RolUrlPermissionVO rolUrlPermission){
		return rolUrlPermissionDao.findUsingTemplate(transaction, rolUrlPermission);
	}
	
	public List<RolUrlPermissionVO> findUsingExactTemplate(Transaction transaction, RolUrlPermissionVO rolUrlPermission){
		return rolUrlPermissionDao.findUsingExactTemplate(transaction, rolUrlPermission);
	}
	
	public Long createElement(Transaction transaction, UserTypeVO userType,
			List<RolUrlPermissionModelVO> listRolUrlPermissionsModel) throws DaoException{
		return this.rolUrlPermissionDao.createElement(transaction, userType, listRolUrlPermissionsModel);
	}
	
	public void deleteElement(Transaction transaction, RolUrlPermissionVO labResultType){
		this.rolUrlPermissionDao.deleteElement(transaction, labResultType);
	}
	
	public void deleteElementByWhere(Transaction transaction, String where){
		this.rolUrlPermissionDao.deleteElementByWhere(transaction, where);
	}
}