package com.infoCofrade.administration.user.vo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.infoCofrade.administration.urlPermission.vo.UrlPermissionVO;

public class UserVO{
	private Long id;
	private String name;
	private String surname;
	private String dni;
	private String email;
	private Long idUserType;
	private String username;
	public String password;
	private String phone;
	private Date lastLogin;
	private Date initDate;
	private Date endDate;
	private Boolean isLogged;
	private Long idHermandad;
	private String profileImagePath;
	private byte[] userImage;
	private Long idCssProfile;
	
	/**
	 * Variables que contienen los datos del usuario
	 */
	private Map<String, UrlPermissionVO> mapUserPermission;
	private Date expiredSessionTime;
	private SessionFactory userSessionFactory;
	private String userType;
	
	public UserVO(){
		this.id = null;
		this.name = null;
		this.surname = null;
		this.dni = null;
		this.email = null;
		this.idUserType = null;
		this.username = null;
		this.password = null;
		this.phone = null;
		this.lastLogin = null;
		this.initDate = null;
		this.endDate = null;
		this.isLogged = null;
		this.idHermandad = null;
		this.profileImagePath = null;
		this.userImage = null;
		this.idCssProfile = null;
		
		this.mapUserPermission = null;
		this.expiredSessionTime = null;
		this.userSessionFactory = null;
		this.userType = null;
	}
	
	//----------------------- GETTERS / SETTERS --------------------------------
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdUserType() {
		return idUserType;
	}

	public void setIdUserType(Long idUserType) {
		this.idUserType = idUserType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;	
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsLogged() {
		return isLogged;
	}

	public void setIsLogged(Boolean isLogged) {
		this.isLogged = isLogged;
	}

	public Long getIdHermandad() {
		return idHermandad;
	}

	public void setIdHermandad(Long idHermandad) {
		this.idHermandad = idHermandad;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	public byte[] getUserImage() {
		return userImage;
	}

	public void setUserImage(byte[] userImage) {
		this.userImage = userImage;
	}

	public Long getIdCssProfile() {
		return idCssProfile;
	}

	public void setIdCssProfile(Long idCssProfile) {
		this.idCssProfile = idCssProfile;
	}
	

	// ************************** ADITIONAL FUNCTIONS *****************************
	public String getMd5Password(){
		return this.password;
	}
	
	public void setMd5Password(String password){
		try{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			this.setPassword(hash.toString(16));
		} catch (NoSuchAlgorithmException nsae) {

		}
	}

	public Map<String, UrlPermissionVO> getMapUserPermission() {
		return mapUserPermission;
	}

	public void setMapUserPermission(Map<String, UrlPermissionVO> mapUserPermission) {
		this.mapUserPermission = mapUserPermission;
	}

	public Date getExpiredSessionTime() {
		return expiredSessionTime;
	}

	public void setExpiredSessionTime(Date expiredSessionTime) {
		this.expiredSessionTime = expiredSessionTime;
	}
	
	public SessionFactory getUserSessionFactory() {
		return userSessionFactory;
	}

	public void setUserSessionFactory(SessionFactory userSessionFactory) {
		this.userSessionFactory = userSessionFactory;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getFileName() {
		String result = "";
		if (this.getProfileImagePath() != null && this.getProfileImagePath().length() > 0) {
			result = this.getProfileImagePath().substring(
					this.getProfileImagePath().lastIndexOf("/") + 1);
		}

		return result;
	}

	// ************************** EQUAL AND CLONE METHODS ***********************
	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			UserVO user = (UserVO) obj;
			if((user.getId() == null && this.id == null) ||
					user.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}