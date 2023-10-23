package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;


//Parâmetros -> Apenas os dados que podem ser atualizados
public record DadosAtualizacaoPaciente(
		
		@NotNull // Apenas este campo é obrigatório
		Long id,
		String nome,
		String telefone,
		DadosEndereco endereco
		
		) {

}
