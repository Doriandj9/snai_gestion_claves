package snai.projects.inventario.recurso;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sqids.Sqids;
import snai.projects.inventario.alcance.Alcance;
import snai.projects.inventario.alcance.AlcanceRepository;
import snai.projects.inventario.alcance.DefaultAlcance;
import snai.projects.inventario.categorias.SubcategoriaRepository;
import snai.projects.inventario.categorias.domain.Categoria;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.categorias.dtos.PayloadSubcategoria;
import snai.projects.inventario.recursos.RecursoRepository;
import snai.projects.inventario.recursos.RecursoService;
import snai.projects.inventario.recursos.RecursoServiceImp;
import snai.projects.inventario.recursos.domain.Recurso;
import snai.projects.inventario.recursos.dtos.RecursoDTO;
import snai.projects.inventario.recursos.dtos.RecursoTableDTO;
import snai.projects.inventario.recursos.dtos.RequestResourceDTO;
import snai.projects.inventario.recursos.mappers.RecursoMapper;
import snai.projects.inventario.ubicaciones.UbicacionesRepository;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.ubicaciones.dtos.UbicacionDTO;
import snai.projects.inventario.usuarios.UsuariosRepository;
import snai.projects.inventario.usuarios.domain.Usuario;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class RecursoServiceImpTest {

    private RecursoService service;

    @Mock
    private RecursoRepository repository;

    @Mock
    private SubcategoriaRepository subcategoriaRepository;

    @Mock
    private AlcanceRepository alcanceRepository;

    @Mock
    UsuariosRepository usuariosRepository;

    @Mock
    UbicacionesRepository ubicacionesRepository;

    private final Long timeLong = System.currentTimeMillis();


    @BeforeEach
    public void setUp() {
        RecursoMapper mapper = new RecursoMapper();
        service = new RecursoServiceImp(
                repository,
                subcategoriaRepository,
                usuariosRepository,
                alcanceRepository,
                mapper,
                ubicacionesRepository,
                timeLong
        );
    }


    @Test
    public void checkListAttributes() {
        //dado un lista de atributos
        List<String> attributes = List.of("ip", "titulo", "descripcion", "usuario");
        //cuando
        List<String> result = service.getAttributes();

        BDDAssertions.then(result).containsAll(attributes);
        BDDAssertions.then(result).doesNotContainAnyElementsOf(RecursoServiceImp.EXCLUDE_FIELDS);
    }


    @Test
    public void checkCreateRecurso() throws JsonProcessingException {
        //dado un dto de attributos arbitrarios
        String payload = "[{\"columnName\":\"ip\",\"selected\":false,\"isRequired\":false,\"order\":\"\"},{\"columnName\":\"clave\",\"selected\":true,\"isRequired\":false,\"order\":1}]";
        Alcance alcance = new Alcance(UUID.randomUUID(), DefaultAlcance.PUBLICO);
        Categoria categoria = new Categoria(UUID.randomUUID(), "Categoria 1");

        Subcategoria subcategoria = new Subcategoria(UUID.randomUUID(), "Sub 1", payload, categoria);

        Ubicacion ubicacion = this.randomUbicacion("CENTRAL");
        Usuario usuario = new Usuario("test.1", "test", "test@mail.com", null, null);

        RequestResourceDTO request = new RequestResourceDTO(
                "192.1555", "Ip del computador", null, null,
                null, null, null, null,
                null, null, subcategoria.getSubcategoriaId(),
                alcance.getAlcanceId(),
                "test.1",
                List.of(ubicacion.getUbicacionId())
        );

        given(ubicacionesRepository.findByUbicacionId(ubicacion.getUbicacionId())).willReturn(Optional.of(ubicacion));
        given(subcategoriaRepository.findBySubcategoriaId(subcategoria.getSubcategoriaId())).willReturn(Optional.of(subcategoria));
        given(usuariosRepository.findByUsuario("test.1")).willReturn(Optional.of(usuario));
        given(alcanceRepository.findById(alcance.getAlcanceId())).willReturn(Optional.of(alcance));

        Sqids sqids = Sqids.builder()
                .alphabet("FxnXM1kBN6cuhsAvjW3Co7l2RePyY8DwaU04Tzt9fHQrqSVKdpimLGIJOgb5ZE")
                .minLength(15)
                .build();


        String nanoId = sqids.encode(List.of(timeLong));

        Usuario expectedUser = Usuario.builder()
                .userId(usuario.getUserId())
                .usuario(usuario.getUsuario())
                .nombre(usuario.getNombre())
                .activo(usuario.isActivo())
                .build();

        RecursoTableDTO expected = new RecursoTableDTO(
                null,
                request.ip(),
                request.titulo(),
                request.clave(),
                request.nombre(),
                request.usuario(),
                request.correo(),
                request.correo(),
                request.descripcion(),
                request.rangosIp(),
                request.puerto(),
                nanoId,
                null,
                null,
                expectedUser,
                Subcategoria
                        .builder()
                        .subcategoriaId(subcategoria.getSubcategoriaId())
                        .nombre(subcategoria.getNombre())
                        .categoria(categoria)
                        .build(),
                alcance,
                List.of(
                        new PayloadSubcategoria(
                                "ip",
                                false,
                                null,
                                false
                        ),
                        new PayloadSubcategoria(
                                "clave",
                                true,
                                1,
                                false
                        )
                ),
                Set.of(new UbicacionDTO(
                        ubicacion.getUbicacionId(),
                        ubicacion.getNombre(),
                        ubicacion.getCreatedAt(),
                        ubicacion.getUpdatedAt(),
                        null
                ))
        );
        //when


        RecursoTableDTO result = service.creteRecurso(request);

        //then
        BDDAssertions.then(result).isEqualTo(expected);

    }


    @Test
    void checkListRecursosForUser() throws JsonProcessingException {
        //dada un nombre usuario
        String username = "test";
        String payload = "[{\"columnName\":\"ip\",\"selected\":false,\"isRequired\":false,\"order\":\"\"},{\"columnName\":\"clave\",\"selected\":true,\"isRequired\":false,\"order\":1}]";
        JsonMapper mapper = new JsonMapper();
        List<PayloadSubcategoria> columns = mapper.readValue(payload, new TypeReference<List<PayloadSubcategoria>>() {
        });
        Categoria categoria = new Categoria(UUID.randomUUID(), "Categoria 1");
        Subcategoria subcategoria = new Subcategoria(UUID.randomUUID(), "Sub 1", payload, categoria);
        Usuario u = Usuario.builder()
                .userId(UUID.randomUUID())
                .nombre("test")
                .usuario("test.1")
                .activo(false)
                .build();
        Alcance alcance = Alcance.builder()
                .alcanceId(UUID.randomUUID())
                .defaultAlcance(DefaultAlcance.PUBLICO)
                .build();
        Recurso recurso = Recurso.builder()
                .recursoId(UUID.randomUUID())
                .clave("1234")
                .titulo("prueba")
                .user(u)
                .ubicaciones(Set.of())
                .subcategoria(subcategoria)
                .alcance(alcance)
                .build();

        List<RecursoTableDTO> expected = List.of(
                new RecursoTableDTO(
                        recurso.getRecursoId(),
                        null,
                        recurso.getTitulo(),
                        recurso.getClave(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        u,
                        Subcategoria.builder()
                                .subcategoriaId(subcategoria.getSubcategoriaId())
                                .nombre(subcategoria.getNombre())
                                .categoria(categoria)
                                .build(),
                        alcance,
                        columns,
                        Set.of()
                )
        );
        given(usuariosRepository.findByUsuario(username)).willReturn(Optional.of(u));
        given(repository.findByUser(u)).willReturn(List.of(recurso));


        //cuando
        List<RecursoTableDTO> result = service.getForUsername(username);
        BDDAssertions.then(result).isEqualTo(expected);

    }

    @Test
    void checkUpdateRecurso() throws JsonProcessingException {
        UUID uuid = UUID.randomUUID();
        Map<String, List<Ubicacion>> listado = getUbicacionesAndUUID(4);
        List<Ubicacion> ubicaciones = listado.get("ubicaciones");
        List<UUID> uuids = ubicaciones.stream().map(Ubicacion::getUbicacionId).collect(Collectors.toList());
        Categoria cat = new Categoria(UUID.randomUUID(), "Categoria 1");
        Subcategoria subCat = new Subcategoria(UUID.randomUUID(), "Sub1", null, cat);
        Alcance alcance = new Alcance(UUID.randomUUID(), DefaultAlcance.PUBLICO);

        Recurso recurso = new Recurso(
                uuid,
                "ip", "titulo", "clave", "nombre", "usuario",
                null, null, null, null, null,
                null, null, null, alcance, null
        );

        RequestResourceDTO request = new RequestResourceDTO(
                "192.168.2.255",
                "nuevo titulo",
                "1234",
                null,
                null, null, null, null, null, null,
                null, alcance.getAlcanceId(), "test.1", uuids
        );

        for (Ubicacion ubicacion : ubicaciones) {
            given(ubicacionesRepository.findByUbicacionId(ubicacion.getUbicacionId())).willReturn(Optional.of(ubicacion));
        }
        Usuario usuario = new Usuario(UUID.randomUUID(),"test.1", "Pruebas","",true, null, null);
        given(usuariosRepository.findByUsuario("test.1")).willReturn(Optional.of(usuario));
        given(repository.findById(uuid)).willReturn(Optional.of(recurso));
        given(subcategoriaRepository.findBySubcategoriaId(subCat.getSubcategoriaId())).willReturn(Optional.of(subCat));
        //

        RecursoDTO result = service.updateRecurso(request, uuid);

        verify(repository).save(new Recurso(
                uuid,
                "192.168.2.255",
                "nuevo titulo",
                "1234",
                "nombre",
                "usuario", null, null, null, null, null,
                null, null, null, null,new HashSet<>(ubicaciones)
        ));

        BDDAssertions.then(result.ip()).isEqualTo("192.168.2.255");

    }

    private Ubicacion randomUbicacion(String nombre) {
        return new Ubicacion(
                UUID.randomUUID(),
                nombre,
                null
        );
    }

    private Map<String, List<Ubicacion>> getUbicacionesAndUUID(Integer n) {
        Map<String, List<Ubicacion>> result = new HashMap<>();
        List<Ubicacion> ubicaciones = new ArrayList<>();
        n = n == null ? 3 : n;

        for (int i = 0; i < n; i++) {
            ubicaciones.add(randomUbicacion("UB - " + i));
        }
        result.put("ubicaciones", ubicaciones);

        return result;
    }
}
