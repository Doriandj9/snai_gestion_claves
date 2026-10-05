package snai.projects.inventario.categorias.dtos;


import snai.projects.inventario.categorias.domain.Categoria;

import java.util.List;
import java.util.UUID;

public record SubcategoriaResponse(
        UUID subcategoriaId,
        String nombre,
        List<PayloadSubcategoria> payload
) {

}
