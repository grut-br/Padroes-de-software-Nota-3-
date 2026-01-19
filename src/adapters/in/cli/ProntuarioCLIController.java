package adapters.in.cli;

import core.domain.Exame;
import core.domain.Medicamento;
import core.domain.Prescricao;
import core.domain.Prontuario;
import ports.in.RegistrarProntuarioUseCase;

public class ProntuarioCLIController {

    private final RegistrarProntuarioUseCase registrarProntuarioUseCase;

    public ProntuarioCLIController(RegistrarProntuarioUseCase registrarProntuarioUseCase) {
        this.registrarProntuarioUseCase = registrarProntuarioUseCase;
    }

    public void registrarProntuario(Long consultaId) {

        Prontuario prontuario = new Prontuario();
        prontuario.adicionarExame(new Exame("Hemograma"));
        prontuario.adicionarPrescricao(
                new Prescricao(
                        new Medicamento("Paracetamol"),
                        "10ml",
                        "Oral",
                        "5 dias"));

        registrarProntuarioUseCase.registrar(consultaId, prontuario);
    }
}
