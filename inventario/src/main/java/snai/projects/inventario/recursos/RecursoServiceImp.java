package snai.projects.inventario.recursos;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sqids.Sqids;
import snai.projects.inventario.alcance.Alcance;
import snai.projects.inventario.alcance.AlcanceRepository;
import snai.projects.inventario.categorias.SubcategoriaRepository;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.recursos.domain.Recurso;
import snai.projects.inventario.recursos.dtos.RecursoDTO;
import snai.projects.inventario.recursos.dtos.RecursoTableDTO;
import snai.projects.inventario.recursos.dtos.RequestResourceDTO;
import snai.projects.inventario.recursos.mappers.RecursoMapper;
import snai.projects.inventario.ubicaciones.UbicacionesRepository;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.usuarios.UsuariosRepository;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecursoServiceImp implements RecursoService {
    public static final List<String> EXCLUDE_FIELDS = List.of(
            "recursoId",
            "nanoId",
            "subcategoria",
            "ubicacion",
            "alcance");
    private final RecursoRepository recursoRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final UsuariosRepository usuariosRepository;
    private final AlcanceRepository alcanceRepository;
    private final RecursoMapper recursoMapper;
    private final UbicacionesRepository ubicacionesRepository;
    private final Long timeLong;


    @Autowired
    public RecursoServiceImp(
            final RecursoRepository recursoRepository,
            final SubcategoriaRepository subcategoriaRepository,
            final UsuariosRepository usuariosRepository,
            final AlcanceRepository alcanceRepository,
            final RecursoMapper recursoMapper,
            final UbicacionesRepository ubicacionesRepository
    ) {
        this.recursoRepository = recursoRepository;
        this.subcategoriaRepository = subcategoriaRepository;
        this.usuariosRepository = usuariosRepository;
        this.alcanceRepository = alcanceRepository;
        this.recursoMapper = recursoMapper;
        this.ubicacionesRepository = ubicacionesRepository;
        this.timeLong = System.currentTimeMillis();
    }

    public RecursoServiceImp(
            final RecursoRepository recursoRepository,
            final SubcategoriaRepository subcategoriaRepository,
            final UsuariosRepository usuariosRepository,
            final AlcanceRepository alcanceRepository,
            final RecursoMapper recursoMapper,
            final UbicacionesRepository ubicacionesRepository,
            final Long timeLong
    ) {
        this.recursoRepository = recursoRepository;
        this.subcategoriaRepository = subcategoriaRepository;
        this.usuariosRepository = usuariosRepository;
        this.alcanceRepository = alcanceRepository;
        this.recursoMapper = recursoMapper;
        this.ubicacionesRepository = ubicacionesRepository;
        this.timeLong = timeLong;
    }


    @Override
    public List<String> getAttributes() {
        Field[] fields = Recurso.class.getDeclaredFields();

        log.info("Atrubutos: {}", (Object) fields);

        List<String> attributes = new ArrayList<>();
        for (Field field : fields) {
            if (EXCLUDE_FIELDS.contains(field.getName())) {
                continue;
            }
            attributes.add(field.getName());
        }

        return attributes;
    }

    @Transactional
    @Override
    public RecursoTableDTO creteRecurso(RequestResourceDTO dto) throws JsonProcessingException {

        Subcategoria subcategoria = subcategoriaRepository.findBySubcategoriaId(dto.subcategoriaId())
                .orElseThrow(() -> new RuntimeException("Subcategoria no relacionada"));
        Usuario usuario = usuariosRepository.findByUsuario(dto.nombreUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alcance alcance = alcanceRepository.findById(dto.alcanceId())
                .orElseThrow(() -> new RuntimeException("Alcance no encontrado"));

        Set<Ubicacion> ubicaciones = dto.ubicaciones()
                .stream()
                .map((ub) -> ubicacionesRepository.findByUbicacionId(ub).orElseThrow(
                        ()-> new IllegalArgumentException("No se encontro una de las ubicacion")
                ))
                .collect(Collectors.toSet());

        String nanoId = getNanoId();

        log.info("NanoId para el recurso: {}", nanoId);

        Recurso recurso = new Recurso(
                dto.ip(),
                dto.titulo(),
                dto.clave(),
                dto.nombre(),
                dto.usuario(),
                dto.correo(),
                dto.serieCodigo(),
                dto.descripcion(),
                dto.rangosIp(),
                dto.puerto(),
                nanoId,
                usuario,
                subcategoria,
                alcance,
                ubicaciones
        );

        recursoRepository.save(recurso);

        return recursoMapper.fromRecursoToDTO(recurso);
    }

    @Transactional
    @Override
    public List<RecursoTableDTO> getForUsername(String username) {
        Usuario user = usuariosRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return recursoRepository.findByUser(user)
                .stream()
                .map((r) -> {
                    try {
                        return recursoMapper.fromRecursoToDTO(r);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();

    }

    @Transactional
    @Override
    public Page<RecursoTableDTO> getForUsername(String username, Pageable pageable) {
        Usuario usuario = usuariosRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return recursoRepository.findByUserOrderByUpdatedAtDesc(usuario, pageable)
                .map(recurso -> {
                    try {
                        // Llama a tu método de mapeo, que incluye la lógica de JsonProcessingException
                        return recursoMapper.fromRecursoToDTO(recurso);
                    } catch (JsonProcessingException e) {
                        // Es buena práctica envolver checked exceptions en RuntimeExceptions si no pueden ser manejadas aquí
                        throw new RuntimeException("Error al procesar JSON durante el mapeo del recurso", e);
                    }
                });
    }

    @Transactional
    @Override
    public RecursoDTO updateRecurso(RequestResourceDTO dto, UUID recursoId) throws JsonProcessingException {

        Recurso recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new IllegalArgumentException("Recurso no encontrado"));

        if (!recurso.getAlcance().getAlcanceId().equals(dto.alcanceId())) {
            recurso.setAlcance(alcanceRepository.findById(dto.alcanceId()).orElseThrow(() -> new IllegalArgumentException("Alcance no encontrado")));
        }

        recurso.setUser(usuariosRepository.findByUsuario(dto.nombreUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado")));

        recurso.setSubcategoria(subcategoriaRepository.findBySubcategoriaId(dto.subcategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Subcategoria no encontrado")));

        Set<Ubicacion> ubicaciones = dto.ubicaciones()
                .stream()
                .map((ub) -> ubicacionesRepository.findByUbicacionId(ub).orElseThrow(
                        ()-> new IllegalArgumentException("No se encontro una de las ubicacion")
                ))
                .collect(Collectors.toSet());

        recurso = recursoMapper.updateEntityForDto(recurso, dto);

        recurso.setUbicaciones(ubicaciones);

        recursoRepository.save(recurso);

        return recursoMapper.fromRecursoResponseDTO(recurso);
    }


    private String getNanoId() {
        Sqids sqids = Sqids.builder()
                .alphabet("FxnXM1kBN6cuhsAvjW3Co7l2RePyY8DwaU04Tzt9fHQrqSVKdpimLGIJOgb5ZE")
                .minLength(15)
                .build();

        return sqids.encode(List.of(timeLong));
    }


}
