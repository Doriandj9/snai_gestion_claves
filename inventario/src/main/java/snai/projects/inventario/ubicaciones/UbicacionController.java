package snai.projects.inventario.ubicaciones;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.ubicaciones.dtos.RequestUbicacion;
import snai.projects.inventario.ubicaciones.dtos.UbicacionDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class UbicacionController {

    private  final UbicacionesService ubicacionesService;

    @GetMapping
    public ResponseEntity<List<Ubicacion>> findAllByOrderByUpdatedAtDesc() {
        return ResponseEntity.ok(ubicacionesService.getUbicaciones());
    }

    @PostMapping
    public  ResponseEntity<UbicacionDTO>  createUbicacion(@RequestBody @Valid RequestUbicacion request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ubicacionesService.createUbicacion(request));
    }

    @PutMapping("/{ubicacionId}")
    public ResponseEntity<UbicacionDTO> updateUbicaciob(@RequestBody @Valid RequestUbicacion request, @PathVariable UUID ubicacionId){
        return ResponseEntity.ok(ubicacionesService.updateUbicacion(request,ubicacionId));
    }
}
