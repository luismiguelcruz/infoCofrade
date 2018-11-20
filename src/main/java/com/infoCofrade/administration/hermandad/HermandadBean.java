package com.infoCofrade.administration.hermandad;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.hermandad.bo.HermandadBO;
import com.infoCofrade.administration.hermandad.vo.HermandadVO;
import com.infoCofrade.administration.localidad.bo.LocalidadBO;
import com.infoCofrade.administration.localidad.vo.LocalidadVO;
import com.infoCofrade.administration.provincia.bo.ProvinciaBO;
import com.infoCofrade.administration.provincia.vo.ProvinciaVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class HermandadBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private HermandadVO hermandad;
	private HermandadVO hermandadFilter;
	
	@Inject
	private HermandadBO hermandadBO;
	@Inject
	private LocalidadBO localidadBO;
	@Inject
	private ProvinciaBO provinciaBO;
	
	private List<HermandadVO> listHermandad;
	
	private LocalidadVO localidadSelected;
	
	/**
	 * Variables para el manejo de la foto del perfil de la hermandad
	 */
	private UploadedFile uploadedFile;
	private StreamedContent embeddedImage;
	private CroppedImage croppedImage;
	
	private static final String listPageName = "listHermandad";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public HermandadBean(){
		this.hermandad = new HermandadVO();
		this.hermandadFilter = new HermandadVO();
	}
	

	public void init(){
		super.init();
		
		this.hermandad = new HermandadVO();
		this.hermandadFilter = new HermandadVO();
		this.localidadSelected = new LocalidadVO();
		
		this.uploadedFile = null;
		this.croppedImage = null;
		
		this.createListHermandad();
	}
	
	public void clear(){
		super.init();
		
		this.hermandad = new HermandadVO();
		this.hermandadFilter = new HermandadVO();
		this.localidadSelected = new LocalidadVO();
		
		this.uploadedFile = null;
		this.croppedImage = null;
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
	 * Find hermandad elements using the filter get.
	 */
	public void doAddFilter(){
		this.changeDataBaseSchema();
		
		listHermandad = this.hermandadBO.findUsingTemplate(null, hermandadFilter, null);
		
		this.restoreDataBaseSchema();
	}
	
	/**
	 * Clear the hermandad filter of the list page
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
			this.hermandad.setIdLocalidad(null);
			if(localidadSelected != null && localidadSelected.getId() != null){
				this.hermandad.setIdLocalidad(localidadSelected.getId());
			}
			
			if (this.uploadedFile != null) {
				// Guardamos la imagen que se ha subido como imagen del usuario en
				// la base de datos
				try {
					hermandad.setHermandadImage(compressImage(this.uploadedFile, 350, 350));
				} catch (IOException e) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Ha sido imposible guardar la imagen seleccionada en el perfil de la hermandad",
									e.getMessage()));
				}
			}
			
			this.changeDataBaseSchema();
			this.hermandadBO.createElement(null, hermandad);
			this.restoreDataBaseSchema();
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the hermandad edition
	 */
	public String doEditElement(){
		String navigateString = editPageName;
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			this.changeDataBaseSchema();
			hermandad = this.hermandadBO.findByPrimaryKey(null, Long.valueOf(id));
			this.restoreDataBaseSchema();
			
			if(edition.equalsIgnoreCase("edition")){
				if(hermandad.getIdLocalidad() != null){
					LocalidadVO localidad = localidadBO.findByPrimaryKey(null, hermandad.getIdLocalidad());
					ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null, localidad.getIdProvincia());
					
					hermandad.setCodigoPostal(localidad.getCodigoPostal());
					hermandad.setProvincia(provincia.getProvincia());
					
					this.setLocalidadSelected(localidad);	
				}
			}
		}

		if (edition != null) {
			if (edition.equalsIgnoreCase("view")) {
				navigateString = viewPageName;
			}
		}
		
		return navigateString;
	}
	
	/**
	 * Delete an element from database
	 * @return String value of the destiny page
	 */
	public void doDeleteElement(){
		HermandadVO hermandad = new HermandadVO();
		hermandad.setId(this.selectedDeleteItemId);
		
		this.changeDataBaseSchema();
		this.hermandadBO.deleteElement(null, hermandad);
		this.restoreDataBaseSchema();
		
		this.createListHermandad();
	}
	
	/**
	 * Para una localidad seleccionada, carga el provincia y el codigo postal para mostrarlos en el formulario 
	 */
	public void loadAddressFields(){
		ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null, localidadSelected.getIdProvincia());
		
		if(localidadSelected.getId() != null){
			hermandad.setIdLocalidad(localidadSelected.getId());	
		}
		hermandad.setCodigoPostal(localidadSelected.getCodigoPostal());
		hermandad.setProvincia(provincia.getProvincia());
	}
	
	/**
	 * Link a file with the actual object 
	 * @param event
	 */
	public void uploadFile(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		uploadedFile = event.getFile();

		// Creamos la imagen para hacerle el crop
	}
	
	public void clearUploadedImage() {
		this.uploadedFile = null;
	}

	public void clearSavedImage() {
		this.hermandad.setHermandadImage(null);
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListHermandad(){
		this.changeDataBaseSchema();
		listHermandad = this.hermandadBO.findAll(null, null);
		this.restoreDataBaseSchema();
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public HermandadVO getHermandad() {
		return hermandad;
	}

	public void setHermandad(HermandadVO hermandad) {
		this.hermandad = hermandad;
	}

	public HermandadVO getHermandadFilter() {
		return hermandadFilter;
	}

	public void setHermandadFilter(HermandadVO hermandadFilter) {
		this.hermandadFilter = hermandadFilter;
	}

	public HermandadBO getHermandadBO() {
		return hermandadBO;
	}

	public void setHermandadBO(HermandadBO hermandadBO) {
		this.hermandadBO = hermandadBO;
	}

	public void setListHermandad(List<HermandadVO> listHermandad) {
		this.listHermandad = listHermandad;
	}

	public List<HermandadVO> getListHermandad(){
		if(listHermandad == null){
			this.createListHermandad();
		}
		
		return listHermandad;
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

	public CroppedImage getCroppedImage() {
		return croppedImage;
	}

	public void setCroppedImage(CroppedImage croppedImage) {
		this.croppedImage = croppedImage;
	}

	/**
	 * Load the image into the list image page
	 * 
	 * @return
	 */
	public StreamedContent getImageInList() {
		FacesContext fc = FacesContext.getCurrentInstance();

		if (fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			embeddedImage = null;
			
			if (this.hermandad.getHermandadImage() != null && this.hermandad.getHermandadImage().length > 0) {
				embeddedImage = new DefaultStreamedContent(
						new ByteArrayInputStream(this.hermandad.getHermandadImage()),
						"image/png");
			}
		}

		return embeddedImage;
	}

	/**
	 * Load the image into uploaded file
	 * 
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