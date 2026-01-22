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

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void adicionarTelefone(Telefone telefone) {
        this.telefones.add(telefone);
    }

    public PlanoSaude getPlanoSaude() {
        return planoSaude;
    }

    public void setPlanoSaude(PlanoSaude planoSaude) {
        this.planoSaude = planoSaude;
    }

    public List<Prontuario> getProntuarios() {
        return prontuarios;
    }

    public void adicionarProntuario(Prontuario prontuario) {
        this.prontuarios.add(prontuario);
    }
}
