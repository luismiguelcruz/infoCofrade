package com.infoCofrade.common.bean;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class Constants {
	public static final String FILE_UPLOAD_PATH;
	
	public static final String EMPTY_MESSAGE;
	
	public static final String EXPIRED_SESSION_TIME_SECONDS;

	public static final String DATE_FORMAT;
	public static final String DATE_TIME_FORMAT;
	public static final String TIME_FORMAT;
	public static final String DAY_OF_MONTH_FORMAT;
	
	public static final String RESULT_TYPE_DECIMAL;
	public static final String RESULT_TYPE_LONG;
	public static final String RESULT_TYPE_INTEGER;
	public static final String RESULT_TYPE_STRING;
	public static final String RESULT_TYPE_FILE;
	
	public static final String DATA_SOURCE_PATH;
	
	public static String IMAGE_COMPRESS_QUALITY;
	
	public static final String LONGITUD_RESUMEN_NOTICIAS;
	
	public static final String URL_PERFIL_CSS_POR_DEFECTO;
	

	static {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "infoCofradeConfig");
		ResourceBundle staticProperties = context.getApplication()
				.getResourceBundle(context, "infoCofradeStaticConfig");

		FILE_UPLOAD_PATH = bundle.getString("uploadDir");
		
		EMPTY_MESSAGE = bundle.getString("emptyMessage");
		
		EXPIRED_SESSION_TIME_SECONDS = staticProperties.getString("expiredSessionTimeSeconds");
		
		DATE_FORMAT = bundle.getString("dateFormat");
		DATE_TIME_FORMAT = bundle.getString("dateTimeFormat");
		TIME_FORMAT = bundle.getString("timeFormat");
		DAY_OF_MONTH_FORMAT = bundle.getString("dayOfMonthFormat");
		
		RESULT_TYPE_DECIMAL = staticProperties.getString("decimal");
		RESULT_TYPE_LONG = staticProperties.getString("long");
		RESULT_TYPE_INTEGER = staticProperties.getString("integer");
		RESULT_TYPE_STRING = staticProperties.getString("string");
		RESULT_TYPE_FILE = staticProperties.getString("file");
		
		DATA_SOURCE_PATH = bundle.getString("dataSourcePath");
		
		IMAGE_COMPRESS_QUALITY = bundle.getString("imageCompressQuality");
		
		LONGITUD_RESUMEN_NOTICIAS = bundle.getString("longitudResumenNoticias");
		
		URL_PERFIL_CSS_POR_DEFECTO = bundle.getString("urlPerfilCssPorDefecto");
	}
}