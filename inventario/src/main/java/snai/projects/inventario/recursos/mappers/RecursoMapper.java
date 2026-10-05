package snai.projects.inventario.recursos.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import snai.projects.inventario.alcance.Alcance;
import snai.projects.inventario.categorias.domain.Categoria;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.categorias.dtos.PayloadSubcategoria;
import snai.projects.inventario.categorias.dtos.SubcategoriaResponse;
import snai.projects.inventario.categorias.mappers.SubcategoriaMapper;
import snai.projects.inventario.recursos.domain.Recurso;
import snai.projects.inventario.recursos.dtos.RecursoDTO;
import snai.projects.inventario.recursos.dtos.RecursoTableDTO;
import snai.projects.inventario.recursos.dtos.RequestResourceDTO;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.ubicaciones.dtos.UbicacionDTO;
import snai.projects.inventario.usuarios.domain.Usuario;
import snai.projects.inventario.usuarios.dtos.UsuarioDTO;
import snai.projects.inventario.usuarios.mappers.UserMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RecursoMapper {

    public RecursoTableDTO fromRecursoToDTO(Recurso recurso) throws JsonProcessingException {
        Usuario user =  Usuario.builder()
                .userId(recurso.getUser().getUserId())
                .usuario(recurso.getUser().getUsuario())
                .nombre(recurso.getUser().getNombre())
                .activo(recurso.getUser().isActivo())
                .build();

        Subcategoria subcategoria = Subcategoria.builder()
                .subcategoriaId(recurso.getSubcategoria().getSubcategoriaId())
                .nombre(recurso.getSubcategoria().getNombre())
                .categoria(Categoria.builder().nombre(
                        recurso.getSubcategoria().getCategoria().getNombre()
                )
                        .categoriaId(
                                recurso.getSubcategoria().getCategoria().getCategoriaId()
                        )
                        .build())
                .build();
        Alcance alcance = Alcance.builder()
                .alcanceId(recurso.getAlcance().getAlcanceId())
                .defaultAlcance(recurso.getAlcance().getDefaultAlcance())
                .build();
        JsonMapper mapper = new JsonMapper();
        List<PayloadSubcategoria> columns = mapper.readValue(recurso.getSubcategoria().getPayload(), new TypeReference<List<PayloadSubcategoria>>() {});

        Set<UbicacionDTO> ubicaciones = recurso.getUbicaciones().stream().map(
                u -> {
                    UbicacionDTO parent = null;
                    if(u.getPadre() != null) {
                        Ubicacion padre = u.getPadre();
                        parent = new UbicacionDTO(
                                padre.getUbicacionId(),
                                padre.getNombre(),
                                padre.getCreatedAt(),
                                padre.getUpdatedAt(),
                                null
                        );
                    }
                    return  new UbicacionDTO(
                            u.getUbicacionId(),
                            u.getNombre(),
                            u.getCreatedAt(),
                            u.getUpdatedAt(),
                            parent
                    );
                }
        ).collect(Collectors.toSet());

        return new RecursoTableDTO(
                recurso.getRecursoId(),
                recurso.getIp(),
                recurso.getTitulo(),
                recurso.getClave(),
                recurso.getNombre(),
                recurso.getUsuario(),
                recurso.getCorreo(),
                recurso.getSerieCodigo(),
                recurso.getDescripcion(),
                recurso.getRangosIp(),
                recurso.getPuerto(),
                recurso.getNanoId(),
                recurso.getCreatedAt(),
                recurso.getUpdatedAt(),
                user,
                subcategoria,
                alcance,
                columns,
                ubicaciones
        );
    }


    public RecursoDTO fromRecursoResponseDTO(Recurso recurso) throws JsonProcessingException {
        UserMapper userMapper = new UserMapper();
        SubcategoriaMapper subcategoriaMapper = new SubcategoriaMapper();
        UsuarioDTO user = userMapper.convertUsuarioToUsuarioDTO(recurso.getUser());
        SubcategoriaResponse sub = subcategoriaMapper.convertForSubcategoria(recurso.getSubcategoria());
        Alcance alcance = Alcance.builder()
                .alcanceId(recurso.getAlcance().getAlcanceId())
                .defaultAlcance(recurso.getAlcance().getDefaultAlcance())
                .build();
        return new RecursoDTO(
                recurso.getRecursoId(),
                recurso.getIp(),
                recurso.getTitulo(),
                recurso.getClave(),
                recurso.getNombre(),
                recurso.getUsuario(),
                recurso.getCorreo(),
                recurso.getSerieCodigo(),
                recurso.getDescripcion(),
                recurso.getRangosIp(),
                recurso.getPuerto(),
                recurso.getNanoId(),
                recurso.getCreatedAt(),
                recurso.getUpdatedAt(),
                user,
                sub,
                alcance
        );
    }

    public Recurso updateEntityForDto(Recurso recurso, RequestResourceDTO dto) {

        if(dto.ip() != null){
            recurso.setIp(dto.ip());
        }

        if(dto.titulo() != null){
            recurso.setTitulo(dto.titulo());
        }
        if(dto.clave() != null){
            recurso.setClave(dto.clave());
        }

        if(dto.nombre() != null){
            recurso.setNombre(dto.nombre());
        }

        if(dto.usuario() != null){
            recurso.setUsuario(dto.usuario());
        }

        if(dto.correo() != null){
            recurso.setCorreo(dto.correo());
        }
        if(dto.serieCodigo() != null){
            recurso.setSerieCodigo(dto.serieCodigo());
        }

        if(dto.descripcion() != null){
            recurso.setDescripcion(dto.descripcion());
        }

        if(dto.rangosIp() != null){
            recurso.setRangosIp(dto.rangosIp());
        }
        if(dto.rangosIp() != null){
            recurso.setRangosIp(dto.rangosIp());
        }

        if(dto.puerto() != null){
            recurso.setRangosIp(dto.puerto());
        }


        return recurso;
    }
}
