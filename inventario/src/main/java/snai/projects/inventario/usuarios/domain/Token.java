package snai.projects.inventario.usuarios.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import snai.projects.inventario.configuration.ProxyJpaConfiguration;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Token {
    @Id
    @GeneratedValue
    private Long tokenId;

    @Column(columnDefinition = "TEXT")
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean expired = false;

    private boolean revoke = false;

    @EqualsAndHashCode.Exclude
    private Instant createdAt;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonSerialize(using = ProxyJpaConfiguration.class)
    private Usuario usuario;


    public Token(String token, Usuario usuario) {
        this(null,token,TokenType.BEARER,false,false,Instant.now(),usuario);
    }
}
