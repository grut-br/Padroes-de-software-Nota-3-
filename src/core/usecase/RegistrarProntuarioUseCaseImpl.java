package core.usecase;

import core.domain.Consulta;
import core.domain.Prontuario;
import ports.in.RegistrarProntuarioUseCase;
import ports.out.ConsultaRepository;
import ports.out.ProntuarioRepository;

public class RegistrarProntuarioUseCaseImpl implements RegistrarProntuarioUseCase {

    private final ConsultaRepository consultaRepository;
    private final ProntuarioRepository prontuarioRepository;

    public RegistrarProntuarioUseCaseImpl(
            ConsultaRepository consultaRepository,
            ProntuarioRepository prontuarioRepository) {
        this.consultaRepository = consultaRepository;
        this.prontuarioRepository = prontuarioRepository;
    }

    @Override
    public void registrar(Long consultaId, Prontuario prontuario) {
        Consulta consulta = consultaRepository.buscarPorId(consultaId)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        consulta.realizarConsulta();
        prontuarioRepository.salvar(prontuario);
    }
}
