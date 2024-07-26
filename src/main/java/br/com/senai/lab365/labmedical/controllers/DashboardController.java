package br.com.senai.lab365.labmedical.controllers;


import br.com.senai.lab365.labmedical.dtos.dashboard.DashboardDTO;
import br.com.senai.lab365.labmedical.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

//    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "dashboard estatiticas")
        @ApiResponse(responseCode = "200", description = "Dashboard estatiticas")
    public ResponseEntity<DashboardDTO> getDashboard() {
        DashboardDTO dashboardDTO = dashboardService.getDashboardStatistics();
        return ResponseEntity.ok(dashboardDTO);
    }
}