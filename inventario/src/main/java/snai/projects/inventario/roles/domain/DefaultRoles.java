package snai.projects.inventario.roles.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DefaultRoles {

    ADMIN("ADMINISTRADOR"),
    CUSTOMER("CLIENTE"),
    MANAGER("DIRECTOR"),
    INFRASTRUCTURE("INFRAESTRUCTURA"),
    TECHNICAL("TECNICO"),
    RECEPTION("SECRETARIA");

    private final String description;

}
