package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

// Responsável pela persistência - necessário inserir dois objetos: 1º -> Tipo da entidade | 2º -> tipo do atributo da chave primária da entidade
// Herda todos os métodos que têm nessa interface
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // Monta query para listar apenas os perfis ativos (o fato do nome ser em inglês o spring consegue montar automaticamente
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    // Nome em Português -> Tem que montar a consulta na mão. Utilizar a anotação @Query("") com a sintaxe do JPQL (Java Persistense Query Language)
    // Text Block -> 3 aspas para escrever o código sem a necessidade de concatenar com o sinal de +.
    @Query("""
                        select m from Medico m
                        where
                        m.ativo = 1
                        and
                        m.especialidade = :especialidade
                        and
                        m.id not in(
                            select c.medico.id from Consulta c
                            where
                            c.data = :data
                    and
                            c.motivoCancelamento is null
                        )
                        order by rand()
                        limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);


    @Query("""
            			select m.ativo
            			from Medico m
            			where
            			m.id = :id
            """)
    boolean findAtivoById(Long id);
}
