package com.bit.backend.dto;

public record DashboardResumoDTO(
        long total,
        long abertas,
        long emAtendimento,
        long concluidas
) {}
