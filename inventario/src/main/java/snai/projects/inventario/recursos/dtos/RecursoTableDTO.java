package snai.projects.inventario.recursos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import snai.projects.inventario.alcance.Alcance;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.categorias.dtos.PayloadSubcategoria;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.ubicaciones.dtos.UbicacionDTO;
import snai.projects.inventario.usuarios.domain.Usuario;


import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public record RecursoTableDTO(

        UUID recursoId,

        String ip,

        @NotNull @NotBlank
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

        @NotNull
        Usuario user,

        @NotNull
        Subcategoria subcategoria,

        @NotNull
        Alcance alcance,

        @NotNull
        List<PayloadSubcategoria> columns,

        @NotNull
        Set<UbicacionDTO> ubicaciones
) {


}
