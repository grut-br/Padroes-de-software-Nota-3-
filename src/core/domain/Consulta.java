package core.domain;

import java.time.LocalDateTime;

public class Consulta {

    private LocalDateTime dataHora;
    private Paciente paciente;
    private Medico medico;
    private Prontuario prontuario;

    public Consulta(LocalDateTime dataHora, Paciente paciente, Medico medico) {
        this.dataHora = dataHora;
        this.paciente = paciente;
        this.medico = medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public boolean isPacienteNovo() {
        return paciente.getProntuarios().isEmpty();
    }

    private java.util.List<ConsultaObserver> observers = new java.util.ArrayList<>();

    public void adicionarObserver(ConsultaObserver observer) {
        observers.add(observer);
    }

    private void notificarObservers() {
        ConsultaRealizadaEvent event = new ConsultaRealizadaEvent(this);
        observers.forEach(o -> o.notificar(event));
    }

    public Prontuario realizarConsulta() {
        if (this.prontuario != null) {
            throw new IllegalStateException("Consulta já realizada");
        }
        this.prontuario = new Prontuario();
        paciente.adicionarProntuario(this.prontuario);
        notificarObservers();
        return this.prontuario;
    }
}
