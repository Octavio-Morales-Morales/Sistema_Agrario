========================================================================
UNIVERSIDAD NACIONAL DE COSTA RICA
========================================================================

EXAMEN PRÁCTICO: Sistema de Gestión Agraria 
ESTUDIANTE: Octavio Morales 
CÉDULA: 604870104
PROFESOR: Juan Gamboa
FECHA DE ENTREGA: 19, Mayo, 2026

------------------------------------------------------------------------
1. DESCRIPCIÓN GENERAL DEL SISTEMA
------------------------------------------------------------------------
Esta aplicación es una solución de escritorio orientada a objetos diseñada
para automatizar el control operativo y administrativo de actividades agrícolas.
El sistema permite gestionar de manera integral el ciclo de vida de cuatro
entidades principales del negocio:
- Parcelas.
- Cultivos.
- Responsables.
- Labores Agrícolas.

------------------------------------------------------------------------
2. ARQUITECTURA Y PATRONES DE DISEÑO IMPLEMENTADOS
------------------------------------------------------------------------
El proyecto se estructuró bajo una arquitectura limpia en capas utilizando 
el patrón de diseño Data Access Object para independizar por completo 
la lógica de persistencia relacional de la capa de presentación visual (Swing).
- Paquete 'Objetos': Contiene los modelos del dominio y herencia polimórfica.
- Paquete 'Service': Centraliza las transacciones de canales SQL y la lógica
 de acceso a datos de las tablas.
- Paquete 'IG': Aloja las ventanas modulares y paneles dinámicos que controlan
  la experiencia del usuario.

------------------------------------------------------------------------
3. REQUISITOS TÉCNICOS DEL ENTORNO
------------------------------------------------------------------------
Para compilar y ejecutar correctamente la aplicación se requiere:
- Java Development Kit (JDK) 17 o superior.
- Apache Maven 3.8 o superior.
- Motor de Base de Datos: Oracle Database Free 23c (Ejecutado en contenedor Docker)
- IDE Recomendado: Apache NetBeans 17 o superior.

------------------------------------------------------------------------
4. INSTRUCCIONES DE MONTAJE Y RESTAURACIÓN DE LA BASE DE DATOS
------------------------------------------------------------------------
Antes de iniciar la aplicación, debe crear la estructura relacional en Oracle:
1. Conéctese a su instancia de Oracle Database mediante SQL Developer.
2. Abra y ejecute el archivo adjunto 'script_bd_sistema_agrario.sql' incluido 
   en la entrega.
3. El script creará de forma secuencial las tablas primarias (AA_PARCELAS, 
   AA_CULTIVOS, AA_RESPONSABLES) y la tabla dependiente (AA_LABORES_AGRICOLAS),
   aplicando restricciones de llaves primarias, foráneas y Checks de dominio.
4. Asegúrese de que el script ejecute la instrucción 'COMMIT;' al final.

------------------------------------------------------------------------
5. INSTRUCCIONES DE COMPILACIÓN Y EJECUCIÓN CON MAVEN
------------------------------------------------------------------------
La aplicación gestiona su ciclo de construcción mediante Maven. Siga estos 
pasos desde la terminal del sistema operativo:
1. Navegue por comandos hasta la carpeta raíz del proyecto (donde reside el 'pom.xml').
2. Limpie los binarios anteriores y compile las clases fuente con:
   clean compile
3. Inicie el entorno gráfico del programa ejecutando la meta de ejecución:
   clean javafx:run

------------------------------------------------------------------------
6. MANUAL DE USUARIO: PASOS PARA LA EXPORTACIÓN DEL INFORME PLANO
------------------------------------------------------------------------
El sistema cuenta con un generador automático de informes planos estructurados 
en cumplimiento con el diseño formal del negocio:
1. En la barra de navegación lateral izquierda del menú de la aplicación, 
   haga clic sobre la pestaña "Ver Labores".
2. La interfaz gráfica invocará en segundo plano una consulta unificada de 
   acoplamiento (INNER JOIN) a la base de datos de Oracle para listar las 
   tareas registradas en la tabla masiva.
3. Presione el botón azul "Exportar Informe Plano (.txt)" ubicado en la esquina 
   inferior derecha del panel.
4. El programa desplegará una ventana interactiva del sistema (JFileChooser). 
   Seleccione la carpeta donde desea guardar el archivo (Escritorio, Documentos, etc.).
5. El sistema sugerirá el nombre 'informe_labores_agricolas.txt' de forma nativa. 
   Haga clic en el botón "Guardar".
6. Una ventana emergente le confirmará el éxito de la escritura física y la 
   ruta absoluta del fichero guardado en el disco duro.

------------------------------------------------------------------------
7. SCRIPTS DE PRUEBA ADICIONALES (OPCIONAL)
------------------------------------------------------------------------
Si desea precargar información de prueba directa en su base de datos para 
validar el renderizado gráfico inmediato de las labores relacionales, 
puede ejecutar las siguientes sentencias INSERT en su consola SQL:

INSERT INTO AA_PARCELAS VALUES ('P1', 'Parcela Norte', 'Cartago', 2500.00, 'Arcilloso', 'Activo');
INSERT INTO AA_CULTIVOS VALUES ('C1', 'Papa', 'Única', '10/05/2026', 'Anual', 120, NULL);
INSERT INTO AA_RESPONSABLES VALUES ('1', 'Luis Vargas', 'luis@mail.com', '8888-8888', 'Tecnico', NULL, 'Agronomía');
INSERT INTO AA_LABORES_AGRICOLAS VALUES ('L1', 'P1', 'C1', '1', '18/05/2026', 'Riego', 'Riego automatizado matutino', 15000.00);
COMMIT;
========================================================================