package core.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Prontuario {

    private LocalDateTime dataCriacao;
    private double peso;
    private double altura;
    private String sintomas;
    private String observacaoClinica;

    private List<Prescricao> prescricoes;
    private List<Exame> exames;

    public Prontuario() {
        this.dataCriacao = LocalDateTime.now();
        this.prescricoes = new ArrayList<>();
        this.exames = new ArrayList<>();
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getObservacaoClinica() {
        return observacaoClinica;
    }

    public void setObservacaoClinica(String observacaoClinica) {
        this.observacaoClinica = observacaoClinica;
    }

    public List<Prescricao> getPrescricoes() {
        return prescricoes;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public void adicionarPrescricao(Prescricao prescricao) {
        this.prescricoes.add(prescricao);
    }

    public void adicionarExame(Exame exame) {
        this.exames.add(exame);
    }
}
