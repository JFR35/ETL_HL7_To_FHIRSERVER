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


![image](https://github.com/user-attachments/assets/9cce6aca-f4b3-45a5-834f-18e3b306f635)



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

## Guía de Configuración y Ejecución

1. Clonar el repositorio
git clone https://github.com/tu_usuario/TL_HL7_To_FHIRSERVER.git
cd TL_HL7_To_FHIRSERVER

2. Instalar Docker
Si no tienes Docker, instálalo desde:
👉 https://docs.docker.com/get-docker/

3. Levantar Aidbox + PostgreSQL
Desde la raíz del proyecto, ejecuta:
docker-compose up -d
Esto levantará el servidor Aidbox y la base de datos PostgreSQL.

4. Importar canal en Mirth Connect
Accede a Mirth Connect desde tu launcher:

Inicia sesión y ve a Channels > Import Channel.

Selecciona el archivo que encontrarás en este repositorio:
./Canal_Mirth_Connect.xml

Luego de importar:

Descárgate el mensaje HL7 que encontrarás en este repositorio:
./ORU^R01.hl7

Ajusta la ruta del File Reader a la carpeta local donde colocarás los mensajes HL7, por ejemplo:
/ruta/absoluta/hl7-messages
(Asegúrate de que esa carpeta exista en tu sistema y tenga permisos de lectura.)

Guarda los cambios y deployar el canal.

5. Ejecutar la aplicación Spring Boot
Desde la carpeta de la app:

cd spring-app
./mvnw spring-boot:run
(O usa mvn spring-boot:run si tienes Maven instalado globalmente.)

6. Enviar un mensaje de prueba
Copia un archivo .hl7 válido al directorio configurado en el File Reader.

Mirth lo procesará, transformará a JSON y lo enviará por HTTP.

La aplicación Spring Boot convertirá ese JSON a recursos FHIR (Patient, Observation, etc.), los validará y los enviará a Aidbox.

7. Visualiza los recursos FHIR en Aidbox
Abre el panel de Aidbox en tu navegador:

👉 http://localhost:8087/ui/console

Aquí podrás buscar y verificar los recursos almacenados.
