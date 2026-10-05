package snai.projects.inventario.roles;

import org.springframework.data.repository.CrudRepository;
import snai.projects.inventario.roles.domain.Rol;

import java.util.List;
import java.util.Set;

public interface RolesRepository extends CrudRepository<Rol, Long> {

    Set<Rol> findByNombreIn(List<String> nombres);
}
