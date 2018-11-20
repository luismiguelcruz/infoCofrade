package com.infoCofrade.administration.pais;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.pais.bo.PaisBO;
import com.infoCofrade.administration.pais.vo.PaisVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class PaisBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private PaisVO pais;
	private PaisVO paisFilter;
	
	@Inject
	private PaisBO paisBO;
	
	private List<PaisVO> listPais;
	
	private static final String listPageName = "listPais";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public PaisBean(){
		this.pais = new PaisVO();
		this.paisFilter = new PaisVO();
	}
	

	public void init(){
		super.init();
		
		this.pais = new PaisVO();
		this.paisFilter = new PaisVO();
		
		this.createListPais();
	}
	
	public void clear(){
		super.init();
		
		this.pais = new PaisVO();
		this.paisFilter = new PaisVO();
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
	 * Find pais elements using the filter get.
	 */
	public void doAddFilter(){
		listPais = this.paisBO.findUsingTemplate(null, paisFilter, null);
	}
	
	/**
	 * Clear the pais filter of the list page
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
			this.paisBO.createElement(null, pais);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the pais edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			pais = this.paisBO.findByPrimaryKey(null, Long.valueOf(id));
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
		PaisVO pais = new PaisVO();
		pais.setId(this.selectedDeleteItemId);
		
		this.paisBO.deleteElement(null, pais);
		
		this.createListPais();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListPais(){
		listPais = this.paisBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public PaisVO getPais() {
		return pais;
	}

	public void setPais(PaisVO pais) {
		this.pais = pais;
	}

	public PaisVO getPaisFilter() {
		return paisFilter;
	}

	public void setPaisFilter(PaisVO paisFilter) {
		this.paisFilter = paisFilter;
	}

	public PaisBO getPaisBO() {
		return paisBO;
	}

	public void setPaisBO(PaisBO paisBO) {
		this.paisBO = paisBO;
	}

	public void setListPais(List<PaisVO> listPais) {
		this.listPais = listPais;
	}

	public List<PaisVO> getListPais(){
		if(listPais == null){
			this.createListPais();
		}
		
		return listPais;
	}
}