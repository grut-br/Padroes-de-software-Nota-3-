package tests;

public class TestInfo {
    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("❌ FALHA: " + message);
        }
        System.out.println("✅ SUCESSO: " + message);
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) {
            System.out.println("✅ SUCESSO: " + message);
            return;
        }
        if (expected == null || !expected.equals(actual)) {
            throw new RuntimeException("❌ FALHA: " + message + " [Esperado: " + expected + ", Atual: " + actual + "]");
        }
        System.out.println("✅ SUCESSO: " + message);
    }

    public static void assertNotNull(Object actual, String message) {
        if (actual == null) {
            throw new RuntimeException("❌ FALHA: " + message + " [Esperado não nulo]");
        }
        System.out.println("✅ SUCESSO: " + message);
    }
}
