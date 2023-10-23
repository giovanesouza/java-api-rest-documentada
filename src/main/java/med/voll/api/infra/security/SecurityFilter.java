package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component // Representa um componente genérico que será carregado automaticamente
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;


    // Métdodo que é chamado quando o filtro for executado (A CADA REQUISIÇÃO)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // System.out.println("FILTRO CHAMADO!");

        var tokenJWT = recuperarToken(request);

        //System.out.println(tokenJWT);

        // Verifica se o token está correto e faz autenticação

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            // Pega o objeto do usuário
            var usuario = repository.findByLogin(subject);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        filterChain.doFilter(request, response); // Continua o fluxo da requisição

    }


    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization"); // recupera o token

        // Spring verifica se o usuário está logado
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "").trim(); // Substitui a palavra Bearer por nada (string vazia)
        }

        return null;

    }


}
