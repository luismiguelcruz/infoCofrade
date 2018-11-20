ALTER TABLE castilleja.NOTICIA MODIFY COLUMN `imagenNoticia` BLOB;
  
DROP TABLE castilleja.CALLE_VIEW;
CREATE OR REPLACE VIEW infoCofrade.CALLE_VIEW AS
  SELECT calle.*, localidad.localidad, localidad.`codigoPostal`, localidad.`idProvincia`, localidad.latitud,
  		 localidad.longitud, provincia.provincia, provincia.`idPais`, pais.nombre as pais,
  		 tipoVia.sigla as tipoVia
    FROM infoCofrade.PAIS as pais, infoCofrade.PROVINCIA as provincia,
    	 infoCofrade.LOCALIDAD as localidad, infoCofrade.CALLE as calle,
    	 infoCofrade.TIPO_VIA as tipoVia
   WHERE calle.`idLocalidad` = localidad.id
     AND localidad.`idProvincia` = provincia.id
     AND provincia.`idPais` = pais.id
     AND calle.`IdTipoVia` = tipoVia.id