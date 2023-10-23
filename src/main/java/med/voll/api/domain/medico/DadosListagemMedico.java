package med.voll.api.domain.medico;

// DTO com os campos que serão devolvidos na listagem dos médicos
public record DadosListagemMedico(
		Long id,
		String nome,
		String email,
		String crm,
		Especialidade especialidade
		) {

	
	public DadosListagemMedico(Medico medico) {
		// chama o próprio construtor record
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
	}
	
}
