package com.infoCofrade.administration.cssProfile;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.cssProfile.bo.CssProfileBO;
import com.infoCofrade.administration.cssProfile.vo.CssProfileVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class CssProfileBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private CssProfileVO cssProfile;
	private CssProfileVO cssProfileFilter;
	
	@Inject
	private CssProfileBO cssProfileBO;
	
	private List<CssProfileVO> listCssProfile;
	
	private static final String listPageName = "listCssProfile";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public CssProfileBean(){
		this.cssProfile = new CssProfileVO();
		this.cssProfileFilter = new CssProfileVO();
	}
	

	public void init(){
		super.init();
		
		this.cssProfile = new CssProfileVO();
		this.cssProfileFilter = new CssProfileVO();
		
		this.createListCssProfile();
	}
	
	public void clear(){
		super.init();
		
		this.cssProfile = new CssProfileVO();
		this.cssProfileFilter = new CssProfileVO();
	}
	
	//------------------------------- ACTIONS ----------------------------------
	/**
	 * Initialize the values where the list page is showed from menu
	 * @return String value of the destiny page
	 */
	public String doNavigate(){
		this.init();
		
		return listPageName;
	}
	
	/**
	 * Find cssProfile elements using the filter get.
	 */
	public void doAddFilter(){
		listCssProfile = this.cssProfileBO.findUsingTemplate(null, cssProfileFilter, null);
	}
	
	/**
	 * Clear the cssProfile filter of the list page
	 */
	public void doClearFilter(){
		this.init();
	}
	
	/**
	 * Add a new element into the database
	 * @return String value of the destiny page
	 */
	public String doCreateElement(){
		String navigateString = listPageName;
		
		try{
			this.cssProfileBO.createElement(null, cssProfile);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the cssProfile edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			cssProfile = this.cssProfileBO.findByPrimaryKey(null, Long.valueOf(id));
		}

		if(edition != null && Boolean.valueOf(edition)){
			return editPageName;	
		} else {
			return viewPageName;
		}
	}
	
	/**
	 * Delete an element from database
	 * @return String value of the destiny page
	 */
	public void doDeleteElement(){
		CssProfileVO cssProfile = new CssProfileVO();
		cssProfile.setId(this.selectedDeleteItemId);
		
		this.cssProfileBO.deleteElement(null, cssProfile);
		
		this.createListCssProfile();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	public void createListCssProfile(){
		listCssProfile = this.cssProfileBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public CssProfileVO getCssProfile() {
		return cssProfile;
	}

	public void setCssProfile(CssProfileVO cssProfile) {
		this.cssProfile = cssProfile;
	}

	public CssProfileVO getCssProfileFilter() {
		return cssProfileFilter;
	}

	public void setCssProfileFilter(CssProfileVO cssProfileFilter) {
		this.cssProfileFilter = cssProfileFilter;
	}

	public CssProfileBO getCssProfileBO() {
		return cssProfileBO;
	}

	public void setCssProfileBO(CssProfileBO cssProfileBO) {
		this.cssProfileBO = cssProfileBO;
	}

	public void setListCssProfile(List<CssProfileVO> listCssProfile) {
		this.listCssProfile = listCssProfile;
	}

	public List<CssProfileVO> getListCssProfile(){
		if(listCssProfile == null){
			this.createListCssProfile();
		}
		
		return listCssProfile;
	}
}