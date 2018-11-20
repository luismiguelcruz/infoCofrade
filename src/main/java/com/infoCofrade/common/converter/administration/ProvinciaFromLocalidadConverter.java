package com.infoCofrade.common.converter.administration;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.administration.localidad.LocalidadBean;
import com.infoCofrade.administration.localidad.bo.LocalidadBO;
import com.infoCofrade.administration.localidad.vo.LocalidadVO;
import com.infoCofrade.administration.provincia.ProvinciaBean;
import com.infoCofrade.administration.provincia.bo.ProvinciaBO;
import com.infoCofrade.administration.provincia.vo.ProvinciaVO;

@ManagedBean(name="provinciaFromLocalidadConverter")
public class ProvinciaFromLocalidadConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	/**
	 * Devuelve la provincia asociada al identificador de provincia seleccionado
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			Long idLocalidad = Long.valueOf(value.toString());
			LocalidadVO localidad;
			ProvinciaVO provincia;
			
			LocalidadBean localidadBean = (LocalidadBean) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "localidadBean");
			LocalidadBO localidadBO = localidadBean.getLocalidadBO();

			localidad = localidadBO.findByPrimaryKey(null, idLocalidad);
			if(localidad != null){
				ProvinciaBean provinciaBean = (ProvinciaBean) FacesContext
						.getCurrentInstance()
						.getELContext()
						.getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "provinciaBean");
				ProvinciaBO provinciaBO = provinciaBean.getProvinciaBO();

				provincia = provinciaBO.findByPrimaryKey(null, localidad.getIdProvincia());
				if(provincia != null){
					result.append(provincia.getProvincia());
				}
			}
		}

		return result.toString();
	}
}