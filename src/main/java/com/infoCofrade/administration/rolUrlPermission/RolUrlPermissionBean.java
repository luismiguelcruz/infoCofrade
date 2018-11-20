package com.infoCofrade.administration.rolUrlPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.rolUrlPermission.bo.RolUrlPermissionBO;
import com.infoCofrade.administration.rolUrlPermission.vo.RolUrlPermissionVO;
import com.infoCofrade.administration.rolUrlPermissionModel.vo.RolUrlPermissionModelVO;
import com.infoCofrade.administration.rolUrlPermissionView.bo.RolUrlPermissionViewBO;
import com.infoCofrade.administration.rolUrlPermissionView.vo.RolUrlPermissionViewVO;
import com.infoCofrade.administration.urlPermission.bo.UrlPermissionBO;
import com.infoCofrade.administration.urlPermission.vo.UrlPermissionVO;
import com.infoCofrade.administration.userType.UserTypeBean;
import com.infoCofrade.administration.userType.bo.UserTypeBO;
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;

@Named
@Scope("session")
public class RolUrlPermissionBean extends AbstractBean {
	private static final long serialVersionUID = 1L;

	private UserTypeVO userType;
	private RolUrlPermissionVO rolUrlPermissionSearch;

	@Inject
	private RolUrlPermissionBO rolUrlPermissionBO;
	@Inject
	private RolUrlPermissionViewBO rolUrlPermissionViewBO;
	@Inject
	private UrlPermissionBO urlPermissionBO;
	@Inject
	private UserTypeBO userTypeBO;

	private List<UserTypeVO> listUserTypes;
	
	private List<RolUrlPermissionModelVO> listRolUrlPermissionsModel;
	
	private Boolean checkAllPermission;

	/**
	 * Pages string for navigation cases
	 */
	private static final String listPageName = "listRolUrlPermission";
	private static final String editPageName = "edit";

	public RolUrlPermissionBean() {
		super.init();
		this.userType = new UserTypeVO();
		this.rolUrlPermissionSearch = new RolUrlPermissionVO();
	}

	public void init() {
		super.init();

		this.userType = new UserTypeVO();
		this.rolUrlPermissionSearch = new RolUrlPermissionVO();

		this.createListUserTypes();

		this.createListUrlPermission();
	}

	// ---------------------- ACTIONS ----------------------
	/**
	 * Initialize the values where the list page is showed from menu
	 * 
	 * @return String value of the destiny page
	 */
	public String doNavigate() {
		this.init();

		return listPageName;
	}

	/**
	 * Find user elements using the filter get.
	 * 
	 * @return String value of the destiny page
	 */
	public void doAddFilter() {
		/*this.listUserTypes = this.rolUrlPermissionBO.findUsingTemplate(
				null, rolUrlPermissionSearch);*/
	}

	/**
	 * Clear the element filter of the list page
	 * 
	 * @return String value of the destiny page
	 */
	public String doClearFilter() {
		this.init();

		return null;
	}

	/**
	 * Add a new element into the database
	 * 
	 * @return String value of the destiny page
	 */
	public String doCreateElement() {
		String navigateString = listPageName;

		try {
			this.rolUrlPermissionBO.createElement(null, this.userType, listRolUrlPermissionsModel);

			this.init();
			
			// Create the list of permissions of the current selected rol
			this.createListUrlPermission();
		} catch (DaoException e) {
			navigateString = editPageName;
		}

		return navigateString;
	}

	/**
	 * Navigate to the edition page
	 * 
	 * @return String destiny mapped page from the disease edition
	 */
	public String doEditElement() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");

		if (id != null) {
			userType = this.userTypeBO.findByPrimaryKey(null,
					Long.valueOf(id));

			// Create the list of permissions of the current selected rol
			this.createListUrlPermission();
		}

		return editPageName;
	}

	/**
	 * Delete an element from database
	 * 
	 * @return String value of the destiny page
	 */
	public String doDeleteElement() {
		if (this.selectedDeleteItemId != null) {
			RolUrlPermissionVO rolUrlPermission = new RolUrlPermissionVO();
			rolUrlPermission.setIdUserType(this.selectedDeleteItemId);

			this.rolUrlPermissionBO.deleteElement(null, rolUrlPermission);
		}

		return "success";
	}
	
	/**
	 * Fill the list of url permissions
	 * @param event
	 */
	public void onChangeRol(){
		this.createListUrlPermission();
	}
	
	/**
	 * Change the selected permission for all the available permissions
	 */
	public void selectAllPermissions() {
		for(RolUrlPermissionModelVO item: listRolUrlPermissionsModel){
			item.setSelected(checkAllPermission);
		}
	}

	// ------------- ADITIONAL FUNCTIONS -----------------------
	private void createListUserTypes() {
		listUserTypes = ((UserTypeBean)this.getBean("userTypeBean")).getListUserTypeRols();
	}

	private void createListUrlPermission() {
		listRolUrlPermissionsModel = new ArrayList<RolUrlPermissionModelVO>();
		
		if(this.userType.getId() != null){
			List<UrlPermissionVO> listAux = this.urlPermissionBO.findAll(null, "idMenuItemGroup");
			RolUrlPermissionViewVO rolUrlPermissionView = new RolUrlPermissionViewVO();
			rolUrlPermissionView.setIdUserType(this.userType.getId());
			List<RolUrlPermissionViewVO> listSettedPermissions = 
					this.rolUrlPermissionViewBO.findUsingTemplate(null, rolUrlPermissionView);
			Map<Long, RolUrlPermissionViewVO> mapSettedPermissions = new HashMap<Long, RolUrlPermissionViewVO>();
			
			for(RolUrlPermissionViewVO item: listSettedPermissions){
				mapSettedPermissions.put(item.getIdUrlPermission(), item);
			}
			
			RolUrlPermissionModelVO rolUrlPermissionModel = null;
			
			for(UrlPermissionVO urlPermission: listAux){
				rolUrlPermissionModel = new RolUrlPermissionModelVO();
				rolUrlPermissionModel.setIdUrlPermission(urlPermission.getId());
				rolUrlPermissionModel.setName(urlPermission.getName());
				rolUrlPermissionModel.setUrl(urlPermission.getUrl());
				rolUrlPermissionModel.setIdMenuItemGroup(urlPermission.getIdMenuItemGroup());
				rolUrlPermissionModel.setBeanName(urlPermission.getBeanName());
				
				if(mapSettedPermissions.containsKey(urlPermission.getId())){
					rolUrlPermissionModel.setSelected(true);
					rolUrlPermissionModel.setId(mapSettedPermissions.get(urlPermission.getId()).getId());
				}
				
				listRolUrlPermissionsModel.add(rolUrlPermissionModel);
			}	
		}
	}

	// -------------------------- GETTERS / SETTERS ---------------------------
	public UserTypeVO getUserType() {
		return userType;
	}

	public void setUserType(UserTypeVO userType) {
		this.userType = userType;
	}
	
	public RolUrlPermissionVO getRolUrlPermissionSearch() {
		return rolUrlPermissionSearch;
	}

	public void setRolUrlPermissionSearch(
			RolUrlPermissionVO rolUrlPermissionSearch) {
		this.rolUrlPermissionSearch = rolUrlPermissionSearch;
	}

	public RolUrlPermissionBO getRolUrlPermissionBO() {
		return rolUrlPermissionBO;
	}

	public void setRolUrlPermissionBO(RolUrlPermissionBO rolUrlPermissionBO) {
		this.rolUrlPermissionBO = rolUrlPermissionBO;
	}

	public List<UserTypeVO> getListUserTypes() {
		return listUserTypes;
	}

	public void setListUserTypes(List<UserTypeVO> listUserTypes) {
		this.listUserTypes = listUserTypes;
	}

	public List<RolUrlPermissionModelVO> getListRolUrlPermissionsModel() {
		if(listRolUrlPermissionsModel == null){
			this.createListUrlPermission();
		}
		
		return listRolUrlPermissionsModel;
	}

	public void setListRolUrlPermissionsModel(
			List<RolUrlPermissionModelVO> listRolUrlPermissionsModel) {
		this.listRolUrlPermissionsModel = listRolUrlPermissionsModel;
	}

	public Boolean getCheckAllPermission() {
		return checkAllPermission;
	}

	public void setCheckAllPermission(Boolean checkAllPermission) {
		this.checkAllPermission = checkAllPermission;
	}
}