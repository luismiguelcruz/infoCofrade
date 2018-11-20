package com.infoCofrade.common.converter.tesoreria;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.tesoreria.cuenta.CuentaBean;
import com.infoCofrade.tesoreria.cuenta.bo.CuentaBO;
import com.infoCofrade.tesoreria.cuenta.vo.CuentaVO;

@ManagedBean(name="cuentaConverter")
public class CuentaConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			Long idCuenta = Long.valueOf(value.toString());
			CuentaVO cuenta;
			
			CuentaBean cuentaBean = (CuentaBean) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "cuentaBean");
			CuentaBO cuentaBO = cuentaBean.getCuentaBO();

			cuenta = cuentaBO.findByPrimaryKey(null, idCuenta);
			if(cuenta != null){
				result.append(cuenta.getNombre());
			}
		}

		return result.toString();
	}
}