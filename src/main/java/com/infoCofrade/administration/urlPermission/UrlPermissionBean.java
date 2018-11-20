package com.infoCofrade.administration.urlPermission;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.menuItem.bo.MenuItemBO;
import com.infoCofrade.administration.menuItem.vo.MenuItemVO;
import com.infoCofrade.administration.urlPermission.bo.UrlPermissionBO;
import com.infoCofrade.administration.urlPermission.vo.UrlPermissionVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;

@Named
@Scope("session")
public class UrlPermissionBean extends AbstractBean 
{
	private static final long serialVersionUID = 1L;

	private UrlPermissionVO urlPermission;
	private UrlPermissionVO urlPermissionSearch;

	@Inject
	private UrlPermissionBO urlPermissionBO;
	@Inject
	private MenuItemBO menuItemBO;
	
	private List<UrlPermissionVO> listUrlPermissions;
	
	private List<MenuItemVO> listUrlGruops;
		
	/**
	 * Pages string for navigation cases
	 */
	private static final String listPageName = "listUrlPermission";
	private static final String editPageName = "edit";
	
	
	public UrlPermissionBean(){
		super.init();
		this.urlPermission = new UrlPermissionVO();
		this.urlPermissionSearch = new UrlPermissionVO();
	}

	public void init(){
		super.init();

		this.urlPermission = new UrlPermissionVO();
		this.urlPermissionSearch = new UrlPermissionVO();
		
		this.createListUrlPermission();
		this.createListUrlGruops();
	}
	
	//------------------------------- ACTIONS ----------------------------------
	/**
	 * Initialize the values where the list page is showed from menu
	 * @return String value of the destiny page
	 */
	public String doNavigate() {
		this.init();

		return listPageName;
	}

	/**
	 * Find user elements using the filter get.
	 * @return String value of the destiny page
	 */
	public void doAddFilter() {
		this.listUrlPermissions = this.urlPermissionBO.findUsingTemplate(null, urlPermissionSearch);
	}

	/**
	 * Clear the element filter of the list page
	 * @return String value of the destiny page
	 */
	public void doClearFilter() {
		this.init();
	}

	/**
	 * Add a new element into the database
	 * @return String value of the destiny page
	 */
	public String doCreateElement() {
		String navigateString = listPageName;

		try {
			this.urlPermissionBO.createElement(null, urlPermission);

			this.init();
		} catch (DaoException e) {
			navigateString = editPageName;
		}

		return navigateString;
	}

	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the disease edition
	 */
	public String doEditElement() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		
		if (id != null) {
			urlPermission = this.urlPermissionBO.findByPrimaryKey(null, Long.valueOf(id));
		}

		return editPageName;
	}

	/**
	 * Delete an element from database
	 * @return String value of the destiny page
	 */
	public String doDeleteElement() {
		if (this.selectedDeleteItemId != null) {
			UrlPermissionVO urlPermission = new UrlPermissionVO();
			urlPermission.setId(this.selectedDeleteItemId);

			this.urlPermissionBO.deleteElement(null, urlPermission);

			this.createListUrlPermission();
		}

		return "success";
	}
	
	// ------------- ADITIONAL FUNCTIONS -----------------------
	private void createListUrlPermission() {
		listUrlPermissions = this.urlPermissionBO.findAll(null, null);
	}
	
	private void createListUrlGruops(){
		listUrlGruops = this.menuItemBO.findAll(null);
	}
	
	//-------------------------- GETTERS / SETTERS ---------------------------
	public UrlPermissionVO getUrlPermission() {
		return urlPermission;
	}

	public void setUrlPermission(UrlPermissionVO urlPermission) {
		this.urlPermission = urlPermission;
	}

	public UrlPermissionVO getUrlPermissionSearch() {
		return urlPermissionSearch;
	}

	public void setUrlPermissionSearch(UrlPermissionVO urlPermissionSearch) {
		this.urlPermissionSearch = urlPermissionSearch;
	}

	public UrlPermissionBO getUrlPermissionBO() {
		return urlPermissionBO;
	}

	public void setUrlPermissionBO(UrlPermissionBO urlPermissionBO) {
		this.urlPermissionBO = urlPermissionBO;
	}

	public void setListUrlPermissions(List<UrlPermissionVO> listUrlPermissions) {
		this.listUrlPermissions = listUrlPermissions;
	}

	public List<UrlPermissionVO> getListUrlPermissions(){
		if(listUrlPermissions == null){
			this.createListUrlPermission();
		}
		
		return listUrlPermissions;
	}
	
	public List<MenuItemVO> getListUrlGruops() {
		return listUrlGruops;
	}

	public void setListUrlGruops(List<MenuItemVO> listUrlGruops) {
		this.listUrlGruops = listUrlGruops;
	}

	public List<MenuItemVO> getListMenuItemGroups(){
		if(listUrlGruops == null){
			this.createListUrlGruops();
		}
		
		return listUrlGruops;
	}
}