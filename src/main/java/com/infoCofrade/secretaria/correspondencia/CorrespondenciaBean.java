package com.infoCofrade.secretaria.correspondencia;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.correspondencia.bo.CorrespondenciaBO;
import com.infoCofrade.secretaria.correspondencia.vo.CorrespondenciaVO;


@Named
@Scope("session")
public class CorrespondenciaBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private CorrespondenciaVO correspondencia;
	private CorrespondenciaVO correspondenciaFilter;
	
	@Inject
	private CorrespondenciaBO correspondenciaBO;
	
	private List<CorrespondenciaVO> listCorrespondencia;
	
	private static final String listPageName = "listCorrespondencia";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public CorrespondenciaBean(){
		this.correspondencia = new CorrespondenciaVO();
		this.correspondenciaFilter = new CorrespondenciaVO();
	}
	

	public void init(){
		super.init();
		
		this.correspondencia = new CorrespondenciaVO();
		this.correspondenciaFilter = new CorrespondenciaVO();
		
		this.createListCorrespondencia();
	}
	
	public void clear(){
		super.init();
		
		this.correspondencia = new CorrespondenciaVO();
		this.correspondenciaFilter = new CorrespondenciaVO();
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
	 * Find correspondencia elements using the filter get.
	 */
	public void doAddFilter(){
		listCorrespondencia = this.correspondenciaBO.findUsingTemplate(null, correspondenciaFilter, null);
	}
	
	/**
	 * Clear the correspondencia filter of the list page
	 */
	public void doClearFilter(){
		this.init();
	}
	
	/**
	 * Add a new element into the database
	 * @return String value of the destiny page
	 */
	public String doCreateElement(){
		String navigateString = editPageName;
		
		if(validate()){
			navigateString = listPageName;
			
			try{
				this.correspondenciaBO.createElement(null, correspondencia);
				
				this.init();
			} catch (DaoException e){
				navigateString = editPageName;
			}
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the correspondencia edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			correspondencia = this.correspondenciaBO.findByPrimaryKey(null, Long.valueOf(id));
		}

		if(edition != null && edition.equalsIgnoreCase("edition")){
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
		CorrespondenciaVO correspondencia = new CorrespondenciaVO();
		correspondencia.setId(this.selectedDeleteItemId);
		
		this.correspondenciaBO.deleteElement(null, correspondencia);
		
		this.createListCorrespondencia();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListCorrespondencia(){
		listCorrespondencia = this.correspondenciaBO.findAll(null, null);
	}
	
	private Boolean validate(){
		Boolean value = true;
		
		if (this.correspondencia.getFechaEntrada() != null && this.correspondencia.getFechaSalida() != null){
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
							"La correspondencia puede ser de entrada o de salida, por tanto no puede tener las dos fechas asignadas"));
			
			value = false;
		}
		
		return value;
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public CorrespondenciaVO getCorrespondencia() {
		return correspondencia;
	}

	public void setCorrespondencia(CorrespondenciaVO correspondencia) {
		this.correspondencia = correspondencia;
	}

	public CorrespondenciaVO getCorrespondenciaFilter() {
		return correspondenciaFilter;
	}

	public void setCorrespondenciaFilter(CorrespondenciaVO correspondenciaFilter) {
		this.correspondenciaFilter = correspondenciaFilter;
	}

	public CorrespondenciaBO getCorrespondenciaBO() {
		return correspondenciaBO;
	}

	public void setCorrespondenciaBO(CorrespondenciaBO correspondenciaBO) {
		this.correspondenciaBO = correspondenciaBO;
	}

	public void setListCorrespondencia(List<CorrespondenciaVO> listCorrespondencia) {
		this.listCorrespondencia = listCorrespondencia;
	}

	public List<CorrespondenciaVO> getListCorrespondencia(){
		if(listCorrespondencia == null){
			this.createListCorrespondencia();
		}
		
		return listCorrespondencia;
	}
}