package tests;

import core.domain.*;
import java.time.LocalDateTime;

public class ObserverTest {

    static boolean notificacaoRecebida = false;

    public static void run() {
        System.out.println("\n=== Testes de Observer (Eventos) ===");
        testDisparoEvento();
    }

    private static void testDisparoEvento() {
        Consulta consulta = new Consulta(LocalDateTime.now(), new Paciente("Pedro", "Pai", null, "M"),
                new Medico("Dr. X", "999"));

        // Registrar um observer de teste
        consulta.adicionarObserver(event -> {
            notificacaoRecebida = true;
            System.out.println("   [Teste] Observer chamado para consulta de: "
                    + event.getConsulta().getPaciente().getNomeCrianca());
        });

        TestInfo.assertTrue(!notificacaoRecebida, "Não deve ter notificação antes de realizar consulta");

        consulta.realizarConsulta();

        TestInfo.assertTrue(notificacaoRecebida, "Deve ter recebido notificação após realizar consulta");
    }
}
