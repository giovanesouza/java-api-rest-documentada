package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component // componente genérico (é carregado na inicialização do projeto)
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {

        var dataConsulta = dados.data(); // Pega a data que vem do DTO e joga na dataConsulta

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY); // Verifica se o dia de agendamento cai em um Domingo
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7; // Verifica se o horário de agendamento é antes da clínica abrir
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;


        if(domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica!");
        }


    }


}
