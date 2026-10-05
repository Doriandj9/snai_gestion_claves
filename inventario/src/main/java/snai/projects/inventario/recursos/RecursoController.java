package snai.projects.inventario.recursos;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import snai.projects.inventario.recursos.domain.Recurso;
import snai.projects.inventario.recursos.dtos.RecursoDTO;
import snai.projects.inventario.recursos.dtos.RecursoTableDTO;
import snai.projects.inventario.recursos.dtos.RequestResourceDTO;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class RecursoController {
    private final RecursoService service;

    @GetMapping("/attributes")
    public ResponseEntity<List<String>> getAttributes() {
        return ResponseEntity.ok(service.getAttributes());
    }

    @Transactional
    @PostMapping
    public ResponseEntity<RecursoTableDTO> create(@RequestBody Map<String, Object> request)
            throws JsonProcessingException {
        RequestResourceDTO dto = validateRequest(request);

        return ResponseEntity.ok(service.creteRecurso(dto));
    }

    @GetMapping(params = {"username", "page", "size"})
    public ResponseEntity<Page<RecursoTableDTO>> allResources(
            @RequestParam(name = "username") final String username,
            @RequestParam(name = "page") final int page,
            @RequestParam(name = "size") final int size
    ) throws JsonProcessingException {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getForUsername(username, pageable));
    }

    @GetMapping(params = {"username"})
    public ResponseEntity<List<RecursoTableDTO>> allResources(
            @RequestParam(name = "username") final String username
    ) throws JsonProcessingException {
        return ResponseEntity.ok(service.getForUsername(username));
    }


    @PutMapping("/{id}")
    public ResponseEntity<RecursoDTO> update(@RequestBody Map<String, Object> request, @PathVariable UUID id)
            throws JsonProcessingException {
        RequestResourceDTO dto = validateRequest(request);

        return ResponseEntity.ok(service.updateRecurso(dto, id));
    }


    private RequestResourceDTO validateRequest(Map<String, Object> request) {

        if (!request.containsKey("titulo")) {
            throw new IllegalArgumentException("El titulo no puede ser nulo");
        }
        if (!request.containsKey("subcategoriaId")) {
            throw new IllegalArgumentException("La subcategoria no puede ser nulo");
        }
        if (!request.containsKey("alcanceId")) {
            throw new IllegalArgumentException("El Alcance no puede ser nulo");
        }
        if (!request.containsKey("nombreUsuario")) {
            throw new IllegalArgumentException("El nombre de usuario no puede ser nulo");
        }

        if(!request.containsKey("ubicaciones")) {
            throw new IllegalArgumentException("Las ubicaciones no puede ser nulas");
        }


        if(request.get("ubicaciones") == null) {
            throw new IllegalArgumentException("Las ubicaciones no puede ser nulas");
        }


        List<UUID> ubicaciones = ((List<String>) request.get("ubicaciones"))
                .stream()
                .map(UUID::fromString)
                .toList();

        return new RequestResourceDTO(
                (String) request.get("ip"),
                (String) request.get("titulo"),
                (String) request.get("clave"),
                (String) request.get("nombre"),
                (String) request.get("usuario"),
                (String) request.get("correo"),
                (String) request.get("serieCodigo"),
                (String) request.get("descripcion"),
                (String) request.get("rangosIp"),
                (String) request.get("puerto"),
                UUID.fromString((String) request.get("subcategoriaId")),
                UUID.fromString((String) request.get("alcanceId")),
                (String) request.get("nombreUsuario"),
                ubicaciones
        );
    }
}
