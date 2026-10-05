package snai.projects.inventario.ubicaciones.mappers;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.ubicaciones.dtos.UbicacionDTO;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UbicacionesMapperTest {
    private UbicacionesMapper ubicacionesMapper;

    @BeforeEach
    public void setUp() {
        ubicacionesMapper = new UbicacionesMapper();
    }

    @Test
    public void testMapUbicacion() {
        UUID id = UUID.randomUUID();
        Ubicacion ubicacion = new Ubicacion(id,"CPL Cuenca",null);
        UbicacionDTO expected = new UbicacionDTO(id, "CPL Cuenca", null,null,null);

        UbicacionDTO result = ubicacionesMapper.toUbicacionDTO(ubicacion);

        BDDAssertions.then(result).isEqualTo(expected);
    }

    @Test
    public void testMapUbicacionParent() {
        UUID parentId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        Ubicacion ubicacionParent = new Ubicacion(parentId,"Zona 5",null);
        Ubicacion ubicacion = new Ubicacion(id,"CPL Cuenca",ubicacionParent);

        UbicacionDTO expectedParent = new UbicacionDTO(parentId, "Zona 5",null,null,null);
        UbicacionDTO expected = new UbicacionDTO(id, "CPL Cuenca", null,null,expectedParent);

        UbicacionDTO result = ubicacionesMapper.toUbicacionDTO(ubicacion);

        BDDAssertions.then(result).isEqualTo(expected);
    }
}
