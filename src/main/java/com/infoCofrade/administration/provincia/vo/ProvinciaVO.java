package com.infoCofrade.administration.provincia.vo;

import com.infoCofrade.administration.provincia.vo.ProvinciaVO;


public class ProvinciaVO implements Cloneable {
	private Long id;
	private String provincia;
	private String provinciaSeo;
	private String codigoProvincia;
	private Long idPais;
	
	public ProvinciaVO(){
		this.id = null;
		this.provincia = null;
		this.provinciaSeo = null;
		this.codigoProvincia = null;
		this.idPais = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getProvinciaSeo() {
		return provinciaSeo;
	}

	public void setProvinciaSeo(String provinciaSeo) {
		this.provinciaSeo = provinciaSeo;
	}

	public String getCodigoProvincia() {
		return codigoProvincia;
	}

	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}
	
	public Long getIdPais() {
		return idPais;
	}

	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        ProvinciaVO clone = new ProvinciaVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getProvincia() != null && !this.getProvincia().isEmpty()){
        	clone.setProvincia(new String(this.getProvincia()));
        }
        if(this.getProvinciaSeo() != null && !this.getProvinciaSeo().isEmpty()){
        	clone.setProvinciaSeo(new String(this.getProvinciaSeo()));
        }
        if(this.getCodigoProvincia() != null){
        	clone.setCodigoProvincia(new String(this.getCodigoProvincia()));
        }
        if(this.getIdPais() != null){
        	clone.setIdPais(new Long(this.getIdPais()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			ProvinciaVO provincia = (ProvinciaVO) obj;
			if((provincia.getId() == null && this.id == null) ||
					provincia.getId().compareTo(this.id) == 0 ||
					provincia.getCodigoProvincia().compareTo(this.getCodigoProvincia()) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
