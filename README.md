
# User Management API

## Descripción

Esta aplicación es una API RESTful para la gestión de usuarios. Permite crear, leer, actualizar y eliminar usuarios, así como gestionar sus teléfonos asociados. La API acepta y retorna datos en formato JSON.

## Tecnologías Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database (base de datos en memoria)
- Maven (gestor de dependencias)
- Spring Security

## Requisitos

- JDK 17 o superior
- Maven 3 o superior

## Configuración y Ejecución

### Clonar el repositorio

git clone https://github.com/tu-usuario/tu-repositorio.git
cd tu-repositorio


### Construir el proyecto

mvn clean install


### Ejecutar la aplicación

mvn spring-boot:run

La aplicación estará disponible en `http://localhost:8080`.


### Base de Datos

La aplicación utiliza una base de datos en memoria H2. Al iniciar la aplicación, se creará y poblará la base de datos automáticamente utilizando el archivo `schema.sql` ubicado en `src/main/resources/db/schema.sql`.

