package snai.projects.inventario.categorias.domain;

import jakarta.persistence.*;
import lombok.*;
import snai.projects.inventario.configuration.EntityAudit;

import java.util.UUID;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Categoria extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID categoriaId;

    private String nombre;

    public Categoria(String nombre) {
        this(null, nombre);
    }
}
