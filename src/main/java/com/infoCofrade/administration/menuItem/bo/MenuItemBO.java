package com.infoCofrade.administration.menuItem.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.menuItem.vo.MenuItemVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface MenuItemBO{
	public MenuItemVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<MenuItemVO> findAll(Transaction transaction);
	
	public List<MenuItemVO> findUsingTemplate(MenuItemVO menuItem);
	
	public void createItem(MenuItemVO menuItem) throws DaoException;
	
	public void deleteItem(MenuItemVO menuItem);
}
