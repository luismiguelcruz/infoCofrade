package com.infoCofrade.administration.userType;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.userType.bo.UserTypeBO;
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class UserTypeBean extends AbstractBean 
{
	private static final long serialVersionUID = 1L;

	private UserTypeVO userType;
	@Inject
	private UserTypeBO userTypeBO;

	private List<UserTypeVO> listUserTypes;
	
	private static final String listPageName = "listUserType";
	private static final String editPageName = "edit";
	
	
	public UserTypeBean(){
		super.init();
		this.userType = new UserTypeVO();
	}
	

	public void init(){
		super.init();
		
		this.userType = new UserTypeVO();
		this.createListUserType();
	}
	
	//------------------------------- ACTIONS ----------------------------------
	public String doNavigate(){
		this.init();
		
		return listPageName;
	}
	
	public void doAddFilter(){
		listUserTypes = this.userTypeBO.findUsingTemplate(null, userType);
	}
	
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
			this.userTypeBO.createElement(null, userType);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the user edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		if (id != null){
			userType = this.userTypeBO.findByPrimaryKey(null, Long.valueOf(id));
		}
		
		return editPageName;
	}
	
	
	public String doDeleteElement(){
		if (this.selectedDeleteItemId != null){
			UserTypeVO type = new UserTypeVO();
			type.setId(this.selectedDeleteItemId);
			this.userTypeBO.deleteElement(null, type);
			
			this.createListUserType();
		}
		
		return "success";
	}

	//---------------------- ADITIONAL FUNCTIONS ------------------------
	private void createListUserType(){
		listUserTypes = this.userTypeBO.findAll(null, null);
	}
	
	//----------------------- GETTERS / SETTERS -----------------------
	public UserTypeVO getUserType() {
		return userType;
	}

	public void setUserType(UserTypeVO userType) {
		this.userType = userType;
	}

	public UserTypeBO getUserTypeBO() {
		return userTypeBO;
	}

	public void setUserTypeBO(UserTypeBO userTypeBO) {
		this.userTypeBO = userTypeBO;
	}

	public List<UserTypeVO> getListUserTypes() {
		if(listUserTypes == null){
			this.createListUserType();
		}
		
		return listUserTypes;
	}

	public void setListUserTypes(List<UserTypeVO> listUserTypes) {
		this.listUserTypes = listUserTypes;
	}
	
	public List<UserTypeVO> getListUserTypeRols(){
		String where = "WHERE id > 0"; 
		return this.userTypeBO.findByWhere(null, where, null);
	}
}