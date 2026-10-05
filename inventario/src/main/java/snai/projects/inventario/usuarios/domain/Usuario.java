package snai.projects.inventario.usuarios.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import snai.projects.inventario.configuration.ProxyJpaConfiguration;
import snai.projects.inventario.roles.domain.Rol;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID userId;

    private String usuario;

    private String nombre;

    private String correo;

    private boolean activo;

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonSerialize(using = ProxyJpaConfiguration.class)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "rolId")
    )
    private Set<Rol> roles;

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonSerialize(using = ProxyJpaConfiguration.class)
    @JoinTable(
            name = "usuarios_ubicacion",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "ubicacion_id")
    )
    private Set<Ubicacion> ubicaciones;


    public Usuario(String usuario,String nombre, String correo, Set<Rol> roles, Set<Ubicacion> ubicaciones) {
        this(null,usuario,nombre,correo,true,roles,ubicaciones);
    }


}
