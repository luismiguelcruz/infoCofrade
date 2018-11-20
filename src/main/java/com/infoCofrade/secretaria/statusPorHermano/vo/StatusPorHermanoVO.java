package com.infoCofrade.secretaria.statusPorHermano.vo;

public class StatusPorHermanoVO implements Cloneable {
	private Long id;
	private Long idStatus;
	private Long idHermano;
	
	public StatusPorHermanoVO(){
		this.id = null;
		this.idStatus = null;
		this.idHermano = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}

	public Long getIdHermano() {
		return idHermano;
	}

	public void setIdHermano(Long idHermano) {
		this.idHermano = idHermano;
	}

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        StatusPorHermanoVO clone = new StatusPorHermanoVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getIdStatus() != null){
        	clone.setIdStatus(new Long(this.getIdStatus()));
        }
        if(this.getIdHermano() != null){
        	clone.setIdHermano(new Long(this.getIdHermano()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			StatusPorHermanoVO statusPorHermano = (StatusPorHermanoVO) obj;
			if((statusPorHermano.getId() == null && this.id == null) ||
					statusPorHermano.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
