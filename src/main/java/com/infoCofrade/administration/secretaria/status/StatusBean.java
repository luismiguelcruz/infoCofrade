package com.infoCofrade.administration.secretaria.status;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.secretaria.status.bo.StatusBO;
import com.infoCofrade.administration.secretaria.status.vo.StatusVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class StatusBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private StatusVO status;
	private StatusVO statusFilter;
	
	@Inject
	private StatusBO statusBO;
	
	private List<StatusVO> listStatus;
	
	private static final String listPageName = "listStatus";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public StatusBean(){
		this.status = new StatusVO();
		this.statusFilter = new StatusVO();
	}
	

	public void init(){
		super.init();
		
		this.status = new StatusVO();
		this.statusFilter = new StatusVO();
		
		this.createListStatus();
	}
	
	public void clear(){
		super.init();
		
		this.status = new StatusVO();
		this.statusFilter = new StatusVO();
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
	 * Find status elements using the filter get.
	 */
	public void doAddFilter(){
		listStatus = this.statusBO.findUsingTemplate(null, statusFilter, null);
	}
	
	/**
	 * Clear the status filter of the list page
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
			this.statusBO.createElement(null, status);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the status edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			status = this.statusBO.findByPrimaryKey(null, Long.valueOf(id));
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
		StatusVO status = new StatusVO();
		status.setId(this.selectedDeleteItemId);
		
		this.statusBO.deleteElement(null, status);
		
		this.createListStatus();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListStatus(){
		listStatus = this.statusBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public StatusVO getStatus() {
		return status;
	}

	public void setStatus(StatusVO status) {
		this.status = status;
	}

	public StatusVO getStatusFilter() {
		return statusFilter;
	}

	public void setStatusFilter(StatusVO statusFilter) {
		this.statusFilter = statusFilter;
	}

	public StatusBO getStatusBO() {
		return statusBO;
	}

	public void setStatusBO(StatusBO statusBO) {
		this.statusBO = statusBO;
	}

	public void setListStatus(List<StatusVO> listStatus) {
		this.listStatus = listStatus;
	}

	public List<StatusVO> getListStatus(){
		if(listStatus == null){
			this.createListStatus();
		}
		
		return listStatus;
	}
}