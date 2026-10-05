package snai.projects.inventario.categorias.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import snai.projects.inventario.configuration.EntityAudit;
import snai.projects.inventario.configuration.ProxyJpaConfiguration;

import java.util.UUID;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class Subcategoria extends EntityAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID subcategoriaId;

    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonSerialize(using = ProxyJpaConfiguration.class)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Subcategoria(String nombre, String payload, Categoria categoria) {
        this(null, nombre, payload, categoria);
    }

}
