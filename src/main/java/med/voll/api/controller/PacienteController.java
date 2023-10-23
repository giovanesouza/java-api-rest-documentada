package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.DadosAtualizacaoPaciente;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.DadosDetalhamentoPaciente;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

@RestController // Representa um Controller de API Rest
@RequestMapping("pacientes") // URL que o Controller vai responder
@SecurityRequirement(name = "bearer-key") // Proteção da API documentada - se aplica a todos os métodos
public class PacienteController {

	@Autowired // Indica para o spring que ele será o responsável em instanciar o repository.
	private PacienteRepository repository;

	// Métodos que vão representar as funcionalidades

	// Cadastro de pacientes

	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {

		var paciente = new Paciente(dados);
		repository.save(paciente);

		var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));

	}

	// Listagem de pacientes
	@GetMapping
	public ResponseEntity<Page<DadosListagemPaciente>> listar(
			@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {

		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
		
	    return ResponseEntity.ok(page);
					
	}

	// Atualização de registro
	@PutMapping
	@Transactional // Utilizado para fazer uma escrita no BD (Atualização)
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {

		var paciente = repository.getReferenceById(dados.id()); // Busca o registro pelo id

		paciente.atualizarInformacoes(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));

	}

	@DeleteMapping("/{id}") 
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {

		var paciente = repository.getReferenceById(id); 

		paciente.excluir();
		
		return ResponseEntity.noContent().build();

	}
	
	
	// Lista um Paciente
	@GetMapping("/{id}")
	public ResponseEntity detalhar(@PathVariable Long id) {
	    var paciente = repository.getReferenceById(id);
	    return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
	}

	
	

}
