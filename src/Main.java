import tests.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 INICIANDO BATERIA DE TESTES (JAVA PURO)\n");

        try {
            DomainTest.run();
            AdapterTest.run();
            UseCaseTest.run();
            ObserverTest.run();

            System.out.println("\n✅✅✅ TODOS OS TESTES PASSARAM COM SUCESSO! ✅✅✅");
        } catch (Exception e) {
            System.err.println("\n❌❌❌ ERRO NA EXECUÇÃO DOS TESTES ❌❌❌");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
