package com.infoCofrade.administration.secretaria.sector;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.secretaria.sector.bo.SectorBO;
import com.infoCofrade.administration.secretaria.sector.vo.SectorVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class SectorBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private SectorVO sector;
	private SectorVO sectorFilter;
	
	@Inject
	private SectorBO sectorBO;
	
	private List<SectorVO> listSector;
	
	private static final String listPageName = "listSector";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public SectorBean(){
		this.sector = new SectorVO();
		this.sectorFilter = new SectorVO();
	}
	

	public void init(){
		super.init();
		
		this.sector = new SectorVO();
		this.sectorFilter = new SectorVO();
		
		this.createListSector();
	}
	
	public void clear(){
		super.init();
		
		this.sector = new SectorVO();
		this.sectorFilter = new SectorVO();
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
	 * Find sector elements using the filter get.
	 */
	public void doAddFilter(){
		listSector = this.sectorBO.findUsingTemplate(null, sectorFilter, null);
	}
	
	/**
	 * Clear the sector filter of the list page
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
			this.sectorBO.createElement(null, sector);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the sector edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			sector = this.sectorBO.findByPrimaryKey(null, Long.valueOf(id));
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
		SectorVO sector = new SectorVO();
		sector.setId(this.selectedDeleteItemId);
		
		this.sectorBO.deleteElement(null, sector);
		
		this.createListSector();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListSector(){
		listSector = this.sectorBO.findAll(null, null);
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public SectorVO getSector() {
		return sector;
	}

	public void setSector(SectorVO sector) {
		this.sector = sector;
	}

	public SectorVO getSectorFilter() {
		return sectorFilter;
	}

	public void setSectorFilter(SectorVO sectorFilter) {
		this.sectorFilter = sectorFilter;
	}

	public SectorBO getSectorBO() {
		return sectorBO;
	}

	public void setSectorBO(SectorBO sectorBO) {
		this.sectorBO = sectorBO;
	}

	public void setListSector(List<SectorVO> listSector) {
		this.listSector = listSector;
	}

	public List<SectorVO> getListSector(){
		if(listSector == null){
			this.createListSector();
		}
		
		return listSector;
	}
}