package com.infoCofrade.common.converter.administration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.common.bean.Constants;

@ManagedBean(name="nextPaymentConverter")
public class NextPaymentConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	/**
	 * Devuelve la fecha del siguiente pago a realizar para la hermandad
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
			DateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
			
			Date lastPayment;
			try {
				lastPayment = sdfInput.parse(value.toString());
				Calendar nextPayment = new GregorianCalendar();
				nextPayment.setTime(lastPayment);
				nextPayment.add(Calendar.YEAR, 1);
				
				result.append(sdf.format(nextPayment.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return result.toString();
	}
}