package com.infoCofrade.administration.menuItem.vo;

import java.io.Serializable;
import java.util.List;

public class MenuItemVO implements Serializable{
	private static final long serialVersionUID = -2073949974831414254L;
	
	private Long id;
	private String itemName;
	private String beanName;
	private Long idParent;
	private Integer orderToShow;
	private Boolean hasChildren;
	
	private List<MenuItemVO> listSubMenu;
	
	public MenuItemVO(){
		this.id = null;
		this.itemName = null;
		this.beanName = null;
		this.idParent = null;
		this.orderToShow = null;
		this.listSubMenu = null;
		this.hasChildren = null;
	}
	
	//----------------------- GETTERS / SETTERS --------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public Long getIdParent() {
		return idParent;
	}

	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}

	public Integer getOrderToShow() {
		return orderToShow;
	}

	public void setOrderToShow(Integer orderToShow) {
		this.orderToShow = orderToShow;
	}

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public List<MenuItemVO> getListSubMenu() {
		return listSubMenu;
	}

	public void setListSubMenu(List<MenuItemVO> listSubMenu) {
		this.listSubMenu = listSubMenu;
	}
	
	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			MenuItemVO menuItem = (MenuItemVO) obj;
			if((menuItem.getId() == null && this.id == null) ||
					menuItem.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
