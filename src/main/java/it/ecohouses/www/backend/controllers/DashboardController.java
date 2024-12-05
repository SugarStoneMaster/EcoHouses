package it.ecohouses.www.backend.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.ecohouses.www.backend.model.ConsumoEnergetico;
import it.ecohouses.www.backend.model.ProduzioneEnergia;
import it.ecohouses.www.backend.services.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PostMapping("/consumi")
    public List<ConsumoEnergetico> getConsumi(@RequestBody Map<String, Object> requestBody) {
        Long abitazioneId = Long.parseLong(requestBody.get("abitazioneId").toString());
        LocalDateTime inizio = LocalDateTime.parse(requestBody.get("inizio").toString());
        LocalDateTime fine = LocalDateTime.parse(requestBody.get("fine").toString());

        return dashboardService.visualizzaConsumi(abitazioneId, inizio, fine);
    }

    @PostMapping("/produzione")
    public List<ProduzioneEnergia> getProduzione(@RequestBody Map<String, Object> requestBody) {
        Long abitazioneId = Long.parseLong(requestBody.get("abitazioneId").toString());
        LocalDateTime inizio = LocalDateTime.parse(requestBody.get("inizio").toString());
        LocalDateTime fine = LocalDateTime.parse(requestBody.get("fine").toString());

        return dashboardService.visualizzaProduzione(abitazioneId, inizio, fine);
    }
}
