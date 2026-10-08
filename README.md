# API REST de Producto - SIS414

Spring Boot, Spring Data JPA, PostgreSQL y Swagger. Java 21.

Producto: id (Long, generado), nombre (String), precio (Double), stock (Integer), categoria (String).

## Endpoints

| Metodo | Ruta | Resultado |
|---|---|---|
| POST | /api/productos | Crear, 201 |
| GET | /api/productos | Listar, 200 |
| GET | /api/productos/{id} | Buscar, 200 o 404 |
| PUT | /api/productos/{id} | Actualizar, 200 o 404 |
| DELETE | /api/productos/{id} | Eliminar, 204 o 404 |

Ejemplo para POST y PUT:
```json
{"nombre":"Cuaderno","precio":15.5,"stock":10,"categoria":"Libreria"}
```

Crear primero y usar el id devuelto. Un 404 indica que el producto solicitado no existe.

## Ejecutar

Configurar DB_URL (jdbc:postgresql://HOST:5432/DATABASE), DB_USERNAME y DB_PASSWORD.
Ejecutar `./gradlew bootRun` o `gradlew.bat bootRun` en Windows.
Swagger: `/swagger-ui/index.html`. Estado: `/health`.

## Render

Crear PostgreSQL y Web Service con Docker en la misma region. Configurar las tres variables anteriores usando los datos internos de la base. El puerto usa PORT de Render. No subir contrasenas al repositorio.

## Pruebas

`./gradlew test bootJar`
