package snai.projects.inventario.recursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import snai.projects.inventario.alcance.Alcance;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.configuration.EntityAudit;
import snai.projects.inventario.configuration.ProxyJpaConfiguration;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Recurso extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private UUID recursoId;

    private String ip;

    private String titulo;

    private String clave;

    private String nombre;

    private String usuario;

    private String correo;

    private String serieCodigo;

    private String descripcion;

    private String rangosIp;

    private String puerto;

    private String nanoId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonSerialize(using = ProxyJpaConfiguration.class)
    @JoinColumn(name = "usuario_id")
    private Usuario user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonSerialize(using = ProxyJpaConfiguration.class)
    @JoinColumn(name = "subcategoria_id")
    private Subcategoria subcategoria;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonSerialize(using = ProxyJpaConfiguration.class)
    @JoinColumn(name = "alcance_id")
    private Alcance alcance;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "recursos_ubicaciones",
            joinColumns = @JoinColumn(name = "recursoId"),
            inverseJoinColumns = @JoinColumn(name = "ubicacionId")
    )
    @JsonSerialize(using = ProxyJpaConfiguration.class)
    private Set<Ubicacion> ubicaciones;


    public Recurso(String ip, String titulo, String clave, String nombre, String usuario, String correo, String serieCodigo, String descripcion, String rangosIp, String puerto, String nanoId, Usuario user, Subcategoria subcategoria, Alcance alcance,  Set<Ubicacion> ubicaciones) {
        this(null, ip, titulo, clave, nombre, usuario, correo, serieCodigo, descripcion, rangosIp, puerto, nanoId, user, subcategoria, alcance,  ubicaciones);
    }

}
