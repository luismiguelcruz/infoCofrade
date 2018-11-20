package com.infoCofrade.tesoreria.tipoActividad;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.tipoActividad.bo.TipoActividadBO;
import com.infoCofrade.tesoreria.tipoActividad.vo.TipoActividadVO;


@Named
@Scope("session")
public class TipoActividadBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private TipoActividadVO tipoActividad;
	private TipoActividadVO tipoActividadFilter;
	
	@Inject
	private TipoActividadBO tipoActividadBO;
	
	private List<TipoActividadVO> listTipoActividad;
	
	private static final String listPageName = "listTipoActividad";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public TipoActividadBean(){
		this.tipoActividad = new TipoActividadVO();
		this.tipoActividadFilter = new TipoActividadVO();
	}
	

	public void init(){
		super.init();
		
		this.tipoActividad = new TipoActividadVO();
		this.tipoActividadFilter = new TipoActividadVO();
		
		this.createListTipoActividad();
	}
	
	public void clear(){
		super.init();
		
		this.tipoActividad = new TipoActividadVO();
		this.tipoActividadFilter = new TipoActividadVO();
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
	 * Find tipoActividad elements using the filter get.
	 */
	public void doAddFilter(){
		listTipoActividad = this.tipoActividadBO.findUsingTemplate(null, tipoActividadFilter, null);
	}
	
	/**
	 * Clear the tipoActividad filter of the list page
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
			this.tipoActividadBO.createElement(null, tipoActividad);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the tipoActividad edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			tipoActividad = this.tipoActividadBO.findByPrimaryKey(null, Long.valueOf(id));
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
		TipoActividadVO tipoActividad = new TipoActividadVO();
		tipoActividad.setId(this.selectedDeleteItemId);
		
		this.tipoActividadBO.deleteElement(null, tipoActividad);
		
		this.createListTipoActividad();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListTipoActividad(){
		listTipoActividad = this.tipoActividadBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public TipoActividadVO getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(TipoActividadVO tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public TipoActividadVO getTipoActividadFilter() {
		return tipoActividadFilter;
	}

	public void setTipoActividadFilter(TipoActividadVO tipoActividadFilter) {
		this.tipoActividadFilter = tipoActividadFilter;
	}

	public TipoActividadBO getTipoActividadBO() {
		return tipoActividadBO;
	}

	public void setTipoActividadBO(TipoActividadBO tipoActividadBO) {
		this.tipoActividadBO = tipoActividadBO;
	}

	public void setListTipoActividad(List<TipoActividadVO> listTipoActividad) {
		this.listTipoActividad = listTipoActividad;
	}

	public List<TipoActividadVO> getListTipoActividad(){
		if(listTipoActividad == null){
			this.createListTipoActividad();
		}
		
		return listTipoActividad;
	}
}