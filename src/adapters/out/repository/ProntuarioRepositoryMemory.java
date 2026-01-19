package adapters.out.repository;

import core.domain.Prontuario;
import ports.out.ProntuarioRepository;
import java.util.ArrayList;
import java.util.List;

public class ProntuarioRepositoryMemory implements ProntuarioRepository {

    private final List<Prontuario> prontuarios = new ArrayList<>();

    @Override
    public void salvar(Prontuario prontuario) {
        prontuarios.add(prontuario);
    }
}
