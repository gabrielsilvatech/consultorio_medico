package med.voll.api.service.consulta.agendamento;

import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public ValidadorPacienteSemOutraConsultaNoDia(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsulta dados) {
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);

        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetweenAndMotivoCancelamentoIsNull(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if(pacientePossuiOutraConsultaNoDia) {
            throw new ValidacaoException("Paciente já possui uma consuta agendada nesse dia");
        }

    }

}
