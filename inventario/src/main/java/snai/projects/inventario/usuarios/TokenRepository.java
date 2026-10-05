package snai.projects.inventario.usuarios;

import org.springframework.data.repository.CrudRepository;
import snai.projects.inventario.usuarios.domain.Token;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByToken(String token);


    Optional<Token> findFirstByUsuario_UserId(UUID user_id);
}
