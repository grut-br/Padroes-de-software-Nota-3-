package ports.in;

import core.domain.Prontuario;
import java.util.List;

public interface ConsultarHistoricoProntuarioUseCase {
    List<Prontuario> consultarHistorico(Long pacienteId);
    List<Double> consultarHistoricoPeso(Long pacienteId);
    List<Double> consultarHistoricoAltura(Long pacienteId);
}
