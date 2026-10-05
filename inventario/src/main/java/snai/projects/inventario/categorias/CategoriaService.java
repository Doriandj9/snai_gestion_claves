package snai.projects.inventario.categorias;

import snai.projects.inventario.categorias.domain.Categoria;
import snai.projects.inventario.categorias.dtos.CreateCategoriaDTO;

import java.util.List;


public interface CategoriaService {

    public Categoria crate(CreateCategoriaDTO dto);

    public List<Categoria> getAllCategories();
}
