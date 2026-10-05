package snai.projects.inventario.categorias;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.categorias.dtos.CreateSubcategoriaDTO;
import snai.projects.inventario.categorias.dtos.SubcategoriaResponse;
import snai.projects.inventario.categorias.dtos.SubcategoriaWithCategoria;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
@RequiredArgsConstructor
public class SubcategoriaController {
    private final SubcategoriaService subcategoriaService;

    @GetMapping
    public ResponseEntity<List<SubcategoriaWithCategoria>> getSubcategorias() throws JsonProcessingException {
        return  ResponseEntity.ok(subcategoriaService.getAllSubcategorias());
    }

    @PostMapping
    public ResponseEntity<SubcategoriaResponse> createSubcategoria(@RequestBody CreateSubcategoriaDTO  request)
            throws JsonProcessingException
    {
        return ResponseEntity.ok(subcategoriaService.createSubcategoria(request));
    }
}
