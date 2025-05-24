package org.alvarowau.productcatalog.application.mapper;

import org.alvarowau.productcatalog.domain.model.Product;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.CreateProductRequest;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.ProductResponse;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.UpdateProductRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Mapper estático para convertir entre objetos de dominio (Product) y DTOs de la aplicación.
 * <p>
 * Proporciona métodos para transformar objetos entre las capas de aplicación y dominio.
 */
public class ProductApplicationMapper {

    /**
     * Constructor privado para prevenir instanciación.
     * <p>
     * Todos los métodos de esta clase son estáticos.
     */
    private ProductApplicationMapper() {}

    /**
     * Convierte un CreateProductRequest a un objeto Product del dominio.
     *
     * @param request DTO con los datos para crear un producto
     * @return Producto del dominio o null si el request es null
     */
    public static Product toDomainProduct(CreateProductRequest request) {
        if (request == null) return null;
        return new Product(
                request.getName(), request.getDescription(), request.getPrice(), request.getStock(),
                request.getCategory(), request.getStatus()
        );
    }

    /**
     * Actualiza un producto del dominio con los datos de un UpdateProductRequest.
     * <p>
     * Solo actualiza los campos que no son nulos en el request.
     *
     * @param product Producto del dominio a actualizar
     * @param request DTO con los nuevos valores
     */
    public static void updateDomainProduct(Product product, UpdateProductRequest request) {
        if (product == null || request == null) return;
        if (request.getName() != null && !request.getName().isEmpty()) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getStock() != null) {
            product.setStock(request.getStock());
        }
        if (request.getCategory() != null) {
            product.setCategory(request.getCategory());
        }
        if (request.getStatus() != null) {
            product.setStatus(request.getStatus());
        }
    }

    /**
     * Convierte un Product del dominio a un ProductResponse DTO.
     *
     * @param product Producto del dominio a convertir
     * @return ProductResponse o null si el producto es null
     */
    public static ProductResponse toProductResponse(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductResponse(
                product.getId(), product.getName(), product.getDescription(),
                product.getPrice(), product.getStock(), product.getCategory(), product.getStatus()
        );
    }

    /**
     * Convierte una lista de Products del dominio a una lista de ProductResponse DTOs.
     *
     * @param products Lista de productos a convertir
     * @return Lista de ProductResponse (vacía si el input es null)
     */
    public static List<ProductResponse> toProductResponseList(List<Product> products) {
        if (products == null) {
            return List.of(); // Devuelve una lista vacía en lugar de null
        }
        return products.stream()
                .map(ProductApplicationMapper::toProductResponse).toList();
    }

    /**
     * Convierte un Optional de Product a un Optional de ProductResponse.
     *
     * @param optionalProduct Optional conteniendo un Product o vacío
     * @return Optional de ProductResponse (vacío si el input está vacío o contiene null)
     */
    public static Optional<ProductResponse> toOptionalProductResponse(Optional<Product> optionalProduct) {
        if (optionalProduct.isEmpty()) {
            return Optional.empty();
        }
        return optionalProduct.map(ProductApplicationMapper::toProductResponse);
    }
}