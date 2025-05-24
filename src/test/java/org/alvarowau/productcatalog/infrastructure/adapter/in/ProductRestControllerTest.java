package org.alvarowau.productcatalog.infrastructure.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.alvarowau.productcatalog.application.port.in.CreateProductUseCase;
import org.alvarowau.productcatalog.application.port.in.DeleteProductUseCase;
import org.alvarowau.productcatalog.application.port.in.GetProductUseCase;
import org.alvarowau.productcatalog.application.port.in.UpdateProductUseCase;
import org.alvarowau.productcatalog.domain.model.Category;
import org.alvarowau.productcatalog.domain.model.Status;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.CreateProductRequest;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.ProductResponse;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.UpdateProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductRestControllerImpl.class)
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateProductUseCase createProductUseCase;
    @MockBean
    private GetProductUseCase getProductUseCase;
    @MockBean
    private UpdateProductUseCase updateProductUseCase;
    @MockBean
    private DeleteProductUseCase deleteProductUseCase;

    // --- Test para crear un producto (POST /product) ---
    @Test
    @DisplayName("Debería crear un producto y devolver 201 Created")
    void shouldCreateProductAndReturn201Created() throws Exception {
        // Arrange
        CreateProductRequest request = new CreateProductRequest(
                "Nuevo Producto", "Descripción", BigDecimal.valueOf(100.00), 10, Category.ELECTRONICS, Status.AVAILABLE
        );
        ProductResponse expectedResponse = new ProductResponse(
                1L, "Nuevo Producto", "Descripción", BigDecimal.valueOf(100.00), 10, Category.ELECTRONICS, Status.AVAILABLE
        );

        when(createProductUseCase.createProduct(any(CreateProductRequest.class)))
                .thenReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.name").value(expectedResponse.getName()));
    }

    @Test
    @DisplayName("Debería devolver 400 Bad Request si la creación de producto falla (ej. Request nulo)")
    void shouldReturn400BadRequestIfProductCreationFails() throws Exception {
        // Arrange
        when(createProductUseCase.createProduct(any(CreateProductRequest.class)))
                .thenReturn(null);

        // Act & Assert
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateProductRequest())))
                .andExpect(status().isBadRequest());
    }

    // --- Test para obtener un producto por ID (GET /product/{id}) ---
    @Test
    @DisplayName("Debería obtener un producto por ID y devolver 200 OK")
    void shouldGetProductByIdAndReturn200Ok() throws Exception {
        // Arrange
        Long productId = 1L;
        ProductResponse expectedResponse = new ProductResponse(
                productId, "Producto Obtenido", "Descripción", BigDecimal.valueOf(150.00), 5, Category.BOOKS, Status.AVAILABLE
        );

        when(getProductUseCase.getProductById(productId))
                .thenReturn(Optional.of(expectedResponse));

        // Act & Assert
        mockMvc.perform(get("/product/{id}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.name").value(expectedResponse.getName()));
    }

    @Test
    @DisplayName("Debería devolver 404 Not Found si el producto no existe")
    void shouldReturn404NotFoundIfProductDoesNotExist() throws Exception {
        // Arrange
        Long nonExistentId = 99L;
        when(getProductUseCase.getProductById(nonExistentId))
                .thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/product/{id}", nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // --- Test para obtener todos los productos (GET /product) ---
    @Test
    @DisplayName("Debería obtener todos los productos y devolver 200 OK")
    void shouldGetAllProductsAndReturn200Ok() throws Exception {
        // Arrange
        ProductResponse p1 = new ProductResponse(1L, "P1", "D1", BigDecimal.TEN, 1, Category.ELECTRONICS, Status.AVAILABLE);
        ProductResponse p2 = new ProductResponse(2L, "P2", "D2", BigDecimal.valueOf(20), 2, Category.BOOKS, Status.OUT_OF_STOCK);
        List<ProductResponse> productList = Arrays.asList(p1, p2);

        when(getProductUseCase.getAllProducts()).thenReturn(productList);

        // Act & Assert
        mockMvc.perform(get("/product")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(p1.getName()))
                .andExpect(jsonPath("$[1].name").value(p2.getName()));
    }

    @Test
    @DisplayName("Debería devolver 204 No Content si no hay productos")
    void shouldReturn204NoContentIfNoProducts() throws Exception {
        // Arrange
        when(getProductUseCase.getAllProducts()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/product")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    // --- Test para actualizar un producto (PUT /product/{id}) ---
    @Test
    @DisplayName("Debería actualizar un producto y devolver 200 OK")
    void shouldUpdateProductAndReturn200Ok() throws Exception {
        // Arrange
        Long productId = 1L;
        UpdateProductRequest updateRequest = new UpdateProductRequest(
                "Nombre Actualizado", "Descripción Actualizada", BigDecimal.valueOf(120.00), 12, Category.HOME, Status.AVAILABLE
        );
        ProductResponse updatedResponse = new ProductResponse(
                productId, "Nombre Actualizado", "Descripción Actualizada", BigDecimal.valueOf(120.00), 12, Category.HOME, Status.AVAILABLE
        );

        when(updateProductUseCase.updateProduct(eq(productId), any(UpdateProductRequest.class)))
                .thenReturn(updatedResponse);

        // Act & Assert
        mockMvc.perform(put("/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedResponse.getName()))
                .andExpect(jsonPath("$.stock").value(updatedResponse.getStock()));
    }

    @Test
    @DisplayName("Debería devolver 404 Not Found al actualizar un producto no existente")
    void shouldReturn404NotFoundWhenUpdatingNonExistentProduct() throws Exception {
        // Arrange
        Long nonExistentId = 99L;
        UpdateProductRequest updateRequest = new UpdateProductRequest(
                "Nombre Falso", null, null, null, null, null
        );

        when(updateProductUseCase.updateProduct(eq(nonExistentId), any(UpdateProductRequest.class)))
                .thenThrow(new RuntimeException("Product not found"));

        // Act & Assert
        mockMvc.perform(put("/product/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    // --- Test para eliminar un producto (DELETE /product/{id}) ---
    @Test
    @DisplayName("Debería eliminar un producto y devolver 204 No Content")
    void shouldDeleteProductAndReturn204NoContent() throws Exception {
        // Arrange
        Long productId = 1L;
        when(deleteProductUseCase.deleteProduct(productId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/product/{id}", productId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debería devolver 404 Not Found al eliminar un producto no existente")
    void shouldReturn404NotFoundWhenDeletingNonExistentProduct() throws Exception {
        // Arrange
        Long nonExistentId = 99L;
        when(deleteProductUseCase.deleteProduct(nonExistentId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/product/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }
}