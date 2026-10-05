package snai.projects.inventario.ubicaciones;


import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import snai.projects.inventario.ubicaciones.dtos.RequestUbicacion;
import snai.projects.inventario.ubicaciones.dtos.UbicacionDTO;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class UbicacionControllerTest {

    @MockitoBean
    private UbicacionesService ubicacionesService;

    @Autowired
    private JacksonTester<RequestUbicacion> jsonRequest;

    @Autowired
    private JacksonTester<UbicacionDTO> jsonResponse;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkCreateUbicacion() throws Exception {
        //dado un dto
        RequestUbicacion request = new RequestUbicacion("test", null);
        UbicacionDTO expected = new UbicacionDTO(null,"test", null,null,null);
        BDDMockito.given(ubicacionesService.createUbicacion(ArgumentMatchers.eq(request))).willReturn(expected);

        //cuando
        MockHttpServletResponse response = mockMvc.perform(
            post("/api/locations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest.write(request).getJson())
        ).andReturn().getResponse();

        BDDAssertions.then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        BDDAssertions.then(response.getContentAsString()).isEqualTo(jsonResponse.write(expected).getJson());
    }


    @Test
    public void checkWrongCreateUbicacion() throws Exception {
        //dado un dto
        RequestUbicacion request = new RequestUbicacion(null, null);

        //cuando
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.write(request).getJson())
        ).andReturn().getResponse();

        BDDAssertions.then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void checkUpdateUbicacion() throws Exception {
        RequestUbicacion request = new RequestUbicacion("test", null);
        UUID ubicacionId = UUID.randomUUID();
        UbicacionDTO expected = new UbicacionDTO(ubicacionId,"test", null,null,null);
        BDDMockito.given(ubicacionesService.updateUbicacion(request, ubicacionId)).willReturn(expected);

        MockHttpServletResponse response = mockMvc.perform(
                put("/api/locations/" + ubicacionId.toString() )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.write(request).getJson())
        ).andReturn().getResponse();

        BDDAssertions.then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        BDDAssertions.then(response.getContentAsString()).isEqualTo(jsonResponse.write(expected).getJson());
    }
}
