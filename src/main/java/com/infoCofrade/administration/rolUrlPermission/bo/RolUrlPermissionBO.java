package com.infoCofrade.administration.rolUrlPermission.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.rolUrlPermission.vo.RolUrlPermissionVO;
import com.infoCofrade.administration.rolUrlPermissionModel.vo.RolUrlPermissionModelVO;
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface RolUrlPermissionBO{
	public RolUrlPermissionVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<RolUrlPermissionVO> findAll(Transaction transaction, String order);
		
	public List<RolUrlPermissionVO> findUsingTemplate(Transaction transaction, RolUrlPermissionVO rolUrlPermission);
		
	public List<RolUrlPermissionVO> findUsingExactTemplate(Transaction transaction, RolUrlPermissionVO rolUrlPermission);

	public Long createElement(Transaction transacion, UserTypeVO userType,
			List<RolUrlPermissionModelVO> listRolUrlPermissionsModel) throws DaoException;
		
	public void deleteElement(Transaction transaction, RolUrlPermissionVO rolUrlPermission);
	
	public void deleteElementByWhere(Transaction transaction, String where);
}