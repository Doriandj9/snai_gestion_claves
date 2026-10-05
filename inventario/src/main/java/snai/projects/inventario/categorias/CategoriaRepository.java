package snai.projects.inventario.categorias;

import org.springframework.data.repository.CrudRepository;
import snai.projects.inventario.categorias.domain.Categoria;

import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository extends CrudRepository<Categoria, UUID> {

    Optional<Categoria> findByNombreIgnoreCase(String nombre);

    Optional<Categoria> findByCategoriaId(UUID categoriaId);
}
