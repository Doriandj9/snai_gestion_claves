package snai.projects.inventario.configuration;

import snai.projects.inventario.usuarios.domain.Usuario;

public class UserContextHolder {

    private static final ThreadLocal<Usuario> userHolder = new ThreadLocal<>();

    public static void setUser(Usuario usuario) {
        userHolder.set(usuario);
    }

    public static Usuario getUser() {
        return userHolder.get();
    }

    public static void clear() {
        userHolder.remove();
    }
}
