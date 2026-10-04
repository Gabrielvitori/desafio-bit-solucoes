package com.bit.backend.dto;

import com.bit.backend.model.enums.Categoria;
import com.bit.backend.model.enums.Status;
import java.time.LocalDateTime;

public class SolicitacaoResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Categoria categoria;
    private Status status;
    private LocalDateTime dataCriacao;
    private String solicitanteUsername;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getSolicitanteUsername() { return solicitanteUsername; }
    public void setSolicitanteUsername(String solicitanteUsername) { this.solicitanteUsername = solicitanteUsername; }
}