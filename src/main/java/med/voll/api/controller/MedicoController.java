package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@RestController // Representa um Controller de API Rest
@RequestMapping("medicos") // URL que o Controller vai responder
@SecurityRequirement(name = "bearer-key") // Proteção da API documentada - se aplica a todos os métodos
public class MedicoController {
	
	// Pega o repository
	
	@Autowired 
	private MedicoRepository repository;
	
	// Métodos que vão representar as funcionalidades
	
	// Cadastro de médicos
	@PostMapping 
	@Transactional // Transação ativa com o BD
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) { // UriComponentsBuilder -> Cria URI 
		
		var medico = new Medico(dados);
		repository.save(medico); 
		
		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri(); // Endereço da API Encapsulado | Endereço URI
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
	}
	
	
	// Listagem de médicos
	@GetMapping
	public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

		
		// Converte A Entidade Medico (JPA) para DadosListagemMedico (DTO) 
		// return repository.findAll(paginacao).map(DadosListagemMedico::new); // Lista todos os registros (ativos/inativos)
		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); // Lista apenas os registros Ativos
			
		return ResponseEntity.ok(page); // Retorna os dados listados
	}
	
	
	// Atualização de registro
	@PutMapping
	@Transactional // Utilizado para fazer uma escrita no BD (Atualização)
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		
		var medico = repository.getReferenceById(dados.id()); // Busca o registro pelo id			
		
		medico.atualizarInformacoes(dados); // Chama o método para atualizar os dados com base no DTO
		
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); // Retorna as informações atualizadas

	}
	
	
	@DeleteMapping("/{id}") 
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) { // ResponseEntity -> Controla a resposta que será devolvida na requisição
		
		// Exclusão lógica - mantém os dados no banco e inativa perfil - ativo = false;
		var medico = repository.getReferenceById(id); // Busca o registro pelo id
		
		// Seta o atributo para inativo
		medico.excluir();
		
		return ResponseEntity.noContent().build(); // noContent() -> cria um objeto | build() -> consome o objeto
		
	}
	
	
	// Exibe informações de 1 médico
	@GetMapping("/{id}") 
	public ResponseEntity detalhar(@PathVariable Long id) { // ResponseEntity -> Controla a resposta que será devolvida na requisição
		
		// Exclusão lógica - mantém os dados no banco e inativa perfil - ativo = false;
		var medico = repository.getReferenceById(id); // Busca o registro pelo id
				
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); // Detalha as informações do médico 
		
	}
	
	

}
