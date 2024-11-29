package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.model.ConsumoEnergetico;
import it.ecohouses.www.backend.model.ProduzioneEnergia;
import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.services.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/consumi")
    public List<ConsumoEnergetico> getConsumi(@RequestParam Utente utente,
                                              @RequestParam LocalDateTime inizio,
                                              @RequestParam LocalDateTime fine) {
        return dashboardService.visualizzaConsumi(utente, inizio, fine);
    }

    @GetMapping("/produzione")
    public List<ProduzioneEnergia> getProduzione(@RequestParam Utente utente,
                                                 @RequestParam LocalDateTime inizio,
                                                 @RequestParam LocalDateTime fine) {
        return dashboardService.visualizzaProduzione(utente, inizio, fine);
    }
}