package com.infoCofrade.administration.login;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.imgscalr.Scalr;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;
import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.cssProfile.CssProfileBean;
import com.infoCofrade.administration.cssProfile.bo.CssProfileBO;
import com.infoCofrade.administration.dataBaseManage.DataBaseManageBean;
import com.infoCofrade.administration.hermandad.bo.HermandadBO;
import com.infoCofrade.administration.hermandad.vo.HermandadVO;
import com.infoCofrade.administration.menuItem.bo.MenuItemBO;
import com.infoCofrade.administration.menuItem.vo.MenuItemVO;
import com.infoCofrade.administration.rolUrlPermissionView.bo.RolUrlPermissionViewBO;
import com.infoCofrade.administration.rolUrlPermissionView.vo.RolUrlPermissionViewVO;
import com.infoCofrade.administration.urlPermission.vo.UrlPermissionVO;
import com.infoCofrade.administration.user.bo.UserBO;
import com.infoCofrade.administration.user.vo.UserVO;
import com.infoCofrade.administration.userType.bo.UserTypeBO;
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.administration.userView.bo.UserViewBO;
import com.infoCofrade.administration.userView.vo.UserViewVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.bean.Constants;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.hibernate.ConfHibernate;

@Named
@Scope("session")
public class LoginBean extends AbstractBean {
	private static final long serialVersionUID = 1L;
	private UserViewVO userView;
	private UserViewVO loggedUser;

	/**
	 * Manage if a user is connected to the app
	 */
	private Boolean isUserLogged;

	@Inject
	private UserViewBO userViewBO;
	@Inject
	private UserBO userBO;
	@Inject
	private HermandadBO hermandadBO;
	@Inject
	private UserTypeBO userTypeBO;
	@Inject
	private MenuItemBO menuItemBO;
	@Inject
	private RolUrlPermissionViewBO rolUrlPermissionViewBO;
	@Inject
	private CssProfileBO cssProfileBO;

	/**
	 * Menu Model
	 */
	private MenuModel menuModel;

	private List<HermandadVO> listHermandad = null;
	
	private String cssFileUrl;

	/**
	 * Map to manage the url permissions for the current logged user
	 */
	private static Map<String, UrlPermissionVO> mapUserPermission;
	Map<String, UrlPermissionVO> mapUserBeanPermission;

	/**
	 * Variable que contiene el nombre de la base de datos a mostrar
	 */
	private HermandadVO hermandadSelected = null;

	/**
	 * Variables para el manejo de la foto del perfil del usuario
	 */
	private UploadedFile uploadedFile;
	private String uploadFolderPath = Constants.FILE_UPLOAD_PATH + "profile/";
	private StreamedContent embeddedImage;
	private CroppedImage croppedImage;
	
	/**
	 * Variables para el manejo de la foto del perfil de la hermandad
	 */
	private UploadedFile uploadedHermandadFile;
	private StreamedContent embeddedHermandadImage;
	private CroppedImage croppedHermandadImage;

	/**
	 * Variables para el redireccionamiento de las paginas
	 */
	private static final String initPage = "index";
	private static final String loginPage = "login";
	private static final String editPageUserName = "editPersonalInformation";
	private static final String editPageHermandadName = "editHermandadInformation";

	public LoginBean() {
		this.userView = new UserViewVO();
		this.isUserLogged = false;
		this.hermandadSelected = new HermandadVO();
		
		this.cssFileUrl = Constants.URL_PERFIL_CSS_POR_DEFECTO;
	}

	public void init() {
		this.userView = new UserViewVO();
		this.isUserLogged = false;
		this.hermandadSelected = new HermandadVO();
		this.uploadedFile = null;
		this.uploadedHermandadFile = null;
		this.croppedImage = null;
		this.croppedHermandadImage = null;
		
		this.cssFileUrl = Constants.URL_PERFIL_CSS_POR_DEFECTO;

		this.createListHermandad();
		mapUserPermission = null;
		mapUserBeanPermission = null;
	}

	// ---------------------- ACTIONS ----------------------
	@Override
	public String doNavigate() {
		return null;
	}

	public String doLogin() {
		String destiny = null;

		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;

		// Nos aseguramos que sea cual sea la base de datos elegida, el usuario
		// la busca en la base
		// de datos de permisos
		if (AbstractBean.hibernateConfFileName != null) {
			AbstractBean.hibernateConfFileName = null;
			ConfHibernate.changeSessionFactory();
		}
		loggedUser = this.userViewBO.findLoggedUser(null, userView);

		if (loggedUser != null && allowedAccess()) {
			UserTypeVO userType = this.userTypeBO.findByPrimaryKey(null,
					loggedUser.getIdUserType());
			loggedUser.setUserType(userType.getType());
			this.isUserLogged = true;
			
			// Le cargamos su perfil visual asociado si lo tuviese
			if(this.loggedUser.getIdCssProfile() != null){
				this.cssFileUrl = loggedUser.getUrlCssProfilePath();
			} else {
				this.cssFileUrl = Constants.URL_PERFIL_CSS_POR_DEFECTO;
			}
			
			// Cargamos los tipos de perfiles a los que puede tener acceso el usuario para
			CssProfileBean cssProfileBean = (CssProfileBean)this.getBean("cssProfileBean");
			cssProfileBean.createListCssProfile();

			// Actualizamos la fecha de último acceso
			loggedUser.setLastLogin(new Date());
			Calendar c = new GregorianCalendar();
			c.setTime(new Date());
			c.add(Calendar.SECOND, Integer
					.valueOf(Constants.EXPIRED_SESSION_TIME_SECONDS.trim()));
			loggedUser.setExpiredSessionTime(c.getTime());
			try {
				UserVO user = this.userBO.findByPrimaryKey(null,
						loggedUser.getId());
				user.setLastLogin(new Date());
				user.setExpiredSessionTime(c.getTime());
				this.userBO.updateLastLoginDate(null, user);
			} catch (DaoException e) {
				e.printStackTrace();
			}

			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@",
					this.userView.getUsername());

			// Creamos el mapa de permisos para el usuario
			this.createMapPermissions();

			// Creamos todos los enlaces del menu permitidos para el usuario
			this.createMenuModel();

			// Cambiamos la base de datos a la que acceder siempre que el usuario tenga hermandad
			// asociada o que sea un super usuario que quiera acceder a una determinada hermandad
			if (loggedUser.getIdHermandad() != null) {
				AbstractBean.hibernateConfFileName = loggedUser
						.getDataSourceName() + ".cfg.xml";
				ConfHibernate.changeSessionFactory();
			} else if (hermandadSelected != null
					&& hermandadSelected.getDataSourceName() != null) {
				HermandadVO hermandadSearch = new HermandadVO();
				hermandadSearch.setDataSourceName(hermandadSelected.getDataSourceName());
				List<HermandadVO> listHermandad = this.hermandadBO.findUsingExactTemplate(null, hermandadSearch, null);
				// Actualizamos los datos de la hermandad a la que ha accedido el usuario genérico
				loggedUser.setIdHermandad(listHermandad.get(0).getId());
				loggedUser.setHermandadNombreCorto(listHermandad.get(0).getNombreCorto());
				loggedUser.setHermandadUrlWeb(listHermandad.get(0).getUrlWeb());
				loggedUser.setHermandadImage(listHermandad.get(0).getHermandadImage());
				
				// Cambiamos el data source para acceder a la nueva base de datos
				AbstractBean.hibernateConfFileName = hermandadSelected
						.getDataSourceName() + ".cfg.xml";
				ConfHibernate.changeSessionFactory();
			}
			
			// Insertamos el usuario actual en sesión
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext
					.getExternalContext().getSession(true);
			session.setAttribute("authenticated", loggedUser);

			destiny = loginPage;
		} else {
			this.isUserLogged = false;
			this.menuModel = null;

			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
					"Invalid credentials");

			destiny = initPage;
		}

		context.addCallbackParam("loggedIn", this.isUserLogged.booleanValue());
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return destiny;
	}

	public String doCreateDataBaseStructure() {
		// Nos aseguramos que sea cual sea la base de datos elegida, el usuario
		// la busca en la base de datos de permisos
		if (AbstractBean.hibernateConfFileName != null) {
			AbstractBean.hibernateConfFileName = null;
			ConfHibernate.changeSessionFactory();
		}
		loggedUser = this.userViewBO.findLoggedUser(null, userView);

		DataBaseManageBean dataBaseManageBean = (DataBaseManageBean) this
				.getBean("dataBaseManageBean");
		Boolean isHermandadSelected = false;

		if (loggedUser != null
				&& loggedUser.getIdUserType().compareTo(new Long(-1)) == 0
				&& hermandadSelected != null
				&& hermandadSelected.getDataSourceName() != null) {
			AbstractBean.hibernateConfFileName = hermandadSelected
					.getDataSourceName() + ".cfg.xml";
			ConfHibernate.changeSessionFactory();
			isHermandadSelected = true;
		}

		return dataBaseManageBean.doCreateDataBaseStructure(isHermandadSelected);
	}

	public String doLogout() {
		String destiny = initPage;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);
		// Change the data base to access
		if (AbstractBean.hibernateConfFileName != null) {
			AbstractBean.hibernateConfFileName = null;
			ConfHibernate.changeSessionFactory();
		}
		session.removeAttribute("authenticated");

		this.init();

		return destiny;
	}

	public String doEditUser() {
		uploadedFile = null;

		return editPageUserName;
	}
	
	public String doEditHermandad() {
		uploadedHermandadFile = null;

		return editPageHermandadName;
	}

	public String doUpdateUserInformation() {
		String navigateString = initPage;
		try{
			String hibernateConfFileName = AbstractBean.hibernateConfFileName;
			if (hibernateConfFileName != null) {
				AbstractBean.hibernateConfFileName = null;
				ConfHibernate.changeSessionFactory();
			}
			UserVO user = this.userBO.findByPrimaryKey(null, loggedUser.getId());
			user.setName(loggedUser.getName());
			user.setSurname(loggedUser.getSurname());
			user.setDni(loggedUser.getDni());
			user.setEmail(loggedUser.getEmail());
			user.setPassword(loggedUser.getPassword());
			user.setPhone(loggedUser.getPhone());
			user.setUserImage(loggedUser.getUserImage());
			user.setIdCssProfile(loggedUser.getIdCssProfile());
			
			if (this.uploadedFile != null) {
				// Guardamos la imagen que se ha subido como imagen del usuario en
				// la base de datos
				try {
					user.setUserImage(compressImage(this.uploadedFile, 200, 200));
				} catch (IOException e) {
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"Ha sido imposible guardar la imagen seleccionada en su perfil",
											e.getMessage()));
				}
			}
			
			// Comprobamos si el usuario ha hecho un cambio de su perfil css
			FacesContext fc = FacesContext.getCurrentInstance();
			String cssProfile = fc.getExternalContext().getRequestParameterMap().get("cssProfile");
			if (cssProfile != null && cssProfile.length() > 0){
				Long idCssProfile = Long.valueOf(cssProfile);
				user.setIdCssProfile(idCssProfile);
			}
	
			// Actualizamos los datos del usuario
			this.userBO.createElement(null, user);
			
			// Actualizamos el usuario de sesión para que muestra su foto actualizada y sus datos
			UserViewVO userView = new UserViewVO();
			userView.setId(loggedUser.getId());
			this.loggedUser = this.userViewBO.findLoggedUser(null, userView);
			if(this.loggedUser.getIdCssProfile() != null){
				this.cssFileUrl = loggedUser.getUrlCssProfilePath();
			} else {
				this.cssFileUrl = Constants.URL_PERFIL_CSS_POR_DEFECTO;
			}
	
			if (hibernateConfFileName != null) {
				AbstractBean.hibernateConfFileName = hibernateConfFileName;
				ConfHibernate.changeSessionFactory();
			}
		} catch (DaoException e){
			navigateString = editPageUserName;
		}
		

		return navigateString;
	}
	
	public String doUpdateHermandadInformation() {
		String navigateString = initPage;
		try{
			String hibernateConfFileName = AbstractBean.hibernateConfFileName;
			if (hibernateConfFileName != null) {
				AbstractBean.hibernateConfFileName = null;
				ConfHibernate.changeSessionFactory();
			}
			HermandadVO hermandad = this.hermandadBO.findByPrimaryKey(null, loggedUser.getIdHermandad());
			hermandad.setNombreCorto(loggedUser.getHermandadNombreCorto());
			hermandad.setUrlWeb(loggedUser.getHermandadUrlWeb());
			hermandad.setHermandadImage(loggedUser.getHermandadImage());
			
			if (this.uploadedHermandadFile != null) {
				// Guardamos la imagen que se ha subido como imagen de la hermandad en la base de datos
				try {
					hermandad.setHermandadImage(compressImage(this.uploadedHermandadFile, 200, 200));
				} catch (IOException e) {
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"Ha sido imposible guardar la imagen seleccionada en el perfil de la hermandad",
											e.getMessage()));
				}
			}
	
			// Actualizamos los datos del usuario
			this.hermandadBO.createElement(null, hermandad);
			
			// Actualizamos el usuario de sesión para que muestra su foto actualizada y sus datos
			UserViewVO userView = new UserViewVO();
			userView.setId(loggedUser.getId());
			this.loggedUser = this.userViewBO.findLoggedUser(null, userView);
			
			// Si el usuario que se ha logado es un administrador del sistema, tenemos que actualizar la hermandad
			// con la que se ha logado para que muestre la imagen en pantalla
			if (hermandadSelected != null && hermandadSelected.getDataSourceName() != null) {
				HermandadVO hermandadSearch = new HermandadVO();
				hermandadSearch.setDataSourceName(hermandadSelected.getDataSourceName());
				List<HermandadVO> listHermandad = this.hermandadBO.findUsingExactTemplate(null, hermandadSearch, null);
				
				loggedUser.setIdHermandad(listHermandad.get(0).getId());
				loggedUser.setHermandadNombreCorto(listHermandad.get(0).getNombreCorto());
				loggedUser.setHermandadUrlWeb(listHermandad.get(0).getUrlWeb());
				loggedUser.setHermandadImage(listHermandad.get(0).getHermandadImage());
			}
	
			if (hibernateConfFileName != null) {
				AbstractBean.hibernateConfFileName = hibernateConfFileName;
				ConfHibernate.changeSessionFactory();
			}
		} catch (DaoException e){
			navigateString = editPageHermandadName;
		}
		

		return navigateString;
	}

	public void doClearDataBase() {
		AbstractBean.hibernateConfFileName = null;
		ConfHibernate.changeSessionFactory();
	}

	/**
	 * Selecciona una hermandad para acceder a la aplicación con un perfil de dicha hermandad
	 * @return
	 */
	public String doSelectHermandad() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String idHermandad = fc.getExternalContext().getRequestParameterMap()
				.get("idHermandad");
		hermandadSelected = this.hermandadBO.findByPrimaryKey(null,
				Long.valueOf(idHermandad));

		return null;
	}

	/**
	 * Limpia la hermandad seleccionada para acceder a la aplicación con un perfil de dicha hermandad
	 * @return
	 */
	public String clearHermandadSelected() {
		this.hermandadSelected = new HermandadVO();

		return null;
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
		this.loggedUser.setProfileImagePath(null);
		this.loggedUser.setUserImage(null);
	}
	
	/**
	 * Sube una imagen de perfil para una hermandad 
	 * @param event
	 */
	public void uploadHermandadFile(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("La imagen de perfil de la hermandad se ha actualizado correctamente.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		uploadedHermandadFile = event.getFile();
	}
	
	public void clearUploadedHermandadImage() {
		this.uploadedHermandadFile = null;
	}
	
	public void clearSavedHermandadImage() {
		this.hermandadSelected.setHermandadImage(null);
	}
	
	public String doResetDataBase() {
		return "listDataBaseManage";
	}

	// -------------------------- ADITIONAL FUNCTIONS --------------------------
	private void createListHermandad() {
		listHermandad = this.hermandadBO.findAll(null, null);
	}

	private Boolean allowedAccess() {
		Boolean allowedAccess = false;

		if (loggedUser.getIdUserType() < 0) {
			allowedAccess = true;
		} else if (loggedUser.getFechaBajaHermandad() == null
				&& loggedUser.getFechaUltimoPagoHermandad() != null) {
			Date todayDate = new Date();
			Date nextPaymentDate = (Date) loggedUser
					.getFechaUltimoPagoHermandad().clone();
			Calendar nextPayment = new GregorianCalendar();
			nextPayment.setTime(nextPaymentDate);
			nextPayment.add(Calendar.YEAR, 1);

			if (nextPayment.getTime().compareTo(todayDate) <= 0) {
				allowedAccess = true;
			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"La hermandad está pendiente de pago, para más información consulte con su administrador",
										""));
			}
		} else if (loggedUser.getFechaBajaHermandad() != null) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Esta hermandad ha sido dada de baja, para más información consulte con su hermandad",
									""));
		}

		return allowedAccess;
	}

	/**
	 * Crea el mapa de permisos para el usuario logado
	 */
	public void createMapPermissions() {
		mapUserBeanPermission = new HashMap<String, UrlPermissionVO>();

		// 1.- Buscamos todos los permisos para el rol seleccionado
		RolUrlPermissionViewVO rolUrlPermissionSearch = new RolUrlPermissionViewVO();
		rolUrlPermissionSearch.setIdUserType(this.loggedUser.getIdUserType());
		List<RolUrlPermissionViewVO> listRolUrlPermission = this.rolUrlPermissionViewBO
				.findUsingTemplate(null, rolUrlPermissionSearch);

		mapUserPermission = new HashMap<String, UrlPermissionVO>();
		UrlPermissionVO urlPermission;
		for (RolUrlPermissionViewVO rolUrlPermission : listRolUrlPermission) {
			urlPermission = new UrlPermissionVO();
			urlPermission.setId(rolUrlPermission.getIdUrlPermission());
			urlPermission.setName(rolUrlPermission.getName());
			urlPermission.setUrl(rolUrlPermission.getUrl());
			urlPermission.setBeanName(rolUrlPermission.getBeanName());

			mapUserPermission.put(urlPermission.getUrl(), urlPermission);
			mapUserBeanPermission.put(urlPermission.getBeanName(),
					urlPermission);
		}

		// Asignamos los permisos al usuario
		this.loggedUser.setMapUserPermission(mapUserPermission);
	}

	/**
	 * Crea la estructura del menú a mostrar para el usuario en concreto
	 */
	private void createMenuModel() {
		this.menuModel = new DefaultMenuModel();

		List<MenuItemVO> listMenuStructure = this.menuItemBO.findAll(null);
		if (listMenuStructure.size() > 0) {
			for (MenuItemVO elem : listMenuStructure) {
				MenuElement menuItem = construyeSubMenu(elem, 1);
				if (menuItem instanceof DefaultMenuItem) {
					menuModel.addElement((DefaultMenuItem) menuItem);
				} else if (menuItem instanceof DefaultSubMenu) {
					DefaultSubMenu nodoPadre = (DefaultSubMenu) menuItem;
					nodoPadre.setStyleClass("subMenu" + 0);
					if (nodoPadre.getElementsCount() > 0) {
						menuModel.addElement(nodoPadre);
					}
				}
			}
		}
	}

	/**
	 * Funcion recursiva que construye el arbol de hijos de los elementos del
	 * menu principal y lo devuelve para ser tratado por el padre
	 * 
	 * @param item
	 *            Elemento a tratar en cada momento
	 * @return MenuElement estructura con el arbol a partir del elemento que se
	 *         pasa como parámetro
	 */
	private MenuElement construyeSubMenu(MenuItemVO item, int profundidad) {
		// Buscamos todos los submenus que tengan como idPadre el id del
		// elemento de menu actual
		MenuItemVO menuItemSearch = new MenuItemVO();
		menuItemSearch.setIdParent(item.getId());
		MenuElement result = null;

		if (item.getHasChildren()) {
			List<MenuItemVO> listChilds = this.menuItemBO
					.findUsingTemplate(menuItemSearch);
			DefaultSubMenu menuContent = new DefaultSubMenu(item.getItemName());
			for (MenuItemVO child : listChilds) {
				MenuElement menuItem = construyeSubMenu(child, profundidad + 1);
				if (menuItem instanceof DefaultMenuItem) {
					menuContent.addElement((DefaultMenuItem) menuItem);
				} else if (menuItem instanceof DefaultSubMenu) {
					DefaultSubMenu nodoPadre = (DefaultSubMenu) menuItem;
					nodoPadre.setStyleClass("subMenu" + profundidad);
					if (nodoPadre.getElementsCount() > 0) {
						menuContent.addElement(nodoPadre);
					}
					// menuContent.addElement((DefaultSubMenu)menuItem);
				}
			}

			result = menuContent;
		} else if (loggedUser.getIdUserType() < 0
				|| mapUserBeanPermission.containsKey(item.getBeanName())) {
			DefaultMenuItem menuItem = new DefaultMenuItem(item.getItemName());

			String action = "#{" + item.getBeanName() + ".doNavigate}";
			menuItem.setCommand(action);
			result = menuItem;
		}

		return result;
	}

	/**
	 * Función antigua que creaba el menu sin recursividad
	 */
	private void createMenuModelOld() {
		this.menuModel = new DefaultMenuModel();
		Map<String, UrlPermissionVO> mapUserBeanPermission = new HashMap<String, UrlPermissionVO>();

		// 1.- Find all the permissions for the user rol
		RolUrlPermissionViewVO rolUrlPermissionSearch = new RolUrlPermissionViewVO();
		rolUrlPermissionSearch.setIdUserType(this.loggedUser.getIdUserType());
		List<RolUrlPermissionViewVO> listRolUrlPermission = this.rolUrlPermissionViewBO
				.findUsingTemplate(null, rolUrlPermissionSearch);

		mapUserPermission = new HashMap<String, UrlPermissionVO>();
		UrlPermissionVO urlPermission;
		for (RolUrlPermissionViewVO rolUrlPermission : listRolUrlPermission) {
			urlPermission = new UrlPermissionVO();
			urlPermission.setId(rolUrlPermission.getIdUrlPermission());
			urlPermission.setName(rolUrlPermission.getName());
			urlPermission.setUrl(rolUrlPermission.getUrl());
			urlPermission.setBeanName(rolUrlPermission.getBeanName());

			mapUserPermission.put(urlPermission.getUrl(), urlPermission);
			mapUserBeanPermission.put(urlPermission.getBeanName(),
					urlPermission);
		}

		// Set the permissions maps to the user
		this.loggedUser.setMapUserPermission(mapUserPermission);

		// 2.- Create the menu
		List<MenuItemVO> listMenuStructure = this.menuItemBO.findAll(null);
		if (listMenuStructure.size() > 0) {
			MenuItemVO menuItemSearch = new MenuItemVO();
			List<MenuItemVO> listSubMenuItems;

			for (MenuItemVO elem : listMenuStructure) {
				menuItemSearch.setIdParent(elem.getId());
				listSubMenuItems = this.menuItemBO
						.findUsingTemplate(menuItemSearch);

				if (listSubMenuItems.size() > 0) {
					DefaultSubMenu menuItem = new DefaultSubMenu(
							elem.getItemName());

					DefaultMenuItem submenuItem;

					for (MenuItemVO subMenu : listSubMenuItems) {
						// If the user is not a super admin and has permissions
						// in the bean that
						// manage the menu, add the menu item
						if (loggedUser.getIdUserType().compareTo(new Long(-1)) == 0
								|| mapUserBeanPermission.containsKey(subMenu
										.getBeanName())) {
							submenuItem = new DefaultMenuItem(
									subMenu.getItemName());

							String action = "#{" + subMenu.getBeanName()
									+ ".doNavigate}";
							submenuItem.setCommand(action);
							menuItem.addElement(submenuItem);
						}
					}

					if (menuItem.getElements().size() > 0) {
						this.menuModel.addElement(menuItem);
					}
				} else {
					// Create the menu empty
					DefaultMenuItem menuItem = new DefaultMenuItem(
							elem.getItemName());

					this.menuModel.addElement(menuItem);
				}
			}
		}
	}

	/**
	 * Save a file into the system
	 * 
	 * @throws IOException
	 */
	private void createFileIntoSystem(String fileName) throws IOException {
		// Copy the file into the path
		String filePath = uploadFolderPath + fileName;
		File dir = new File(uploadFolderPath);
		File file = new File(filePath);
		if (!dir.exists()) {
			dir.mkdir();
		}

		if (!file.exists()) {
			InputStream inputStream = null;
			OutputStream outputStream = null;
			ImageOutputStream ios = null;
			try {
				inputStream = uploadedFile.getInputstream();

				// write the inputStream to a FileOutputStream
				outputStream = new FileOutputStream(new File(filePath));

				// Set the quality compression for the image
				float quality = Float
						.parseFloat(this.getImageCompressQuality());

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
	 * 
	 * @param path
	 * @throws IOException
	 */
	private void deleteOldFile(String path) {
		File file = new File(path);
		file.delete();
	}

	// --------------------------- GETTERS / SETTERS ---------------------------
	public UserViewVO getUserView() {
		return userView;
	}

	public void setUserView(UserViewVO userView) {
		this.userView = userView;
	}

	public UserViewVO getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(UserViewVO loggedUser) {
		this.loggedUser = loggedUser;
	}

	public Boolean getIsUserLogged() {
		return this.isUserLogged;
	}

	public void setIsUserLogged(Boolean isUserLogged) {
		this.isUserLogged = isUserLogged;
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

	public static Map<String, UrlPermissionVO> getMapUserPermission() {
		return mapUserPermission;
	}

	public static void setMapUserPermission(
			Map<String, UrlPermissionVO> mapUserPermission) {
		LoginBean.mapUserPermission = mapUserPermission;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public MenuModel getMenuModel() {
		if (this.menuModel == null) {
			this.createMenuModel();
		}

		return this.menuModel;
	}

	public List<HermandadVO> getListHermandad() {
		if (this.listHermandad == null) {
			this.createListHermandad();
		}
		
		return listHermandad;
	}

	public void setListHermandad(List<HermandadVO> listHermandad) {
		this.listHermandad = listHermandad;
	}

	public HermandadVO getHermandadSelected() {
		return hermandadSelected;
	}

	public void setHermandadSelected(HermandadVO hermandadSelected) {
		this.hermandadSelected = hermandadSelected;
	}
	
	public String getCssFileUrl() {
		return cssFileUrl;
	}

	public void setCssFileUrl(String cssFileUrl) {
		this.cssFileUrl = cssFileUrl;
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

	public UploadedFile getUploadedHermandadFile() {
		return uploadedHermandadFile;
	}

	public void setUploadedHermandadFile(UploadedFile uploadedHermandadFile) {
		this.uploadedHermandadFile = uploadedHermandadFile;
	}

	public StreamedContent getEmbeddedHermandadImage() {
		return embeddedHermandadImage;
	}

	public void setEmbeddedHermandadImage(StreamedContent embeddedHermandadImage) {
		this.embeddedHermandadImage = embeddedHermandadImage;
	}

	public CroppedImage getCroppedHermandadImage() {
		return croppedHermandadImage;
	}

	public void setCroppedHermandadImage(CroppedImage croppedHermandadImage) {
		this.croppedHermandadImage = croppedHermandadImage;
	}

	/**
	 * Cargamos la imagen del usuario de la base de datos para mostrarla en la web
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
			
			if (this.loggedUser.getUserImage() != null && this.loggedUser.getUserImage().length > 0) {
				embeddedImage = new DefaultStreamedContent(
						new ByteArrayInputStream(this.loggedUser.getUserImage()),
						"image/png");
			}
		}

		return embeddedImage;
	}

	/**
	 * Cargamos la imagen que tenemos en el uploaded file del usuario
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
	
	/**
	 * Cargamos la imagen de la hermandad de la base de datos para mostrarla en la web
	 * @return
	 */
	public StreamedContent getImageHermandadInList() {
		FacesContext fc = FacesContext.getCurrentInstance();

		if (fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			embeddedImage = null;
			
			if (this.loggedUser.getHermandadImage() != null &&
					this.loggedUser.getHermandadImage().length > 0) {
				embeddedImage = new DefaultStreamedContent(
						new ByteArrayInputStream(this.loggedUser.getHermandadImage()),
						"image/png");
			}
		}

		return embeddedImage;
	}
	
	/**
	 * Cargamos la imagen que tenemos en el uploaded file de la hermandad
	 * @return
	 */
	public StreamedContent getImageHermandadInListUploaded() {
		FacesContext fc = FacesContext.getCurrentInstance();

		if (fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			embeddedImage = null;
			try {
				embeddedImage = new DefaultStreamedContent(
						uploadedHermandadFile.getInputstream(), "image/png");
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