# 📌 Issue Tracker - API REST

| <h1>UTN-FRLP</h1> | <img src="./logo.png" alt="Logo del Proyecto" width="100"> |
|-------------------|----------------------------------|

El proyecto **Issue Tracker** es desarrollado por estudiantes de Ingeniería en Sistemas de Información de la Universidad Tecnológica Nacional - Regional La Plata, para la materia Diseño de Sistemas de Información.

## 🚀 Tecnologías utilizadas  

Este proyecto está construido con las siguientes tecnologías:  

- **Java 21** - Lenguaje de programación principal.  
- **Spring Boot** - Framework para el desarrollo de la API REST.  
- **Spring Data JPA** - Capa de persistencia para interactuar con la base de datos.  
- **Spring Security** - Gestión de autenticación y autorización.  
- **Hibernate** - ORM para la comunicación con la base de datos.  
- **Lombok** - Reducción de código boilerplate en Java.  
- **Maven** - Herramienta de gestión de dependencias y compilación.  
- **MySQL / SQL Server** - Motor de base de datos utilizado.  
- **Docker** - Contenedor para la base de datos.  

## 📦 Instalación  

Sigue estos pasos para instalar y ejecutar el proyecto en tu entorno local:  

### 1️⃣ Prerrequisitos  

Asegúrate de tener las siguientes herramientas instaladas en tu sistema:  

- **Java 21** ([Descargar](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html))  
- **Apache Maven 3.9.9** ([Descargar](https://maven.apache.org/download.cgi))  
- **Docker** (Opcional, si deseas usar la base de datos en contenedor)  

🔹 **Verificar instalación de Java y Maven**  

```sh
java -version
mvn -version
```

### 2️⃣ Clonar el repositorio

```sh
git clone https://github.com/tu-usuario/issue-tracker-api.git
cd issue-tracker-api
```

3️⃣ Configurar conexión a la base de datos

Modificar las propiedades en el file src/main/resources/application.properties

```sh
spring.datasource.url=jdbc:mysql://localhost:[PUERTO]/[NOMBRE_BASE_DATOS] 
spring.datasource.username=[USUARIO] 
spring.datasource.password=[PASSWORD]
```

4️⃣ Instalar dependencias

```sh
mvn clean install
```

5️⃣ Ejecutar la aplicación

```sh
mvn spring-boot:run
```

La API corre en el puerto 8080
``` sh
http://localhost:8080
```

## 👥 Integrantes  
1. [Petosa Ayala Franco](https://www.linkedin.com/in/franco-petosa-ayala-48b8b9206/)  
2. [Bresciani Isabella ❤️](https://www.linkedin.com/in/isabellabresciani/)  

## Profesores
1. Leopoldo Nahuel
2. Marchessini Javier