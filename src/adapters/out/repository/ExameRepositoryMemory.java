package adapters.out.repository;

import core.domain.Exame;
import ports.out.ExameRepository;
import java.util.List;

public class ExameRepositoryMemory implements ExameRepository {

    private final List<Exame> exames;

    public ExameRepositoryMemory(List<Exame> exames) {
        this.exames = exames;
    }

    @Override
    public List<Exame> listarTodos() {
        return exames;
    }
}
