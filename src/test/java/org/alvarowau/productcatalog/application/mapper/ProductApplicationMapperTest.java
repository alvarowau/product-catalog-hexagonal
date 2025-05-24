package org.alvarowau.productcatalog.application.mapper;

import org.alvarowau.productcatalog.domain.model.Category;
import org.alvarowau.productcatalog.domain.model.Product;
import org.alvarowau.productcatalog.domain.model.Status;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.CreateProductRequest;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.ProductResponse;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.UpdateProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductApplicationMapperTest {

    // --- Tests para toDomainProduct (CreateProductRequest -> Product) ---

    @Test
    @DisplayName("Debería mapear CreateProductRequest a Product de dominio correctamente")
    void shouldMapCreateProductRequestToDomainProductCorrectly() {
        // Arrange
        CreateProductRequest request = new CreateProductRequest(
                "Laptop Prueba",
                "Descripción de la laptop de prueba",
                BigDecimal.valueOf(1200.00),
                10,
                Category.ELECTRONICS,
                Status.AVAILABLE
        );

        // Act
        Product product = ProductApplicationMapper.toDomainProduct(request);

        // Assert
        assertNotNull(product, "El producto mapeado no debería ser nulo");
        assertNull(product.getId(), "El ID del producto debería ser nulo para un producto nuevo"); // ID lo genera la DB
        assertEquals(request.getName(), product.getName(), "El nombre no coincide");
        assertEquals(request.getDescription(), product.getDescription(), "La descripción no coincide");
        assertTrue(request.getPrice().compareTo(product.getPrice()) == 0, "El precio no coincide");
        assertEquals(request.getStock(), product.getStock(), "El stock no coincide");
        assertEquals(request.getCategory(), product.getCategory(), "La categoría no coincide");
        assertEquals(request.getStatus(), product.getStatus(), "El estado no coincide");
    }

    @Test
    @DisplayName("Debería manejar CreateProductRequest nulo para mapeo a Product")
    void shouldHandleNullCreateProductRequestToDomainProduct() {
        // Act
        Product product = ProductApplicationMapper.toDomainProduct(null);

        // Assert
        assertNull(product, "El producto debería ser nulo si el request es nulo");
    }

    // --- Tests para updateDomainProduct (Actualizar Product con UpdateProductRequest) ---

    @Test
    @DisplayName("Debería actualizar el Product de dominio con UpdateProductRequest correctamente")
    void shouldUpdateDomainProductWithUpdateRequestCorrectly() {
        // Arrange
        Product existingProduct = new Product(
                1L,
                "Producto Antiguo",
                "Descripción antigua",
                BigDecimal.valueOf(50.00),
                5,
                Category.BOOKS,
                Status.AVAILABLE
        );

        UpdateProductRequest updateRequest = new UpdateProductRequest(
                "Producto Nuevo",
                "Nueva descripción",
                BigDecimal.valueOf(75.50),
                7,
                Category.HOME, // <-- CORREGIDO AQUÍ
                Status.OUT_OF_STOCK
        );

        // Act
        ProductApplicationMapper.updateDomainProduct(existingProduct, updateRequest);

        // Assert
        assertEquals(1L, existingProduct.getId(), "El ID no debería cambiar");
        assertEquals("Producto Nuevo", existingProduct.getName(), "El nombre debería actualizarse");
        assertEquals("Nueva descripción", existingProduct.getDescription(), "La descripción debería actualizarse");
        assertTrue(BigDecimal.valueOf(75.50).compareTo(existingProduct.getPrice()) == 0, "El precio debería actualizarse");
        assertEquals(7, existingProduct.getStock(), "El stock debería actualizarse");
        assertEquals(Category.HOME, existingProduct.getCategory(), "La categoría debería actualizarse"); // <-- CORREGIDO AQUÍ
        assertEquals(Status.OUT_OF_STOCK, existingProduct.getStatus(), "El estado debería actualizarse");
    }


    @Test
    @DisplayName("Debería realizar actualización parcial de Product con UpdateProductRequest")
    void shouldHandlePartialUpdateOfDomainProduct() {
        // Arrange
        Product existingProduct = new Product(
                2L,
                "Producto Original",
                "Descripción original",
                BigDecimal.valueOf(200.00),
                20,
                Category.ELECTRONICS,
                Status.AVAILABLE
        );

        UpdateProductRequest partialUpdateRequest = new UpdateProductRequest(
                "Nombre Parcial", // Solo cambia el nombre y el stock
                null,
                null,
                15,
                null,
                null
        );

        // Act
        ProductApplicationMapper.updateDomainProduct(existingProduct, partialUpdateRequest);

        // Assert
        assertEquals(2L, existingProduct.getId(), "El ID no debería cambiar en actualización parcial");
        assertEquals("Nombre Parcial", existingProduct.getName(), "El nombre debería actualizarse");
        assertEquals("Descripción original", existingProduct.getDescription(), "La descripción no debería cambiar"); // Sin cambio
        assertTrue(BigDecimal.valueOf(200.00).compareTo(existingProduct.getPrice()) == 0, "El precio no debería cambiar"); // Sin cambio
        assertEquals(15, existingProduct.getStock(), "El stock debería actualizarse");
        assertEquals(Category.ELECTRONICS, existingProduct.getCategory(), "La categoría no debería cambiar"); // Sin cambio
        assertEquals(Status.AVAILABLE, existingProduct.getStatus(), "El estado no debería cambiar"); // Sin cambio
    }

    @Test
    @DisplayName("Debería manejar Product nulo en updateDomainProduct")
    void shouldHandleNullProductInUpdateDomainProduct() {
        // Arrange
        UpdateProductRequest updateRequest = new UpdateProductRequest("Nombre", null, null, null, null, null);

        // Act & Assert (no debería lanzar excepción y simplemente retornar)
        assertDoesNotThrow(() -> ProductApplicationMapper.updateDomainProduct(null, updateRequest),
                "No debería lanzar excepción cuando el producto es nulo");
    }

    @Test
    @DisplayName("Debería manejar UpdateProductRequest nulo en updateDomainProduct")
    void shouldHandleNullUpdateRequestInUpdateDomainProduct() {
        // Arrange
        Product existingProduct = new Product(
                3L, "Test", "Desc", BigDecimal.TEN, 1, Category.BOOKS, Status.AVAILABLE
        );
        String originalName = existingProduct.getName();

        // Act
        ProductApplicationMapper.updateDomainProduct(existingProduct, null);

        // Assert (el producto no debería cambiar)
        assertEquals(originalName, existingProduct.getName(), "El producto no debería cambiar si el request es nulo");
    }


    // --- Tests para toProductResponse (Product -> ProductResponse) ---

    @Test
    @DisplayName("Debería mapear Product de dominio a ProductResponse correctamente")
    void shouldMapDomainProductToProductResponseCorrectly() {
        // Arrange
        Product domainProduct = new Product(
                1L,
                "Producto Mapeado",
                "Descripción del producto mapeado",
                BigDecimal.valueOf(250.00),
                5,
                Category.GROCERIES, // <-- CORREGIDO AQUÍ
                Status.OUT_OF_STOCK
        );

        // Act
        ProductResponse response = ProductApplicationMapper.toProductResponse(domainProduct);

        // Assert
        assertNotNull(response, "La respuesta no debería ser nula");
        assertEquals(domainProduct.getId(), response.getId(), "El ID no coincide");
        assertEquals(domainProduct.getName(), response.getName(), "El nombre no coincide");
        assertEquals(domainProduct.getDescription(), response.getDescription(), "La descripción no coincide");
        assertTrue(domainProduct.getPrice().compareTo(response.getPrice()) == 0, "El precio no coincide");
        assertEquals(domainProduct.getStock(), response.getStock(), "El stock no coincide");
        assertEquals(domainProduct.getCategory(), response.getCategory(), "La categoría no coincide");
        assertEquals(domainProduct.getStatus(), response.getStatus(), "El estado no coincide");
    }

    @Test
    @DisplayName("Debería manejar Product de dominio nulo para mapeo a ProductResponse")
    void shouldHandleNullDomainProductToProductResponse() {
        // Act
        ProductResponse response = ProductApplicationMapper.toProductResponse(null);

        // Assert
        assertNull(response, "La respuesta debería ser nula si el producto de dominio es nulo");
    }

    // --- Tests para toProductResponseList (List<Product> -> List<ProductResponse>) ---

    @Test
    @DisplayName("Debería mapear una lista de Products a una lista de ProductResponse correctamente")
    void shouldMapListOfProductsToListOfProductResponseCorrectly() {
        // Arrange
        Product p1 = new Product(
                1L, "P1", "D1", BigDecimal.TEN, 1, Category.BOOKS, Status.AVAILABLE
        );
        Product p2 = new Product(
                2L, "P2", "D2", BigDecimal.valueOf(20), 2, Category.ELECTRONICS, Status.OUT_OF_STOCK
        );
        List<Product> domainProducts = List.of(p1, p2);

        // Act
        List<ProductResponse> responses = ProductApplicationMapper.toProductResponseList(domainProducts);

        // Assert
        assertNotNull(responses, "La lista de respuestas no debería ser nula");
        assertEquals(2, responses.size(), "Debería haber 2 elementos en la lista de respuestas");
        // Puedes añadir más aserciones para verificar los contenidos de los elementos individuales
        assertEquals(p1.getName(), responses.get(0).getName());
        assertEquals(p2.getName(), responses.get(1).getName());
    }

    @Test
    @DisplayName("Debería devolver una lista vacía cuando la lista de Products es nula")
    void shouldReturnEmptyListWhenListOfProductsIsNull() {
        // Act
        List<ProductResponse> responses = ProductApplicationMapper.toProductResponseList(null);

        // Assert
        assertNotNull(responses, "La lista de respuestas no debería ser nula");
        assertTrue(responses.isEmpty(), "La lista de respuestas debería estar vacía");
    }

    @Test
    @DisplayName("Debería devolver una lista vacía cuando la lista de Products está vacía")
    void shouldReturnEmptyListWhenListOfProductsIsEmpty() {
        // Act
        List<ProductResponse> responses = ProductApplicationMapper.toProductResponseList(List.of());

        // Assert
        assertNotNull(responses, "La lista de respuestas no debería ser nula");
        assertTrue(responses.isEmpty(), "La lista de respuestas debería estar vacía");
    }

    // --- Tests para toOptionalProductResponse (Optional<Product> -> Optional<ProductResponse>) ---

    @Test
    @DisplayName("Debería mapear Optional<Product> a Optional<ProductResponse> correctamente si está presente")
    void shouldMapOptionalProductToOptionalProductResponseWhenPresent() {
        // Arrange
        Product domainProduct = new Product(
                1L, "Opt Product", "Desc", BigDecimal.valueOf(99), 1, Category.BOOKS, Status.AVAILABLE
        );
        Optional<Product> optionalProduct = Optional.of(domainProduct);

        // Act
        Optional<ProductResponse> optionalResponse = ProductApplicationMapper.toOptionalProductResponse(optionalProduct);

        // Assert
        assertTrue(optionalResponse.isPresent(), "La Optional de respuesta debería estar presente");
        assertEquals(domainProduct.getName(), optionalResponse.get().getName(), "El nombre del producto no coincide");
    }

    @Test
    @DisplayName("Debería mapear Optional<Product> a Optional.empty() si el Optional de entrada está vacío")
    void shouldMapOptionalProductToEmptyOptionalResponseWhenEmpty() {

        Optional<ProductResponse> optionalResponse = ProductApplicationMapper.toOptionalProductResponse(Optional.empty());

        assertTrue(optionalResponse.isEmpty(), "La Optional de respuesta debería estar vacía");
    }


}