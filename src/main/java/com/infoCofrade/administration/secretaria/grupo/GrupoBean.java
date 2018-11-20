package com.infoCofrade.administration.secretaria.grupo;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.secretaria.grupo.bo.GrupoBO;
import com.infoCofrade.administration.secretaria.grupo.vo.GrupoVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class GrupoBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private GrupoVO grupo;
	private GrupoVO grupoFilter;
	
	@Inject
	private GrupoBO grupoBO;
	
	private List<GrupoVO> listGrupo;
	
	private static final String listPageName = "listGrupo";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public GrupoBean(){
		this.grupo = new GrupoVO();
		this.grupoFilter = new GrupoVO();
	}
	

	public void init(){
		super.init();
		
		this.grupo = new GrupoVO();
		this.grupoFilter = new GrupoVO();
		
		this.createListGrupo();
	}
	
	public void clear(){
		super.init();
		
		this.grupo = new GrupoVO();
		this.grupoFilter = new GrupoVO();
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
	 * Find grupo elements using the filter get.
	 */
	public void doAddFilter(){
		listGrupo = this.grupoBO.findUsingTemplate(null, grupoFilter, null);
	}
	
	/**
	 * Clear the grupo filter of the list page
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
			this.grupoBO.createElement(null, grupo);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the grupo edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			grupo = this.grupoBO.findByPrimaryKey(null, Long.valueOf(id));
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
		GrupoVO grupo = new GrupoVO();
		grupo.setId(this.selectedDeleteItemId);
		
		this.grupoBO.deleteElement(null, grupo);
		
		this.createListGrupo();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListGrupo(){
		listGrupo = this.grupoBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public GrupoVO getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoVO grupo) {
		this.grupo = grupo;
	}

	public GrupoVO getGrupoFilter() {
		return grupoFilter;
	}

	public void setGrupoFilter(GrupoVO grupoFilter) {
		this.grupoFilter = grupoFilter;
	}

	public GrupoBO getGrupoBO() {
		return grupoBO;
	}

	public void setGrupoBO(GrupoBO grupoBO) {
		this.grupoBO = grupoBO;
	}

	public void setListGrupo(List<GrupoVO> listGrupo) {
		this.listGrupo = listGrupo;
	}

	public List<GrupoVO> getListGrupo(){
		if(listGrupo == null){
			this.createListGrupo();
		}
		
		return listGrupo;
	}
}