package snai.projects.inventario.ubicaciones.dtos;



import java.time.Instant;
import java.util.UUID;

public record UbicacionDTO(
        UUID ubicacionId,
        String nombre,
        Instant createdAt,
        Instant updatedAt,
        UbicacionDTO padre
) {
}
