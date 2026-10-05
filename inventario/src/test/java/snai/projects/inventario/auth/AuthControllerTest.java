package snai.projects.inventario.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import snai.projects.inventario.auth.dtos.AuthRequest;
import snai.projects.inventario.auth.dtos.AuthResponse;
import snai.projects.inventario.configuration.SecurityConfig;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.BDDAssertions.then;
import org.springframework.context.annotation.Import;
import snai.projects.inventario.security.JwtAuthenticationFilter;
import snai.projects.inventario.usuarios.TokenRepository;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureJsonTesters
@ExtendWith(SpringExtension.class)
@Import(value = {SecurityConfig.class})
public class AuthControllerTest {

    @MockitoBean
    private AuthService authService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AuthRequest> jsonRequest;

    @Autowired
    private JacksonTester<AuthResponse> jsonResponse;


    @MockitoBean
    private  JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockitoBean
    private  AuthenticationProvider authenticationProvider;
    @MockitoBean
    private TokenRepository tokenRepository;

    @Test
    void checkLoginResponse() throws Exception {
        //dado un request ver que me regrese
        AuthRequest request = new AuthRequest("dorian.armijos","Snai.2025");
        AuthResponse expected =  new AuthResponse("JTDDSSDD.SSDAD","DDSSS.DDDL");
        given(authService.login(eq(request))).willReturn(expected);
        //cuando

        MockHttpServletResponse response = mvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.write(request).getJson())
        ).andReturn().getResponse();

        // entonces

        then(response.getStatus()).isEqualTo(200);
       // then(response.getContentAsString()).isEqualTo(jsonResponse.write(expected).getJson());
    }
}
