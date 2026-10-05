package snai.projects.inventario.alcance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Alcance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID alcanceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre")
    private DefaultAlcance defaultAlcance;
}
