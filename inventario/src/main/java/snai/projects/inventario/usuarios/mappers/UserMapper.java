package snai.projects.inventario.usuarios.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import snai.projects.inventario.roles.domain.Rol;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.usuarios.domain.Usuario;
import snai.projects.inventario.usuarios.dtos.UsuarioDTO;

import java.util.stream.Collectors;

@Slf4j
@Component
public class UserMapper {

    public UsuarioDTO convertUsuarioToUsuarioDTO(Usuario usuario) {

        return new UsuarioDTO(
                usuario.getUserId().toString(),
                usuario.getUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet()),
                usuario.getUbicaciones().stream().map(Ubicacion::getNombre).collect(Collectors.toSet())

        );
    }
}
