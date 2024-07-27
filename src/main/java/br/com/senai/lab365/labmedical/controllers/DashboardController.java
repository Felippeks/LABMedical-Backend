package br.com.senai.lab365.labmedical.controllers;


import br.com.senai.lab365.labmedical.dtos.dashboard.DashboardDTO;
import br.com.senai.lab365.labmedical.exceptions.responses.ApiResponseOK;
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

    @GetMapping
    @Operation(summary = "dashboard estatiticas")
    @ApiResponse(responseCode = "200", description = "Dashboard estatiticas")
    public ResponseEntity<ApiResponseOK<DashboardDTO>> getDashboard() {
        DashboardDTO dashboardDTO = dashboardService.getDashboardStatistics();
        ApiResponseOK<DashboardDTO> response = new ApiResponseOK<>("Dashboard estatiticas", dashboardDTO);
        return ResponseEntity.ok(response);
    }
}