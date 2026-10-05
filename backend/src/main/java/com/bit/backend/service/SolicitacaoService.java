package com.bit.backend.service;

import com.bit.backend.dto.SolicitacaoRequestDTO;
import com.bit.backend.dto.SolicitacaoResponseDTO;
import com.bit.backend.dto.StatusRequestDTO;
import com.bit.backend.model.Solicitacao;
import com.bit.backend.model.Usuario;
import com.bit.backend.model.enums.Role;
import com.bit.backend.model.enums.Status;
import com.bit.backend.repository.SolicitacaoRepository;
import com.bit.backend.repository.UsuarioRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.bit.backend.model.enums.Categoria;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository, UsuarioRepository usuarioRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoResponseDTO> pesquisar(Status status, Categoria categoria, String titulo, LocalDateTime dataInicio, LocalDateTime dataFim) {
        Usuario usuarioLogado = getUsuarioLogado();
        Long usuarioIdFiltro = (usuarioLogado.getRole() == Role.ROLE_ADMIN) ? null : usuarioLogado.getId();

        Specification<Solicitacao> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isTrue(root.get("ativo")));

            if (usuarioIdFiltro != null) {
                predicates.add(cb.equal(root.get("solicitante").get("id"), usuarioIdFiltro));
            }

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (categoria != null) {
                predicates.add(cb.equal(root.get("categoria"), categoria));
            }
            if (titulo != null && !titulo.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
            }
            if (dataInicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataCriacao"), dataInicio));
            }
            if (dataFim != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataCriacao"), dataFim));
            }

            query.orderBy(cb.desc(root.get("dataCriacao")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return solicitacaoRepository.findAll(spec).stream()
                .map(this::mapearParaDTO)
                .collect(Collectors.toList());
    }

    public SolicitacaoResponseDTO criarSolicitacao(SolicitacaoRequestDTO dto) {
        Usuario usuarioLogado = (Usuario) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setTitulo(dto.getTitulo());
        solicitacao.setDescricao(dto.getDescricao());
        solicitacao.setCategoria(dto.getCategoria());
        solicitacao.setSolicitante(usuarioLogado);
        solicitacao.setStatus(Status.ABERTO);
        solicitacao.setAtivo(true);

        Solicitacao salva = solicitacaoRepository.save(solicitacao);
        return mapearParaDTO(salva);
    }

    public void excluir(Long id) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada com o ID: " + id));

        Usuario usuarioLogado = getUsuarioLogado();

        if (!solicitacao.getSolicitante().getId().equals(usuarioLogado.getId())
                && usuarioLogado.getRole() != Role.ROLE_ADMIN) {
            throw new RuntimeException("Você não tem permissão para excluir esta solicitação.");
        }

        if (solicitacao.getStatus() != Status.ABERTO) {
            throw new RuntimeException("Apenas solicitações com status ABERTO podem ser excluídas.");
        }

        solicitacao.setAtivo(false);
        solicitacaoRepository.save(solicitacao);
    }

    public SolicitacaoResponseDTO atualizarStatus(Long id, StatusRequestDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada com o ID: " + id));

        Usuario usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.getRole() != Role.ROLE_ADMIN) {
            throw new RuntimeException("Apenas administradores podem alterar o status de uma solicitação.");
        }

        solicitacao.setStatus(dto.getStatus());
        Solicitacao salva = solicitacaoRepository.save(solicitacao);

        return mapearParaDTO(salva);
    }

    private Usuario getUsuarioLogado() {
        return (Usuario) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    }

    private SolicitacaoResponseDTO mapearParaDTO(Solicitacao solicitacao) {
        SolicitacaoResponseDTO dto = new SolicitacaoResponseDTO();
        dto.setId(solicitacao.getId());
        dto.setTitulo(solicitacao.getTitulo());
        dto.setDescricao(solicitacao.getDescricao());
        dto.setCategoria(solicitacao.getCategoria());
        dto.setStatus(solicitacao.getStatus());
        dto.setDataCriacao(solicitacao.getDataCriacao());
        dto.setSolicitanteUsername(solicitacao.getSolicitante().getUsername());
        return dto;
    }
}