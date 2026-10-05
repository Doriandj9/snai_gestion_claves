package snai.projects.inventario.recurso;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import snai.projects.inventario.alcance.Alcance;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.configuration.SecurityConfig;
import snai.projects.inventario.recursos.RecursoController;
import snai.projects.inventario.recursos.RecursoService;
import snai.projects.inventario.recursos.dtos.RecursoTableDTO;
import snai.projects.inventario.recursos.dtos.RequestResourceDTO;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.time.Instant;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class RecursoControllerTest {

    @MockitoBean
    private RecursoService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Map<String, Object>> request;

    @Autowired
    private JacksonTester<RecursoTableDTO> recurso;

    @Autowired
    private JacksonTester<List<RecursoTableDTO>> listResponseJson;

    @Test
    public void checkCreateRecurso() throws  Exception {


        Map<String,Object> map = new HashMap<>();
        map.put("titulo","prueba");
        map.put("subcategoriaId", UUID.randomUUID().toString());
        map.put("alcanceId",UUID.randomUUID().toString());
        map.put("nombreUsuario", "test");

        Instant createdAt = Instant.now();
        Instant updatedAt = createdAt.plusSeconds(3600);

        RequestResourceDTO dto = new RequestResourceDTO(
                (String) map.get("ip"),
                (String) map.get("titulo"),
                (String) map.get("clave"),
                (String) map.get("nombre"),
                (String) map.get("usuario"),
                (String) map.get("correo"),
                (String) map.get("serieCodigo"),
                (String) map.get("descripcion"),
                (String) map.get("rangosIp"),
                (String) map.get("puerto"),
                UUID.fromString((String) map.get("subcategoriaId")),
                UUID.fromString((String) map.get("alcanceId")),
                (String) map.get("nombreUsuario"),
                List.of()
        );

        RecursoTableDTO expected = new RecursoTableDTO(
                null,
                null,
                dto.titulo(),
                null,
                dto.nombre(),
                dto.usuario(),
                dto.correo(),
                dto.serieCodigo(),
                dto.descripcion(),
                dto.rangosIp(),
                dto.puerto(),
                null,
                createdAt,
                updatedAt,
                new Usuario("test","Test","tes@ts.com", Set.of(),Set.of()),
                Subcategoria.builder().build(),
                Alcance.builder().build(),
                List.of(),
                Set.of()
        );

        BDDMockito.given(service.creteRecurso(dto)).willReturn(expected);


        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(map).getJson())
        ).andReturn().getResponse();

        BDDAssertions.then(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());

        BDDAssertions.then(response.getContentAsString()).isEqualTo(recurso.write(expected).getJson());
    }

    @Test
    public void  checkUpdateRecurso() throws  Exception {
        /*UUID uuid = UUID.randomUUID();
        Map<String, Object> map = new HashMap<>();
        map.put("ip", "192.168.1.2");
        map.put("titulo", "test");
        map.put("subcategoriaId", UUID.randomUUID().toString());
        map.put("alcanceId", UUID.randomUUID().toString());
        map.put("nombreUsuario", "test");

        RequestResourceDTO dto = new RequestResourceDTO(
                "192.168.1.2",
                "test",
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

        RecursoTableDTO exepected = new RecursoTableDTO(uuid, "192.168.1.2",
                "test",
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
                null,
                null,
                null
        );
        BDDMockito.given(service.updateRecurso(dto, uuid)).willReturn(exepected);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/resources/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(map).getJson())
        ).andReturn().getResponse();

        BDDAssertions.then(response.getStatus()).isEqualTo(HttpStatus.OK.value());*/
    }
}
