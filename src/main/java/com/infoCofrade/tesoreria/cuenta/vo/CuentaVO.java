package com.infoCofrade.tesoreria.cuenta.vo;

public class CuentaVO implements Cloneable {
	private Long id;
	private Long idCuentaPadre;
	private Integer numeroCuenta;
	private String nombre;
	private Long idTipoActividad;
	private Integer anyo;
	private Boolean esConceptoFijo;
	private Integer nivel;

	public CuentaVO() {
		this.id = null;
		this.idCuentaPadre = null;
		this.numeroCuenta = null;
		this.nombre = null;
		this.idTipoActividad = null;
		this.anyo = null;
		this.esConceptoFijo = null;
		this.nivel = null;
	}

	// -------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCuentaPadre() {
		return idCuentaPadre;
	}

	public void setIdCuentaPadre(Long idCuentaPadre) {
		this.idCuentaPadre = idCuentaPadre;
	}

	public Integer getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(Integer numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdTipoActividad() {
		return idTipoActividad;
	}

	public void setIdTipoActividad(Long idTipoActividad) {
		this.idTipoActividad = idTipoActividad;
	}

	public Integer getAnyo() {
		return anyo;
	}

	public void setAnyo(Integer anyo) {
		this.anyo = anyo;
	}

	public Boolean getEsConceptoFijo() {
		return esConceptoFijo;
	}

	public void setEsConceptoFijo(Boolean esConceptoFijo) {
		this.esConceptoFijo = esConceptoFijo;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone() {
		CuentaVO clone = new CuentaVO();

		if (this.getId() != null) {
			clone.setId(new Long(this.getId()));
		}
		if (this.getIdCuentaPadre() != null) {
			clone.setIdCuentaPadre(new Long(this.getIdCuentaPadre()));
		}
		if (this.getNumeroCuenta() != null) {
			clone.setNumeroCuenta(new Integer(this.getNumeroCuenta()));
		}
		if (this.getNombre() != null && !this.getNombre().isEmpty()) {
			clone.setNombre(new String(this.getNombre()));
		}
		if (this.getIdTipoActividad() != null) {
			clone.setIdTipoActividad(new Long(this.getIdTipoActividad()));
		}
		if (this.getAnyo() != null) {
			clone.setAnyo(new Integer(this.getAnyo()));
		}
		if (this.getEsConceptoFijo() != null) {
			clone.setEsConceptoFijo(new Boolean(this.getEsConceptoFijo()));
		}
		if (this.getNivel() != null){
			clone.setNivel(new Integer(this.getNivel()));
		}

		return clone;
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = false;

		if (obj != null) {
			CuentaVO cuenta = (CuentaVO) obj;
			if ((cuenta.getId() == null && this.id == null)
					|| cuenta.getId().compareTo(this.id) == 0) {
				result = true;
			}
		}

		return result;
	}
}