package snai.projects.inventario.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import snai.projects.inventario.auth.dtos.AuthRequest;
import snai.projects.inventario.auth.dtos.AuthResponse;
import snai.projects.inventario.ldap.PersonLdapRepository;
import snai.projects.inventario.roles.RolesRepository;
import snai.projects.inventario.roles.domain.DefaultRoles;
import snai.projects.inventario.roles.domain.Rol;
import snai.projects.inventario.security.JwtService;
import snai.projects.inventario.ubicaciones.UbicacionesRepository;
import snai.projects.inventario.ubicaciones.domain.DefaultUbicaciones;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.usuarios.TokenRepository;
import snai.projects.inventario.usuarios.UsuariosRepository;
import snai.projects.inventario.usuarios.domain.Token;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class,MockitoExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthServiceImpTest {

    private AuthService authService;

    @Autowired
    private PersonLdapRepository personLdapRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private UsuariosRepository usuariosRepository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private UbicacionesRepository ubicacionesRepository;


    @BeforeEach
    void setUp(){
        authService = new AuthServiceImp(
            authenticationManager,
                personLdapRepository,
                usuariosRepository,
                tokenRepository,
                jwtService,
                rolesRepository,
                ubicacionesRepository
        );


    }

    @Test
    public void checkAuthentication(){
        //dado un dto de autenticacion
        AuthRequest request = new AuthRequest("dorian.armijos","Snai2012k@");
        Set<Rol> roles = Set.of(new Rol(DefaultRoles.ADMIN.name(), DefaultRoles.ADMIN.getDescription()));
        Set<Ubicacion> ubicacions = Set.of(new Ubicacion(DefaultUbicaciones.PLANTA_CENTRAL.name()));
        UUID uuid = UUID.randomUUID();
        Usuario usuario = new Usuario(uuid,"dorian.armijos","Dorian Armijos",
                "dorian.armijos@atencionintegral.gob.ec",true,roles,ubicacions);
        given(usuariosRepository.findByUsuario("dorian.armijos")).willReturn(Optional.of(usuario));
        //cuando
        AuthResponse response = authService.login(request);


        then(response.jwt()).isNotEmpty();
        verify(tokenRepository).save(new Token(response.jwt(), usuario));
    }

    @Test
    public void checkWrongAuthentication(){
        //dado un dto de autenticacion
        AuthRequest request = new AuthRequest("dorian.armijos","Clavenovale");
        //cuando
        Throwable throwable = catchThrowable( () -> {
            authService.login(request);
        });

        then(throwable)
                .isNotNull()
                .isInstanceOf(AuthenticationException.class);



    }

}
