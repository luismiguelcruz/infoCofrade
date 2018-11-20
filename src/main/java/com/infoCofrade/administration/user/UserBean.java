package com.infoCofrade.administration.user;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;

import org.imgscalr.Scalr;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.user.bo.UserBO;
import com.infoCofrade.administration.user.vo.UserVO;
import com.infoCofrade.administration.userType.bo.UserTypeBO;
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.bean.Constants;
import com.infoCofrade.common.exceptions.DaoException;


@Named
@Scope("session")
public class UserBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private UserVO user;
	
	@Inject
	private UserBO userBO;
	@Inject
	private UserTypeBO userTypeBO;
	
	private List<UserVO> listUsers;
	
	private List<UserTypeVO> listUserType;
	
	// Variables para el manejo de la foto del perfil
	private UploadedFile uploadedFile;
	private String uploadFolderPath = Constants.FILE_UPLOAD_PATH + "profile/";
	
	private static final String listPageName = "listUser";
	private static final String editPageName = "edit";
	
	
	public UserBean(){
		this.user = new UserVO();
	}
	

	public void init(){
		super.init();
		
		this.user = new UserVO();
		this.uploadedFile = null;
		this.createListUser();
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
	 * Find user elements using the filter get. 
	 */
	public void doAddFilter(){
		this.changeDataBaseSchema();
		listUsers = this.userBO.findUsingTemplate(null, user);
		this.restoreDataBaseSchema();
	}
	
	/**
	 * Clear the user filter of the list page
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
				this.changeDataBaseSchema();
				this.userBO.createElement(null, user);
				this.restoreDataBaseSchema();
				
				this.init();
			} catch (DaoException e){
				navigateString = editPageName;
			}
		}
		
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the user edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		if (id != null){
			this.changeDataBaseSchema();
			user = this.userBO.findByPrimaryKey(null, Long.valueOf(id));
			this.restoreDataBaseSchema();
		}
		
		this.createListUserType();
		
		return editPageName;
	}
	
	/**
	 * Delete an element from database
	 * @return String value of the destiny page
	 */
	public String doDeleteElement(){
		UserVO us = new UserVO();
		us.setId(this.selectedDeleteItemId);
		
		this.changeDataBaseSchema();
		this.userBO.deleteElement(null, us);
		this.restoreDataBaseSchema();
		
		this.createListUser();
		
		return "success";
	}
	
	/**
	 * Link a file with the actual object
	 * @param event
	 */
	public void uploadFile(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Imagen ", event.getFile()
				.getFileName() + " subida correctamente.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		uploadedFile = event.getFile();
	}
	
	public void clearUploadedImage(){
		this.uploadedFile = null;
	}
	
	public void clearSavedImage(){
		this.user.setProfileImagePath(null);
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListUser(){
		this.changeDataBaseSchema();
		listUsers = this.userBO.findAll(null, null);
		this.restoreDataBaseSchema();
	}
	
	private void createListUserType(){
		this.changeDataBaseSchema();
		listUserType = this.userTypeBO.findAll(null, null);
		this.restoreDataBaseSchema();
	}
	
	private Boolean validate(){
		Boolean value = true; 
	
		if (this.user.getIdUserType() == null && this.user.getIdUserType() >= 0 &&
				this.user.getIdHermandad() == null) {
			FacesContext
					.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error de validación: ",
									"El campo Hermandad es obligatorio"));

			value = false;
		}
		
		return value;
	}
	
	/**
	 * Save a file into the system
	 * @throws IOException
	 */
	private void createFileIntoSystem(String fileName) throws IOException {
		// Copy the file into the path
		String filePath = uploadFolderPath
				+ fileName;
		File dir = new File(uploadFolderPath);
		File file = new File(filePath);
		if (!dir.exists()) {
			dir.mkdir();
		}

		if(!file.exists()){
			InputStream inputStream = null;
			OutputStream outputStream = null;
			ImageOutputStream ios = null;
			try {
				inputStream = uploadedFile.getInputstream();

				// write the inputStream to a FileOutputStream
				outputStream = new FileOutputStream(new File(filePath));

				// Set the quality compression for the image
				float quality = Float.parseFloat(this.getImageCompressQuality());

				// create a BufferedImage as the result of decoding the supplied
				// InputStream
				BufferedImage image = ImageIO.read(inputStream);

				// Escale the image
				BufferedImage scaledImage = Scalr.resize(image,
						Scalr.Method.SPEED, Scalr.Mode.AUTOMATIC, 1366, 864,
						Scalr.OP_ANTIALIAS);

				// get all image writers for JPG format
				Iterator<ImageWriter> writers = ImageIO
						.getImageWritersByFormatName("jpg");

				if (!writers.hasNext())
					throw new IllegalStateException("No writers found");

				ImageWriter writer = (ImageWriter) writers.next();
				ios = ImageIO.createImageOutputStream(outputStream);
				writer.setOutput(ios);

				ImageWriteParam param = writer.getDefaultWriteParam();

				// compress to a given quality
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(quality);

				// appends a complete image stream containing a single image and
				// associated stream and image metadata and thumbnails to the
				// output
				writer.write(null, new IIOImage(scaledImage, null, null), param);

				// close all streams
				writer.dispose();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
						throw new IOException();
					}
				}
				if (outputStream != null) {
					try {
						// outputStream.flush();
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
						throw new IOException();
					}
				}
				if (ios != null) {
					try {
						ios.close();
					} catch (IOException e) {
						e.printStackTrace();
						throw new IOException();
					}
				}
			}
		}
	}
	
	/**
	 * Delete a file of the system
	 * @param path
	 * @throws IOException
	 */
	private void deleteOldFile(String path){
		File file = new File(path);
		file.delete();
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}
	
	public UserBO getUserBO() {
		return userBO;
	}

	public void setUserBO(UserBO userBO) {
		this.userBO = userBO;
	}

	public UserTypeBO getUserTypeBO() {
		return userTypeBO;
	}

	public void setUserTypeBO(UserTypeBO userTypeBO) {
		this.userTypeBO = userTypeBO;
	}

	public void setListUsers(List<UserVO> listUsers) {
		this.listUsers = listUsers;
	}

	public List<UserVO> getListUsers(){
		if(listUsers == null){
			this.createListUser();
		}
		
		return listUsers;
	}

	public List<UserTypeVO> getListUserType() {
		if(listUserType == null){
			this.createListUserType();
		}
		
		return listUserType;
	}

	public void setListUserType(List<UserTypeVO> listUserType) {
		this.listUserType = listUserType;
	}
}