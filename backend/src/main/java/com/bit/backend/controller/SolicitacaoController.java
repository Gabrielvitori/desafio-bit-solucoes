package com.bit.backend.controller;

import com.bit.backend.dto.SolicitacaoRequestDTO;
import com.bit.backend.dto.SolicitacaoResponseDTO;
import com.bit.backend.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping
    public ResponseEntity<List<SolicitacaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(solicitacaoService.listarTodas());
    }

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<SolicitacaoResponseDTO> criar(
            @PathVariable Long usuarioId,
            @Valid @RequestBody SolicitacaoRequestDTO dto) { // @Valid liga a proteção!

        SolicitacaoResponseDTO novaSolicitacao = solicitacaoService.criarSolicitacao(dto, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSolicitacao);
    }
}