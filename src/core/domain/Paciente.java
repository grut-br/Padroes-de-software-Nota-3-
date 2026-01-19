package core.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paciente {

    private String nomeCrianca;
    private String nomeResponsavel;
    private LocalDate dataNascimento;
    private String sexo;
    private Endereco endereco;
    private List<Telefone> telefones;
    private PlanoSaude planoSaude;
    private List<Prontuario> prontuarios;

    public Paciente(String nomeCrianca, String nomeResponsavel, LocalDate dataNascimento, String sexo) {
        this.nomeCrianca = nomeCrianca;
        this.nomeResponsavel = nomeResponsavel;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.telefones = new ArrayList<>();
        this.prontuarios = new ArrayList<>();
    }

    public String getNomeCrianca() {
        return nomeCrianca;
    }

    public void adicionarProntuario(Prontuario prontuario) {
        this.prontuarios.add(prontuario);
    }
}
