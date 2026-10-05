package snai.projects.inventario.ubicaciones.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import snai.projects.inventario.configuration.EntityAudit;
import snai.projects.inventario.configuration.ProxyJpaConfiguration;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion extends EntityAudit {
    @Id
    private UUID ubicacionId;

    private String nombre;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PADRE_ID")
    private Ubicacion padre;



    public Ubicacion(String nombre){
        this(null,nombre,null);
    }

    public Ubicacion(String nombre, Ubicacion ubicacionPadre){
        this(null,nombre,ubicacionPadre);
    }
}
