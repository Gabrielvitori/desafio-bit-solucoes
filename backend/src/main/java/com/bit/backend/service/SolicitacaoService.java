package com.bit.backend.service;

import com.bit.backend.dto.SolicitacaoRequestDTO;
import com.bit.backend.dto.SolicitacaoResponseDTO;
import com.bit.backend.dto.StatusRequestDTO;
import com.bit.backend.model.Solicitacao;
import com.bit.backend.model.Usuario;
import com.bit.backend.model.enums.Status;
import com.bit.backend.repository.SolicitacaoRepository;
import com.bit.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

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

    public List<SolicitacaoResponseDTO> listarTodas() {
        return solicitacaoRepository.findAllByAtivoTrue()
                .stream()
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

        solicitacao.setAtivo(false);
        solicitacaoRepository.save(solicitacao);
    }

    public SolicitacaoResponseDTO atualizarStatus(Long id, StatusRequestDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada com o ID: " + id));

        solicitacao.setStatus(dto.getStatus());
        Solicitacao salva = solicitacaoRepository.save(solicitacao);

        return mapearParaDTO(salva);
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