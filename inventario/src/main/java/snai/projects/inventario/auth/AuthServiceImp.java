package snai.projects.inventario.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snai.projects.inventario.auth.dtos.AuthRequest;
import snai.projects.inventario.auth.dtos.AuthResponse;
import snai.projects.inventario.ldap.PersonLdap;
import snai.projects.inventario.ldap.PersonLdapRepository;
import snai.projects.inventario.roles.RolesRepository;
import snai.projects.inventario.roles.domain.DefaultRoles;
import snai.projects.inventario.roles.domain.Rol;
import snai.projects.inventario.security.JwtService;
import snai.projects.inventario.ubicaciones.domain.DefaultUbicaciones;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.ubicaciones.UbicacionesRepository;
import snai.projects.inventario.usuarios.TokenRepository;
import snai.projects.inventario.usuarios.UsuariosRepository;
import snai.projects.inventario.usuarios.domain.Token;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImp  implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final PersonLdapRepository personLdapRepository;
    private final UsuariosRepository usuariosRepository;
    private final TokenRepository tokenRepository;
    private  final JwtService jwtService;
    private final RolesRepository rolesRepository;
    private final UbicacionesRepository ubicacionesRepository;
    private final static String DOMAIN = "@atencionintegral.gob.ec";


    @Transactional
    @Override
    public AuthResponse login(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.username(),
                            authRequest.password()
                    )
            );
        }catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password");
        }

        Usuario  usuario = usuariosRepository.findByUsuario(authRequest.username())
                .orElseGet(() -> buildNewUsuario(authRequest.username()));

        if (!Hibernate.isInitialized(usuario.getUbicaciones())) {
            Hibernate.initialize(usuario.getUbicaciones());
        }

        Set<String> claimsRoles = usuario.getRoles()
                .stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet());



        Set<String> claimsUbicaciones = usuario.getUbicaciones()
                .stream()
                .map(Ubicacion::getNombre)
                .collect(Collectors.toSet());

        String jwt = jwtService.generateToken(usuario, claimsRoles,claimsUbicaciones);
        String refreshToken = jwtService.generateRefreshToken(usuario, claimsRoles);


        saveTokenUser(usuario,jwt);

        return  new AuthResponse(jwt,refreshToken);
    }


    private  Usuario buildNewUsuario(String username){
        PersonLdap personLdap = personLdapRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("El usuario no existe en el dominio:  "+username)
        );

        String correo = username + DOMAIN;
        Set<Rol> roles = rolesRepository.findByNombreIn(List.of(DefaultRoles.CUSTOMER.name()));
        Set<Ubicacion> ubicaciones = ubicacionesRepository.findByNombreIn(List.of(DefaultUbicaciones.TERRITORIO.name()));

        Usuario usuario = new Usuario(
                username, personLdap.getNombre(),correo,roles,ubicaciones
        );

        usuariosRepository.save(usuario);

        return usuario;
    }


    private void saveTokenUser(Usuario usuario, String token) {
        tokenRepository.save(new Token(token, usuario));
    }

}
