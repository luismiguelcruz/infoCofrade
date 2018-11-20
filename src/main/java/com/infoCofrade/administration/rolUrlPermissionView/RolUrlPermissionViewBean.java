package com.infoCofrade.administration.rolUrlPermissionView;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.rolUrlPermissionView.bo.RolUrlPermissionViewBO;
import com.infoCofrade.administration.rolUrlPermissionView.vo.RolUrlPermissionViewVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;

@Named
@Scope("session")
public class RolUrlPermissionViewBean extends AbstractBean 
{
	private static final long serialVersionUID = 1L;

	private RolUrlPermissionViewVO rolUrlPermissionView;
	private RolUrlPermissionViewVO rolUrlPermissionViewSearch;

	@Inject
	private RolUrlPermissionViewBO rolUrlPermissionViewBO;
	
	private List<RolUrlPermissionViewVO> listRolUrlPermissionViews;
		
	/**
	 * Pages string for navigation cases
	 */
	private static final String listPageName = "listRolUrlPermissionView";
	private static final String editPageName = "edit";
	
	
	public RolUrlPermissionViewBean(){
		super.init();
		this.rolUrlPermissionView = new RolUrlPermissionViewVO();
		this.rolUrlPermissionViewSearch = new RolUrlPermissionViewVO();
	}

	public void init(){
		super.init();

		this.rolUrlPermissionView = new RolUrlPermissionViewVO();
		this.rolUrlPermissionViewSearch = new RolUrlPermissionViewVO();
		
		this.createListRolUrlPermissionView();
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
		this.listRolUrlPermissionViews = this.rolUrlPermissionViewBO.findUsingTemplate(null, rolUrlPermissionViewSearch);
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
			this.rolUrlPermissionViewBO.createElement(null, rolUrlPermissionView);

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
			rolUrlPermissionView = this.rolUrlPermissionViewBO.findByPrimaryKey(null, Long.valueOf(id));
		}

		return editPageName;
	}

	/**
	 * Delete an element from database
	 * @return String value of the destiny page
	 */
	public String doDeleteElement() {
		if (this.selectedDeleteItemId != null) {
			RolUrlPermissionViewVO rolUrlPermissionView = new RolUrlPermissionViewVO();
			rolUrlPermissionView.setId(this.selectedDeleteItemId);

			this.rolUrlPermissionViewBO.deleteElement(null, rolUrlPermissionView);

			this.createListRolUrlPermissionView();
		}

		return "success";
	}
	
	// ------------- ADITIONAL FUNCTIONS -----------------------
	private void createListRolUrlPermissionView() {
		listRolUrlPermissionViews = this.rolUrlPermissionViewBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS ---------------------------
	public RolUrlPermissionViewVO getRolUrlPermissionView() {
		return rolUrlPermissionView;
	}

	public void setRolUrlPermissionView(RolUrlPermissionViewVO rolUrlPermissionView) {
		this.rolUrlPermissionView = rolUrlPermissionView;
	}

	public RolUrlPermissionViewVO getRolUrlPermissionViewSearch() {
		return rolUrlPermissionViewSearch;
	}

	public void setRolUrlPermissionViewSearch(RolUrlPermissionViewVO rolUrlPermissionViewSearch) {
		this.rolUrlPermissionViewSearch = rolUrlPermissionViewSearch;
	}

	public RolUrlPermissionViewBO getRolUrlPermissionViewBO() {
		return rolUrlPermissionViewBO;
	}

	public void setRolUrlPermissionViewBO(RolUrlPermissionViewBO rolUrlPermissionViewBO) {
		this.rolUrlPermissionViewBO = rolUrlPermissionViewBO;
	}

	public void setListRolUrlPermissionViews(List<RolUrlPermissionViewVO> listRolUrlPermissionViews) {
		this.listRolUrlPermissionViews = listRolUrlPermissionViews;
	}

	public List<RolUrlPermissionViewVO> getListRolUrlPermissionViews(){
		if(listRolUrlPermissionViews == null){
			this.createListRolUrlPermissionView();
		}
		
		return listRolUrlPermissionViews;
	}
}