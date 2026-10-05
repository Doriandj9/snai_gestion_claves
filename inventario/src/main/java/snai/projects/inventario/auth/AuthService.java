package snai.projects.inventario.auth;

import snai.projects.inventario.auth.dtos.AuthRequest;
import snai.projects.inventario.auth.dtos.AuthResponse;

public interface AuthService {

    /**
     * @return AuthResponse con los tokens de acceso
     */

    public AuthResponse login(AuthRequest authRequest);


    public record RegisterUser(
            String jwt,
            String refresh_token
    ){

    }
}
