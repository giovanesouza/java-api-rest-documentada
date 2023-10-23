package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Configurações de segurança
@Configuration
@EnableWebSecurity // Indica que haverá personalização nas configurações de segurança
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    // Configuração no processo de autenticação
    @Bean // @Bean -> Expõe o retorno do método
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception  {

        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                //.requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
                //.requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

        // .csrf().disable() -> Desabilita a proteção contra ataques do tipo Cross-Site Request Forgery (pq será utilizado o token que já é uma proteção contra esse tipo de ataque)
        // .sessionManagement() -> Mostra o gerenciamento da sessão
        // .sessionCreationPolicy() -> Política de criação de sessão
        // .and().authorizeRequests() -> configura como será a autorização das requisiçõe
        // .requestMatchers(HttpMehod.POST, "/login").permitAll() -> As requisição de login fica liberada e as demais são bloqueadas
        //   .anyRequest().authenticated() -> Bloqueia as demais rotas - necessário estar autenticado
        // .and().addFilterBefore() -> Adiciona o filtro do 1º parametro antes do Filtro de segurança
        // .requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN") e .requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN") ->
        // -> Indica ao Spring Security que as requisições do tipo DELETE para as URLs /medicos e /pacientes somente podem ser executadas por usuários autenticados e cujo perfil de acesso seja ADMIN.

        // .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() -> Endereços para documentação (Público)

    }


    // Cria objeto objeto AuthenticationManager para ser utilizado na autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Configura o algoritmo de hash de senha para BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() { // PasswordEncoder -> Classe que representa o algoritmo de hash em si
        return new BCryptPasswordEncoder();
    }



}
