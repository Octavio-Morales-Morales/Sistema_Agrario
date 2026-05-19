--------------------------------------------------------
-- Archivo creado  - lunes-mayo-18-2026   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table AA_CULTIVOS
--------------------------------------------------------

  CREATE TABLE "AA_CULTIVOS" 
   (	"CODIGO" VARCHAR2(20 BYTE), 
	"NOMBRE" VARCHAR2(100 BYTE), 
	"VARIEDAD" VARCHAR2(100 BYTE), 
	"FECHA_SIEMBRA" VARCHAR2(20 BYTE), 
	"TIPO_CULTIVO" VARCHAR2(30 BYTE), 
	"DURACION_CICLO_DIAS" NUMBER(*,0), 
	"ANIOS_PRODUCCION" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table AA_LABORES_AGRICOLAS
--------------------------------------------------------

  CREATE TABLE "AA_LABORES_AGRICOLAS" 
   (	"CODIGO" VARCHAR2(20 BYTE), 
	"CODIGO_PARCELA" VARCHAR2(20 BYTE), 
	"CODIGO_CULTIVO" VARCHAR2(20 BYTE), 
	"IDENTIFICACION_RESPONSABLE" VARCHAR2(30 BYTE), 
	"FECHA_REALIZACION" VARCHAR2(20 BYTE), 
	"TIPO_LABOR" VARCHAR2(50 BYTE), 
	"DESCRIPCION" VARCHAR2(250 BYTE), 
	"COSTO_ESTIMADO" NUMBER(10,2)
   ) ;
--------------------------------------------------------
--  DDL for Table AA_PARCELAS
--------------------------------------------------------

  CREATE TABLE "AA_PARCELAS" 
   (	"CODIGO" VARCHAR2(20 BYTE), 
	"NOMBRE" VARCHAR2(100 BYTE), 
	"UBICACION" VARCHAR2(150 BYTE), 
	"AREA" NUMBER(10,2), 
	"TIPO_SUELO" VARCHAR2(80 BYTE), 
	"ESTADO" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table AA_RESPONSABLES
--------------------------------------------------------

  CREATE TABLE "AA_RESPONSABLES" 
   (	"IDENTIFICACION" VARCHAR2(20 BYTE), 
	"NOMBRE_COMPLETO" VARCHAR2(150 BYTE), 
	"CORREO" VARCHAR2(120 BYTE), 
	"TELEFONO" VARCHAR2(30 BYTE), 
	"TIPO_RESPONSABLE" VARCHAR2(40 BYTE), 
	"NOMBRE_FINCA_ASOCIACION" VARCHAR2(150 BYTE), 
	"ESPECIALIDAD_TECNICA" VARCHAR2(120 BYTE)
   ) ;
--  Constraints y confirmación final para la base de datos
ALTER TABLE "AA_LABORES_AGRICOLAS" ADD FOREIGN KEY ("CODIGO_PARCELA") REFERENCES "AA_PARCELAS"("CODIGO");
ALTER TABLE "AA_LABORES_AGRICOLAS" ADD FOREIGN KEY ("CODIGO_CULTIVO") REFERENCES "AA_CULTIVOS"("CODIGO");
ALTER TABLE "AA_LABORES_AGRICOLAS" ADD FOREIGN KEY ("IDENTIFICACION_RESPONSABLE") REFERENCES "AA_RESPONSABLES"("IDENTIFICACION");

COMMIT;