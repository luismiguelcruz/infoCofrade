package com.infoCofrade.administration.menuItem.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.menuItem.vo.MenuItemVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface MenuItemDao {
	public MenuItemVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<MenuItemVO> findAll(Transaction transaction);
	
	public Long countAll();
	
	public List<MenuItemVO> findUsingTemplate(Transaction transaction, MenuItemVO menuItem);
	
	public Long countUsingTemplate(MenuItemVO menuItem);
	
	public Long countByQuery(String query);
	
	public void createItem(MenuItemVO menuItem) throws DaoException;
	
	public void deleteItem(MenuItemVO menuItem);
}
