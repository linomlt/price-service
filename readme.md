# Price Service

Servicio REST desarrollado en Spring Boot para consultar el precio efectivo de un producto.

## 📋 Descripción

Este proyecto implementa un servicio de consulta de precios que permite obtener el precio aplicable a un producto de una marca específica en una fecha determinada. El sistema maneja múltiples tarifas con prioridades y rangos de fechas.

## 🛠️ Tecnologías Utilizadas

- **Java 21**
- **Maven**
- **Spring Boot 3.5.6**
- **Spring Data JPA** 
- **Spring Web MVC**
- **H2 Database**
- **Lombok**
- **JUnit 5**

## 🚀 Cómo Ejecutar el Proyecto

### Prerrequisitos
- Java 21 o superior
- Maven 3.6+ (opcional, se incluye wrapper)

### Ejecución del proyecto con Maven Wrapper (Recomendado)
`./mvnw`

### Probar el endpoint REST usando curl
Para consultar el precio del producto con ID 1 de la marca con ID 1 el 14 de junio a las 10:00:

`curl http://localhost:8080/api/prices?brandId=1&productId=35455&applicationDate=2020-06-14T10:00:00`

### Ejecución de las pruebas con Maven Wrapper
`./mvnw test`


## 💾 Datos de prueba de la tabla PRICES

| BRAND_ID | START_DATE          | END_DATE            | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----------|---------------------|---------------------|------------|------------|----------|-------|------|
| 1        | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 1          | 35455      | 0        | 35.50 | EUR  |
| 1        | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 2          | 35455      | 1        | 25.45 | EUR  |
| 1        | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 3          | 35455      | 1        | 30.50 | EUR  |
| 1        | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 4          | 35455      | 1        | 38.95 | EUR  |

