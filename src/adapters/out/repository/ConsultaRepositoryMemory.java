package adapters.out.repository;

import core.domain.Consulta;
import ports.out.ConsultaRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConsultaRepositoryMemory implements ConsultaRepository {

    private final Map<Long, Consulta> consultas = new HashMap<>();

    @Override
    public Optional<Consulta> buscarPorId(Long id) {
        return Optional.ofNullable(consultas.get(id));
    }

    public void salvar(Long id, Consulta consulta) {
        consultas.put(id, consulta);
    }

    @Override
    public java.util.List<Consulta> listarTodas() {
        return new java.util.ArrayList<>(consultas.values());
    }
}
