package com.infoCofrade.administration.dataBaseManage;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.dataBaseManage.bo.DataBaseManageBO;
import com.infoCofrade.administration.login.LoginBean;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.hibernate.ConfHibernate;


@Named
@Scope("session")
public class DataBaseManageBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	
	
	@Inject
	private DataBaseManageBO dataBaseManageBO;
	
	private static final String initPage = "index";
	private static final String listPageName = "listDataBaseManage";
	private static final String listCreateInserts = "indexConfiguration";
		
	
	public DataBaseManageBean(){
		
	}
	

	public void init(){
		super.init();
	}
	
	public void clear(){
		super.init();
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
	
	public String doInitPage(){
		this.init();
		
		return "loggedIndex";
	}
	
	public String doResetDataBase(){
		this.dataBaseManageBO.resetDataBase(null);
		
		return this.doLogout();
	}
	
	public String doCreateDataBaseStructure(Boolean hermandadSelected){
		if(hermandadSelected){
			try{
				// Creamos las vistas de la base de datos
				this.dataBaseManageBO.createViews(null, true);
				
				// Creamos los inserts necesarios para resetear la aplicación
				this.dataBaseManageBO.createInserts(null, true);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido resetear la base de datos",
								e.getMessage()));
			}
		} else {
			try{
				// Creamos las vistas de la base de datos
				this.dataBaseManageBO.createViews(null, false);
				
				// Creamos los inserts necesarios para resetear la aplicación
				this.dataBaseManageBO.createInserts(null, false);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido resetear la base de datos",
								e.getMessage()));
			}
		}
		
		return listCreateInserts;
	}
	
	public String doLogout() {
		String destiny = initPage;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);
		// Change the data base to access
    	AbstractBean.hibernateConfFileName = null;
		ConfHibernate.changeSessionFactory();
		session.removeAttribute("authenticated");

		this.init();

		return destiny;
	}
}