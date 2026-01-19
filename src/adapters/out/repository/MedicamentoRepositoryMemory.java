package adapters.out.repository;

import core.domain.Medicamento;
import ports.out.MedicamentoRepository;
import java.util.List;

public class MedicamentoRepositoryMemory implements MedicamentoRepository {

    private final List<Medicamento> medicamentos;

    public MedicamentoRepositoryMemory(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    @Override
    public List<Medicamento> listarTodos() {
        return medicamentos;
    }
}
