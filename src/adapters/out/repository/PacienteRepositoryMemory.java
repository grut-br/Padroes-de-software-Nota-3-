package adapters.out.repository;

import core.domain.Paciente;
import ports.out.PacienteRepository;

import java.util.*;

public class PacienteRepositoryMemory implements PacienteRepository {

    private final Map<Long, Paciente> pacientes = new HashMap<>();

    @Override
    public void salvar(Long id, Paciente paciente) {
        pacientes.put(id, paciente);
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        return Optional.ofNullable(pacientes.get(id));
    }

    @Override
    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes.values());
    }
}
