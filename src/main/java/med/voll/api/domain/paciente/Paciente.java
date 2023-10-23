package med.voll.api.domain.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pacientes")
@Entity(name = "Paciente")
public class Paciente {
	
	 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nome;
	    private String email;
	    private String cpf;
	    private String telefone;

	    @Embedded
	    private Endereco endereco;
	    
	    
		private Boolean ativo;
		

	    public Paciente(DadosCadastroPaciente dados) {
	    	this.ativo = true; // Ativa cadastro ao realizar um registro
	        this.nome = dados.nome();
	        this.email = dados.email();
	        this.telefone = dados.telefone();
	        this.cpf = dados.cpf();
	        this.endereco = new Endereco(dados.endereco());
	    }

	    

		public void atualizarInformacoes(@Valid DadosAtualizacaoPaciente dados) {
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
