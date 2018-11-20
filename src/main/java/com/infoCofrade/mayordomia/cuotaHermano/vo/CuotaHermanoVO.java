package com.infoCofrade.mayordomia.cuotaHermano.vo;

public class CuotaHermanoVO implements Cloneable {
	private Long id;
	private Double cuantia;
	private Integer edadMinima;
	private Integer edadMaxima;
	private Long idCuenta;
	
	public CuotaHermanoVO(){
		this.id = null;
		this.cuantia = null;
		this.edadMinima = null;
		this.edadMaxima = null;
		this.idCuenta = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCuantia() {
		return cuantia;
	}

	public void setCuantia(Double cuantia) {
		this.cuantia = cuantia;
	}

	public Integer getEdadMinima() {
		return edadMinima;
	}

	public void setEdadMinima(Integer edadMinima) {
		this.edadMinima = edadMinima;
	}

	public Integer getEdadMaxima() {
		return edadMaxima;
	}

	public void setEdadMaxima(Integer edadMaxima) {
		this.edadMaxima = edadMaxima;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}
	

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        CuotaHermanoVO clone = new CuotaHermanoVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getCuantia() != null){
        	clone.setCuantia(new Double(this.getCuantia()));
        }
        if(this.getEdadMinima() != null){
        	clone.setEdadMinima(new Integer(this.getEdadMinima()));
        }
        if(this.getEdadMaxima() != null){
        	clone.setEdadMaxima(new Integer(this.getEdadMaxima()));
        }
        if(this.getIdCuenta() != null){
        	clone.setIdCuenta(new Long(this.getIdCuenta()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			CuotaHermanoVO cuotaHermano = (CuotaHermanoVO) obj;
			if((cuotaHermano.getId() == null && this.id == null) ||
					cuotaHermano.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}