package com.infoCofrade.mayordomia.facturaCuotaHermano.vo;

import java.text.DateFormatSymbols;

public class FacturaCuotaHermanoVO implements Cloneable {
	private Long id;
	private Long idHermano;
	private Long idCuotaHermano;
	private Integer mes;
	private Integer anyo;
	private Boolean pagada;
	
	// Campos rellenables no guardables en base de datos
	private String nombreMes;
	
	public FacturaCuotaHermanoVO(){
		this.id = null;
		this.idHermano = null;
		this.idCuotaHermano = null;
		this.mes = null;
		this.anyo = null;
		this.pagada = null;
		
		this.nombreMes = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdHermano() {
		return idHermano;
	}

	public void setIdHermano(Long idHermano) {
		this.idHermano = idHermano;
	}

	public Long getIdCuotaHermano() {
		return idCuotaHermano;
	}

	public void setIdCuotaHermano(Long idCuotaHermano) {
		this.idCuotaHermano = idCuotaHermano;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAnyo() {
		return anyo;
	}

	public void setAnyo(Integer anyo) {
		this.anyo = anyo;
	}

	public Boolean getPagada() {
		return pagada;
	}

	public void setPagada(Boolean pagada) {
		this.pagada = pagada;
	}
	
	//-------------- GETTERS / SETTERS ADICIONALES ---------------
	public void setNombreMes(String nombreMes) {
		this.nombreMes = nombreMes;
	}
	
	public String getNombreMes() {
		String month = null;
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    
	    String[] months = dfs.getMonths();
	    if (mes != null && mes >= 0 && mes <= 11 ) {
	        month = months[mes];
	    }
	    
	    return month;
	}

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        FacturaCuotaHermanoVO clone = new FacturaCuotaHermanoVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getIdHermano() != null){
        	clone.setIdHermano(new Long(this.getIdHermano()));
        }
        if(this.getIdCuotaHermano() != null){
        	clone.setIdCuotaHermano(new Long(this.getIdCuotaHermano()));
        }
        if(this.getMes() != null){
        	clone.setMes(new Integer(this.getMes()));
        }
        if(this.getAnyo() != null){
        	clone.setAnyo(new Integer(this.getAnyo()));
        }
        if(this.getPagada() != null){
        	clone.setPagada(new Boolean(this.getPagada()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			FacturaCuotaHermanoVO facturaCuotaHermano = (FacturaCuotaHermanoVO) obj;
			if((facturaCuotaHermano.getId() == null && this.id == null) ||
					facturaCuotaHermano.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}