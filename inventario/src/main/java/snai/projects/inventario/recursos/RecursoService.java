package snai.projects.inventario.recursos;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import snai.projects.inventario.recursos.domain.Recurso;
import snai.projects.inventario.recursos.dtos.RecursoDTO;
import snai.projects.inventario.recursos.dtos.RecursoTableDTO;
import snai.projects.inventario.recursos.dtos.RequestResourceDTO;

import java.util.List;
import java.util.UUID;

public interface RecursoService {

   public List<String> getAttributes();

   public RecursoTableDTO creteRecurso(RequestResourceDTO dto) throws JsonProcessingException;

   public List<RecursoTableDTO> getForUsername(String username) throws JsonProcessingException;

   public Page<RecursoTableDTO> getForUsername(String username, Pageable pageable) throws JsonProcessingException;

   public RecursoDTO updateRecurso(RequestResourceDTO dto, UUID recursoId) throws JsonProcessingException;
}
