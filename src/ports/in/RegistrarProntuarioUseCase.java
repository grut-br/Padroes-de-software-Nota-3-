package ports.in;

import core.domain.Prontuario;

public interface RegistrarProntuarioUseCase {
    void registrar(Long consultaId, Prontuario prontuario);
}
