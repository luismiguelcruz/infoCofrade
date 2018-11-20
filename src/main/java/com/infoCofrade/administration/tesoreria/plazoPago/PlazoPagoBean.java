package com.infoCofrade.administration.tesoreria.plazoPago;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.tesoreria.plazoPago.bo.PlazoPagoBO;
import com.infoCofrade.administration.tesoreria.plazoPago.vo.PlazoPagoVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class PlazoPagoBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private PlazoPagoVO plazoPago;
	private PlazoPagoVO plazoPagoFilter;
	
	@Inject
	private PlazoPagoBO plazoPagoBO;
	
	private List<PlazoPagoVO> listPlazoPago;
	
	private Boolean selectAllCheckboxes;
	
	private static final String listPageName = "listPlazoPago";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public PlazoPagoBean(){
		this.plazoPago = new PlazoPagoVO();
		this.plazoPagoFilter = new PlazoPagoVO();
	}
	

	public void init(){
		super.init();
		
		this.plazoPago = new PlazoPagoVO();
		this.plazoPagoFilter = new PlazoPagoVO();
		this.selectAllCheckboxes = null;
		
		this.createListPlazoPago();
	}
	
	public void clear(){
		super.init();
		
		this.plazoPago = new PlazoPagoVO();
		this.plazoPagoFilter = new PlazoPagoVO();
		this.selectAllCheckboxes = null;
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
	 * Find plazoPago elements using the filter get.
	 */
	public void doAddFilter(){
		listPlazoPago = this.plazoPagoBO.findUsingTemplate(null, plazoPagoFilter, null);
	}
	
	/**
	 * Clear the plazoPago filter of the list page
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
			this.plazoPagoBO.createElement(null, plazoPago);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the plazoPago edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			plazoPago = this.plazoPagoBO.findByPrimaryKey(null, Long.valueOf(id));
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
		PlazoPagoVO plazoPago = new PlazoPagoVO();
		plazoPago.setId(this.selectedDeleteItemId);
		
		this.plazoPagoBO.deleteElement(null, plazoPago);
		
		this.createListPlazoPago();
	}
	
	public String doUpdateAll(){
		try{
			this.plazoPagoBO.createAllElement(null, listPlazoPago);
		} catch (DaoException e){
		}
		
		return null;
	}
	
	public void doChangeAllCheckboxes(){
		for(int i=0; i<listPlazoPago.size(); i++){
			listPlazoPago.get(i).setSeleccionable(this.selectAllCheckboxes);
		}
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListPlazoPago(){
		listPlazoPago = this.plazoPagoBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public PlazoPagoVO getPlazoPago() {
		return plazoPago;
	}

	public void setPlazoPago(PlazoPagoVO plazoPago) {
		this.plazoPago = plazoPago;
	}

	public PlazoPagoVO getPlazoPagoFilter() {
		return plazoPagoFilter;
	}

	public void setPlazoPagoFilter(PlazoPagoVO plazoPagoFilter) {
		this.plazoPagoFilter = plazoPagoFilter;
	}

	public PlazoPagoBO getPlazoPagoBO() {
		return plazoPagoBO;
	}

	public void setPlazoPagoBO(PlazoPagoBO plazoPagoBO) {
		this.plazoPagoBO = plazoPagoBO;
	}
	
	public Boolean getSelectAllCheckboxes() {
		return selectAllCheckboxes;
	}

	public void setSelectAllCheckboxes(Boolean selectAllCheckboxes) {
		this.selectAllCheckboxes = selectAllCheckboxes;
	}

	public void setListPlazoPago(List<PlazoPagoVO> listPlazoPago) {
		this.listPlazoPago = listPlazoPago;
	}

	public List<PlazoPagoVO> getListPlazoPago(){
		if(listPlazoPago == null){
			this.createListPlazoPago();
		}
		
		return listPlazoPago;
	}
}