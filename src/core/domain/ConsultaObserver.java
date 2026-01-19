package core.domain;

public interface ConsultaObserver {
    void notificar(ConsultaRealizadaEvent event);
}
