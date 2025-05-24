package org.alvarowau.productcatalog.infrastructure.adapter.in;

import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de productos.
 * <p>
 * Expone los endpoints para operaciones CRUD de productos siguiendo las mejores prácticas RESTful.
 * Mapea las peticiones HTTP a los casos de uso correspondientes del sistema.
 */
public interface ProductRestController {

    /**
     * Crea un nuevo producto.
     *
     * @param request Datos necesarios para la creación del producto
     * @return ResponseEntity con el producto creado y estado HTTP 201
     * @apiNote POST /api/products
     */
    @PostMapping
    ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request);

    /**
     * Obtiene un producto específico por su ID.
     *
     * @param id Identificador único del producto
     * @return ResponseEntity con el producto encontrado (200) o no encontrado (404)
     * @apiNote GET /api/products/{id}
     */
    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> getProductById(@PathVariable Long id);

    /**
     * Obtiene todos los productos disponibles.
     *
     * @return ResponseEntity con lista de productos (200) o lista vacía (200) si no hay productos
     * @apiNote GET /api/products
     */
    @GetMapping
    ResponseEntity<List<ProductResponse>> getAllProducts();

    /**
     * Actualiza un producto existente.
     *
     * @param id Identificador del producto a actualizar
     * @param request Datos para la actualización (campos nulos serán ignorados)
     * @return ResponseEntity con el producto actualizado (200) o no encontrado (404)
     * @apiNote PUT /api/products/{id}
     */
    @PutMapping("/{id}")
    ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request);

    /**
     * Elimina un producto existente.
     *
     * @param id Identificador del producto a eliminar
     * @return ResponseEntity vacío con estado 204 (éxito) o 404 (no encontrado)
     * @apiNote DELETE /api/products/{id}
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable Long id);
}