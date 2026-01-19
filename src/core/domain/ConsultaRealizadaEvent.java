package core.domain;

public class ConsultaRealizadaEvent {
    private final Consulta consulta;

    public ConsultaRealizadaEvent(Consulta consulta) {
        this.consulta = consulta;
    }

    public Consulta getConsulta() {
        return consulta;
    }
}
