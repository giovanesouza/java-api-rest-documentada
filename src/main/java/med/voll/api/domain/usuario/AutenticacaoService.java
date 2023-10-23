package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Contém a lógica de autenticação do projeto
@Service // Indica que é um componente do tipo serviço -> Neste caso, serviço de autenticação
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;


    // Método que o spring chama automaticamente sempre que realizar o login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }


}
