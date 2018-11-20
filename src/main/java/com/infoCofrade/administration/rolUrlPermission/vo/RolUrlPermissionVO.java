package com.infoCofrade.administration.rolUrlPermission.vo;


public class RolUrlPermissionVO implements Cloneable{
	private Long id;
	private Long idUserType;
	private Long idUrlPermission;
	
	public RolUrlPermissionVO(){
		this.id = null;
		this.idUserType = null;
		this.idUrlPermission = null;
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

	
	public Object clone()
    {
        RolUrlPermissionVO clone = new RolUrlPermissionVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getIdUserType() != null){
        	clone.setIdUserType(new Long(this.getIdUserType()));
        }
        if(this.getIdUrlPermission() != null){
        	clone.setIdUrlPermission(new Long(this.getIdUrlPermission()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			RolUrlPermissionVO rolUrlPermission = (RolUrlPermissionVO) obj;
			if((rolUrlPermission.getId() != null && this.getId() != null &&
					rolUrlPermission.getId() == this.getId()) ||
					(rolUrlPermission.getIdUserType() != null && this.getIdUserType() != null &&
					rolUrlPermission.getIdUserType().compareTo(this.getIdUserType()) == 0 &&
					rolUrlPermission.getIdUrlPermission() != null && this.getIdUrlPermission() != null &&
					rolUrlPermission.getIdUrlPermission().compareTo(this.getIdUrlPermission()) == 0)){
				result = true;
			}
		}
		
		return result;
	}
}