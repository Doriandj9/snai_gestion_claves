package snai.projects.inventario.usuarios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuariosRepository extends CrudRepository<Usuario,String> {

    Optional<Usuario> findByUsuario(String username);

    Optional<Usuario> findByUserId(UUID userId);

}
