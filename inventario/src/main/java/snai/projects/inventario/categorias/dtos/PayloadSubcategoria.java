package snai.projects.inventario.categorias.dtos;

public record PayloadSubcategoria(
        String columnName,
        boolean selected,
        Integer order,
        boolean isRequired
) {
}
