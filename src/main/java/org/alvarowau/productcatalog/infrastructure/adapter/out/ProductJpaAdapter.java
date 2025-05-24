package org.alvarowau.productcatalog.infrastructure.adapter.out;

import org.alvarowau.productcatalog.application.port.out.ProductRepositoryPort;
import org.alvarowau.productcatalog.domain.model.Product;
import org.alvarowau.productcatalog.infrastructure.adapter.out.mapper.ProductJpaMapper;
import org.alvarowau.productcatalog.infrastructure.adapter.out.persistence.ProductJpaEntity;
import org.alvarowau.productcatalog.infrastructure.adapter.out.persistence.ProductJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador JPA que implementa el puerto de repositorio de productos.
 * <p>
 * Esta clase conecta la capa de dominio con la infraestructura JPA,
 * transformando entre entidades de dominio y entidades JPA.
 */
@Component
public class ProductJpaAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository repository;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param repository Repositorio JPA de productos
     */
    public ProductJpaAdapter(ProductJpaRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Implementación con JPA:
     * 1. Convierte la entidad de dominio a entidad JPA
     * 2. Persiste la entidad JPA
     * 3. Convierte la entidad guardada de vuelta a dominio
     */
    @Override
    public Product save(Product product) {
        ProductJpaEntity entity = ProductJpaMapper.toJpaEntity(product);
        ProductJpaEntity savedEntity = repository.save(entity);
        return ProductJpaMapper.toDomainEntity(savedEntity);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Implementación con JPA:
     * 1. Busca la entidad JPA por ID
     * 2. Si existe, la convierte a entidad de dominio
     */
    @Override
    public Optional<Product> findById(Long id) {
        Optional<ProductJpaEntity> optionalEntity = repository.findById(id);
        return optionalEntity.map(ProductJpaMapper::toDomainEntity);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Implementación con JPA:
     * 1. Recupera todas las entidades JPA
     * 2. Convierte cada entidad a dominio
     */
    @Override
    public List<Product> findAll() {
        List<ProductJpaEntity> entities = repository.findAll();
        return ProductJpaMapper.toDomainEntities(entities);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Implementación con JPA:
     * 1. Verifica si existe la entidad
     * 2. Si existe, la elimina y retorna true
     * 3. Si no existe, retorna false
     */
    @Override
    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}