package core.domain;

import java.util.ArrayList;
import java.util.List;

public class Prontuario {

    private double peso;
    private double altura;
    private String sintomas;
    private String observacaoClinica;

    private List<Prescricao> prescricoes;
    private List<Exame> exames;

    public Prontuario() {
        this.prescricoes = new ArrayList<>();
        this.exames = new ArrayList<>();
    }

    public void adicionarPrescricao(Prescricao prescricao) {
        this.prescricoes.add(prescricao);
    }

    public void adicionarExame(Exame exame) {
        this.exames.add(exame);
    }
}
