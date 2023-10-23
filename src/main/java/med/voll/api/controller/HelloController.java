package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // Anotação que representa um Controller de API Rest
@RequestMapping("/hello") // Indica a URL que o Controller vai responder
public class HelloController {

	@GetMapping // Protocolo HTTP que será utilizado para acessar o método
	public String olaMundo() {
		return "Hello World Spring!";
	}
	
}
