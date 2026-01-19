package ports.out;

import core.domain.Medicamento;
import java.util.List;

public interface MedicamentoRepository {
    List<Medicamento> listarTodos();
}
