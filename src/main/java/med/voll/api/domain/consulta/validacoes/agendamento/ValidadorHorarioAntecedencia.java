package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

// componente genérico (é carregado na inicialização do projeto)
@Component("ValidadorHorarioAntecedenciaAgendamento") // Renomeia o validador para não dar conflito com a outra classe de mesmo nome no cancelamento
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();

        var diferencaEmMinutos = Duration.between(agora,dataConsulta).toMinutes();

        if(diferencaEmMinutos < 30) {
            throw new ValidacaoException("A consulta deve ser agendada com antecedência mínima de 30 minutos!");
        }

    }


}
