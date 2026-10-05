package snai.projects.inventario.recursos.dtos;

import snai.projects.inventario.alcance.Alcance;
import snai.projects.inventario.categorias.dtos.SubcategoriaResponse;
import snai.projects.inventario.usuarios.dtos.UsuarioDTO;

import java.time.Instant;
import java.util.UUID;

public record RecursoDTO(
        UUID recursoId,

        String ip,

        String titulo,

        String clave,

        String nombre,

        String usuario,

        String correo,

        String serieCodigo,

        String descripcion,

        String rangosIp,

        String puerto,

        String nanoId,

        Instant createdAt,

        Instant updatedAt,

        UsuarioDTO user,

        SubcategoriaResponse subcategoria,

        Alcance alcance
)  {
}
