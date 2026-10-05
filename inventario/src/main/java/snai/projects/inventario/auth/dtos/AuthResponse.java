package snai.projects.inventario.auth.dtos;

public record AuthResponse(
        String jwt,
        String refreshToken
) {
}
