package snai.projects.inventario.ubicaciones;

import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.ubicaciones.dtos.RequestUbicacion;
import snai.projects.inventario.ubicaciones.dtos.UbicacionDTO;

import java.util.List;
import java.util.UUID;

public interface UbicacionesService {

    public List<Ubicacion> getUbicaciones();

    public UbicacionDTO createUbicacion(RequestUbicacion request);

    public UbicacionDTO updateUbicacion(RequestUbicacion request, UUID ubicacionId);
}
