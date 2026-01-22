package adapters.out.notification;

import core.domain.ConsultaObserver;
import core.domain.ConsultaRealizadaEvent;
import java.time.format.DateTimeFormatter;

public class NotificacaoService implements ConsultaObserver {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void notificar(ConsultaRealizadaEvent event) {
        String nomePaciente = event.getConsulta().getPaciente().getNomeCrianca();
        String nomeMedico = event.getConsulta().getMedico().getNome();
        String dataHora = event.getConsulta().getDataHora().format(FORMATTER);

        System.out.println("=== NOTIFICACAO DE CONSULTA REALIZADA ===");
        System.out.println("Paciente: " + nomePaciente);
        System.out.println("Medico: " + nomeMedico);
        System.out.println("Data/Hora: " + dataHora);
        System.out.println("==========================================");

        enviarEmail(nomePaciente, dataHora);
        enviarSMS(event.getConsulta().getPaciente(), dataHora);
    }

    private void enviarEmail(String nomePaciente, String dataHora) {
        System.out.println("[EMAIL] Enviando lembrete de retorno para " + nomePaciente);
    }

    private void enviarSMS(core.domain.Paciente paciente, String dataHora) {
        if (!paciente.getTelefones().isEmpty()) {
            String telefone = paciente.getTelefones().get(0).getNumero();
            System.out.println("[SMS] Enviando confirmacao para " + telefone);
        }
    }
}
