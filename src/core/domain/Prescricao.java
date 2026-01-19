package core.domain;

public class Prescricao {
    private Medicamento medicamento;
    private String dosagem;
    private String administracao;
    private String tempoUso;

    public Prescricao(Medicamento medicamento, String dosagem, String administracao, String tempoUso) {
        this.medicamento = medicamento;
        this.dosagem = dosagem;
        this.administracao = administracao;
        this.tempoUso = tempoUso;
    }
}
