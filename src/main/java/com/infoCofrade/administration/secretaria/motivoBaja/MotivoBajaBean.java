package com.infoCofrade.administration.secretaria.motivoBaja;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.secretaria.motivoBaja.bo.MotivoBajaBO;
import com.infoCofrade.administration.secretaria.motivoBaja.vo.MotivoBajaVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class MotivoBajaBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private MotivoBajaVO motivoBaja;
	private MotivoBajaVO motivoBajaFilter;
	
	@Inject
	private MotivoBajaBO motivoBajaBO;
	
	private List<MotivoBajaVO> listMotivoBaja;
	
	private static final String listPageName = "listMotivoBaja";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public MotivoBajaBean(){
		this.motivoBaja = new MotivoBajaVO();
		this.motivoBajaFilter = new MotivoBajaVO();
	}
	

	public void init(){
		super.init();
		
		this.motivoBaja = new MotivoBajaVO();
		this.motivoBajaFilter = new MotivoBajaVO();
		
		this.createListMotivoBaja();
	}
	
	public void clear(){
		super.init();
		
		this.motivoBaja = new MotivoBajaVO();
		this.motivoBajaFilter = new MotivoBajaVO();
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
	 * Find motivoBaja elements using the filter get.
	 */
	public void doAddFilter(){
		listMotivoBaja = this.motivoBajaBO.findUsingTemplate(null, motivoBajaFilter, null);
	}
	
	/**
	 * Clear the motivoBaja filter of the list page
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
			this.motivoBajaBO.createElement(null, motivoBaja);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the motivoBaja edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			motivoBaja = this.motivoBajaBO.findByPrimaryKey(null, Long.valueOf(id));
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
		MotivoBajaVO motivoBaja = new MotivoBajaVO();
		motivoBaja.setId(this.selectedDeleteItemId);
		
		this.motivoBajaBO.deleteElement(null, motivoBaja);
		
		this.createListMotivoBaja();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListMotivoBaja(){
		listMotivoBaja = this.motivoBajaBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public MotivoBajaVO getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(MotivoBajaVO motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public MotivoBajaVO getMotivoBajaFilter() {
		return motivoBajaFilter;
	}

	public void setMotivoBajaFilter(MotivoBajaVO motivoBajaFilter) {
		this.motivoBajaFilter = motivoBajaFilter;
	}

	public MotivoBajaBO getMotivoBajaBO() {
		return motivoBajaBO;
	}

	public void setMotivoBajaBO(MotivoBajaBO motivoBajaBO) {
		this.motivoBajaBO = motivoBajaBO;
	}

	public void setListMotivoBaja(List<MotivoBajaVO> listMotivoBaja) {
		this.listMotivoBaja = listMotivoBaja;
	}

	public List<MotivoBajaVO> getListMotivoBaja(){
		if(listMotivoBaja == null){
			this.createListMotivoBaja();
		}
		
		return listMotivoBaja;
	}
}