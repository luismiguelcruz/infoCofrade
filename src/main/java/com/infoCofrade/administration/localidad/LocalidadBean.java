package com.infoCofrade.administration.localidad;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.localidad.bo.LocalidadBO;
import com.infoCofrade.administration.localidad.vo.LocalidadVO;
import com.infoCofrade.administration.provincia.bo.ProvinciaBO;
import com.infoCofrade.administration.provincia.vo.ProvinciaVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class LocalidadBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private LocalidadVO localidad;
	private LocalidadVO localidadFilter;
	
	@Inject
	private LocalidadBO localidadBO;
	@Inject
	private ProvinciaBO provinciaBO;
	
	private List<LocalidadVO> listLocalidad;
	
	private List<ProvinciaVO> listSearchProvincia;
	private List<ProvinciaVO> listFilteredProvincias;
	
	private static final String listPageName = "listLocalidad";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public LocalidadBean(){
		this.localidad = new LocalidadVO();
		this.localidadFilter = new LocalidadVO();
	}
	

	public void init(){
		super.init();
		
		this.localidad = new LocalidadVO();
		this.localidadFilter = new LocalidadVO();
		
		this.createListLocalidad();
		this.createListSearchProvincia();
	}
	
	public void clear(){
		super.init();
		
		this.localidad = new LocalidadVO();
		this.localidadFilter = new LocalidadVO();
		
		this.listFilteredProvincias = null;
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
	 * Find localidad elements using the filter get.
	 */
	public void doAddFilter(){
		listLocalidad = this.localidadBO.findUsingTemplate(null, localidadFilter, null);
	}
	
	/**
	 * Find calle elements using filter by the ValueChangeEvent
	 * @param event contains the behaviour of the step
	 */
	public void doAddFilter(ValueChangeEvent event) {
		if(event.getNewValue() != null){
			if(event.getComponent().getAttributes().get("idProvincia") != null &&
					Boolean.parseBoolean((String)event.getComponent().getAttributes().get("idProvincia"))){
				this.localidad.setIdProvincia((Long)event.getNewValue());
			}
			
			listLocalidad = this.localidadBO.findUsingTemplate(null, localidad, null);
		}
	}
	
	/**
	 * Clear the localidad filter of the list page
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
			this.localidadBO.createElement(null, localidad);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the localidad edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			localidad = this.localidadBO.findByPrimaryKey(null, Long.valueOf(id));
			
			this.createListSearchProvincia();
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
		LocalidadVO localidad = new LocalidadVO();
		localidad.setId(this.selectedDeleteItemId);
		
		this.localidadBO.deleteElement(null, localidad);
		
		this.createListLocalidad();
	}
	
	/**
	 * Carga la lista embebida de provincias para añadirlas a una localidad
	 */
	public void loadEmbeddedList(){
		this.createListSearchProvincia();
	}
	
	public String doSelectProvincia(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String idSample = fc.getExternalContext().getRequestParameterMap()
				.get("id");
		localidad.setIdProvincia(Long.valueOf(idSample));
		
		return null;
	}
	
	/**
	 * Eliminar el id de provincia asociado a la localidad
	 */
	public String clearIdProvincia(){
		localidad.setIdProvincia(null);
		this.createListSearchProvincia();
		this.listFilteredProvincias = null;
		
		return null;
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListLocalidad(){
		listLocalidad = this.localidadBO.findAll(null, null);
	}
	
	private void createListSearchProvincia() {
		listSearchProvincia = this.provinciaBO.findAll(null, "`provincia`");
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public LocalidadVO getLocalidad() {
		return localidad;
	}

	public void setLocalidad(LocalidadVO localidad) {
		this.localidad = localidad;
	}

	public LocalidadVO getLocalidadFilter() {
		return localidadFilter;
	}

	public void setLocalidadFilter(LocalidadVO localidadFilter) {
		this.localidadFilter = localidadFilter;
	}

	public LocalidadBO getLocalidadBO() {
		return localidadBO;
	}

	public void setLocalidadBO(LocalidadBO localidadBO) {
		this.localidadBO = localidadBO;
	}

	public void setListLocalidad(List<LocalidadVO> listLocalidad) {
		this.listLocalidad = listLocalidad;
	}

	public List<LocalidadVO> getListLocalidad(){
		if(listLocalidad == null){
			this.createListLocalidad();
		}
		
		return listLocalidad;
	}
	
	public void setListSearchProvincia(List<ProvinciaVO> listSearchProvincia) {
		this.listSearchProvincia = listSearchProvincia;
	}
	
	public List<ProvinciaVO> getListSearchProvincia() {
		if (listSearchProvincia == null) {
			this.createListSearchProvincia();
		}

		return listSearchProvincia;
	}
	
	 public List<ProvinciaVO> getListFilteredProvincias() {  
        return listFilteredProvincias;
    }  
  
    public void setListFilteredProvincias(List<ProvinciaVO> listFilteredProvincias) {  
        this.listFilteredProvincias = listFilteredProvincias;  
    }
	
	public List<LocalidadVO> getListLocalidadFilter(){
		return this.localidadBO.findAll(null, "`localidad`");
	}
}