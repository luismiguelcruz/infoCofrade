package com.infoCofrade.tesoreria.cuenta.vo;

public class LineaCuentaVO {
	public String nombre;
	public Boolean gasto;
	public Boolean ingreso;
	public Double saldo;
	public Long idCuenta;
	public Long idGasto;
	public Long idIngreso;
	public Long idTipoActividad;

	public LineaCuentaVO() {
		this.nombre = null;
		this.gasto = null;
		this.ingreso = null;
		this.saldo = null;
		this.idCuenta = null;
		this.idGasto = null;
		this.idIngreso = null;
		this.idTipoActividad = null;
	}

	public LineaCuentaVO(String nombre, Boolean gasto, Boolean ingreso,
			Double saldo) {
		this.nombre = nombre;
		this.gasto = gasto;
		this.ingreso = ingreso;
		this.saldo = saldo;
		this.idCuenta = null;
		this.idGasto = null;
		this.idIngreso = null;
		this.idTipoActividad = null;
	}

	// -------------------- GETTERS / SETTERS -------------------
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getGasto() {
		return gasto;
	}

	public void setGasto(Boolean gasto) {
		this.gasto = gasto;
	}

	public Boolean getIngreso() {
		return ingreso;
	}

	public void setIngreso(Boolean ingreso) {
		this.ingreso = ingreso;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public Long getIdGasto() {
		return idGasto;
	}

	public void setIdGasto(Long idGasto) {
		this.idGasto = idGasto;
	}

	public Long getIdIngreso() {
		return idIngreso;
	}

	public void setIdIngreso(Long idIngreso) {
		this.idIngreso = idIngreso;
	}

	public Long getIdTipoActividad() {
		return idTipoActividad;
	}

	public void setIdTipoActividad(Long idTipoActividad) {
		this.idTipoActividad = idTipoActividad;
	}
	

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone() {
		LineaCuentaVO clone = new LineaCuentaVO();

		if (this.getNombre() != null && !this.getNombre().isEmpty()) {
			clone.setNombre(new String(this.getNombre()));
		}
		if (this.getGasto() != null) {
			clone.setGasto(new Boolean(this.getGasto()));
		}
		if (this.getIngreso() != null) {
			clone.setIngreso(new Boolean(this.getIngreso()));
		}
		if (this.getSaldo() != null) {
			clone.setSaldo(new Double(this.getSaldo()));
		}
		if (this.getIdCuenta() != null){
			clone.setIdCuenta(new Long(this.getIdCuenta()));
		}
		if (this.getIdGasto() != null) {
			clone.setIdGasto(new Long(this.getIdGasto()));
		}
		if (this.getIdIngreso() != null) {
			clone.setIdIngreso(new Long(this.getIdIngreso()));
		}
		if (this.getIdTipoActividad() != null) {
			clone.setIdTipoActividad(new Long(this.getIdTipoActividad()));
		}

		return clone;
	}
}
