package snai.projects.inventario.usuarios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.sqids.Sqids;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuariosServiceImp implements UsuariosService {
    private final UsuariosRepository usuariosRepository;
    private Usuario usuario;

    @Override
    public List<Usuario> allUsers() {
        return StreamSupport.stream(usuariosRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Usuario findUserByUsername(String username) {
        if(this.usuario !=null){
            return this.usuario;
        }

        Usuario user = usuariosRepository.findByUsuario(username).orElse(null);
        if (user != null) {
            this.usuario = user;
        }
        return user;
    }

    @Override
    public Usuario findUserByUsername(String username, boolean refresh) {

        Usuario user = usuariosRepository.findByUsuario(username).orElse(null);

        if (user != null) {
            this.usuario = user;
        }

        return user;


    }
}