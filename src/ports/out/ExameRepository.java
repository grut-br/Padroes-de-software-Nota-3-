package ports.out;

import core.domain.Exame;
import java.util.List;

public interface ExameRepository {
    List<Exame> listarTodos();
}
