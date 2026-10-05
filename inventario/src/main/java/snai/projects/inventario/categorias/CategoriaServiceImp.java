package snai.projects.inventario.categorias;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import snai.projects.inventario.categorias.domain.Categoria;
import snai.projects.inventario.categorias.dtos.CreateCategoriaDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImp implements CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Override
    public Categoria crate(CreateCategoriaDTO dto) {
        Optional<Categoria> isCategoria = categoriaRepository.findByNombreIgnoreCase(dto.nombre());
        if (isCategoria.isPresent()) {
            throw new IllegalArgumentException("Categoria existente");
        }
        Categoria categoria = new Categoria(dto.nombre());
        categoriaRepository.save(categoria);
        return categoria;
    }

    @Override
    public List<Categoria> getAllCategories() {
        return StreamSupport.stream(categoriaRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }


}
