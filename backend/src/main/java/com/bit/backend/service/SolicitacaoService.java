package com.bit.backend.service;

import com.bit.backend.model.Solicitacao;
import com.bit.backend.model.Usuario;
import com.bit.backend.model.enums.Status;
import com.bit.backend.repository.SolicitacaoRepository;
import com.bit.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository, UsuarioRepository usuarioRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Solicitacao> listarTodas() {
        return solicitacaoRepository.findAll();
    }

    public Solicitacao criarSolicitacao(Solicitacao solicitacao, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        solicitacao.setSolicitante(usuario);
        solicitacao.setStatus(Status.ABERTO);
        solicitacao.setAtivo(true);
        
        return solicitacaoRepository.save(solicitacao);
    }
}