package com.infoCofrade.common.bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServlet;

import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateException;
import net.sf.jooreports.templates.DocumentTemplateFactory;

import org.imgscalr.Scalr;
import org.odftoolkit.odfdom.converter.pdf.PdfConverter;
import org.odftoolkit.odfdom.converter.pdf.PdfOptions;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.localidad.bo.LocalidadBO;
import com.infoCofrade.administration.localidad.vo.LocalidadVO;
import com.infoCofrade.administration.login.LoginBean;
import com.infoCofrade.common.hibernate.ConfHibernate;

@Named
@Scope("session")
/**
 * @author Luis Miguel Cruz
 */
public abstract class AbstractBean extends HttpServlet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private LocalidadBO localidadBO;

	/**
	 * Contains the global message to show in the Page
	 */
	private String message;

	/** Select the item to delete from a list */
	protected Long selectedDeleteItemId;

	/**
	 * Manage the file path of a file that we have to download
	 */
	protected String downloadFilePath = null;

	public static String hibernateConfFileName;

	public AbstractBean() {
	}

	public void init() {
		this.selectedDeleteItemId = null;
	}

	public void redirect(String url) {
		getServletContext().getRequestDispatcher(url);
	}

	abstract public String doNavigate();
	
	public static Boolean preventSqlInjection(StringBuilder sqlBuilder){
		Boolean sqlInjectionFounded = false;
		String sql = sqlBuilder.toString();
		
		if(sql != null &&
			(sql.toUpperCase().contains("--") ||
			 sql.toUpperCase().contains("/*") ||
			 sql.toUpperCase().contains("*/") ||
			 sql.toUpperCase().contains("INSERT") ||
			 sql.toUpperCase().contains("UPDATE") || 
			 sql.toUpperCase().contains("DELETE") || 
			 sql.toUpperCase().contains("DROP") ||
			 sql.toUpperCase().contains("COMMIT"))){
			sqlInjectionFounded = true;
		}
		
		return sqlInjectionFounded;
	}

	// --------------------- GENERIC FUNCTIONS ---------------------
	/**
	 * Download a selected file from the server
	 * 
	 * @return
	 */
	public StreamedContent getDownloadFile() {
		StreamedContent result = null;

		if (this.downloadFilePath != null && !this.downloadFilePath.isEmpty()) {
			InputStream stream;
			try {
				stream = new FileInputStream(this.downloadFilePath);

				String mimeType = FacesContext.getCurrentInstance()
						.getExternalContext().getMimeType(downloadFilePath);

				if (mimeType == null || mimeType.isEmpty()) {
					result = new DefaultStreamedContent(stream);
				} else {
					result = new DefaultStreamedContent(stream, mimeType,
							this.downloadFilePath
									.substring(this.downloadFilePath
											.lastIndexOf("/") + 10));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error: ", "Failure reading image file"));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
							"Failure reading image file"));
		}

		this.downloadFilePath = null;

		return result;
	}
	
	/**
	 * Descarga un fichero pdf generado a partir de una plantilla ODT
	 * @return
	 */
	public StreamedContent getDownloadReportFile(Map<Object, Object> data, String templatePath, String outputFileName){
		StreamedContent result = null;
		DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
		DocumentTemplate template;
		String mimeType = "application/pdf";
		
		try {
			template = documentTemplateFactory.getTemplate(new File(templatePath));
			
			// 1) Creamos el fichero de salida con formato ODT y lo metemos en un ByteArrayOutputStream temporal
			ByteArrayOutputStream baosOdt = new ByteArrayOutputStream();
			template.createDocument(data, baosOdt);
			
			// 2) Cargamos el ODT de salida en un ODFDOM OdfTextDocument 
			ByteArrayInputStream in = new ByteArrayInputStream(baosOdt.toByteArray());
			OdfTextDocument document = OdfTextDocument.loadDocument(in);

			// 3) Seteamos las opciones del PDF
			PdfOptions options = PdfOptions.create();

			// 4) Convertimos el OdfTextDocument a PDF a través de IText y lo metemos en otro ByteArrayOutputStream
			ByteArrayOutputStream baosPdf = new ByteArrayOutputStream();
			PdfConverter.getInstance().convert(document, baosPdf, options);
			
			// 5) Modificamos el ByteArrayOutputStream para convertirlo en un ByteArrayInputStream de entrada
			ByteArrayInputStream inputStream = new ByteArrayInputStream(baosPdf.toByteArray());
			
			// 6) Creamos el StreamedContent a partir del ByteArrayInputStream de entrada
			result = new DefaultStreamedContent(inputStream, mimeType, outputFileName+".pdf");
		} catch (IOException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"ERROR DE INFORME: ",
							"Error generando el informe, la plantilla del informe no está bien formada"));
		} catch (DocumentTemplateException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"ERROR DE INFORME: ",
							"Error generando el informe, no se ha creado el objeto a través de la plantilla"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"ERROR DE INFORME: ",
							"Error generando el informe, no se ha cargado el fichero OdfTextDocument"));
		}
		
		return result;
	}

	/**
	 * Obtiene una lista de localidades cuyo nombre contenga la cadena que
	 * recibe como parámetro
	 * 
	 * @param query
	 * @return
	 */
	public List<LocalidadVO> doAutoComplete(String query) {
		String filterValue = (String) UIComponent
				.getCurrentComponent(FacesContext.getCurrentInstance())
				.getAttributes().get("filter");

		List<LocalidadVO> listResults = new LinkedList<LocalidadVO>();
		LocalidadVO localidadSearch = new LocalidadVO();

		if (filterValue != null) {
			Field field;
			try {
				// Setteamos el campo correspondiente de la clase con el valor
				// del filtro de busqueda
				field = localidadSearch.getClass()
						.getDeclaredField(filterValue);
				field.setAccessible(true);

				if (Constants.RESULT_TYPE_DECIMAL.equalsIgnoreCase(field
						.getType().getName())) {
					field.set(localidadSearch, Double.valueOf(query.trim()));
				} else if (Constants.RESULT_TYPE_LONG.equalsIgnoreCase(field
						.getType().getName())) {
					field.set(localidadSearch, Long.valueOf(query.trim()));
				} else if (Constants.RESULT_TYPE_INTEGER.equalsIgnoreCase(field
						.getType().getName())) {
					field.set(localidadSearch, Integer.valueOf(query.trim()));
				} else {
					field.set(localidadSearch, query);
				}

				List<LocalidadVO> listLocalidadesAux = this.localidadBO
						.findAutoComplete(null, localidadSearch,
								"localidad ASC");

				for (int i = 0; i < listLocalidadesAux.size() && i < 10; i++) {
					listResults.add(listLocalidadesAux.get(i));
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return listResults;
	}

	protected byte[] compressImage(UploadedFile uploadedFileImage, Integer witdh, Integer heigth) throws IOException {
		byte[] scaledAndCompressedImage = null;
		
		// Metemos la imagen subida en un array de bytes
		byte[] loadedImage = uploadedFileImage.getContents();

		// Set the quality compression for the image
		float quality = Float
				.parseFloat(this.getImageCompressQuality());
		
		// Escale the image
		InputStream inputStream = new ByteArrayInputStream(loadedImage); 
		
		// create a BufferedImage as the result of decoding the supplied
		// InputStream
		BufferedImage image = ImageIO.read(inputStream);
		
		BufferedImage scaledImage = Scalr.resize(image,
				Scalr.Method.SPEED, Scalr.Mode.AUTOMATIC, witdh, heigth,
				Scalr.OP_ANTIALIAS);

		// get all image writers for JPG format
		Iterator<ImageWriter> writers = ImageIO
				.getImageWritersByFormatName("jpg");

		if (!writers.hasNext())
			throw new IllegalStateException("No writers found");

		ImageWriter writer = (ImageWriter) writers.next();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageOutputStream ios = ImageIO
				.createImageOutputStream(baos);
		writer.setOutput(ios);

		// Comprimir a la calidad seleccionada en el fichero de properties
		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality);
		
		// appends a complete image stream containing a single image and
		// associated stream and image metadata and thumbnails to the
		// output
		writer.write(null, new IIOImage(scaledImage, null, null), param);
		
		ImageIO.write(scaledImage, "jpg", baos);
		baos.flush();
		scaledAndCompressedImage = baos.toByteArray();
		baos.close();
		
		return scaledAndCompressedImage;
	}
	
	protected void changeDataBaseSchema(){
		if (hibernateConfFileName != null) {
			hibernateConfFileName = null;
			ConfHibernate.changeSessionFactory();
		}
	}
	
	protected void restoreDataBaseSchema(){
		LoginBean loginBean = (LoginBean)this.getBean("loginBean");
		
		if (loginBean.getLoggedUser() != null
				&& loginBean.getLoggedUser().getIdUserType().compareTo(new Long(-1)) == 0
				&& loginBean.getHermandadSelected() != null
				&& loginBean.getHermandadSelected().getDataSourceName() != null) {
			AbstractBean.hibernateConfFileName = loginBean.getHermandadSelected()
					.getDataSourceName() + ".cfg.xml";
			ConfHibernate.changeSessionFactory();
		}
	}

	// -------------------- GETTERS / SETTERS --------------------
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msg");

		this.message = bundle.getString(message);
	}

	/**
	 * Return the date format to show
	 * 
	 * @return String date format
	 */
	public String getDateFormat() {
		return Constants.DATE_FORMAT;
	}

	/**
	 * Return the empty message to show if we not find any element
	 * 
	 * @return String message
	 */
	public String getEmptyMessage() {
		return Constants.EMPTY_MESSAGE;
	}

	/**
	 * Return the date format with time to show
	 * 
	 * @return String date time format
	 */
	public String getDateTimeFormat() {
		return Constants.DATE_TIME_FORMAT;
	}

	/**
	 * Return the time format to show
	 * 
	 * @return String time format
	 */
	public String getTimeFormat() {
		return Constants.TIME_FORMAT;
	}

	/**
	 * Return the day of month format to show
	 * 
	 * @return String time format
	 */
	public String getDayOfMonthFormat() {
		return Constants.DAY_OF_MONTH_FORMAT;
	}

	/**
	 * Devuelve el índice de compresión de las imagenes que se suben al proyecto
	 * 
	 * @return
	 */
	public String getImageCompressQuality() {
		return Constants.IMAGE_COMPRESS_QUALITY;
	}

	public Object getBean(String beanName) {
		return FacesContext
				.getCurrentInstance()
				.getELContext()
				.getELResolver()
				.getValue(FacesContext.getCurrentInstance().getELContext(),
						null, beanName);
	}

	public Long getSelectedDeleteItemId() {
		return selectedDeleteItemId;
	}

	public void setSelectedDeleteItemId(Long selectedDeleteItemId) {
		this.selectedDeleteItemId = selectedDeleteItemId;
	}

	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}

	public Date getToday() {
		return new Date();
	}
}