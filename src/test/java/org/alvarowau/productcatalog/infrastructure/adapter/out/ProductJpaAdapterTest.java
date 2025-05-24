package org.alvarowau.productcatalog.infrastructure.adapter.out;

import org.alvarowau.productcatalog.domain.model.Category;
import org.alvarowau.productcatalog.domain.model.Product;
import org.alvarowau.productcatalog.domain.model.Status;
import org.alvarowau.productcatalog.infrastructure.adapter.out.persistence.ProductJpaEntity;
import org.alvarowau.productcatalog.infrastructure.adapter.out.persistence.ProductJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ProductJpaAdapter.class)
@ActiveProfiles("test")
class ProductJpaAdapterTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductJpaAdapter productJpaAdapter;

    @BeforeEach
    void setUp() {
        productJpaRepository.deleteAll();
    }

    // --- Tests para save ---
    @Test
    @DisplayName("Debería guardar un producto correctamente")
    void shouldSaveProductCorrectly() {
        // Arrange
        Product productToSave = new Product(
                null,
                "Producto a Guardar", "Descripción", BigDecimal.valueOf(100), 5, Category.ELECTRONICS, Status.AVAILABLE
        );

        // Act
        Product savedProduct = productJpaAdapter.save(productToSave);

        // Assert
        assertNotNull(savedProduct.getId(), "El producto guardado debería tener un ID");
        assertEquals(productToSave.getName(), savedProduct.getName());
        assertTrue(productToSave.getPrice().compareTo(savedProduct.getPrice()) == 0);
        assertEquals(productToSave.getStock(), savedProduct.getStock());
        assertEquals(productToSave.getCategory(), savedProduct.getCategory());
        assertEquals(productToSave.getStatus(), savedProduct.getStatus());

        Optional<ProductJpaEntity> retrievedEntity = productJpaRepository.findById(savedProduct.getId());
        assertTrue(retrievedEntity.isPresent(), "La entidad JPA debería existir en la base de datos");
        assertEquals(savedProduct.getName(), retrievedEntity.get().getName(), "Los nombres deberían coincidir");
    }

    // --- Tests para findById ---
    @Test
    @DisplayName("Debería encontrar un producto por ID correctamente")
    void shouldFindProductByIdCorrectly() {
        // Arrange
        ProductJpaEntity entityToSave = new ProductJpaEntity(
                null,
                "Producto a Encontrar", "Descripción", BigDecimal.valueOf(50), 2, Category.BOOKS, Status.OUT_OF_STOCK
        );
        ProductJpaEntity savedEntity = productJpaRepository.save(entityToSave);

        // Act
        Optional<Product> foundProduct = productJpaAdapter.findById(savedEntity.getId());

        // Assert
        assertTrue(foundProduct.isPresent(), "Debería encontrar el producto");
        assertEquals(savedEntity.getId(), foundProduct.get().getId());
        assertEquals(savedEntity.getName(), foundProduct.get().getName());
        assertTrue(savedEntity.getPrice().compareTo(foundProduct.get().getPrice()) == 0);
        assertEquals(savedEntity.getStock(), foundProduct.get().getStock());
        assertEquals(savedEntity.getCategory(), foundProduct.get().getCategory());
        assertEquals(savedEntity.getStatus(), foundProduct.get().getStatus());
    }

    @Test
    @DisplayName("Debería devolver Optional.empty() si no encuentra el producto por ID")
    void shouldReturnEmptyOptionalIfProductNotFound() {
        Long nonExistentId = 99L;

        // Act
        Optional<Product> foundProduct = productJpaAdapter.findById(nonExistentId);

        // Assert
        assertFalse(foundProduct.isPresent(), "No debería encontrar el producto");
    }

    // --- Tests para findAll ---
    @Test
    @DisplayName("Debería encontrar todos los productos correctamente")
    void shouldFindAllProductsCorrectly() {
        // Arrange
        ProductJpaEntity entity1 = new ProductJpaEntity(
                null, "Producto 1", "Desc 1", BigDecimal.TEN, 1, Category.FASHION, Status.AVAILABLE
        );
        ProductJpaEntity entity2 = new ProductJpaEntity(
                null, "Producto 2", "Desc 2", BigDecimal.valueOf(20), 2, Category.TOYS, Status.OUT_OF_STOCK
        );
        productJpaRepository.saveAll(List.of(entity1, entity2));

        // Act
        List<Product> products = productJpaAdapter.findAll();

        // Assert
        assertEquals(2, products.size(), "Debería encontrar 2 productos");
        assertEquals(entity1.getName(), products.get(0).getName());
        assertEquals(entity2.getName(), products.get(1).getName());
    }

    @Test
    @DisplayName("Debería devolver una lista vacía si no hay productos")
    void shouldReturnEmptyListIfNoProducts() {
        // Act
        List<Product> products = productJpaAdapter.findAll();

        // Assert
        assertTrue(products.isEmpty(), "Debería devolver una lista vacía");
    }

    // --- Tests para deleteById ---
    @Test
    @DisplayName("Debería eliminar un producto por ID correctamente")
    void shouldDeleteProductByIdCorrectly() {
        // Arrange
        ProductJpaEntity entityToDelete = new ProductJpaEntity(
                null,
                "Producto a Eliminar", "Descripción", BigDecimal.valueOf(75), 7, Category.GROCERIES, Status.AVAILABLE
        );
        ProductJpaEntity savedEntity = productJpaRepository.save(entityToDelete);

        // Act
        boolean result = productJpaAdapter.deleteById(savedEntity.getId());

        // Assert
        assertTrue(result, "Debería eliminar el producto");
        assertFalse(productJpaRepository.existsById(savedEntity.getId()), "No debería existir en la base de datos");
    }

    @Test
    @DisplayName("Debería devolver false si intenta eliminar un producto que no existe")
    void shouldReturnFalseWhenDeletingNonExistentProduct() {
        Long nonExistentId = 99L;

        // Act
        boolean result = productJpaAdapter.deleteById(nonExistentId);

        // Assert
        assertFalse(result, "Debería devolver false");
    }
}