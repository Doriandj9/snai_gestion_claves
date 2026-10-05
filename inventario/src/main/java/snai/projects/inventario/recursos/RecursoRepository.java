package snai.projects.inventario.recursos;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import snai.projects.inventario.recursos.domain.Recurso;
import org.springframework.data.domain.Pageable;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.util.List;
import java.util.UUID;

public interface RecursoRepository extends CrudRepository<Recurso, UUID> {

    public List<Recurso> findByUser( Usuario user);

    public Page<Recurso> findByUserOrderByUpdatedAtDesc(Usuario user, Pageable pageable);

}
