package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
		
		@NotBlank // Campo obrigatório
		String logradouro,
		
		@NotBlank
		String bairro,
		
		@NotBlank
		@Pattern(regexp = "\\d{8}") // 8 digitos
		String cep,
		
		@NotBlank
		String cidade,
		
		@NotBlank
		String uf,
		
		// Campos opcionais
		String complemento,
		String numero) {

}
