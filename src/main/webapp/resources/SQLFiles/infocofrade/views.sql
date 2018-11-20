ALTER TABLE infoCofrade.USER MODIFY COLUMN `userImage` BLOB;
ALTER TABLE infoCofrade.HERMANDAD MODIFY COLUMN `hermandadImage` BLOB;
ALTER TABLE infoCofrade.NOTICIA MODIFY COLUMN `imagenNoticia` BLOB;

DROP TABLE infoCofrade.`ROL_URL_PERMISSION_VIEW`;
DROP VIEW infoCofrade.`ROL_URL_PERMISSION_VIEW`;
CREATE OR REPLACE VIEW infoCofrade.`ROL_URL_PERMISSION_VIEW` AS 
 SELECT rolurlpermission.id, rolurlpermission.`idUserType`, 
		rolurlpermission.`idUrlPermission`, urlpermission.name, urlpermission.url,
		urlpermission.`idMenuItemGroup`, urlpermission.`beanName`, menuitem.`itemName`
   FROM infoCofrade.`ROL_URL_PERMISSION` rolurlpermission, infoCofrade.`URL_PERMISSION` urlpermission
			LEFT JOIN infoCofrade.`MENU_ITEM` menuitem ON urlpermission.`idMenuItemGroup` = menuitem.id
  WHERE rolurlpermission.`idUrlPermission` = urlpermission.id;
  
DROP TABLE infoCofrade.USER_VIEW;
DROP VIEW infoCofrade.USER_VIEW;
CREATE OR REPLACE VIEW infoCofrade.USER_VIEW AS
 SELECT user.*, cssProfile.`urlPath` as `urlCssProfilePath`, 
   hermandad.`fechaUltimoPago` as `fechaUltimoPagoHermandad`, 
   hermandad.`fechaBaja` as `fechaBajaHermandad`, hermandad.`dataSourceName`,
   hermandad.`nombreCorto` as `hermandadNombreCorto`, hermandad.`urlWeb` as `hermandadUrlWeb`,
   hermandad.`hermandadImage`
   FROM infoCofrade.`USER` user LEFT JOIN infoCofrade.`HERMANDAD` hermandad ON user.`idHermandad` = hermandad.id
   LEFT JOIN infoCofrade.`CSS_PROFILE` cssProfile ON user.`idCssProfile` = cssProfile.id;
   
DROP TABLE infoCofrade.CALLE_VIEW;
DROP VIEW infoCofrade.CALLE_VIEW;
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