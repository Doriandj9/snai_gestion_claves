package snai.projects.inventario.categorias;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snai.projects.inventario.categorias.domain.Categoria;
import snai.projects.inventario.categorias.dtos.CreateCategoriaDTO;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> getAllCategories() {
        return  ResponseEntity.ok(categoriaService.getAllCategories());
    }


    @PostMapping
    public ResponseEntity<Categoria> saveCategoria(@RequestBody CreateCategoriaDTO categoria){

        return ResponseEntity.ok(categoriaService.crate(categoria));
    }
}
