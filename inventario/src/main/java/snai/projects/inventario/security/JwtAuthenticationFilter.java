package snai.projects.inventario.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import snai.projects.inventario.configuration.UserContextHolder;
import snai.projects.inventario.usuarios.TokenRepository;
import snai.projects.inventario.usuarios.UsuariosRepository;
import snai.projects.inventario.usuarios.domain.Token;
import snai.projects.inventario.usuarios.domain.Usuario;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuariosRepository usuariosRepository;
    private final TokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, ExpiredJwtException {
        try {

            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (header == null || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            final String jwtToken = header.substring(7);
            final String username = jwtService.extractUsername(jwtToken);

            if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
                return;
            }

            Token token = tokenRepository.findByToken(jwtToken).orElse(null);

            if (token == null || token.isExpired() || token.isRevoke()) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            Optional<Usuario> usuario = usuariosRepository.findByUsuario(username);

            if (usuario.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            UserContextHolder.setUser(usuario.get());

            boolean isTokenValid = jwtService.isValidToken(jwtToken, usuario.get());

            if (!isTokenValid) {
                filterChain.doFilter(request, response);
                return;
            }


            var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {

           throw  new ExpiredJwtException(e.getHeader(),e.getClaims(),"Se expiro el token de acceso vuelve a iniciar session");
        } finally {
            UserContextHolder.clear();
            log.info("Filter executed finally, and clear user context");
        }
    }
}