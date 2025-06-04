# ETL_HL7_To_FHIRSERVER

**ETL para transformar un mensaje HL7 v2 ORU^R01 recibido por Mirth Connect a un recurso FHIR y enviarlo a un servidor FHIR (Aidbox) mediante una API en Spring Boot con HAPI FHIR.**

---
## Valor Profesional
Este proyecto refleja mis competencias prácticas en interoperabilidad sanitaria: HL7v2, FHIR, transformación ETL, validación de datos clínicos, uso de herramientas reales (Mirth, Aidbox), y desarrollo backend con buenas prácticas en Java + Spring Boot.

## Objetivos del Proyecto

- Procesar mensajes **HL7v2** mediante **Mirth Connect**.
- Transformar HL7 a **JSON** con JavaScript.
- Convertir el JSON a **recursos FHIR** (`Patient`, `Observation`, etc.) en Java con **HAPI FHIR**.
- Validar los recursos generados.
- Enviar los recursos válidos al **servidor FHIR Aidbox**.
- Desarrollar una **API RESTful** para pruebas y control.
- Demostrar conocimientos técnicos de estándares clínicos e interoperabilidad.

---

## 🛠️ Tecnologías y Herramientas

| Componente        | Herramienta               |
|------------------|--------------------------|
| HL7v2             | Mirth Connect            |
| Transformación    | Java + HAPI FHIR         |
| Validación FHIR   | HAPI `FhirValidator`     |
| Cliente FHIR      | HAPI `IGenericClient`    |
| Servidor FHIR     | Aidbox Community Edition |
| Backend           | Spring Boot + Maven      |
| Contenedores      | Docker / Docker Compose  |
| IDE               | IntelliJ IDEA            |

---

## Ciclo ETL (Extract - Transform - Load)

[HL7 ORU^R01] → [Mirth Connect] → [JSON] → [Spring Boot + HAPI FHIR] → [FHIR Resource] → [Aidbox Server]





## Componentes Explicados

### 1️⃣ Mirth Connect

- Recibe mensaje HL7 ORU^R01 mediante `File Reader`.
- Transforma el HL7 a JSON mediante `JavaScript Transformer`.
- Envía el JSON a Spring Boot mediante HTTP Web Service.

### 2️⃣ Spring Boot + HAPI FHIR

- Recibe JSON y lo convierte en recursos FHIR (`Patient`, `Observation`, etc.).
- Valida el recurso con `FhirValidator`.
- Si el recurso es válido, lo envía a Aidbox usando `IGenericClient`.
- Si no es válido, logea el error.

### 3️⃣ Aidbox FHIR Server

- Recibe los recursos como POST en su API REST.
- Permite visualización y gestión de los datos clínicos en formato FHIR.

---
