package com.infoCofrade.secretaria.statusPorHermano;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.statusPorHermano.bo.StatusPorHermanoBO;
import com.infoCofrade.secretaria.statusPorHermano.vo.StatusPorHermanoVO;


@Named
@Scope("session")
public class StatusPorHermanoBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private StatusPorHermanoVO statusPorHermano;
	private StatusPorHermanoVO statusPorHermanoFilter;
	
	@Inject
	private StatusPorHermanoBO statusPorHermanoBO;
	
	private List<StatusPorHermanoVO> listStatusPorHermano;
	
	private static final String listPageName = "listStatusPorHermano";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public StatusPorHermanoBean(){
		this.statusPorHermano = new StatusPorHermanoVO();
		this.statusPorHermanoFilter = new StatusPorHermanoVO();
	}
	

	public void init(){
		super.init();
		
		this.statusPorHermano = new StatusPorHermanoVO();
		this.statusPorHermanoFilter = new StatusPorHermanoVO();
		
		this.createListStatusPorHermano();
	}
	
	public void clear(){
		super.init();
		
		this.statusPorHermano = new StatusPorHermanoVO();
		this.statusPorHermanoFilter = new StatusPorHermanoVO();
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
	 * Find statusPorHermano elements using the filter get.
	 */
	public void doAddFilter(){
		listStatusPorHermano = this.statusPorHermanoBO.findUsingTemplate(null, statusPorHermanoFilter, null);
	}
	
	/**
	 * Clear the statusPorHermano filter of the list page
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
			this.statusPorHermanoBO.createElement(null, statusPorHermano);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the statusPorHermano edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			statusPorHermano = this.statusPorHermanoBO.findByPrimaryKey(null, Long.valueOf(id));
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
		StatusPorHermanoVO statusPorHermano = new StatusPorHermanoVO();
		statusPorHermano.setId(this.selectedDeleteItemId);
		
		this.statusPorHermanoBO.deleteElement(null, statusPorHermano);
		
		this.createListStatusPorHermano();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListStatusPorHermano(){
		listStatusPorHermano = this.statusPorHermanoBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public StatusPorHermanoVO getStatusPorHermano() {
		return statusPorHermano;
	}

	public void setStatusPorHermano(StatusPorHermanoVO statusPorHermano) {
		this.statusPorHermano = statusPorHermano;
	}

	public StatusPorHermanoVO getStatusPorHermanoFilter() {
		return statusPorHermanoFilter;
	}

	public void setStatusPorHermanoFilter(StatusPorHermanoVO statusPorHermanoFilter) {
		this.statusPorHermanoFilter = statusPorHermanoFilter;
	}

	public StatusPorHermanoBO getStatusPorHermanoBO() {
		return statusPorHermanoBO;
	}

	public void setStatusPorHermanoBO(StatusPorHermanoBO statusPorHermanoBO) {
		this.statusPorHermanoBO = statusPorHermanoBO;
	}

	public void setListStatusPorHermano(List<StatusPorHermanoVO> listStatusPorHermano) {
		this.listStatusPorHermano = listStatusPorHermano;
	}

	public List<StatusPorHermanoVO> getListStatusPorHermano(){
		if(listStatusPorHermano == null){
			this.createListStatusPorHermano();
		}
		
		return listStatusPorHermano;
	}
}