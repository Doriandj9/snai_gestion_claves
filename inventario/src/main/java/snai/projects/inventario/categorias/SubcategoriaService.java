package snai.projects.inventario.categorias;

import com.fasterxml.jackson.core.JsonProcessingException;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.categorias.dtos.CreateSubcategoriaDTO;
import snai.projects.inventario.categorias.dtos.SubcategoriaResponse;
import snai.projects.inventario.categorias.dtos.SubcategoriaWithCategoria;

import java.util.List;

public interface SubcategoriaService {

    SubcategoriaResponse createSubcategoria(CreateSubcategoriaDTO dto) throws JsonProcessingException;

    List<SubcategoriaWithCategoria> getAllSubcategorias() throws JsonProcessingException;
}
