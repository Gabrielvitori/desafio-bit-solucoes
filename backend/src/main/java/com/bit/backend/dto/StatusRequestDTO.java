package com.bit.backend.dto;

import com.bit.backend.model.enums.Status;
import jakarta.validation.constraints.NotNull;

public class StatusRequestDTO {

    @NotNull(message = "O novo status é obrigatório.")
    private Status status;

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}