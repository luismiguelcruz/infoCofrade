package com.infoCofrade.administration.userType.vo;


public class UserTypeVO{
	private Long id;
	private String type;
	
	public UserTypeVO(){
		this.id = null;
		this.type = null;
	}
	
	//----------------------- GETTERS / SETTERS --------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			UserTypeVO userType = (UserTypeVO) obj;
			if((userType.getId() == null && this.id == null) ||
					userType.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}