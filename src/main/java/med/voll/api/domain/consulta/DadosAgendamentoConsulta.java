package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future // Tem que ser uma data no futuro (do dia atual para frente)
        LocalDateTime data,

        Especialidade especialidade // Opcional (quando o id do médico não for informado)

        ) {


}
