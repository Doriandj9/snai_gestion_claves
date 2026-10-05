package snai.projects.inventario.usuarios;

import snai.projects.inventario.usuarios.domain.Usuario;
import snai.projects.inventario.usuarios.dtos.UsuarioDTO;

import java.util.List;
import java.util.Map;

public interface UsuariosService {

    public List<Usuario> allUsers();

    public Usuario findUserByUsername(String username);

    public Usuario findUserByUsername(String username, boolean refresh);

}
