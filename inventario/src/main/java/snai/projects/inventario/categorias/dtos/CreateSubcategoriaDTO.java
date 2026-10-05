package snai.projects.inventario.categorias.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateSubcategoriaDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String payload,
        @NotBlank
        UUID categoryId
) {
}
