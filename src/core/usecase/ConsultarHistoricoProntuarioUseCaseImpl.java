package core.usecase;

import core.domain.Paciente;
import core.domain.Prontuario;
import ports.in.ConsultarHistoricoProntuarioUseCase;
import ports.out.PacienteRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ConsultarHistoricoProntuarioUseCaseImpl implements ConsultarHistoricoProntuarioUseCase {

    private final PacienteRepository pacienteRepository;

    public ConsultarHistoricoProntuarioUseCaseImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public List<Prontuario> consultarHistorico(Long pacienteId) {
        Paciente paciente = pacienteRepository.buscarPorId(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        return paciente.getProntuarios();
    }

    @Override
    public List<Double> consultarHistoricoPeso(Long pacienteId) {
        return consultarHistorico(pacienteId).stream()
                .map(Prontuario::getPeso)
                .filter(peso -> peso > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Double> consultarHistoricoAltura(Long pacienteId) {
        return consultarHistorico(pacienteId).stream()
                .map(Prontuario::getAltura)
                .filter(altura -> altura > 0)
                .collect(Collectors.toList());
    }
}
