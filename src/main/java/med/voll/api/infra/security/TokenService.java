package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import med.voll.api.domain.usuario.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Indica que a leitura será com base no application.properties
    @Value("${api.security.token.secret}")
    private String secret; // Senha de configuração


    private static final String ISSUER = "API Voll.med";


    // Responsável pela geração de Token
    public String gerarToken(Usuario usuario) {

        // System.out.println(secret); // Printa a senha para ver se está sendo impressa corretamente

        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER) // identificação do app
                    .withSubject(usuario.getLogin()) // Identifica o usuário a quem o token pertence
                    // .withClaim("id", usuario.getId()) // Guarda as informações do usuário
                    .withExpiresAt(dataExpiracao()) // Validade do Token (horas)
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token jwt" + exception);
        }

    }


    // Passa o token e verifica se ele está válido
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }

    }


    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // Expiração de 2h
    }

}
