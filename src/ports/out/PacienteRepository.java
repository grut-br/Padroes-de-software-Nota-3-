package ports.out;

import core.domain.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository {
    void salvar(Long id, Paciente paciente);
    Optional<Paciente> buscarPorId(Long id);
    List<Paciente> listarTodos();
}
