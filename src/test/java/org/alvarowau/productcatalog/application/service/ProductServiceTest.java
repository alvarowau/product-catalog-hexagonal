package org.alvarowau.productcatalog.application.service;

import org.alvarowau.productcatalog.application.port.out.ProductRepositoryPort;
import org.alvarowau.productcatalog.domain.model.Category;
import org.alvarowau.productcatalog.domain.model.Product;
import org.alvarowau.productcatalog.domain.model.Status;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.CreateProductRequest;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.ProductResponse;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.UpdateProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @InjectMocks
    private ProductService productService;


    @BeforeEach
    void setUp() {
    }

    // --- Tests para createProduct ---
    @Test
    @DisplayName("Debería crear un producto exitosamente")
    void shouldCreateProductSuccessfully() {
        // Arrange
        CreateProductRequest request = new CreateProductRequest(
                "Nuevo Producto", "Descripción", BigDecimal.valueOf(100.00), 10, Category.ELECTRONICS, Status.AVAILABLE
        );
        Product productToSave = new Product( // Objeto Product esperado por el repositorio
                "Nuevo Producto", "Descripción", BigDecimal.valueOf(100.00), 10, Category.ELECTRONICS, Status.AVAILABLE
        );
        Product savedProduct = new Product( // Objeto Product que el repositorio "devolvería"
                1L, "Nuevo Producto", "Descripción", BigDecimal.valueOf(100.00), 10, Category.ELECTRONICS, Status.AVAILABLE
        );

        // Configurar el mock: Cuando se llame a save() con cualquier Product, devolver savedProduct
        when(productRepositoryPort.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        ProductResponse response = productService.createProduct(request);

        // Assert
        assertNotNull(response, "La respuesta no debería ser nula");
        assertEquals(savedProduct.getId(), response.getId(), "El ID no coincide");
        assertEquals(savedProduct.getName(), response.getName(), "El nombre no coincide");
        // Verificar que el método save() del mock fue llamado exactamente una vez con un Product
        verify(productRepositoryPort, times(1)).save(any(Product.class));
    }

    // --- Tests para getProductById ---
    @Test
    @DisplayName("Debería obtener un producto por ID exitosamente")
    void shouldGetProductByIdSuccessfully() {
        // Arrange
        Long productId = 1L;
        Product foundProduct = new Product(
                productId, "Producto Existente", "Descripción", BigDecimal.valueOf(50.00), 5, Category.BOOKS, Status.AVAILABLE
        );

        // Configurar el mock: Cuando se llame a findById(productId), devolver el Optional con foundProduct
        when(productRepositoryPort.findById(productId)).thenReturn(Optional.of(foundProduct));

        // Act
        Optional<ProductResponse> response = productService.getProductById(productId);

        // Assert
        assertTrue(response.isPresent(), "La respuesta debería contener un producto");
        assertEquals(foundProduct.getId(), response.get().getId(), "El ID no coincide");
        verify(productRepositoryPort, times(1)).findById(productId);
    }

    @Test
    @DisplayName("Debería devolver Optional vacío si el producto no se encuentra por ID")
    void shouldReturnEmptyOptionalIfProductNotFoundById() {
        // Arrange
        Long productId = 99L; // Un ID que no existe

        // Configurar el mock: Cuando se llame a findById(productId), devolver Optional.empty()
        when(productRepositoryPort.findById(productId)).thenReturn(Optional.empty());

        // Act
        Optional<ProductResponse> response = productService.getProductById(productId);

        // Assert
        assertFalse(response.isPresent(), "La respuesta debería estar vacía");
        verify(productRepositoryPort, times(1)).findById(productId);
    }

    // --- Tests para getAllProducts ---
    @Test
    @DisplayName("Debería obtener todos los productos exitosamente")
    void shouldGetAllProductsSuccessfully() {
        // Arrange
        List<Product> products = Arrays.asList(
                new Product(1L, "P1", "D1", BigDecimal.TEN, 1, Category.ELECTRONICS, Status.AVAILABLE),
                new Product(2L, "P2", "D2", BigDecimal.valueOf(20), 2, Category.BOOKS, Status.OUT_OF_STOCK)
        );

        // Configurar el mock: Cuando se llame a findAll(), devolver la lista de productos
        when(productRepositoryPort.findAll()).thenReturn(products);

        // Act
        List<ProductResponse> response = productService.getAllProducts();

        // Assert
        assertNotNull(response, "La lista de respuesta no debería ser nula");
        assertEquals(2, response.size(), "Debería devolver 2 productos");
        assertEquals(products.get(0).getName(), response.get(0).getName(), "El nombre del primer producto no coincide");
        assertEquals(products.get(1).getName(), response.get(1).getName(), "El nombre del segundo producto no coincide");
        verify(productRepositoryPort, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería devolver una lista vacía si no hay productos")
    void shouldReturnEmptyListIfNoProducts() {
        // Arrange
        when(productRepositoryPort.findAll()).thenReturn(List.of()); // Devolver una lista vacía

        // Act
        List<ProductResponse> response = productService.getAllProducts();

        // Assert
        assertNotNull(response, "La lista de respuesta no debería ser nula");
        assertTrue(response.isEmpty(), "La lista de respuesta debería estar vacía");
        verify(productRepositoryPort, times(1)).findAll();
    }

    // --- Tests para updateProduct ---
    @Test
    @DisplayName("Debería actualizar un producto existente exitosamente")
    void shouldUpdateExistingProductSuccessfully() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = new Product(
                productId, "Old Name", "Old Desc", BigDecimal.valueOf(10.00), 10, Category.BOOKS, Status.AVAILABLE
        );
        UpdateProductRequest updateRequest = new UpdateProductRequest(
                "New Name", "New Desc", BigDecimal.valueOf(20.00), 20, Category.ELECTRONICS, Status.OUT_OF_STOCK
        );
        Product updatedProduct = new Product( // El producto que el repositorio devolverá después de actualizar
                productId, "New Name", "New Desc", BigDecimal.valueOf(20.00), 20, Category.ELECTRONICS, Status.OUT_OF_STOCK
        );

        // Configurar el mock:
        when(productRepositoryPort.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepositoryPort.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        ProductResponse response = productService.updateProduct(productId, updateRequest);

        // Assert
        assertNotNull(response, "La respuesta no debería ser nula");
        assertEquals(updatedProduct.getName(), response.getName(), "El nombre no se actualizó correctamente");
        assertEquals(updatedProduct.getStock(), response.getStock(), "El stock no se actualizó correctamente");
        verify(productRepositoryPort, times(1)).findById(productId);
        verify(productRepositoryPort, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException al intentar actualizar un producto no existente")
    void shouldThrowExceptionWhenUpdatingNonExistingProduct() {
        // Arrange
        Long productId = 99L;
        UpdateProductRequest updateRequest = new UpdateProductRequest(
                "New Name", null, null, null, null, null
        );

        when(productRepositoryPort.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.updateProduct(productId, updateRequest),
                "Debería lanzar RuntimeException para un producto no encontrado");
        verify(productRepositoryPort, times(1)).findById(productId);
        verify(productRepositoryPort, never()).save(any(Product.class)); // Asegurarse de que save nunca fue llamado
    }

    // --- Tests para deleteProduct ---
    @Test
    @DisplayName("Debería eliminar un producto exitosamente")
    void shouldDeleteProductSuccessfully() {
        // Arrange
        Long productId = 1L;
        when(productRepositoryPort.deleteById(productId)).thenReturn(true);

        // Act
        boolean result = productService.deleteProduct(productId);

        // Assert
        assertTrue(result, "La eliminación debería ser exitosa");
        verify(productRepositoryPort, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("Debería retornar false si el producto a eliminar no existe")
    void shouldReturnFalseIfProductToDeleteDoesNotExist() {
        // Arrange
        Long productId = 99L;
        when(productRepositoryPort.deleteById(productId)).thenReturn(false);

        // Act
        boolean result = productService.deleteProduct(productId);

        // Assert
        assertFalse(result, "La eliminación debería fallar si el producto no existe");
        verify(productRepositoryPort, times(1)).deleteById(productId);
    }
}