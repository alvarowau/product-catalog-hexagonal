package org.alvarowau.productcatalog.infrastructure.config;

import org.alvarowau.productcatalog.application.port.out.ProductRepositoryPort;
import org.alvarowau.productcatalog.application.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para la definición de beans de la aplicación.
 * <p>
 * Esta clase configura los beans principales del módulo de catálogo de productos,
 * estableciendo las dependencias entre los componentes según la arquitectura hexagonal.
 */
@Configuration
public class ApplicationConfig {

    /**
     * Define el bean del servicio de productos.
     * <p>
     * Configura la inyección de dependencias para el ProductService,
     * conectando el puerto de entrada (caso de uso) con el puerto de salida (repositorio).
     *
     * @param repositoryPort Puerto de repositorio inyectado automáticamente
     * @return Instancia configurada del ProductService
     */
    @Bean
    public ProductService productService(ProductRepositoryPort repositoryPort) {
        return new ProductService(repositoryPort);
    }
}