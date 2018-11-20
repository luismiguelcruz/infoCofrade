package com.infoCofrade.administration.tipoVia;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.tipoVia.bo.TipoViaBO;
import com.infoCofrade.administration.tipoVia.vo.TipoViaVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class TipoViaBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private TipoViaVO tipoVia;
	private TipoViaVO tipoViaFilter;
	
	@Inject
	private TipoViaBO tipoViaBO;
	
	private List<TipoViaVO> listTipoVia;
	
	private static final String listPageName = "listTipoVia";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public TipoViaBean(){
		this.tipoVia = new TipoViaVO();
		this.tipoViaFilter = new TipoViaVO();
	}
	

	public void init(){
		super.init();
		
		this.tipoVia = new TipoViaVO();
		this.tipoViaFilter = new TipoViaVO();
		
		this.createListTipoVia();
	}
	
	public void clear(){
		super.init();
		
		this.tipoVia = new TipoViaVO();
		this.tipoViaFilter = new TipoViaVO();
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
	 * Find tipoVia elements using the filter get.
	 */
	public void doAddFilter(){
		listTipoVia = this.tipoViaBO.findUsingTemplate(null, tipoViaFilter, null);
	}
	
	/**
	 * Clear the tipoVia filter of the list page
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
			this.tipoViaBO.createElement(null, tipoVia);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the tipoVia edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			tipoVia = this.tipoViaBO.findByPrimaryKey(null, Long.valueOf(id));
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
		TipoViaVO tipoVia = new TipoViaVO();
		tipoVia.setId(this.selectedDeleteItemId);
		
		this.tipoViaBO.deleteElement(null, tipoVia);
		
		this.createListTipoVia();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListTipoVia(){
		listTipoVia = this.tipoViaBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public TipoViaVO getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(TipoViaVO tipoVia) {
		this.tipoVia = tipoVia;
	}

	public TipoViaVO getTipoViaFilter() {
		return tipoViaFilter;
	}

	public void setTipoViaFilter(TipoViaVO tipoViaFilter) {
		this.tipoViaFilter = tipoViaFilter;
	}

	public TipoViaBO getTipoViaBO() {
		return tipoViaBO;
	}

	public void setTipoViaBO(TipoViaBO tipoViaBO) {
		this.tipoViaBO = tipoViaBO;
	}

	public void setListTipoVia(List<TipoViaVO> listTipoVia) {
		this.listTipoVia = listTipoVia;
	}

	public List<TipoViaVO> getListTipoVia(){
		if(listTipoVia == null){
			this.createListTipoVia();
		}
		
		return listTipoVia;
	}
}