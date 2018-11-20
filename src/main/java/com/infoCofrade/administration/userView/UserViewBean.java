package com.infoCofrade.administration.userView;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.userType.bo.UserTypeBO;
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.administration.userView.bo.UserViewBO;
import com.infoCofrade.administration.userView.vo.UserViewVO;
import com.infoCofrade.common.bean.AbstractBean;


@Named
@Scope("session")
public class UserViewBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private UserViewVO userView;
	
	@Inject
	private UserViewBO userViewBO;
	@Inject
	private UserTypeBO userTypeBO;
	
	private List<UserViewVO> listUserViews;
	
	private List<UserTypeVO> listUserType;
	
	private static final String listPageName = "listUserView";
	private static final String editPageName = "edit";
	
	
	public UserViewBean(){
		this.userView = new UserViewVO();
	}
	

	public void init(){
		super.init();
		
		this.userView = new UserViewVO();
		this.createListUserView();
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
	 * Find userView elements using the filter get. 
	 */
	public void doAddFilter(){
		this.changeDataBaseSchema();
		listUserViews = this.userViewBO.findUsingTemplate(null, userView);
		this.restoreDataBaseSchema();
	}
	
	/**
	 * Clear the userView filter of the list page
	 */
	public void doClearFilter(){
		this.init();
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the userView edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		if (id != null){
			this.changeDataBaseSchema();
			userView = this.userViewBO.findByPrimaryKey(null, Long.valueOf(id));
			this.restoreDataBaseSchema();
		}
		
		this.createListUserType();
		
		return editPageName;
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListUserView(){
		this.changeDataBaseSchema();
		listUserViews = this.userViewBO.findAll(null, null);
		this.restoreDataBaseSchema();
	}
	
	private void createListUserType(){
		this.changeDataBaseSchema();
		listUserType = this.userTypeBO.findAll(null, null);
		this.restoreDataBaseSchema();
	}
	
	private Boolean validate(){
		Boolean value = true; 
	
		if (this.userView.getIdUserType() == null && this.userView.getIdUserType() >= 0 &&
				this.userView.getIdHermandad() == null) {
			FacesContext
					.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error de validación: ",
									"El campo Hermandad es obligatorio"));

			value = false;
		}
		
		return value;
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public UserViewVO getUserView() {
		return userView;
	}

	public void setUserView(UserViewVO userView) {
		this.userView = userView;
	}
	
	public UserViewBO getUserViewBO() {
		return userViewBO;
	}

	public void setUserViewBO(UserViewBO userViewBO) {
		this.userViewBO = userViewBO;
	}

	public UserTypeBO getUserTypeBO() {
		return userTypeBO;
	}

	public void setUserTypeBO(UserTypeBO userTypeBO) {
		this.userTypeBO = userTypeBO;
	}

	public void setListUserViews(List<UserViewVO> listUserViews) {
		this.listUserViews = listUserViews;
	}

	public List<UserViewVO> getListUserViews(){
		if(listUserViews == null){
			this.createListUserView();
		}
		
		return listUserViews;
	}

	public List<UserTypeVO> getListUserType() {
		if(listUserType == null){
			this.createListUserType();
		}
		
		return listUserType;
	}

	public void setListUserType(List<UserTypeVO> listUserType) {
		this.listUserType = listUserType;
	}
}