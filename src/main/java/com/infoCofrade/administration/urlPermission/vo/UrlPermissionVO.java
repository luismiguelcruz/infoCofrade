package com.infoCofrade.administration.urlPermission.vo;


public class UrlPermissionVO implements Cloneable{
	private Long id;
	private String name;
	private String url;
	private Long idMenuItemGroup;
	private String beanName;
	
	public UrlPermissionVO(){
		this.id = null;
		this.name = null;
		this.url = null;
		this.idMenuItemGroup = null;
		this.beanName = null;
	}
	
	//----------------------- GETTERS / SETTERS -----------------------
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

	
	public Object clone()
    {
        UrlPermissionVO clone = new UrlPermissionVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
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
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			UrlPermissionVO urlPermission = (UrlPermissionVO) obj;
			if((urlPermission.getId() != null && this.getId() != null &&
					urlPermission.getId() == this.getId()) ||
					(urlPermission.getUrl() != null && this.getUrl() != null &&
					urlPermission.getUrl().equalsIgnoreCase(this.getUrl()))){
				result = true;
			}
		}
		
		return result;
	}
}