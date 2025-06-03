# ETL_HL7_To_FHIRSERVER
ETL para transformar un Mensaje ORU^R01 HL7 recibido por Mirth Connet a Recurso FHIR en Spring Boot y endpoint a servidor FHIR

Objetivos del proyecto

- Capacidad para **procesar HL7v2 con motor de integración Mirth y transformación a JSON**.
    
- Transformación a FHIR con **Java + HAPI FHIR**.
    
- Validación y mapeo del JSON a recursos FHIR.
    
- Almacenamiento en un servidor real como **Aidbox**.
    
- Buen diseño técnico y conocimiento de **estándares e interoperabilidad**.

- Desarrollo de una API RESTful con Spring Boot.

# Tecnologías y herramientas utilizadas:
- Aidbox FHIR Server.
- Mirth Connect.
- HL7v2.5.
- FHIR R4.
- HAPI FHIR librería.
- Spring Boot.
- Docker.
  
# Diagrama de componentes y arquitectura a alto nivel del sistema:
![image](https://github.com/user-attachments/assets/78a06fea-f3c5-4b65-9cc7-5cac842971b3)

Explicación del Componentes:

	1) Entrada Hl7v2 en Mirth Connect:
	
- El canal recibe un ORU^R01 por FIleReader
- Utiliza un JavaScript transformer para parsear el mensaje y construir un JSON
- Envía el Json por Web Service
	
	2)  Aplicación en Spring Boot con HAPI FHIR:
	
- Se recibe el JSON desde Mirth.
- Crear instancias de FHIR con HAPI FHIR.
- Validar el recurso generado antes de enviar a Aidbox
- Si no es válido, se logea el error.
- Si la validación es exitosa IGenericClient para POST hacía Aidbox.
		
		3) Servidor Aidbox:
		
* Se utiliza como repositorio para mostrar los recursos creados.

Stack utilizado:

| Componente     | Herramienta           |
| -------------- | --------------------- |
| HL7v2          | Mirth Connect         |
| Transformación | Java + HAPI FHIR      |
| Validación     | HAPI `FhirValidator`  |
| Cliente FHIR   | HAPI `IGenericClient` |
| Servidor FHIR  | Aidbox Community      |
| IDE            | IntelliJ con Maven    |

**Ciclo ETL (Extract, Transform, Load):**

- **Extract (E):** Mirth Connect ingesta el archivo HL7v2.
- **Transform (T):** Mirth con JavaScript transformando a  HL7v2 a JSON, y luego envío a Spring Boot transformando JSON a recursos FHIR y validándolos.
- **Load (L):** Tu Spring Boot enviando los recursos FHIR a Aidbox.

**Guía de Configuración y Ejecución: Pasos claros para que cualquiera pueda levantar el proyecto localmente:**

- **Clonar el repositorio.**

- **Instalar Docker.**

- **Levantar Aidbox/Postgres (docker-compose up -d).**

- **Configurar Mirth Connect (importar el canal, configurar el directorio de entrada/salida).**

- **Compilar y ejecutar la aplicación Spring Boot.**

- **Deployar el canal y enviar mensaje.**
