package com.bit.backend.service;

import com.bit.backend.dto.DashboardResumoDTO;
import com.bit.backend.model.enums.Role;
import com.bit.backend.model.enums.Status;
import com.bit.backend.model.Usuario;
import com.bit.backend.repository.SolicitacaoRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final SolicitacaoRepository repository;

    public DashboardService(SolicitacaoRepository repository) {
        this.repository = repository;
    }

    public DashboardResumoDTO obterDadosDashboard() {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (usuarioLogado.getRole() == Role.ROLE_ADMIN) {
            long total = repository.countByAtivoTrue();
            long abertas = repository.countByStatusAndAtivoTrue(Status.ABERTO);
            long emAtendimento = repository.countByStatusAndAtivoTrue(Status.EM_ATENDIMENTO);
            long concluidas = repository.countByStatusAndAtivoTrue(Status.CONCLUIDO);

            return new DashboardResumoDTO(total, abertas, emAtendimento, concluidas);
        } else {
            Long id = usuarioLogado.getId();
            long total = repository.countBySolicitante_IdAndAtivoTrue(id);
            long abertas = repository.countBySolicitante_IdAndStatusAndAtivoTrue(id, Status.ABERTO);
            long emAtendimento = repository.countBySolicitante_IdAndStatusAndAtivoTrue(id, Status.EM_ATENDIMENTO);
            long concluidas = repository.countBySolicitante_IdAndStatusAndAtivoTrue(id, Status.CONCLUIDO);

            return new DashboardResumoDTO(total, abertas, emAtendimento, concluidas);
        }
    }
}