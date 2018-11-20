package com.infoCofrade.administration.menuItem;

import java.util.List;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.ToggleEvent;
import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.menuItem.bo.MenuItemBO;
import com.infoCofrade.administration.menuItem.vo.MenuItemVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;

@Named
@Scope("session")
public class MenuItemBean extends AbstractBean {
	private static final long serialVersionUID = 1L;

	/** Value Object */
	private MenuItemVO menuItem;
	private MenuItemVO menuItemConfiguration;
	private MenuItemVO subMenuConfiguration;
	private MenuItemVO subMenuItemConfiguration;

	/** Access object to the BO interface */
	@Inject
	private MenuItemBO menuItemBO;

	/** Select the submenu to show */
	private Integer selectedSubMenu;

	/** List that contains the menu that every user can view */
	private List<MenuItemVO> listMenuItems;

	/** List that contains the menu items configuration */
	private List<MenuItemVO> listMenuItemsConfiguration;

	private static final String listPageName = "listMenuItem";
	private static final String editPageName = "edit";

	public MenuItemBean() {
		this.init();
		this.menuItem = new MenuItemVO();
		this.menuItemConfiguration = new MenuItemVO();
		this.subMenuConfiguration = new MenuItemVO();
		this.subMenuItemConfiguration = new MenuItemVO();
		this.selectedSubMenu = -1;
	}

	public void init() {
		super.init();
		this.menuItem = new MenuItemVO();
		this.menuItemConfiguration = new MenuItemVO();
		this.subMenuConfiguration = new MenuItemVO();
		this.subMenuItemConfiguration = new MenuItemVO();
		this.selectedSubMenu = -1;
	}

	// --------------------- ACTIONS ---------------------
	public String doShowSubMenu(Integer idMenu) {
		return "success";
	}

	/**
	 * Open a submenu associated to the menu selected by the user
	 * 
	 * @return
	 */
	public String doShowSubMenu() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		this.selectedSubMenu = Integer.valueOf(id);

		return "success";
	}

	/**
	 * Generic navigate in the general menu
	 * 
	 * @return
	 */
	public String doNavigateMenu() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		String beanName = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("beanName");

		AbstractBean bean = (AbstractBean) FacesContext.getCurrentInstance()
				.getApplication().getELResolver()
				.getValue(elContext, null, beanName);
		this.selectedSubMenu = -1;

		return bean.doNavigate();
	}

	/**
	 * Navigate to the Menu Item Configuration Page
	 */
	public String doNavigate() {
		this.init();
		this.createListMenuItemConfiguration();

		return listPageName;
	}

	/**
	 * Show the sub menus for a selected menu into the menu configuration
	 * interface
	 * 
	 * @return String name of the
	 */
	public void doChangeSubMenuWatch() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Long id = Long.valueOf(fc.getExternalContext().getRequestParameterMap()
				.get("id"));
		this.subMenuConfiguration.setIdParent(id);
	}
	
	/**
	 * Show the sub menus for a selected menu into the menu configuration
	 * interface
	 * 
	 * @return String name of the
	 */
	public void doChangeSubMenuItemWatch() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Long id = Long.valueOf(fc.getExternalContext().getRequestParameterMap()
				.get("id"));
		this.subMenuItemConfiguration.setIdParent(id);
	}

	/**
	 * Show an extra message when a row of a table is dropped down
	 * 
	 * @param event
	 */
	public void onRowToggle(ToggleEvent event) {
		/*
		 * FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
		 * "Row State " + event.getVisibility(), "");
		 * FacesContext.getCurrentInstance().addMessage(null, msg);
		 */
	}

	/**
	 * Clear the menu element
	 * 
	 * @return
	 */
	public String doClearFilter() {
		this.menuItemConfiguration = new MenuItemVO();

		return "success";
	}
	
	/**
	 * Clear the submenu element
	 * 
	 * @return
	 */
	public void doClearFilterSubMenu() {
		this.subMenuConfiguration = new MenuItemVO();
	}

	/**
	 * Clear the submenu element
	 * 
	 * @return
	 */
	public String doClearFilterSubMenuItem() {
		this.subMenuItemConfiguration = new MenuItemVO();

		return "success";
	}

	/**
	 * Create a new element into the database
	 * 
	 * @return
	 */
	public String doAddMenuItem() {
		MenuItemVO menuItem = new MenuItemVO();
		menuItem.setItemName(menuItemConfiguration.getItemName());
		menuItem.setOrderToShow(menuItemConfiguration.getOrderToShow());
		menuItem.setBeanName(menuItemConfiguration.getBeanName());

		try {
			this.menuItemBO.createItem(menuItem);
			// Load the new list of elements
			this.createListMenuItemConfiguration();
			this.createListMenuItem();
		} catch (DaoException e) {
		}

		return "success";
	}
	
	/**
	 * Create a new element into the database
	 * 
	 * @return
	 */
	public void doAddSubMenu() {
		MenuItemVO menuItem = new MenuItemVO();
		menuItem.setId(subMenuConfiguration.getId());
		menuItem.setItemName(subMenuConfiguration.getItemName());
		menuItem.setIdParent(subMenuConfiguration.getIdParent());
		menuItem.setOrderToShow(subMenuConfiguration.getOrderToShow());
		menuItem.setBeanName(subMenuConfiguration.getBeanName());
		menuItem.setHasChildren(true);

		try {
			this.menuItemBO.createItem(menuItem);
			// Load the new list of elements
			this.createListMenuItemConfiguration();
			this.init();
		} catch (DaoException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", e.getMessage()));
		}
	}

	/**
	 * Create a new element into the database
	 * 
	 * @return
	 */
	public String doAddSubMenuItem() {
		MenuItemVO menuItem = new MenuItemVO();
		menuItem.setId(subMenuItemConfiguration.getId());
		menuItem.setItemName(subMenuItemConfiguration.getItemName());
		menuItem.setIdParent(subMenuItemConfiguration.getIdParent());
		menuItem.setOrderToShow(subMenuItemConfiguration.getOrderToShow());
		menuItem.setBeanName(subMenuItemConfiguration.getBeanName());
		menuItem.setHasChildren(false);

		try {
			this.menuItemBO.createItem(menuItem);
			// Load the new list of elements
			this.createListMenuItemConfiguration();
			this.init();
		} catch (DaoException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Database error: ", e.getMessage()));
		}

		return "success";
	}

	/**
	 * Delete an element from the database
	 * @return
	 */
	public String doRemoveMenuItem() {
		MenuItemVO menuItem = new MenuItemVO();
		menuItem.setId(this.selectedDeleteItemId);

		this.menuItemBO.deleteItem(menuItem);

		// Load the new list of elements
		this.createListMenuItemConfiguration();
		this.createListMenuItem();

		return "success";
	}

	public void doEditElement() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");

		if (id != null) {
			this.subMenuItemConfiguration = this.menuItemBO.findByPrimaryKey(
					null, Long.valueOf(id));
		}
	}

	// ---------------- ADITIONAL FUNCTIONS ----------------
	private void createListMenuItem() {
		listMenuItems = this.menuItemBO.findAll(null);
		if (listMenuItems.size() > 0) {
			MenuItemVO menuItemSearch = new MenuItemVO();
			List<MenuItemVO> listSubMenuItems;
			for (int i = 0; i < listMenuItems.size(); i++) {
				MenuItemVO elem = listMenuItems.get(i);
				menuItemSearch.setIdParent(elem.getId());
				listSubMenuItems = this.menuItemBO
						.findUsingTemplate(menuItemSearch);
				if (listSubMenuItems.size() > 0) {
					elem.setListSubMenu(listSubMenuItems);
					listMenuItems.set(i, elem);
				}
			}
		}
	}

	private void createListMenuItemConfiguration() {
		listMenuItemsConfiguration = this.menuItemBO.findAll(null);
		if (listMenuItemsConfiguration.size() > 0) {
			MenuItemVO menuItemSearch = new MenuItemVO();
			List<MenuItemVO> listSubMenuItems;
			for (int i = 0; i < listMenuItemsConfiguration.size(); i++) {
				MenuItemVO elem = listMenuItemsConfiguration.get(i);
				menuItemSearch.setIdParent(elem.getId());
				listSubMenuItems = this.menuItemBO
						.findUsingTemplate(menuItemSearch);
				if (listSubMenuItems.size() > 0) {
					elem.setListSubMenu(listSubMenuItems);
					listMenuItemsConfiguration.set(i, elem);
				}
			}
		}
	}

	// ----------------- GETTERS / SETTERS -----------------
	public MenuItemVO getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItemVO menuItem) {
		this.menuItem = menuItem;
	}

	public MenuItemVO getMenuItemConfiguration() {
		return menuItemConfiguration;
	}

	public void setMenuItemConfiguration(MenuItemVO menuItemConfiguration) {
		this.menuItemConfiguration = menuItemConfiguration;
	}

	public MenuItemVO getSubMenuConfiguration() {
		return subMenuConfiguration;
	}

	public void setSubMenuConfiguration(MenuItemVO subMenuConfiguration) {
		this.subMenuConfiguration = subMenuConfiguration;
	}

	public MenuItemVO getSubMenuItemConfiguration() {
		return subMenuItemConfiguration;
	}

	public void setSubMenuItemConfiguration(MenuItemVO subMenuItemConfiguration) {
		this.subMenuItemConfiguration = subMenuItemConfiguration;
	}

	public MenuItemBO getMenuItemBO() {
		return menuItemBO;
	}

	public void setMenuItemBO(MenuItemBO menuItemBO) {
		this.menuItemBO = menuItemBO;
	}

	public List<MenuItemVO> getListMenuItems() {
		this.createListMenuItem();

		return listMenuItems;
	}

	public void setListMenuItems(List<MenuItemVO> listMenuItems) {
		this.listMenuItems = listMenuItems;
	}

	public Integer getSelectedSubMenu() {
		return selectedSubMenu;
	}

	public void setSelectedSubMenu(Integer selectedSubMenu) {
		this.selectedSubMenu = selectedSubMenu;
	}

	public List<MenuItemVO> getListMenuItemsConfiguration() {
		if (listMenuItemsConfiguration == null
				|| listMenuItemsConfiguration.size() == 0) {
			this.createListMenuItemConfiguration();
		}

		return listMenuItemsConfiguration;
	}

	public void setListMenuItemsConfiguration(
			List<MenuItemVO> listMenuItemsConfiguration) {
		this.listMenuItemsConfiguration = listMenuItemsConfiguration;
	}
}