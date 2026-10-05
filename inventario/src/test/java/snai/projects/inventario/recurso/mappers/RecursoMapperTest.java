package snai.projects.inventario.recurso.mappers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import snai.projects.inventario.alcance.Alcance;
import snai.projects.inventario.alcance.DefaultAlcance;
import snai.projects.inventario.categorias.domain.Categoria;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.categorias.dtos.PayloadSubcategoria;
import snai.projects.inventario.recursos.domain.Recurso;
import snai.projects.inventario.recursos.dtos.RecursoTableDTO;
import snai.projects.inventario.recursos.dtos.RequestResourceDTO;
import snai.projects.inventario.recursos.mappers.RecursoMapper;
import snai.projects.inventario.usuarios.domain.Usuario;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
public class RecursoMapperTest {
    private RecursoMapper recursoMapper;

    @Spy
    private JsonMapper jsonMapper;

    @Spy
    private  Recurso recurso;



    @BeforeEach
    public void setUp() {
        recursoMapper = new RecursoMapper();
    }



    @ParameterizedTest
    @ValueSource(strings = {
            "[{\"columnName\":\"ip\",\"selected\":false,\"isRequired\":false,\"order\":\"\"},{\"columnName\":\"clave\",\"selected\":true,\"isRequired\":false,\"order\":1}]",
            "[{\"columnName\":\"usuario\",\"selected\":true,\"isRequired\":true,\"order\":2}]"
    })
    void checkConvertRecursoToDTO(String payload) throws JsonProcessingException {

        List<PayloadSubcategoria> columns = jsonMapper.readValue(payload, new  TypeReference<List<PayloadSubcategoria>>(){});

        Categoria categoria = new Categoria(UUID.randomUUID(),"test");
        Subcategoria subcategoria = new Subcategoria(UUID.randomUUID(),"subt2",payload,categoria);
        Alcance alcance = new Alcance(UUID.randomUUID(), DefaultAlcance.PUBLICO);
        recurso.setSubcategoria(subcategoria);
        recurso.setAlcance(alcance);
        recurso.setUser(new Usuario(UUID.randomUUID(),"dorian", "dorian armijos","d@ma.com",true, Set.of(),Set.of()));


        RecursoTableDTO result = recursoMapper.fromRecursoToDTO(recurso);


        then(result.columns()).isEqualTo(columns);
    }

    @Test
    void checkUpdateRecurso() {
        Recurso recurso = new Recurso(
                "ip","titulo","clave","nombre","usuario",null,null,null,null,null,null,null,null,null,null
        );

        RequestResourceDTO dto = new RequestResourceDTO(
                "192.168.2",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        recursoMapper.updateEntityForDto(recurso, dto);

        then(recurso.getIp()).isEqualTo("192.168.2");
        then(recurso.getTitulo()).isEqualTo("titulo");
        then(recurso.getNombre()).isEqualTo("nombre");
        then(recurso.getUsuario()).isEqualTo("usuario");
    }

}
