package adapters.out.notification;

import core.domain.ConsultaObserver;
import core.domain.ConsultaRealizadaEvent;

public class NotificacaoService implements ConsultaObserver {

    @Override
    public void notificar(ConsultaRealizadaEvent event) {
        System.out.println(
                "Notificação: Consulta realizada para o paciente " +
                        event.getConsulta().getPaciente().getNomeCrianca());
    }
}
