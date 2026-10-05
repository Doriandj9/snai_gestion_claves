package snai.projects.inventario.alcance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/scopes")
@RequiredArgsConstructor
public class AlcanceController {
    private final AlcanceRepository alcanceRepository;

    @GetMapping
    public ResponseEntity<List<Alcance>> getAlcance() {
        return ResponseEntity.ok(
                StreamSupport.stream(alcanceRepository.findAll().spliterator(), false)
                        .toList()
        );
    }
}
