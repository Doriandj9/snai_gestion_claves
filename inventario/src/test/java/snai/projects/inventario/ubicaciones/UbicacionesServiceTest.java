package snai.projects.inventario.ubicaciones;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.ubicaciones.dtos.RequestUbicacion;
import snai.projects.inventario.ubicaciones.dtos.UbicacionDTO;
import snai.projects.inventario.ubicaciones.mappers.UbicacionesMapper;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UbicacionesServiceTest {

    private UbicacionesService ubicacionesService;

    @Mock
    private UbicacionesRepository ubicacionesRepository;

    @Mock
    private UbicacionesMapper  ubicacionesMapper;


    @BeforeEach
    void setUp() {
        ubicacionesService = new UbicacionesServiceImp(ubicacionesRepository, ubicacionesMapper);
    }


    @Test
    public  void checkCreateUbicacion(){
        //dado un tdo request
        RequestUbicacion dto = new RequestUbicacion("CPL Manabi", null);
        Ubicacion ubicacion = new Ubicacion(dto.nombre(), null);
        UbicacionDTO expected = new UbicacionDTO(ubicacion.getUbicacionId(),dto.nombre(),null,null,null);
        BDDMockito.given(ubicacionesMapper.toUbicacionDTO(ubicacion)).willReturn(expected);


        UbicacionDTO result  =  ubicacionesService.createUbicacion(dto);

        Mockito.verify(ubicacionesRepository).save(ubicacion);
        BDDAssertions.then(expected).isEqualTo(result);

    }

    @Test
    public void checkCreateUbicacionWithParentUbicacion(){
        UUID parentUbicacionId = UUID.randomUUID();
        Ubicacion parent = new  Ubicacion(parentUbicacionId,"CPL Manabi", null);
        RequestUbicacion dto = new RequestUbicacion("CAI 2", parentUbicacionId);
        Ubicacion ubicacion = new Ubicacion(dto.nombre(), parent);
        UbicacionDTO expectedParent = new UbicacionDTO(parentUbicacionId,"CPL Manabi",null,null,null);
        UbicacionDTO expected = new UbicacionDTO(ubicacion.getUbicacionId(),dto.nombre(),null,null,expectedParent);

        BDDMockito.given(ubicacionesRepository.findByUbicacionId(parentUbicacionId)).willReturn(Optional.of(parent));
        BDDMockito.given(ubicacionesMapper.toUbicacionDTO(ubicacion)).willReturn(expected);


        UbicacionDTO result  =  ubicacionesService.createUbicacion(dto);

        Mockito.verify(ubicacionesRepository).save(ubicacion);
        BDDAssertions.then(expected).isEqualTo(result);
    }

    @Test
    public void checkUpdateUbicacion(){
        UUID ubicacionId = UUID.randomUUID();
        Ubicacion ubicacion = new Ubicacion(ubicacionId,"CPL Manabi", null);
        RequestUbicacion dto = new RequestUbicacion("CPL Cuenca", null);
        UbicacionDTO expected = new UbicacionDTO(ubicacionId, "CPL Cuenca",null,null,null);

        BDDMockito.given(ubicacionesRepository.findByUbicacionId(ubicacionId)).willReturn(Optional.of(ubicacion));
        BDDMockito.given(ubicacionesMapper.toUbicacionDTO(new Ubicacion(ubicacionId,dto.nombre(),null))).willReturn(expected);


        UbicacionDTO result = ubicacionesService.updateUbicacion(dto, ubicacionId);

        Mockito.verify(ubicacionesRepository).save(new Ubicacion(ubicacionId,"CPL Cuenca",null));
        BDDAssertions.then(expected).isEqualTo(result);
    }

}
