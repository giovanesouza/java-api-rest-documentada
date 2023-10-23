package med.voll.api.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios") // Representa o nome da tabela no BD
@Entity(name = "Usuario") // Representa o nome da Classe/Entidade
// Anotações Lombok
@Getter // Gera os getters
@NoArgsConstructor // Gera um construtor Default (sem argumentos) -> É exigido pelo JPA
@AllArgsConstructor // Gera Construtor com todos os campos
@EqualsAndHashCode(of = "id") // Gera o Equals e HashCode em cima do id.
public class Usuario implements UserDetails { // UserDetails -> interface para

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;

    // Métodos da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // Controle de permissões (perfis)
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); // Perfil único e fixo
    }

    @Override
    public String getPassword() {
        return senha; // senha do usuário
    }

    @Override
    public String getUsername() {
        return login; // Atributo que representa o usuário
    }

    // Configurações de conta bloqueada, bloqueada, expirada...
    @Override
    public boolean isAccountNonExpired() {
        return true; // por padrão vem false
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // por padrão vem false
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // por padrão vem false
    }

    @Override
    public boolean isEnabled() {
        return true; // por padrão vem false
    }

}
