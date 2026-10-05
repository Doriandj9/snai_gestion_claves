package snai.projects.inventario.categorias.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import snai.projects.inventario.categorias.domain.Categoria;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.categorias.dtos.CreateSubcategoriaDTO;
import snai.projects.inventario.categorias.dtos.PayloadSubcategoria;
import snai.projects.inventario.categorias.dtos.SubcategoriaResponse;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class SubcategoriaMapperTest {
    private  SubcategoriaMapper subcategoriaMapper;


    @BeforeEach
    void setUp() {
        subcategoriaMapper = new SubcategoriaMapper();
    }

    @Test
    public  void  checkSerializeJson() throws JsonProcessingException {
        String payload = "[{\"columnName\":\"col1\",\"selected\":false,\"isRequired\":true,\"order\":1},{\"columnName\":\"col2\",\"selected\":true,\"isRequired\":false,\"order\":\"\"}]";
        Categoria categoria = new Categoria(UUID.randomUUID(),"Test");
        Subcategoria subcategoria = new Subcategoria(UUID.randomUUID(),"Subtest", payload, categoria);
        List<PayloadSubcategoria> expected = List.of(
                new PayloadSubcategoria("col1",false,1,true),
                new PayloadSubcategoria("col2",true,null,false)

        );

        SubcategoriaResponse response = subcategoriaMapper.convertForSubcategoria(subcategoria);

        BDDAssertions.then(response.payload()).isEqualTo(expected);
    }
}
