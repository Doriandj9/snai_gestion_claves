package snai.projects.inventario.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import snai.projects.inventario.ubicaciones.domain.Ubicacion;
import snai.projects.inventario.usuarios.domain.Usuario;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private Long expiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    /**
     * Extrae el nombre de usuario el subject que puede ser el correo o un usuario
     * @return String
     */

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     *
     * @param token el token a decodifcar
     * @param claimsResolver el metodo que sirve para extraer un dato del token decodificado
     * @return T generalmente un Mapper se <String, Object>
     * @param <T> lo que pide el generico
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String buildToken(final Map<String, Object> extraData, final Usuario usuario, final long timeExpired) {
        return Jwts.builder()
                .setId(usuario.getUserId().toString())
                .setSubject(usuario.getUsuario())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .addClaims(extraData)
                .setExpiration(new Date(System.currentTimeMillis() + timeExpired))
                .signWith(getSignInKey())
                .compact();
    }

    public String generateToken(final Usuario usuario, final Set<String> roles, final Set<String>  ubicaciones) {
        Map<String, Object> extraData = new HashMap<>();
        extraData.put("roles", roles);
        extraData.put("ubicaciones", ubicaciones);
        extraData.put("name", usuario.getNombre());
        extraData.put("email", usuario.getCorreo());
        return  buildToken(extraData, usuario, expiration);
    }

    public String generateRefreshToken(final Usuario usuario, final Set<String> roles ){
        Map<String, Object> extraData = new HashMap<>();
        extraData.put("roles", roles);
        extraData.put("name", usuario.getNombre());
        return  buildToken(extraData, usuario, refreshTokenExpiration);
    }


    public boolean isValidToken(String token, Usuario usuario) {
        final String username = extractUsername(token);
        return  (username.equals(usuario.getUsuario()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

}
