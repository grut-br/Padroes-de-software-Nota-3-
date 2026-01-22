package tests;

import core.domain.*;
import java.time.LocalDateTime;

public class DomainTest {

    public static void run() {
        System.out.println("\n=== Testes de Domínio ===");
        testConsultaUnica();
    }

    private static void testConsultaUnica() {
        Paciente paciente = new Paciente("Joãozinho", "Maria", null, "M");
        Medico medico = new Medico("Dr. Silva", "12345");
        Consulta consulta = new Consulta(LocalDateTime.now(), paciente, medico);

        Prontuario p1 = consulta.realizarConsulta();
        TestInfo.assertNotNull(p1, "Consulta deve gerar um prontuário");

        try {
            consulta.realizarConsulta();
            throw new RuntimeException("❌ FALHA: Deveria ter lançado exceção ao realizar consulta duplicada");
        } catch (IllegalStateException e) {
            TestInfo.assertTrue(true, "Impediu consulta duplicada corretamente");
        }
    }
}
