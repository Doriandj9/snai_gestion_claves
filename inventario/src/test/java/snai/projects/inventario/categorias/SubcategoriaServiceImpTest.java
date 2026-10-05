package snai.projects.inventario.categorias;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import snai.projects.inventario.categorias.domain.Categoria;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.categorias.dtos.CreateSubcategoriaDTO;
import snai.projects.inventario.categorias.dtos.PayloadSubcategoria;
import snai.projects.inventario.categorias.dtos.SubcategoriaResponse;
import snai.projects.inventario.categorias.mappers.SubcategoriaMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SubcategoriaServiceImpTest {
    private SubcategoriaService subcategoriaService;
    @Mock
    private SubcategoriaRepository subcategoriaRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private SubcategoriaMapper subcategoriaMapper;

    @BeforeEach
    void setUp() {
        subcategoriaService = new SubcategoriaServiceImp(subcategoriaRepository, categoriaRepository, subcategoriaMapper);
    }

    @Test
    void checkCreateSubcategoria() throws JsonProcessingException {
        //dado un dto deveulveme una subcategoria
        UUID categoriaId = UUID.randomUUID();
        String payload = "[{\"columnName\":\"col1\",\"selected\":false,\"isRequired\":true,\"order\":1},{\"columnName\":\"col2\",\"selected\":true,\"isRequired\":false,\"order\":3}]";
        JsonMapper mapper = new JsonMapper();
        List<PayloadSubcategoria> columns = mapper.readValue(payload, new TypeReference<List<PayloadSubcategoria>>() {
        });
        CreateSubcategoriaDTO dto = new CreateSubcategoriaDTO("Internas", payload, categoriaId);
        Categoria categoria = new Categoria(categoriaId, "PC");
        Subcategoria subcategoria = new Subcategoria("Internas", payload, categoria);
        SubcategoriaResponse expected = new SubcategoriaResponse(categoriaId,"Internas",columns);

        given(categoriaRepository.findByCategoriaId(categoriaId)).willReturn(Optional.of(categoria));
        given(subcategoriaMapper.convertForSubcategoria(subcategoria)).willReturn(
                new SubcategoriaResponse(
                        expected.subcategoriaId(),
                        expected.nombre(),
                        columns
                )
        );
        //when
        SubcategoriaResponse result = subcategoriaService.createSubcategoria(dto);

        verify(subcategoriaRepository).save(subcategoria);

        BDDAssertions.then(result).isEqualTo(expected);
    }
}
