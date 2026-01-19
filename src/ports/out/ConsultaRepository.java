package ports.out;

import core.domain.Consulta;
import java.util.Optional;

public interface ConsultaRepository {
    Optional<Consulta> buscarPorId(Long id);

    java.util.List<Consulta> listarTodas();
}
