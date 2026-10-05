package snai.projects.inventario.usuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import snai.projects.inventario.usuarios.mappers.UserMapper;



@ExtendWith(MockitoExtension.class)
public class UsuariosServiceImpTest {
    private UsuariosService usuariosService;

    @Mock
    private UsuariosRepository usuariosRepository;
    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        usuariosService = new UsuariosServiceImp(usuariosRepository);
    }
}
