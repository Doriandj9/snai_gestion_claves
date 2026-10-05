package snai.projects.inventario.categorias;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snai.projects.inventario.categorias.domain.Categoria;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.categorias.dtos.CreateSubcategoriaDTO;
import snai.projects.inventario.categorias.dtos.SubcategoriaResponse;
import snai.projects.inventario.categorias.dtos.SubcategoriaWithCategoria;
import snai.projects.inventario.categorias.mappers.SubcategoriaMapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
@Service
@RequiredArgsConstructor
public class SubcategoriaServiceImp implements SubcategoriaService {
    private  final SubcategoriaRepository subcategoriaRepository;
    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaMapper subcategoriaMapper;

    @Transactional
    @Override
    public SubcategoriaResponse createSubcategoria(CreateSubcategoriaDTO dto) throws JsonProcessingException {
        Categoria categoria = categoriaRepository.findByCategoriaId(dto.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada"));
        Subcategoria subcategoria = new Subcategoria(
                dto.nombre(), dto.payload(),categoria
        );
        subcategoriaRepository.save(subcategoria);
        log.info("Subcategria {}", subcategoria);

        subcategoria.setCategoria(categoria);

        return subcategoriaMapper.convertForSubcategoria(subcategoria);
    }

    @Transactional
    @Override
    public List<SubcategoriaWithCategoria> getAllSubcategorias() throws JsonProcessingException {

        return subcategoriaMapper.convertSubcategoriaWithCategorias(StreamSupport
                .stream(subcategoriaRepository.findAll().spliterator(), false)
                .toList());
    }
}
