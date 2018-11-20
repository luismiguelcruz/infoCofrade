package com.infoCofrade.secretaria.acto;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.localidad.bo.LocalidadBO;
import com.infoCofrade.administration.localidad.vo.LocalidadVO;
import com.infoCofrade.administration.provincia.bo.ProvinciaBO;
import com.infoCofrade.administration.provincia.vo.ProvinciaVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.acto.bo.ActoBO;
import com.infoCofrade.secretaria.acto.vo.ActoVO;


@Named
@Scope("session")
public class ActoBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private ActoVO acto;
	private ActoVO actoFilter;
	
	@Inject
	private ActoBO actoBO;
	@Inject
	private LocalidadBO localidadBO;
	@Inject
	private ProvinciaBO provinciaBO;
	
	private List<ActoVO> listActo;
	
	/**
	 * Almacena la localidad para seleccionar automaticamente el autocomplete
	 */
	private LocalidadVO localidadSelected;
	
	private static final String listPageName = "listActo";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public ActoBean(){
		this.acto = new ActoVO();
		this.actoFilter = new ActoVO();
	}
	

	public void init(){
		super.init();
		
		this.acto = new ActoVO();
		this.actoFilter = new ActoVO();
		this.localidadSelected = new LocalidadVO();
		
		this.createListActo();
	}
	
	public void clear(){
		super.init();
		
		this.acto = new ActoVO();
		this.actoFilter = new ActoVO();
		this.localidadSelected = new LocalidadVO();
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
	 * Find acto elements using the filter get.
	 */
	public void doAddFilter(){
		listActo = this.actoBO.findUsingTemplate(null, actoFilter, null);
	}
	
	/**
	 * Clear the acto filter of the list page
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
			this.acto.setIdLocalidad(null);
			if(localidadSelected != null && localidadSelected.getId() != null){
				this.acto.setIdLocalidad(localidadSelected.getId());
			}
			
			this.actoBO.createElement(null, acto);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the acto edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			acto = this.actoBO.findByPrimaryKey(null, Long.valueOf(id));
			
			if(acto.getIdLocalidad() != null){
				LocalidadVO localidad = localidadBO.findByPrimaryKey(null, acto.getIdLocalidad());
				ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null, localidad.getIdProvincia());
				
				acto.setCodigoPostal(localidad.getCodigoPostal());
				acto.setProvincia(provincia.getProvincia());
				
				this.setLocalidadSelected(localidad);	
			}
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
		ActoVO acto = new ActoVO();
		acto.setId(this.selectedDeleteItemId);
		
		this.actoBO.deleteElement(null, acto);
		
		this.createListActo();
	}
	
	/**
	 * Para una localidad seleccionada, carga el provincia y el codigo postal para mostrarlos en el formulario 
	 */
	public void loadAddressFields(){
		ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null, localidadSelected.getIdProvincia());
		
		if(localidadSelected.getId() != null){
			acto.setIdLocalidad(localidadSelected.getId());	
		}
		acto.setCodigoPostal(localidadSelected.getCodigoPostal());
		acto.setProvincia(provincia.getProvincia());
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListActo(){
		listActo = this.actoBO.findAll(null, "fecha DESC");
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public ActoVO getActo() {
		return acto;
	}

	public void setActo(ActoVO acto) {
		this.acto = acto;
	}

	public ActoVO getActoFilter() {
		return actoFilter;
	}

	public void setActoFilter(ActoVO actoFilter) {
		this.actoFilter = actoFilter;
	}

	public ActoBO getActoBO() {
		return actoBO;
	}

	public void setActoBO(ActoBO actoBO) {
		this.actoBO = actoBO;
	}

	public void setListActo(List<ActoVO> listActo) {
		this.listActo = listActo;
	}

	public List<ActoVO> getListActo(){
		if(listActo == null){
			this.createListActo();
		}
		
		return listActo;
	}

	public LocalidadVO getLocalidadSelected() {
		return localidadSelected;
	}

	public void setLocalidadSelected(LocalidadVO localidadSelected) {
		this.localidadSelected = localidadSelected;
	}
	
	public LocalidadVO getLocalidadCPSelected() {
		return localidadSelected;
	}
	
	public void setLocalidadCPSelected(LocalidadVO localidadSelected) {
		if(this.localidadSelected == null || localidadSelected != null){
			this.localidadSelected = localidadSelected;	
		}
	}
}