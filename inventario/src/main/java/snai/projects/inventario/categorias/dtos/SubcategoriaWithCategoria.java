package snai.projects.inventario.categorias.dtos;

import snai.projects.inventario.categorias.domain.Categoria;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record SubcategoriaWithCategoria(
        UUID subcategoriaId,
        String nombre,
        List<PayloadSubcategoria> payload,
        Instant createdAt,
        Instant updatedAt,
        Categoria categoria
) {
}
