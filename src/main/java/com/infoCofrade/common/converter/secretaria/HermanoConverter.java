package com.infoCofrade.common.converter.secretaria;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.secretaria.hermano.HermanoBean;
import com.infoCofrade.secretaria.hermano.bo.HermanoBO;
import com.infoCofrade.secretaria.hermano.vo.HermanoVO;

@ManagedBean(name="hermanoConverter")
public class HermanoConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	/**
	 * Devuelve la hermano asociada al identificador de hermano seleccionado
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			Long idHermano = Long.valueOf(value.toString());
			HermanoVO hermano;
			
			HermanoBean hermanoBean = (HermanoBean) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "hermanoBean");
			HermanoBO hermanoBO = hermanoBean.getHermanoBO();

			hermano = hermanoBO.findByPrimaryKey(null, idHermano);
			if(hermano != null){
				result.append(hermano.getNombre()+" "+hermano.getApellidos());
			}
		}

		return result.toString();
	}
}