package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
//Anotações Lombok
@Getter // Gera os getters
@NoArgsConstructor // Gera um construtor Default (sem argumentos) -> É exigido pelo JPA
@AllArgsConstructor // Gera Construtor com todos os campos
public class Endereco {

	private String logradouro;
	private String bairro;
	private String cep;
	private String numero;
	private String complemento;
	private String cidade;
	private String uf;
	


	public Endereco(DadosEndereco dados) {
		this.logradouro = dados.logradouro();
		this.bairro = dados.bairro();
		this.cep = dados.cep();
		this.numero = dados.numero();
		this.complemento = dados.complemento();
		this.cidade = dados.cidade();
		this.uf = dados.uf();
	}


	public void atualizarInformacoes(DadosEndereco dados) {
		// Atualiza apenas se o campo não estiver nulo
		 if (dados.logradouro() != null) {
	            this.logradouro = dados.logradouro();
	        }
		// Caso o campo seja preenchido -> atualiza
	        if (dados.bairro() != null) {
	            this.bairro = dados.bairro();
	        }
	        
	        if (dados.cep() != null) {
	            this.cep = dados.cep();
	        }
	        
	        if (dados.uf() != null) {
	            this.uf = dados.uf();
	        }
	        
	        if (dados.cidade() != null) {
	            this.cidade = dados.cidade();
	        }
	        
	        if (dados.numero() != null) {
	            this.numero = dados.numero();
	        }
	        
	        if (dados.complemento() != null) {
	            this.complemento = dados.complemento();
	        }
		
	}
	
	
}
