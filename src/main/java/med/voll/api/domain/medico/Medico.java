package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.Endereco;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "medicos") // Representa o nome da tabela no BD
@Entity(name = "Medico") // Representa o nome da Classe/Entidade
// Anotações Lombok
@Getter // Gera os getters
@NoArgsConstructor // Gera um construtor Default (sem argumentos) -> É exigido pelo JPA
@AllArgsConstructor // Gera Construtor com todos os campos
@EqualsAndHashCode(of = "id") // Gera o Equals e HashCode em cima do id.
public class Medico {
	

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String crm;
	
	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;
	
	@Embedded // Fica em uma classe separada, mas no BD é considerado como da tabela de Médicos
	private Endereco endereco;
	
	
	private Boolean ativo;
	

	public Medico(DadosCadastroMedico dados) {
		this.ativo = true; // Ativa cadastro ao realizar um registro
		this.nome = dados.nome();
		this.email = dados.email();
		this.telefone = dados.telefone();
		this.crm = dados.crm();
		this.especialidade = dados.especialidade();
		this.endereco = new Endereco(dados.endereco());
	}


	public void atualizarInformacoes(@Valid DadosAtualizacaoMedico dados) {
		// Atualiza apenas se o campo não estiver nulo
		 if (dados.nome() != null) {
			 // Caso o campo seja preenchido -> atualiza
	            this.nome = dados.nome();
	        }
	        if (dados.telefone() != null) {
	            this.telefone = dados.telefone();
	        }
	        if (dados.endereco() != null) {
	            this.endereco.atualizarInformacoes(dados.endereco());
	        }
		
	}


	// Utilizado para realizar a exclusão lógica
	public void excluir() {
		this.ativo = false;
	}
	
}
