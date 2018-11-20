package com.infoCofrade.tesoreria.ingreso.vo;

public class IngresoVO implements Cloneable {
	private Long id;
	private Long idCuenta;
	private Double cuantia;
	private String concepto;
	private Integer anyo;
	
	public IngresoVO(){
		this.id = null;
		this.idCuenta = null;
		this.cuantia = null;
		this.concepto = null;
		this.anyo = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public Double getCuantia() {
		return cuantia;
	}

	public void setCuantia(Double cuantia) {
		this.cuantia = cuantia;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Integer getAnyo() {
		return anyo;
	}

	public void setAnyo(Integer anyo) {
		this.anyo = anyo;
	}

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        IngresoVO clone = new IngresoVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getIdCuenta() != null){
        	clone.setIdCuenta(new Long(this.getIdCuenta()));
        }
        if(this.getCuantia() != null){
        	clone.setCuantia(new Double(this.getCuantia()));
        }
        if (this.getConcepto() != null && !this.getConcepto().isEmpty()) {
			clone.setConcepto(new String(this.getConcepto()));
		}
        if(this.getAnyo() != null){
        	clone.setAnyo(new Integer(this.getAnyo()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			IngresoVO ingreso = (IngresoVO) obj;
			if((ingreso.getId() == null && this.id == null) ||
					ingreso.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}