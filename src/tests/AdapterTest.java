package tests;

import adapters.out.repository.ConsultaRepositoryMemory;
import core.domain.Consulta;
import core.domain.Medico;
import core.domain.Paciente;
import java.time.LocalDateTime;
import java.util.Optional;

public class AdapterTest {

    public static void run() {
        System.out.println("\n=== Testes de Adaptadores ===");
        testConsultaRepository();
    }

    private static void testConsultaRepository() {
        ConsultaRepositoryMemory repo = new ConsultaRepositoryMemory();
        Consulta c = new Consulta(LocalDateTime.now(), new Paciente("Ana", "Mãe", null, "F"),
                new Medico("Dr. House", "000"));

        repo.salvar(1L, c);

        Optional<Consulta> found = repo.buscarPorId(1L);
        TestInfo.assertTrue(found.isPresent(), "Deve encontrar consulta salva");

        TestInfo.assertEquals(1, repo.listarTodas().size(), "Deve listar 1 consulta");
    }
}
