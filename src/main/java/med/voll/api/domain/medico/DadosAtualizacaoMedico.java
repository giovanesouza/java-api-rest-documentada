package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

// Parâmetros -> Apenas os dados que podem ser atualizados
public record DadosAtualizacaoMedico(
		
		@NotNull // Apenas este campo é obrigatório
		Long id,
		String nome,
		String telefone,
		DadosEndereco endereco
		
		) {

}
