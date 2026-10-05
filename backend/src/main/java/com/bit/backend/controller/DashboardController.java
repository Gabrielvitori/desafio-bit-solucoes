package com.bit.backend.controller;

import com.bit.backend.dto.DashboardResumoDTO;
import com.bit.backend.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResumoDTO> obterDashboard() {
        DashboardResumoDTO resumo = dashboardService.obterDadosDashboard();
        return ResponseEntity.ok(resumo);
    }
}