package snai.projects.inventario.usuarios.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import snai.projects.inventario.roles.domain.Rol;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;

import java.util.Set;

public record UsuarioDTO(
        @NotBlank
        String id,
        @NotBlank
        String usuario,
        @NotBlank
        String nombre,
        @NotBlank @Email
        String correo,
        Set<String> roles,
        Set<String> ubicaciones
) {
}
