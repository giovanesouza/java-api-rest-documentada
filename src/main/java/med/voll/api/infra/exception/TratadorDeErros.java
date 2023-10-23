package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Responsável pelo tratamento de erros da aplicação
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class) // Indica quando o método deve ser chamado
    public ResponseEntity tratarErro404() {

        return ResponseEntity.notFound().build();

    }


     @ExceptionHandler(MethodArgumentNotValidException.class) // Indica quando o método deve ser chamado (quando houver campo inválido)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) { // parâmetro/objeto para pegar os erros que aconteceram
        var erros = ex.getFieldErrors(); // Guarda os erros que aconteceram em uma lista

        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList()); // converte lista de erros para DadosErrosValidacao | new -> chama o construtor do record
    }

    @ExceptionHandler(ValidacaoException.class) // Indica quando o método deve ser chamado (quando houver campo inválido)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) { // parâmetro/objeto para pegar os erros que aconteceram

        return ResponseEntity.badRequest().body(ex.getMessage()); // Exibe a exception ao realizar a requisição

    }


    // record (DTO) interno para converter a lista do tratamento de erro 400 com campos e mensagem
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
            // erro.getField() -> dá o nome do campo
            // erro.getDefaultMessage() -> dá a mensagem para o campo específico
        }
    }


}
