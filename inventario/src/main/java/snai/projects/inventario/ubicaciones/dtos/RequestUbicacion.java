package snai.projects.inventario.ubicaciones.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record RequestUbicacion(
        @NotBlank
        String nombre,
        UUID parentId
) {
}
