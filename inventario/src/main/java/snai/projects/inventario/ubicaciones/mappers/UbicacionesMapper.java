package snai.projects.inventario.ubicaciones.mappers;

import org.springframework.stereotype.Component;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.ubicaciones.dtos.UbicacionDTO;

@Component
public class UbicacionesMapper {

    public UbicacionDTO toUbicacionDTO(Ubicacion ubicacion) {
        UbicacionDTO parent = null;
        if(ubicacion.getPadre() !=null){
           parent = new UbicacionDTO(
                    ubicacion.getPadre().getUbicacionId(),
                    ubicacion.getPadre().getNombre(),
                    ubicacion.getPadre().getCreatedAt(),
                    ubicacion.getPadre().getUpdatedAt(),
                    null
            );
        }

        return new UbicacionDTO(
                ubicacion.getUbicacionId(),
                ubicacion.getNombre(),
                ubicacion.getCreatedAt(),
                ubicacion.getUpdatedAt(),
                parent
        );
    }
}
