package med.voll.api.domain.medico;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(

		@NotBlank(message = "Nome é obrigatório") // Campo não pode estar em branco nem nulo - caso não seja preenchido, print a msg
		String nome,
		
		@NotBlank(message = "Email é obrigatório")
		@Email //
		String email,
		
		@NotBlank(message = "Telefone é obrigatório")
		String telefone,
		
		@NotBlank (message = "CRM é obrigatório")
		@Pattern(regexp = "\\d{4,6}", message = "Formato do CRM é inválido") // Regra para aceitar apenas números por meio de expressão regular - \\d{4,6} = Recebe de 4 a 6 digitos
		String crm,
		
		
		@NotNull(message = "Especialidade é obrigatória")
		Especialidade especialidade,
		
		@NotNull(message = "Dados do endereço são obrigatórios")
		@Valid // indica que é para validar o objeto em questão
        DadosEndereco endereco) { // Campos que estão chegando da requisição

}
