package org.alvarowau.productcatalog.infrastructure.adapter.out.mapper;

import org.alvarowau.productcatalog.domain.model.Product;
import org.alvarowau.productcatalog.infrastructure.adapter.out.persistence.ProductJpaEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper estático para convertir entre entidades de dominio (Product) y entidades JPA (ProductJpaEntity).
 * <p>
 * Proporciona métodos para transformar objetos entre la capa de dominio y la capa de persistencia.
 */
public class ProductJpaMapper {

    /**
     * Constructor privado para prevenir instanciación.
     * <p>
     * Todos los métodos de esta clase son estáticos.
     */
    private ProductJpaMapper() {
    }

    /**
     * Convierte una entidad de dominio Product a una entidad JPA ProductJpaEntity.
     *
     * @param domainProduct Entidad de dominio a convertir (puede ser null)
     * @return Entidad JPA o null si el parámetro es null
     */
    public static ProductJpaEntity toJpaEntity(Product domainProduct) {
        if (domainProduct == null) {
            return null;
        }
        ProductJpaEntity jpaEntity = new ProductJpaEntity();
        jpaEntity.setId(domainProduct.getId());
        jpaEntity.setName(domainProduct.getName());
        jpaEntity.setDescription(domainProduct.getDescription());
        jpaEntity.setPrice(domainProduct.getPrice());
        jpaEntity.setStock(domainProduct.getStock());
        jpaEntity.setCategory(domainProduct.getCategory());
        jpaEntity.setStatus(domainProduct.getStatus());
        return jpaEntity;
    }

    /**
     * Convierte una entidad JPA ProductJpaEntity a una entidad de dominio Product.
     *
     * @param jpaEntity Entidad JPA a convertir (puede ser null)
     * @return Entidad de dominio o null si el parámetro es null
     */
    public static Product toDomainEntity(ProductJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        Product domainProduct = new Product();
        domainProduct.setId(jpaEntity.getId());
        domainProduct.setName(jpaEntity.getName());
        domainProduct.setDescription(jpaEntity.getDescription());
        domainProduct.setPrice(jpaEntity.getPrice());
        domainProduct.setStock(jpaEntity.getStock());
        domainProduct.setCategory(jpaEntity.getCategory());
        domainProduct.setStatus(jpaEntity.getStatus());
        return domainProduct;
    }

    /**
     * Convierte una lista de entidades JPA a una lista de entidades de dominio.
     *
     * @param jpaEntities Lista de entidades JPA a convertir (puede ser null)
     * @return Lista de entidades de dominio (lista vacía si el parámetro es null)
     */
    public static List<Product> toDomainEntities(List<ProductJpaEntity> jpaEntities) {
        if(jpaEntities == null) return List.of();
        return jpaEntities.stream()
                .map(ProductJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de entidades de dominio a una lista de entidades JPA.
     *
     * @param domainProducts Lista de entidades de dominio a convertir (puede ser null)
     * @return Lista de entidades JPA (lista vacía si el parámetro es null)
     */
    public static List<ProductJpaEntity> toJpaEntities(List<Product> domainProducts) {
        if(domainProducts == null) return List.of();
        return domainProducts.stream()
                .map(ProductJpaMapper::toJpaEntity).toList();
    }
}