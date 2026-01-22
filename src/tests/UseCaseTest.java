package tests;

import adapters.out.repository.ConsultaRepositoryMemory;
import adapters.out.repository.PacienteRepositoryMemory;
import adapters.out.repository.ProntuarioRepositoryMemory;
import core.domain.*;
import core.usecase.ConsultarHistoricoProntuarioUseCaseImpl;
import core.usecase.ListarConsultasDoDiaUseCaseImpl;
import core.usecase.RegistrarProntuarioUseCaseImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UseCaseTest {

    public static void run() {
        System.out.println("\n=== Testes de Casos de Uso ===");
        testRegistrarProntuario();
        testListarConsultas();
        testConsultarHistoricoProntuario();
    }

    private static void testRegistrarProntuario() {
        ConsultaRepositoryMemory consultaRepo = new ConsultaRepositoryMemory();
        ProntuarioRepositoryMemory prontuarioRepo = new ProntuarioRepositoryMemory();
        RegistrarProntuarioUseCaseImpl useCase = new RegistrarProntuarioUseCaseImpl(consultaRepo, prontuarioRepo);

        Consulta consulta = new Consulta(LocalDateTime.now(), new Paciente("Lucas", "Pai", null, "M"),
                new Medico("Dr. Z", "888"));
        consultaRepo.salvar(10L, consulta);

        Prontuario novoProntuario = new Prontuario();
        useCase.registrar(10L, novoProntuario);

        TestInfo.assertTrue(true, "RegistrarProntuario executado sem erros");
    }

    private static void testListarConsultas() {
        ConsultaRepositoryMemory consultaRepo = new ConsultaRepositoryMemory();
        ListarConsultasDoDiaUseCaseImpl useCase = new ListarConsultasDoDiaUseCaseImpl(consultaRepo);

        LocalDate hoje = LocalDate.now();
        LocalDate amanha = hoje.plusDays(1);

        consultaRepo.salvar(1L, new Consulta(hoje.atTime(10, 0), null, null));
        consultaRepo.salvar(2L, new Consulta(hoje.atTime(14, 0), null, null));
        consultaRepo.salvar(3L, new Consulta(amanha.atTime(10, 0), null, null));

        var listaHoje = useCase.listar(hoje);

        TestInfo.assertEquals(2, listaHoje.size(), "Deve retornar apenas as 2 consultas de hoje");
    }

    private static void testConsultarHistoricoProntuario() {
        PacienteRepositoryMemory pacienteRepo = new PacienteRepositoryMemory();
        ConsultarHistoricoProntuarioUseCaseImpl useCase = new ConsultarHistoricoProntuarioUseCaseImpl(pacienteRepo);

        Paciente paciente = new Paciente("Maria", "Mae", null, "F");

        Prontuario p1 = new Prontuario();
        p1.setPeso(12.5);
        p1.setAltura(0.85);
        paciente.adicionarProntuario(p1);

        Prontuario p2 = new Prontuario();
        p2.setPeso(13.2);
        p2.setAltura(0.88);
        paciente.adicionarProntuario(p2);

        pacienteRepo.salvar(1L, paciente);

        var historico = useCase.consultarHistorico(1L);
        TestInfo.assertEquals(2, historico.size(), "Deve retornar 2 prontuarios no historico");

        var historicoPeso = useCase.consultarHistoricoPeso(1L);
        TestInfo.assertEquals(2, historicoPeso.size(), "Deve retornar 2 pesos no historico");

        var historicoAltura = useCase.consultarHistoricoAltura(1L);
        TestInfo.assertEquals(2, historicoAltura.size(), "Deve retornar 2 alturas no historico");
    }
}
