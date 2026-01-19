package ports.in;

import core.domain.Consulta;
import java.time.LocalDate;
import java.util.List;

public interface ListarConsultasDoDiaUseCase {
    List<Consulta> listar(LocalDate data);
}
