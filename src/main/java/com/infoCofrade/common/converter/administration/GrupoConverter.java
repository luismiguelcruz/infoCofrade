package com.infoCofrade.common.converter.administration;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.administration.secretaria.grupo.GrupoBean;
import com.infoCofrade.administration.secretaria.grupo.bo.GrupoBO;
import com.infoCofrade.administration.secretaria.grupo.vo.GrupoVO;

@ManagedBean(name="grupoConverter")
public class GrupoConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	/**
	 * Devuelve la grupo asociada al identificador de grupo seleccionado
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			Long idGrupo = Long.valueOf(value.toString());
			GrupoVO grupo;
			
			GrupoBean grupoBean = (GrupoBean) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "grupoBean");
			GrupoBO grupoBO = grupoBean.getGrupoBO();

			grupo = grupoBO.findByPrimaryKey(null, idGrupo);
			if(grupo != null){
				result.append(grupo.getNombre());
			}
		}

		return result.toString();
	}
}