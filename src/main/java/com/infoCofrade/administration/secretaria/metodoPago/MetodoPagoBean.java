package com.infoCofrade.administration.secretaria.metodoPago;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.secretaria.metodoPago.bo.MetodoPagoBO;
import com.infoCofrade.administration.secretaria.metodoPago.vo.MetodoPagoVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;

@Named
@Scope("session")
public class MetodoPagoBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private MetodoPagoVO metodoPago;
	private MetodoPagoVO metodoPagoFilter;
	
	@Inject
	private MetodoPagoBO metodoPagoBO;
	
	private List<MetodoPagoVO> listMetodoPago;
	
	private Boolean selectAllCheckboxes;
	
	private static final String listPageName = "listMetodoPago";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public MetodoPagoBean(){
		this.metodoPago = new MetodoPagoVO();
		this.metodoPagoFilter = new MetodoPagoVO();
	}
	

	public void init(){
		super.init();
		
		this.metodoPago = new MetodoPagoVO();
		this.metodoPagoFilter = new MetodoPagoVO();
		this.selectAllCheckboxes = null;
		
		this.createListMetodoPago();
	}
	
	public void clear(){
		super.init();
		
		this.metodoPago = new MetodoPagoVO();
		this.metodoPagoFilter = new MetodoPagoVO();
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
	 * Find metodoPago elements using the filter get.
	 */
	public void doAddFilter(){
		listMetodoPago = this.metodoPagoBO.findUsingTemplate(null, metodoPagoFilter, null);
	}
	
	/**
	 * Clear the metodoPago filter of the list page
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
			this.metodoPagoBO.createElement(null, metodoPago);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the metodoPago edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			metodoPago = this.metodoPagoBO.findByPrimaryKey(null, Long.valueOf(id));
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
		MetodoPagoVO metodoPago = new MetodoPagoVO();
		metodoPago.setId(this.selectedDeleteItemId);
		
		this.metodoPagoBO.deleteElement(null, metodoPago);
		
		this.createListMetodoPago();
	}
	
	public String doUpdateAll(){
		try{
			this.metodoPagoBO.createAllElement(null, listMetodoPago);
		} catch (DaoException e){
		}
		
		return null;
	}
	
	public void doChangeAllCheckboxes(){
		for(int i=0; i<listMetodoPago.size(); i++){
			listMetodoPago.get(i).setSeleccionable(this.selectAllCheckboxes);
		}
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListMetodoPago(){
		listMetodoPago = this.metodoPagoBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public MetodoPagoVO getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPagoVO metodoPago) {
		this.metodoPago = metodoPago;
	}

	public MetodoPagoVO getMetodoPagoFilter() {
		return metodoPagoFilter;
	}

	public void setMetodoPagoFilter(MetodoPagoVO metodoPagoFilter) {
		this.metodoPagoFilter = metodoPagoFilter;
	}

	public MetodoPagoBO getMetodoPagoBO() {
		return metodoPagoBO;
	}

	public void setMetodoPagoBO(MetodoPagoBO metodoPagoBO) {
		this.metodoPagoBO = metodoPagoBO;
	}

	public Boolean getSelectAllCheckboxes() {
		return selectAllCheckboxes;
	}

	public void setSelectAllCheckboxes(Boolean selectAllCheckboxes) {
		this.selectAllCheckboxes = selectAllCheckboxes;
	}

	public void setListMetodoPago(List<MetodoPagoVO> listMetodoPago) {
		this.listMetodoPago = listMetodoPago;
	}

	public List<MetodoPagoVO> getListMetodoPago(){
		if(listMetodoPago == null){
			this.createListMetodoPago();
		}
		
		return listMetodoPago;
	}
}