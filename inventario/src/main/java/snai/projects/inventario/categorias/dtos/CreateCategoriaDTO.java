package snai.projects.inventario.categorias.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoriaDTO(
        @NotBlank
        String nombre
) {
}
