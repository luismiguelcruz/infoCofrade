package com.infoCofrade.administration.cssProfile.vo;

import com.infoCofrade.administration.cssProfile.vo.CssProfileVO;


public class CssProfileVO implements Cloneable {
	private Long id;
	private String color;
	private String urlPath;
	
	public CssProfileVO(){
		this.id = null;
		this.color = null;
		this.urlPath = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	
	public String getStyleProfile(){
		StringBuilder result = new StringBuilder();
		
		result.append("display: block;");
		result.append("background-color:"+this.getColor()+";");
		result.append("border: 1px solid black;");
		result.append("width: 20px;");
		result.append("height: 20px;");
		
		return result.toString();
	}
	

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        CssProfileVO clone = new CssProfileVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getColor() != null && !this.getColor().isEmpty()){
        	clone.setColor(new String(this.getColor()));
        }
        if(this.getUrlPath() != null && !this.getUrlPath().isEmpty()){
        	clone.setUrlPath(new String(this.getUrlPath()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			CssProfileVO cssProfile = (CssProfileVO) obj;
			if((cssProfile.getId() == null && this.id == null) ||
					cssProfile.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
