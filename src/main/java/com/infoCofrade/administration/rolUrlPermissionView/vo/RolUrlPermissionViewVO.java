package com.infoCofrade.administration.rolUrlPermissionView.vo;


public class RolUrlPermissionViewVO implements Cloneable{
	private Long id;
	private Long idUserType;
	private Long idUrlPermission;
	private String name;
	private String url;
	private Long idMenuItemGroup;
	private String beanName;
	private String itemName;
	
	public RolUrlPermissionViewVO(){
		this.id = null;
		this.idUserType = null;
		this.idUrlPermission = null;
		this.name = null;
		this.url = null;
		this.idMenuItemGroup = null;
		this.beanName = null;
		this.itemName = null;
	}
	
	//----------------------- GETTERS / SETTERS -----------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdUserType() {
		return idUserType;
	}

	public void setIdUserType(Long idUserType) {
		this.idUserType = idUserType;
	}

	public Long getIdUrlPermission() {
		return idUrlPermission;
	}

	public void setIdUrlPermission(Long idUrlPermission) {
		this.idUrlPermission = idUrlPermission;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getIdMenuItemGroup() {
		return idMenuItemGroup;
	}

	public void setIdMenuItemGroup(Long idMenuItemGroup) {
		this.idMenuItemGroup = idMenuItemGroup;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Object clone()
    {
        RolUrlPermissionViewVO clone = new RolUrlPermissionViewVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getIdUserType() != null){
        	clone.setIdUserType(new Long(this.getIdUserType()));
        }
        if(this.getIdUrlPermission() != null){
        	clone.setIdUrlPermission(new Long(this.getIdUrlPermission()));
        }
        if(this.getName() != null && !this.getName().isEmpty()){
        	clone.setName(new String(this.getName()));
        }
        if(this.getUrl() != null && !this.getUrl().isEmpty()){
        	clone.setUrl(new String(this.getUrl()));
        }
        if(this.getIdMenuItemGroup() != null){
        	clone.setIdMenuItemGroup(new Long(this.getIdMenuItemGroup()));
        }
        if(this.getBeanName() != null && !this.getBeanName().isEmpty()){
        	clone.setBeanName(new String(this.getBeanName()));
        }
        if(this.getItemName() != null && !this.getItemName().isEmpty()){
        	clone.setItemName(new String(this.getItemName()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			RolUrlPermissionViewVO rolUrlPermissionView = (RolUrlPermissionViewVO) obj;
			if((rolUrlPermissionView.getId() != null && this.getId() != null &&
					rolUrlPermissionView.getId() == this.getId()) ||
					(rolUrlPermissionView.getIdUserType() != null && this.getIdUserType() != null &&
					rolUrlPermissionView.getIdUserType().compareTo(this.getIdUserType()) == 0 &&
					rolUrlPermissionView.getIdUrlPermission() != null && this.getIdUrlPermission() != null &&
					rolUrlPermissionView.getIdUrlPermission().compareTo(this.getIdUrlPermission()) == 0)){
				result = true;
			}
		}
		
		return result;
	}
}