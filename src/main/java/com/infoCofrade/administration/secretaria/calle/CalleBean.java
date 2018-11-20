package com.infoCofrade.administration.secretaria.calle;

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
import com.infoCofrade.administration.secretaria.calle.bo.CalleBO;
import com.infoCofrade.administration.secretaria.calle.vo.CalleVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class CalleBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private CalleVO calle;
	private CalleVO calleFilter;
	
	@Inject
	private CalleBO calleBO;
	@Inject
	private LocalidadBO localidadBO;
	@Inject
	private ProvinciaBO provinciaBO;
	
	private List<CalleVO> listCalle;
	
	private List<CalleVO> listSearchCalle;
	private List<CalleVO> listFilteredCalles;
	private List<CalleVO> listSearchCalleCobro;
	private List<CalleVO> listFilteredCallesCobro;
	
	private LocalidadVO localidadSelected;
	
	private static final String listPageName = "listCalle";
	private static final String editPageName = "edit";
	
	
	public CalleBean(){
		this.calle = new CalleVO();
		this.calleFilter = new CalleVO();
	}
	

	public void init(){
		super.init();
		
		this.calle = new CalleVO();
		this.calleFilter = new CalleVO();
		
		this.localidadSelected = new LocalidadVO();
		
		this.createListCalle();
		this.createListSearchCalle();
		this.createListSearchCalleCobro();
	}
	
	public void clear(){
		super.init();
		
		this.calle = new CalleVO();
		this.calleFilter = new CalleVO();
		
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
	 * Find calle elements using the filter get.
	 */
	public void doAddFilter(){
		listCalle = this.calleBO.findUsingTemplate(null, calleFilter, null);
	}
	
	/**
	 * Find calle elements using filter by the ValueChangeEvent
	 * @param event contains the behaviour of the step
	 */
	public void doAddFilter(ValueChangeEvent event) {
		if(event.getNewValue() != null){
			if(event.getComponent().getAttributes().get("idLocalidad") != null &&
					Boolean.parseBoolean((String)event.getComponent().getAttributes().get("idLocalidad"))){
				this.calle.setIdLocalidad((Long)event.getNewValue());
			}
			
			listCalle = this.calleBO.findUsingTemplate(null, calle, null);
		}
	}
	
	/**
	 * Clear the calle filter of the list page
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
			this.calle.setIdLocalidad(null);
			if(localidadSelected != null && localidadSelected.getId() != null){
				this.calle.setIdLocalidad(localidadSelected.getId());
			}
			
			this.calleBO.createElement(null, calle);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the calle edition
	 */
	public String doEditElement(){
		String navigateString = editPageName;
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			calle = this.calleBO.findByPrimaryKey(null, Long.valueOf(id));
			
			if(edition.equalsIgnoreCase("edition")){
				if(calle.getIdLocalidad() != null){
					LocalidadVO localidad = localidadBO.findByPrimaryKey(null, calle.getIdLocalidad());
					ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null, localidad.getIdProvincia());
					
					calle.setCodigoPostal(localidad.getCodigoPostal());
					calle.setProvincia(provincia.getProvincia());
					
					this.setLocalidadSelected(localidad);	
				}
			}
			
			this.createListSearchCalle();
		}
		
		return navigateString;
	}
	
	/**
	 * Delete an element from database
	 * @return String value of the destiny page
	 */
	public void doDeleteElement(){
		CalleVO calle = new CalleVO();
		calle.setId(this.selectedDeleteItemId);
		
		this.calleBO.deleteElement(null, calle);
		
		this.createListCalle();
		this.createListSearchCalle();
	}
	
	/**
	 * Para una localidad seleccionada, carga el provincia y el codigo postal para mostrarlos en el formulario 
	 */
	public void loadAddressFields(){
		ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null, localidadSelected.getIdProvincia());
		
		if(localidadSelected.getId() != null){
			calle.setIdLocalidad(localidadSelected.getId());	
		}
		calle.setCodigoPostal(localidadSelected.getCodigoPostal());
		calle.setProvincia(provincia.getProvincia());
	}
	
	/**
	 * Carga la lista embebida de provincias para añadirlas a una localidad
	 */
	public void loadEmbeddedList() {
		this.calleFilter = new CalleVO();

		this.createListCalle();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListCalle(){
		listCalle = this.calleBO.findAll(null, null);
	}
	
	private void createListSearchCalle() {
		listSearchCalle = this.calleBO.findAll(null, "`nombreVia`");
	}
	
	private void createListSearchCalleCobro() {
		listSearchCalleCobro = this.calleBO.findAll(null, "`nombreVia`");
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public CalleVO getCalle() {
		return calle;
	}

	public void setCalle(CalleVO calle) {
		this.calle = calle;
	}

	public CalleVO getCalleFilter() {
		return calleFilter;
	}

	public void setCalleFilter(CalleVO calleFilter) {
		this.calleFilter = calleFilter;
	}

	public CalleBO getCalleBO() {
		return calleBO;
	}

	public void setCalleBO(CalleBO calleBO) {
		this.calleBO = calleBO;
	}

	public void setListCalle(List<CalleVO> listCalle) {
		this.listCalle = listCalle;
	}

	public List<CalleVO> getListCalle(){
		if(listCalle == null){
			this.createListCalle();
		}
		
		return listCalle;
	}
	
	public void setListSearchCalle(List<CalleVO> listSearchCalle) {
		this.listSearchCalle = listSearchCalle;
	}
	
	public List<CalleVO> getListSearchCalle() {
		if (listSearchCalle == null) {
			this.createListSearchCalle();
		}

		return listSearchCalle;
	}
	
	 public List<CalleVO> getListFilteredCalles() {  
        return listFilteredCalles;
    }  
  
    public void setListFilteredCalles(List<CalleVO> listFilteredCalles) {  
        this.listFilteredCalles = listFilteredCalles;  
    }
    
    public void setListSearchCalleCobro(List<CalleVO> listSearchCalleCobro) {
		this.listSearchCalleCobro = listSearchCalleCobro;
	}
	
	public List<CalleVO> getListSearchCalleCobro() {
		if (listSearchCalleCobro == null) {
			this.createListSearchCalleCobro();
		}

		return listSearchCalleCobro;
	}
	
	 public List<CalleVO> getListFilteredCallesCobro() {  
        return listFilteredCallesCobro;
    }
  
    public void setListFilteredCallesCobro(List<CalleVO> listFilteredCallesCobro) {  
        this.listFilteredCallesCobro = listFilteredCallesCobro;
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