package com.infoCofrade.common.converter.tesoreria;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.tesoreria.tipoActividad.TipoActividadBean;
import com.infoCofrade.tesoreria.tipoActividad.bo.TipoActividadBO;
import com.infoCofrade.tesoreria.tipoActividad.vo.TipoActividadVO;

@ManagedBean(name="tipoActividadConverter")
public class TipoActividadConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			Long idTipoActividad = Long.valueOf(value.toString());
			TipoActividadVO tipoActividad;
			
			TipoActividadBean tipoActividadBean = (TipoActividadBean) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "tipoActividadBean");
			TipoActividadBO tipoActividadBO = tipoActividadBean.getTipoActividadBO();

			tipoActividad = tipoActividadBO.findByPrimaryKey(null, idTipoActividad);
			if(tipoActividad != null){
				result.append(tipoActividad.getNombre());
			}
		}

		return result.toString();
	}
}