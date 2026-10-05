package com.bit.backend.repository;

import com.bit.backend.model.enums.Status;
import com.bit.backend.model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long>, JpaSpecificationExecutor<Solicitacao> {
    List<Solicitacao> findAllByAtivoTrue();
    long countByAtivoTrue();
    long countByStatusAndAtivoTrue(Status status);
    long countBySolicitante_IdAndAtivoTrue(Long usuarioId);
    long countBySolicitante_IdAndStatusAndAtivoTrue(Long usuarioId, Status status);
}