package org.alvarowau.productcatalog.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad ProductJpaEntity.
 * <p>
 * Proporciona operaciones CRUD básicas y de paginación para la gestión de productos
 * en la base de datos, heredando toda la funcionalidad de JpaRepository.
 * <p>
 * Las implementaciones concretas son generadas automáticamente por Spring Data JPA.
 */
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {

}