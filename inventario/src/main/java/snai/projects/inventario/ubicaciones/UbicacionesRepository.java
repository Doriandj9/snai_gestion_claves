package snai.projects.inventario.ubicaciones;

import org.springframework.data.repository.CrudRepository;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UbicacionesRepository extends CrudRepository<Ubicacion, Long> {

    Set<Ubicacion> findByNombreIn(List<String> nombres);

    List<Ubicacion> findAllByOrderByUpdatedAtDesc();

    Optional<Ubicacion> findByUbicacionId(UUID ubicacionId);
}
