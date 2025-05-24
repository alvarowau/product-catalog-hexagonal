📦 Sistema de Catálogo de Productos - Arquitectura Hexagonal (Ports & Adapters)

Este proyecto es una implementación de un sistema de gestión de catálogo de productos utilizando **Spring Boot** y la **Arquitectura Hexagonal (Ports & Adapters)**. Ha sido diseñado específicamente como un recurso educativo para comprender y aplicar los principios de esta arquitectura limpia y desacoplada en un entorno de desarrollo moderno.

## ✨ Características Principales

* **Arquitectura Hexagonal (Ports & Adapters)**: La aplicación está rigurosamente estructurada en capas que encapsulan el dominio de negocio, aislándolo de los detalles de infraestructura y tecnología.
    * **Dominio**: El corazón agnóstico a la tecnología, con la lógica de negocio central (`Product`, `Category`, `Status`).
    * **Capa de Aplicación**: Define los casos de uso (puertos de entrada) y lo que el dominio necesita del exterior (puertos de salida), orquestando el flujo de negocio.
    * **Capa de Infraestructura**: Contiene los adaptadores que conectan el dominio con el mundo exterior (APIs REST, bases de datos, etc.).
* **Spring Boot**: Framework líder para el desarrollo de aplicaciones basadas en Spring, facilitando la configuración y el despliegue.
* **Spring Data JPA & H2 Database**: Persistencia de datos con una base de datos en memoria para el desarrollo y testing rápido.
* **API RESTful**: Exposición de las funcionalidades del catálogo a través de endpoints REST bien definidos.
* **DTOs (Data Transfer Objects)**: Implementación de DTOs para una comunicación clara y desacoplada entre las capas.
* **Suite de Tests Completa**: Incluye tests unitarios y de integración que demuestran cómo testear eficazmente cada capa de una aplicación hexagonal.
    * Tests de Dominio.
    * Tests de Mapeadores (entre DTOs y Dominio, y Dominio y JPA Entity).
    * Tests de Servicio (con Mockito).
    * Tests de Adaptador de Persistencia (`@DataJpaTest`).
    * Tests de Controlador REST (`@WebMvcTest`).

## 🎯 Objetivo del Proyecto

El propósito principal de este repositorio es servir como una guía práctica y un ejemplo funcional para:

* **Comprender la separación de preocupaciones** en una aplicación.
* **Aplicar los principios de la Arquitectura Hexagonal**, haciendo que el dominio sea el centro y los detalles de infraestructura sean enchufables.
* **Dominar el uso de Puertos y Adaptadores** para un desacoplamiento efectivo.
* **Aprender a testear cada capa** de una aplicación hexagonal con diferentes estrategias (unitarias, mocks, integración).
* **Familiarizarse con el desarrollo de APIs REST** robustas y limpias con Spring Boot.

## 🚀 Cómo Empezar

Para levantar y probar la aplicación en tu entorno local:

1.  **Clona el repositorio:**
    ```bash
    git clone https://github.com/alvarowau/product-catalog-hexagonal.git
    cd product-catalog-hexagonal
    ```
2.  **Construye el proyecto (Maven):**
    ```bash
    mvn clean install
    ```
3.  **Ejecuta la aplicación:**
    ```bash
    mvn spring-boot:run
    ```
    La aplicación se iniciará en `http://localhost:8080`.

### Consola H2 (Base de Datos en Memoria)

Puedes acceder a la consola de la base de datos H2 en memoria para ver los datos:
* **URL:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:productdb`
* **User Name:** `sa`
* **Password:** `password`

### Endpoints de la API REST

Puedes usar herramientas como Postman o cURL para interactuar con la API. La ruta base es `/product`.

* **`POST /product`**: Crear un nuevo producto.
* **`GET /product/{id}`**: Obtener un producto por ID.
* **`GET /product`**: Listar todos los productos.
* **`PUT /product/{id}`**: Actualizar un producto existente.
* **`DELETE /product/{id}`**: Eliminar un producto por ID.

## ✅ Ejecución de Tests

Para ejecutar la suite completa de tests y verificar el correcto funcionamiento de cada capa:

```bash
mvn test
Verás un informe detallado de todos los tests unitarios y de integración, asegurando la calidad del código.
```
## 🛠️ Tecnologías Utilizadas

* **Java 17+**
* **Spring Boot 3.x**
* **Maven**
* **Spring Data JPA**
* **H2 Database**
* **Lombok**
* **JUnit 5**
* **Mockito**
* **Jackson (para JSON)**

## 🤝 Contribución

Siéntete libre de explorar el código, proponer mejoras o utilizarlo como base para tus propios proyectos. Este proyecto está diseñado para el aprendizaje y la experimentación.

---