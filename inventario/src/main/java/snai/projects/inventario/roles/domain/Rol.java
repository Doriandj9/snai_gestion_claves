package snai.projects.inventario.roles.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import snai.projects.inventario.configuration.ProxyJpaConfiguration;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    private String nombre;

    private  String descripcion;

    //auditoria
    @EqualsAndHashCode.Exclude
    Instant createdAt;

    @EqualsAndHashCode.Exclude
    Instant updatedAt;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonSerialize(using = ProxyJpaConfiguration.class)
    @JoinColumn(name = "created_by")
    private Usuario creador;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    @JsonSerialize(using = ProxyJpaConfiguration.class)
    private Usuario actualizador;

    public Rol(
           final String nombre,
           final String descripcion
    ){
        this(null,nombre,descripcion,Instant.now(),Instant.now(),null,null);
    }
}
