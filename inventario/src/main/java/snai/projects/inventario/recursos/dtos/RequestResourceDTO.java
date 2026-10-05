package snai.projects.inventario.recursos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record RequestResourceDTO(
        String ip,

        @NotNull(message = "Ingrese un titulo") @NotBlank(message = "No puede esta sin contenido")
        String titulo,

        String clave,

        String nombre,

        String usuario,

        String correo,

        String serieCodigo,

        String descripcion,

        String rangosIp,

        String puerto,

        UUID subcategoriaId,

        UUID alcanceId,

        @NotBlank
        String nombreUsuario,

        @NotNull
        List<UUID> ubicaciones
) {
}
