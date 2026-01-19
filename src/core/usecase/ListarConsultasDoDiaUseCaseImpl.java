package core.usecase;

import core.domain.Consulta;
import ports.in.ListarConsultasDoDiaUseCase;
import ports.out.ConsultaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class ListarConsultasDoDiaUseCaseImpl implements ListarConsultasDoDiaUseCase {

    private final ConsultaRepository consultaRepository;

    public ListarConsultasDoDiaUseCaseImpl(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public List<Consulta> listar(LocalDate data) {
        List<Consulta> todas = consultaRepository.listarTodas();
        List<Consulta> filtradas = new ArrayList<>();

        for (Consulta c : todas) {
            if (c.getDataHora().toLocalDate().equals(data)) {
                filtradas.add(c);
            }
        }

        return filtradas;
    }
}
