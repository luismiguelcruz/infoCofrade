package com.infoCofrade.common.converter.administration;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.administration.pais.PaisBean;
import com.infoCofrade.administration.pais.bo.PaisBO;
import com.infoCofrade.administration.pais.vo.PaisVO;

@ManagedBean(name="paisConverter")
public class PaisConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	/**
	 * Devuelve la pais asociada al identificador de pais seleccionado
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			Long idPais = Long.valueOf(value.toString());
			PaisVO pais;
			
			PaisBean paisBean = (PaisBean) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "paisBean");
			PaisBO paisBO = paisBean.getPaisBO();

			pais = paisBO.findByPrimaryKey(null, idPais);
			if(pais != null){
				result.append(pais.getNombre());
			}
		}

		return result.toString();
	}
}