package com.bit.backend.controller;

import com.bit.backend.dto.SolicitacaoRequestDTO;
import com.bit.backend.dto.SolicitacaoResponseDTO;
import com.bit.backend.dto.StatusRequestDTO;
import com.bit.backend.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.bit.backend.model.enums.Categoria;
import com.bit.backend.model.enums.Status;
import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping
    public ResponseEntity<List<SolicitacaoResponseDTO>> listar(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Categoria categoria,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {

        List<SolicitacaoResponseDTO> resultados = solicitacaoService.pesquisar(status, categoria, titulo, dataInicio, dataFim);
        return ResponseEntity.ok(resultados);
    }

    @PostMapping
    public ResponseEntity<SolicitacaoResponseDTO> criar(@Valid @RequestBody SolicitacaoRequestDTO dto) {
        SolicitacaoResponseDTO novaSolicitacao = solicitacaoService.criarSolicitacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSolicitacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        solicitacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SolicitacaoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusRequestDTO dto) {

        SolicitacaoResponseDTO atualizada = solicitacaoService.atualizarStatus(id, dto);
        return ResponseEntity.ok(atualizada);
    }
}