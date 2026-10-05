package snai.projects.inventario.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import snai.projects.inventario.ldap.PersonLdap;
import snai.projects.inventario.usuarios.UsuariosRepository;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.util.stream.Collectors;

@Configuration
@EnableLdapRepositories(basePackageClasses = PersonLdap.class)
@RequiredArgsConstructor
public class LdapConfiguration {
    private final UsuariosRepository  usuariosRepository;

    @Bean
    ContextSource contextSource(
            @Value("${spring.ldap.username}") final String username,
            @Value("${spring.ldap.password}") final String password
    ) {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://192.168.1.30:389");
        contextSource.setBase("dc=mgpenitenciario,dc=init");
        contextSource.setUserDn(username);
        contextSource.setPassword(password);
        return contextSource;
    }

    @Bean
    LdapTemplate ldapTemplate(ContextSource contextSource) {
        var template = new LdapTemplate(contextSource);
        template.setIgnorePartialResultException(true);

        return template;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            final Usuario user = usuariosRepository.findByUsuario(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsuario())
                    .password("{noop}")
                    .authorities(user.getRoles().stream().map((item) -> new SimpleGrantedAuthority("ROL_" + item.getNombre().toUpperCase())).collect(Collectors.toList()))
                    .build();
        };
    }

    @Bean
    ActiveDirectoryLdapAuthenticationProvider authenticationProvider() {
        return new ActiveDirectoryLdapAuthenticationProvider("mgpenitenciario.init", "ldap://192.168.1.30:389");
    }

    @Bean
    AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource, ActiveDirectoryLdapAuthenticationProvider adProvider) {
        return new ProviderManager(adProvider);
    }


}
