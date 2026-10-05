package snai.projects.inventario.categorias;

import org.springframework.data.repository.CrudRepository;
import snai.projects.inventario.categorias.domain.Subcategoria;

import java.util.Optional;
import java.util.UUID;

public interface SubcategoriaRepository extends CrudRepository<Subcategoria, UUID> {

    Optional<Subcategoria> findBySubcategoriaId(UUID id);
}
