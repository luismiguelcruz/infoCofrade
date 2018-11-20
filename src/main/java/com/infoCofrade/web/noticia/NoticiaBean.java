package com.infoCofrade.web.noticia;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.cuenta.CuentaBean;
import com.infoCofrade.tesoreria.cuenta.bo.CuentaBO;
import com.infoCofrade.tesoreria.cuenta.vo.CuentaVO;
import com.infoCofrade.web.noticia.bo.NoticiaBO;
import com.infoCofrade.web.noticia.vo.NoticiaVO;


@Named
@Scope("session")
public class NoticiaBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private NoticiaVO noticia;
	private NoticiaVO noticiaFilter;
	
	@Inject
	private NoticiaBO noticiaBO;
	@Inject
	private CuentaBO cuentaBO;
	
	private List<NoticiaVO> listNoticia;
	
	private List<CuentaVO> listSearchCuenta;
	private List<CuentaVO> listFilteredCuentas;
	
	// Variables para el manejo de la foto del perfil
	private UploadedFile uploadedFile;
	private StreamedContent embeddedImage;
	
	private static final String listPageName = "listNoticia";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	private static final String editPageFromExternalServlet = "editNoticia";
	
	private String returnPageName = null;
	private Long idCuentaSelected = null;
	
	
	public NoticiaBean(){
		this.noticia = new NoticiaVO();
		this.noticiaFilter = new NoticiaVO();
	}
	

	public void init(){
		super.init();
		
		this.noticia = new NoticiaVO();
		this.noticiaFilter = new NoticiaVO();
		this.idCuentaSelected = null;
		
		this.uploadedFile = null;
		
		this.createListNoticia();
		this.createListSearchCuenta();
	}
	
	public void clear(){
		super.init();
		
		this.noticia = new NoticiaVO();
		this.noticiaFilter = new NoticiaVO();
		this.idCuentaSelected = null;
		
		this.uploadedFile = null;
		
		this.listFilteredCuentas = null;
	}
	
	//------------------------------- ACTIONS ----------------------------------
	/**
	 * Initialize the values where the list page is showed from menu
	 * @return String value of the destiny page
	 */
	public String doNavigate(){
		this.init();
		
		// Ponemos la pagina de retorno a nulo para que no vuelva desde la edicion a otra pagina que
		// no sea el listado de noticias
		this.returnPageName = null;
		
		return listPageName;
	}
	
	/**
	 * Find noticia elements using the filter get.
	 */
	public void doAddFilter(){
		listNoticia = this.noticiaBO.findUsingTemplate(null, noticiaFilter, null);
	}
	
	/**
	 * Clear the noticia filter of the list page
	 */
	public void doClearFilter(){
		this.init();
	}
	
	/**
	 * Add a new element into the database
	 * @return String value of the destiny page
	 */
	public String doCreateElement(){
		String navigateString = editPageName;
		
		if(validate()){
			navigateString = listPageName;	
			
			try{
				if (this.uploadedFile != null) {
					// Guardamos la imagen que se ha subido como imagen del usuario en
					// la base de datos
					try {
						noticia.setImagenNoticia(compressImage(this.uploadedFile, 350, 350));
					} catch (IOException e) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Ha sido imposible guardar la imagen seleccionada en su perfil",
										e.getMessage()));
					}
				}
				
				this.noticiaBO.createElement(null, noticia);
				
				this.init();
			} catch (DaoException e){
				navigateString = editPageName;
			}
		}
		
		if(returnPageName != null && !navigateString.equalsIgnoreCase(editPageName)){
			CuentaBean cuentaBean = (CuentaBean)this.getBean("cuentaBean");
			return cuentaBean.doNavigate();
		} else {
			return navigateString;	
		}
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the noticia edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			noticia = this.noticiaBO.findByPrimaryKey(null, Long.valueOf(id));
			
			this.createListSearchCuenta();
		}

		if (edition != null) {
			if(edition.equalsIgnoreCase(editPageName)){
				return editPageName;	
			} else {
				return editPageFromExternalServlet;
			}
		} else {
			return viewPageName;
		}
	}
	

	/**
	 * Delete an element from database
	 * @return String value of the destiny page
	 */
	public void doDeleteElement(){
		NoticiaVO noticia = new NoticiaVO();
		noticia.setId(this.selectedDeleteItemId);
		
		this.noticiaBO.deleteElement(null, noticia);
		
		this.createListNoticia();
	}
	
	
	/**
	 * Sube una imagen de perfil para un usuario 
	 * @param event
	 */
	public void uploadFile(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Se ha modificado la imagen de perfil correctamente.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		uploadedFile = event.getFile();

		// Creamos la imagen para hacerle el crop
	}

	
	public void clearUploadedImage() {
		this.uploadedFile = null;
	}

	
	public void clearSavedImage() {
		this.noticia.setImagenNoticia(null);
	}
	
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListNoticia(){
		listNoticia = this.noticiaBO.findAll(null, null);
	}
	
	private void createListSearchCuenta() {
		listSearchCuenta = this.cuentaBO.findAll(null, "`nivel`, `nombre`");
	}
	
	private Boolean validate(){
		Boolean value = true;
		
		/*if (this.noticia.getFechaEntrada() != null && this.noticia.getFechaSalida() != null){
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
							"La noticia puede ser de entrada o de salida, por tanto no puede tener las dos fechas asignadas"));
			
			value = false;
		}*/
		
		return value;
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public NoticiaVO getNoticia() {
		return noticia;
	}

	public void setNoticia(NoticiaVO noticia) {
		this.noticia = noticia;
	}

	public NoticiaVO getNoticiaFilter() {
		return noticiaFilter;
	}

	public void setNoticiaFilter(NoticiaVO noticiaFilter) {
		this.noticiaFilter = noticiaFilter;
	}

	public NoticiaBO getNoticiaBO() {
		return noticiaBO;
	}

	public void setNoticiaBO(NoticiaBO noticiaBO) {
		this.noticiaBO = noticiaBO;
	}

	public void setListNoticia(List<NoticiaVO> listNoticia) {
		this.listNoticia = listNoticia;
	}

	public List<NoticiaVO> getListNoticia(){
		if(listNoticia == null){
			this.createListNoticia();
		}
		
		return listNoticia;
	}
	
	public void setListSearchCuenta(List<CuentaVO> listSearchCuenta) {
		this.listSearchCuenta = listSearchCuenta;
	}
	
	public List<CuentaVO> getListSearchCuenta() {
		if (listSearchCuenta == null) {
			this.createListSearchCuenta();
		}

		return listSearchCuenta;
	}
	
	public void setListFilteredCuentas(List<CuentaVO> listFilteredCuentas) {  
        this.listFilteredCuentas = listFilteredCuentas;  
    }
	
	public List<CuentaVO> getListFilteredCuentas() {  
        return listFilteredCuentas;
    }

	public String getReturnPageName() {
		return returnPageName;
	}

	public void setReturnPageName(String returnPageName) {
		this.returnPageName = returnPageName;
	}

	public Long getIdCuentaSelected() {
		return idCuentaSelected;
	}

	public void setIdCuentaSelected(Long idCuentaSelected) {
		this.idCuentaSelected = idCuentaSelected;
	}
	
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public StreamedContent getEmbeddedImage() {
		return embeddedImage;
	}

	public void setEmbeddedImage(StreamedContent embeddedImage) {
		this.embeddedImage = embeddedImage;
	}


	/**
	 * Cargamos la imagen de la noticia de la base de datos para mostrarla en la web
	 * @return
	 */
	public StreamedContent getImageInList() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String indexString = fc.getExternalContext().getRequestParameterMap().get("index");

		if (fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			embeddedImage = null;
			Integer index = Integer.valueOf(indexString);
			
			if (this.listNoticia.get(index).getImagenNoticia() != null &&
					this.listNoticia.get(index).getImagenNoticia().length > 0) {
				embeddedImage = new DefaultStreamedContent(
						new ByteArrayInputStream(this.listNoticia.get(index).getImagenNoticia()),
						"image/png");
			}
		}

		return embeddedImage;
	}

	/**
	 * Cargamos la imagen que tenemos en el uploaded file de la noticia
	 * @return
	 */
	public StreamedContent getImageInListUploaded() {
		FacesContext fc = FacesContext.getCurrentInstance();

		if (fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			embeddedImage = null;
			try {
				embeddedImage = new DefaultStreamedContent(
						uploadedFile.getInputstream(), "image/png");
			} catch (IOException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"IO Exception: ", e.getMessage()));
			}
		}

		return embeddedImage;
	}
}