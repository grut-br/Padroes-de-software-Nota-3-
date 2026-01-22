package core.domain;

public class Telefone {
    private String numero;
    private String responsavel;

    public Telefone(String numero, String responsavel) {
        this.numero = numero;
        this.responsavel = responsavel;
    }

    public String getNumero() {
        return numero;
    }

    public String getResponsavel() {
        return responsavel;
    }
}
