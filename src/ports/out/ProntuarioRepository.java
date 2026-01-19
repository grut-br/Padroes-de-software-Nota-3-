package ports.out;

import core.domain.Prontuario;

public interface ProntuarioRepository {
    void salvar(Prontuario prontuario);
}
