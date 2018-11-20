package com.infoCofrade.administration.menuItem.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.menuItem.bo.MenuItemBO;
import com.infoCofrade.administration.menuItem.dao.MenuItemDao;
import com.infoCofrade.administration.menuItem.dao.impl.MenuItemDaoImpl;
import com.infoCofrade.administration.menuItem.vo.MenuItemVO;
import com.infoCofrade.common.exceptions.DaoException;


public class MenuItemBOImpl implements MenuItemBO{
	private MenuItemDao menuItemDao = MenuItemDaoImpl.getInstance();
	
	public MenuItemVO findByPrimaryKey(Transaction transaction, Long id){
		return menuItemDao.findByPrimaryKey(transaction, id);
	}
	
	public List<MenuItemVO> findAll(Transaction transaction){
		return menuItemDao.findAll(transaction);
	}
	
	public List<MenuItemVO> findUsingTemplate(MenuItemVO menuItem){
		return menuItemDao.findUsingTemplate(null, menuItem);
	}
	
	public void createItem(MenuItemVO menuItem) throws DaoException{
		this.menuItemDao.createItem(menuItem);
	}
	
	public void deleteItem(MenuItemVO menuItem){
		this.menuItemDao.deleteItem(menuItem);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public MenuItemDao getMenuItemDao() {
		return menuItemDao;
	}

	public void setMenuItemDao(MenuItemDao menuItemDao) {
		this.menuItemDao = menuItemDao;
	}
}
