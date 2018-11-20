package com.infoCofrade.secretaria.entidad;

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
import com.infoCofrade.secretaria.entidad.bo.EntidadBO;
import com.infoCofrade.secretaria.entidad.vo.EntidadVO;


@Named
@Scope("session")
public class EntidadBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private EntidadVO entidad;
	private EntidadVO entidadFilter;
	
	@Inject
	private EntidadBO entidadBO;
	@Inject
	private LocalidadBO localidadBO;
	@Inject
	private ProvinciaBO provinciaBO;
	
	private List<EntidadVO> listEntidad;
	
	/**
	 * Almacena la localidad para seleccionar automaticamente el autocomplete
	 */
	private LocalidadVO localidadSelected;
	
	private static final String listPageName = "listEntidad";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public EntidadBean(){
		this.entidad = new EntidadVO();
		this.entidadFilter = new EntidadVO();
	}
	

	public void init(){
		super.init();
		
		this.entidad = new EntidadVO();
		this.entidadFilter = new EntidadVO();
		this.localidadSelected = new LocalidadVO();
		
		this.createListEntidad();
	}
	
	public void clear(){
		super.init();
		
		this.entidad = new EntidadVO();
		this.entidadFilter = new EntidadVO();
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
	 * Find entidad elements using the filter get.
	 */
	public void doAddFilter(){
		listEntidad = this.entidadBO.findUsingTemplate(null, entidadFilter, null);
	}
	
	/**
	 * Clear the entidad filter of the list page
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
			this.entidad.setIdLocalidad(null);
			if(localidadSelected != null && localidadSelected.getId() != null){
				this.entidad.setIdLocalidad(localidadSelected.getId());
			}
			
			this.entidadBO.createElement(null, entidad);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the entidad edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			entidad = this.entidadBO.findByPrimaryKey(null, Long.valueOf(id));
			
			if(entidad.getIdLocalidad() != null){
				LocalidadVO localidad = localidadBO.findByPrimaryKey(null, entidad.getIdLocalidad());
				ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null, localidad.getIdProvincia());
				
				entidad.setCodigoPostal(localidad.getCodigoPostal());
				entidad.setProvincia(provincia.getProvincia());
				
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
		EntidadVO entidad = new EntidadVO();
		entidad.setId(this.selectedDeleteItemId);
		
		this.entidadBO.deleteElement(null, entidad);
		
		this.createListEntidad();
	}
	
	/**
	 * Para una localidad seleccionada, carga el provincia y el codigo postal para mostrarlos en el formulario 
	 */
	public void loadAddressFields(){
		ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null, localidadSelected.getIdProvincia());
		
		if(localidadSelected.getId() != null){
			entidad.setIdLocalidad(localidadSelected.getId());	
		}
		entidad.setCodigoPostal(localidadSelected.getCodigoPostal());
		entidad.setProvincia(provincia.getProvincia());
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListEntidad(){
		listEntidad = this.entidadBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public EntidadVO getEntidad() {
		return entidad;
	}

	public void setEntidad(EntidadVO entidad) {
		this.entidad = entidad;
	}

	public EntidadVO getEntidadFilter() {
		return entidadFilter;
	}

	public void setEntidadFilter(EntidadVO entidadFilter) {
		this.entidadFilter = entidadFilter;
	}

	public EntidadBO getEntidadBO() {
		return entidadBO;
	}

	public void setEntidadBO(EntidadBO entidadBO) {
		this.entidadBO = entidadBO;
	}

	public void setListEntidad(List<EntidadVO> listEntidad) {
		this.listEntidad = listEntidad;
	}

	public List<EntidadVO> getListEntidad(){
		if(listEntidad == null){
			this.createListEntidad();
		}
		
		return listEntidad;
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