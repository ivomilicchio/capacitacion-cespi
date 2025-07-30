# Cómo correr la app localmente

---

### Base de Datos

- **PostgreSQL versión 16.9** 

Se debe crear una Base de Datos que corra de forma local en el puerto 5432 con el nombre `database` y un usuario con nombre de usuario `postgres` y contraseña `postgres`. De forma alternativa podemos cambiar estos valores en el archivo `/backend/src/main/resources/application.properties`.

### Backend

- **Java versión 24.0.2** 

No es necesaria la instalación de Maven ya que el proyecto contiene un Maven Wrapper (mvnw). Las demás dependencias se instalarán de forma automática al correr la aplicación.

Para levantar el backend debemos abrir una terminal ubicados en la carpeta backend y ejecutar `./mvnw spring-boot:run`. Otra alternativa es abrir el proyecto en un IDE (como pueder IntellijIDEA o Eclipse) y correr el archivo `BackendApplication.java`. Utilizando cualquiera de las opciones levantaremos el backend de forma local en el puerto 8080 (`localhost:8080`).

Aclaración: el backend no se va a levantar si no puede conectarse a la Base de Datos.

### Frontend

- **NodeJS versión 22.17.1** 
- **TypeScript versión 5.8.3** 
- **Angular CLI versión 20.0.5** 

Para instalar las demás dependencias debemos abrir una terminal ubicados en la carpeta frontend y ejecutar el comando `npm install`

Para levantar el frontend debemos abrir una terminal ubicados en la carpeta frontend y ejecutar el comando `ng s`. Con esto levantaremos el frontend de forma local en el puerto 4200 (localhost:4200).
