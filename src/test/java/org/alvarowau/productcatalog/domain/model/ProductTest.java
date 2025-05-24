package org.alvarowau.productcatalog.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(
                "Producto de Prueba",
                "Un producto para pruebas unitarias.",
                BigDecimal.valueOf(100.00),
                10,
                Category.ELECTRONICS,
                Status.AVAILABLE
        );
    }

    // --- Tests para el constructor y getters (validación inicial) ---

    @Test
    @DisplayName("Debería crear el producto con valores iniciales válidos")
    void deberiaCrearProductoConValoresInicialesValidos() {
        assertNotNull(product, "El producto no debería ser nulo");
        assertEquals("Producto de Prueba", product.getName(), "El nombre del producto no coincide");
        assertEquals("Un producto para pruebas unitarias.", product.getDescription(), "La descripción del producto no coincide");
        assertEquals(0, BigDecimal.valueOf(100.00).compareTo(product.getPrice()), "El precio debería ser 100.00");
        assertEquals(10, product.getStock(), "El stock del producto no coincide");
        assertEquals(Category.ELECTRONICS, product.getCategory(), "La categoría del producto no coincide");
        assertEquals(Status.AVAILABLE, product.getStatus(), "El estado del producto no coincide");
    }

    @Test
    @DisplayName("Debería aplicar la descripción por defecto si está vacía o es nula")
    void deberiaAplicarDescripcionPorDefectoSiVaciaONula() {
        // Prueba con descripción nula
        Product nullDescProduct = new Product(
                "Producto con Desc Nula", null, BigDecimal.valueOf(50.00), 5, Category.BOOKS, Status.AVAILABLE
        );
        assertEquals("No disponible", nullDescProduct.getDescription(), "La descripción debería ser 'No disponible' para nulo");

        // Prueba con descripción vacía
        Product emptyDescProduct = new Product(
                "Producto con Desc Vacía", "", BigDecimal.valueOf(50.00), 5, Category.BEAUTY, Status.AVAILABLE
        );
        assertEquals("No disponible", emptyDescProduct.getDescription(), "La descripción debería ser 'No disponible' para vacío");
    }

    // --- Tests para lógica de negocio (setters) ---

    @Test
    @DisplayName("Debería establecer el stock correctamente con un valor válido")
    void deberiaEstablecerStockCorrectamenteConValorValido() {
        product.setStock(20);
        assertEquals(20, product.getStock(), "El stock debería actualizarse a 20");
    }

    @Test
    @DisplayName("Debería establecer el stock a 0 si es nulo o negativo")
    void deberiaEstablecerStockACeroSiNuloONegativo() {
        product.setStock(null);
        assertEquals(0, product.getStock(), "El stock debería ser 0 después de establecerlo a nulo");

        product.setStock(-5);
        assertEquals(0, product.getStock(), "El stock debería ser 0 después de establecerlo a negativo");
    }

    @Test
    @DisplayName("Debería establecer el precio correctamente con un valor válido")
    void deberiaEstablecerPrecioCorrectamenteConValorValido() {
        product.setPrice(BigDecimal.valueOf(150.50));
        assertEquals(0, BigDecimal.valueOf(150.50).compareTo(product.getPrice()), "El precio debería ser 150.50");
    }

    @Test
    @DisplayName("Debería establecer el precio a 0 si es nulo o negativo")
    void deberiaEstablecerPrecioACeroSiNuloONegativo() {
        product.setPrice(null);
        assertEquals(0, BigDecimal.ZERO.compareTo(product.getPrice()), "El precio debería ser 0.00 después de establecerlo a nulo");

        product.setPrice(BigDecimal.valueOf(-10.00));
        assertEquals(0, BigDecimal.ZERO.compareTo(product.getPrice()), "El precio debería ser 0.00 después de establecerlo a negativo");
    }

    @Test
    @DisplayName("Debería establecer la categoría por defecto si es nula")
    void deberiaEstablecerCategoriaPorDefectoSiNula() {
        product.setCategory(null);
        assertEquals(Category.DEFAULT, product.getCategory(), "La categoría debería ser DEFAULT cuando es nula");
    }

    @Test
    @DisplayName("Debería establecer el estado correctamente con un valor válido")
    void deberiaEstablecerEstadoCorrectamenteConValorValido() {
        product.setStatus(Status.DISCONTINUED);
        assertEquals(Status.DISCONTINUED, product.getStatus(), "El estado debería ser DISCONTINUED");
    }

    @Test
    @DisplayName("Debería establecer el estado a FUERA_DE_STOCK si el stock es 0 y no está DESCONTINUADO")
    void deberiaEstablecerEstadoFueraDeStockSiStockEsCero() {
        product.setStock(0); // Poner el stock a 0
        product.setStatus(Status.AVAILABLE); // Intentar ponerlo a AVAILABLE
        assertEquals(Status.OUT_OF_STOCK, product.getStatus(), "El estado debería ser OUT_OF_STOCK si el stock es 0 y no está discontinuado");
    }

    @Test
    @DisplayName("Debería permitir el estado DESCONTINUADO aunque el stock sea 0")
    void deberiaPermitirEstadoDescontinuadoAunqueStockSeaCero() {
        product.setStock(0);
        product.setStatus(Status.DISCONTINUED); // Intentar ponerlo a DISCONTINUED
        assertEquals(Status.DISCONTINUED, product.getStatus(), "El estado debería ser DISCONTINUED incluso con stock 0");
    }

    @Test
    @DisplayName("Debería establecer el estado a POR_DEFECTO si es nulo y el stock no es 0")
    void deberiaEstablecerEstadoPorDefectoSiNuloYStockNoCero() {
        product.setStock(10); // Asegurarse de que el stock no es 0
        product.setStatus(null);
        assertEquals(Status.DEFAULT, product.getStatus(), "El estado debería ser DEFAULT si es nulo y el stock no es 0");
    }
}