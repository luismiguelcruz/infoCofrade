package com.infoCofrade.administration.provincia;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.provincia.bo.ProvinciaBO;
import com.infoCofrade.administration.provincia.vo.ProvinciaVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class ProvinciaBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private ProvinciaVO provincia;
	private ProvinciaVO provinciaFilter;
	
	@Inject
	private ProvinciaBO provinciaBO;
	
	private List<ProvinciaVO> listProvincia;
	
	private static final String listPageName = "listProvincia";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public ProvinciaBean(){
		this.provincia = new ProvinciaVO();
		this.provinciaFilter = new ProvinciaVO();
	}

	public void init(){
		super.init();
		
		this.provincia = new ProvinciaVO();
		this.provinciaFilter = new ProvinciaVO();
		
		this.createListProvincia();
	}
	
	public void clear(){
		super.init();
		
		this.provincia = new ProvinciaVO();
		this.provinciaFilter = new ProvinciaVO();
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
	 * Find provincia elements using the filter get.
	 */
	public void doAddFilter(){
		listProvincia = this.provinciaBO.findUsingTemplate(null, provinciaFilter, null);
	}
	
	/**
	 * Clear the provincia filter of the list page
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
			this.provinciaBO.createElement(null, provincia);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the provincia edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			provincia = this.provinciaBO.findByPrimaryKey(null, Long.valueOf(id));
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
		if (this.selectedDeleteItemId != null) {
			ProvinciaVO provincia = new ProvinciaVO();
			provincia.setId(this.selectedDeleteItemId);
			
			this.provinciaBO.deleteElement(null, provincia);
			
			this.createListProvincia();
		}
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListProvincia(){
		listProvincia = this.provinciaBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

	public ProvinciaVO getProvinciaFilter() {
		return provinciaFilter;
	}

	public void setProvinciaFilter(ProvinciaVO provinciaFilter) {
		this.provinciaFilter = provinciaFilter;
	}

	public ProvinciaBO getProvinciaBO() {
		return provinciaBO;
	}

	public void setProvinciaBO(ProvinciaBO provinciaBO) {
		this.provinciaBO = provinciaBO;
	}

	public void setListProvincia(List<ProvinciaVO> listProvincia) {
		this.listProvincia = listProvincia;
	}

	public List<ProvinciaVO> getListProvincia(){
		if(listProvincia == null){
			this.createListProvincia();
		}
		
		return listProvincia;
	}
	
	public List<ProvinciaVO> getListProvinciaFilter(){
		return this.provinciaBO.findAll(null, "`provincia`");
	}
}