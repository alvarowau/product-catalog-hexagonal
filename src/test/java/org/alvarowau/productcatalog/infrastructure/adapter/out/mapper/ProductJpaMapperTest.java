package org.alvarowau.productcatalog.infrastructure.adapter.out.mapper;

import org.alvarowau.productcatalog.domain.model.Category;
import org.alvarowau.productcatalog.domain.model.Product;
import org.alvarowau.productcatalog.domain.model.Status;
import org.alvarowau.productcatalog.infrastructure.adapter.out.persistence.ProductJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductJpaMapperTest {

    // --- Tests para toJpaEntity (Product -> ProductJpaEntity) ---

    @Test
    @DisplayName("Debería mapear Product de dominio a ProductJpaEntity correctamente")
    void shouldMapDomainProductToJpaEntityCorrectly() {
        // Arrange
        Product domainProduct = new Product(
                1L,
                "Producto Dominio",
                "Descripción del producto de dominio",
                BigDecimal.valueOf(150.75),
                10,
                Category.ELECTRONICS,
                Status.AVAILABLE
        );

        // Act
        ProductJpaEntity jpaEntity = ProductJpaMapper.toJpaEntity(domainProduct);

        // Assert
        assertNotNull(jpaEntity, "La entidad JPA no debería ser nula");
        assertEquals(domainProduct.getId(), jpaEntity.getId(), "El ID no coincide");
        assertEquals(domainProduct.getName(), jpaEntity.getName(), "El nombre no coincide");
        assertEquals(domainProduct.getDescription(), jpaEntity.getDescription(), "La descripción no coincide");
        assertTrue(domainProduct.getPrice().compareTo(jpaEntity.getPrice()) == 0, "El precio no coincide");
        assertEquals(domainProduct.getStock(), jpaEntity.getStock(), "El stock no coincide");
        assertEquals(domainProduct.getCategory(), jpaEntity.getCategory(), "La categoría no coincide");
        assertEquals(domainProduct.getStatus(), jpaEntity.getStatus(), "El estado no coincide");
    }

    @Test
    @DisplayName("Debería mapear Product de dominio con ID nulo a ProductJpaEntity correctamente")
    void shouldMapDomainProductWithNullIdToJpaEntityCorrectly() {
        // Arrange
        Product domainProduct = new Product(
                null,
                "Nuevo Producto",
                "Descripción de nuevo producto",
                BigDecimal.valueOf(200.00),
                5,
                Category.BOOKS,
                Status.AVAILABLE
        );

        // Act
        ProductJpaEntity jpaEntity = ProductJpaMapper.toJpaEntity(domainProduct);

        // Assert
        assertNotNull(jpaEntity, "La entidad JPA no debería ser nula");
        assertNull(jpaEntity.getId(), "El ID de la entidad JPA debería ser nulo");
        assertEquals(domainProduct.getName(), jpaEntity.getName());
        assertEquals(domainProduct.getDescription(), jpaEntity.getDescription());
        assertTrue(domainProduct.getPrice().compareTo(jpaEntity.getPrice()) == 0);
        assertEquals(domainProduct.getStock(), jpaEntity.getStock());
        assertEquals(domainProduct.getCategory(), jpaEntity.getCategory());
        assertEquals(domainProduct.getStatus(), jpaEntity.getStatus());
    }

    @Test
    @DisplayName("Debería manejar Product de dominio nulo para mapeo a ProductJpaEntity")
    void shouldHandleNullDomainProductToJpaEntity() {
        // Act
        ProductJpaEntity jpaEntity = ProductJpaMapper.toJpaEntity(null);

        // Assert
        assertNull(jpaEntity, "La entidad JPA debería ser nula si el producto de dominio es nulo");
    }

    // --- Tests para toDomainEntity (ProductJpaEntity -> Product) ---

    @Test
    @DisplayName("Debería mapear ProductJpaEntity a Product de dominio correctamente")
    void shouldMapJpaEntityToDomainProductCorrectly() {
        // Arrange
        ProductJpaEntity jpaEntity = new ProductJpaEntity(
                1L,
                "Entidad JPA",
                "Descripción de la entidad JPA",
                BigDecimal.valueOf(25.50),
                20,
                Category.SPORTS,
                Status.OUT_OF_STOCK
        );

        // Act
        Product domainProduct = ProductJpaMapper.toDomainEntity(jpaEntity);

        // Assert
        assertNotNull(domainProduct, "El producto de dominio no debería ser nulo");
        assertEquals(jpaEntity.getId(), domainProduct.getId(), "El ID no coincide");
        assertEquals(jpaEntity.getName(), domainProduct.getName(), "El nombre no coincide");
        assertEquals(jpaEntity.getDescription(), domainProduct.getDescription(), "La descripción no coincide");
        assertTrue(jpaEntity.getPrice().compareTo(domainProduct.getPrice()) == 0, "El precio no coincide");
        assertEquals(jpaEntity.getStock(), domainProduct.getStock(), "El stock no coincide");
        assertEquals(jpaEntity.getCategory(), domainProduct.getCategory(), "La categoría no coincide");
        assertEquals(jpaEntity.getStatus(), domainProduct.getStatus(), "El estado no coincide");
    }

    @Test
    @DisplayName("Debería manejar ProductJpaEntity nulo para mapeo a Product de dominio")
    void shouldHandleNullJpaEntityToDomainProduct() {
        // Act
        Product domainProduct = ProductJpaMapper.toDomainEntity(null);

        // Assert
        assertNull(domainProduct, "El producto de dominio debería ser nulo si la entidad JPA es nula");
    }

    // --- Tests para toDomainEntities (List<ProductJpaEntity> -> List<Product>) ---

    @Test
    @DisplayName("Debería mapear una lista de ProductJpaEntity a una lista de Products de dominio correctamente")
    void shouldMapListOfJpaEntitiesToListOfDomainProductsCorrectly() {
        // Arrange
        ProductJpaEntity jpa1 = new ProductJpaEntity(
                1L, "JPA1", "D1", BigDecimal.ONE, 1, Category.FASHION, Status.AVAILABLE
        );
        ProductJpaEntity jpa2 = new ProductJpaEntity(
                2L, "JPA2", "D2", BigDecimal.TEN, 2, Category.TOYS, Status.OUT_OF_STOCK
        );
        List<ProductJpaEntity> jpaEntities = List.of(jpa1, jpa2);

        // Act
        List<Product> domainProducts = ProductJpaMapper.toDomainEntities(jpaEntities);

        // Assert
        assertNotNull(domainProducts, "La lista de productos de dominio no debería ser nula");
        assertEquals(2, domainProducts.size(), "Debería haber 2 elementos en la lista de dominio");
        assertEquals(jpa1.getName(), domainProducts.get(0).getName());
        assertEquals(jpa2.getName(), domainProducts.get(1).getName());
        assertEquals(jpa1.getCategory(), domainProducts.get(0).getCategory());
        assertEquals(jpa2.getStatus(), domainProducts.get(1).getStatus());
    }

    @Test
    @DisplayName("Debería devolver una lista vacía cuando la lista de ProductJpaEntity es nula")
    void shouldReturnEmptyListWhenListOfJpaEntitiesIsNull() {
        // Act
        List<Product> domainProducts = ProductJpaMapper.toDomainEntities(null);

        // Assert
        assertNotNull(domainProducts, "La lista de productos de dominio no debería ser nula (debería ser vacía)");
        assertTrue(domainProducts.isEmpty(), "La lista de productos de dominio debería estar vacía");
    }

    @Test
    @DisplayName("Debería devolver una lista vacía cuando la lista de ProductJpaEntity está vacía")
    void shouldReturnEmptyListWhenListOfJpaEntitiesIsEmpty() {
        // Act
        List<Product> domainProducts = ProductJpaMapper.toDomainEntities(List.of());

        // Assert
        assertNotNull(domainProducts, "La lista de productos de dominio no debería ser nula");
        assertTrue(domainProducts.isEmpty(), "La lista de productos de dominio debería estar vacía");
    }

    // --- Tests para toJpaEntities (List<Product> -> List<ProductJpaEntity>) ---

    @Test
    @DisplayName("Debería mapear una lista de Products de dominio a una lista de ProductJpaEntity correctamente")
    void shouldMapListOfDomainProductsToListOfJpaEntitiesCorrectly() {
        // Arrange
        Product p1 = new Product(
                1L, "P1", "D1", BigDecimal.ONE, 1, Category.HEALTH, Status.AVAILABLE // Categoría válida
        );
        Product p2 = new Product(
                2L, "P2", "D2", BigDecimal.TEN, 2, Category.AUTOMOTIVE, Status.OUT_OF_STOCK // Categoría válida
        );
        List<Product> domainProducts = List.of(p1, p2);

        // Act
        List<ProductJpaEntity> jpaEntities = ProductJpaMapper.toJpaEntities(domainProducts);

        // Assert
        assertNotNull(jpaEntities, "La lista de entidades JPA no debería ser nula");
        assertEquals(2, jpaEntities.size(), "Debería haber 2 elementos en la lista de entidades JPA");
        assertEquals(p1.getName(), jpaEntities.get(0).getName());
        assertEquals(p2.getName(), jpaEntities.get(1).getName());
        assertEquals(p1.getCategory(), jpaEntities.get(0).getCategory());
        assertEquals(p2.getStatus(), jpaEntities.get(1).getStatus());
    }
}