package snai.projects.inventario.ubicaciones;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.ubicaciones.dtos.RequestUbicacion;
import snai.projects.inventario.ubicaciones.dtos.UbicacionDTO;
import snai.projects.inventario.ubicaciones.mappers.UbicacionesMapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UbicacionesServiceImp implements UbicacionesService {
    private final UbicacionesRepository ubicacionesRepository;
    private final UbicacionesMapper ubicacionesMapper;

    @Override
    public List<Ubicacion> getUbicaciones() {
        return ubicacionesRepository.findAllByOrderByUpdatedAtDesc();
    }

    @Override
    public UbicacionDTO createUbicacion(RequestUbicacion request) {
        Ubicacion parentUbicacion = null;
        if (request.parentId() != null) {
            parentUbicacion = ubicacionesRepository.findByUbicacionId(request.parentId()).orElseThrow(
                    () -> new IllegalArgumentException("No existe la ubicacion padre")
            );
        }

        Ubicacion ubicacion = new Ubicacion(request.nombre(), parentUbicacion);
        ubicacionesRepository.save(ubicacion);
        return ubicacionesMapper.toUbicacionDTO(ubicacion);
    }

    @Override
    public UbicacionDTO updateUbicacion(RequestUbicacion request, UUID ubicacionId) {
        Ubicacion ubicacion = ubicacionesRepository.findByUbicacionId(ubicacionId)
                .orElseThrow(() -> new IllegalArgumentException("No existe la ubicacion"));
        Ubicacion parentUbicacion = null;
        if (request.parentId() != null) {
            parentUbicacion = ubicacionesRepository.findByUbicacionId(request.parentId()).orElseThrow(
                    () -> new IllegalArgumentException("No existe la ubicacion padre")
            );
        }

        if (!request.nombre().equals(ubicacion.getNombre())) {
            ubicacion.setNombre(request.nombre());
        }

        if (parentUbicacion != null &&
                !ubicacion.getPadre().getUbicacionId().equals(parentUbicacion.getUbicacionId())) {
            ubicacion.setPadre(parentUbicacion);
        }

        ubicacionesRepository.save(ubicacion);

        return ubicacionesMapper.toUbicacionDTO(ubicacion);
    }


}
