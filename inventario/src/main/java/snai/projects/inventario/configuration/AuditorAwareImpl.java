package snai.projects.inventario.configuration;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import java.util.Optional;

import snai.projects.inventario.usuarios.domain.Usuario;

@Slf4j
@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<Usuario> {

    @Override
    public Optional<Usuario> getCurrentAuditor() {
        Usuario usuario = UserContextHolder.getUser();
        return Optional.ofNullable(usuario);
    }
}
