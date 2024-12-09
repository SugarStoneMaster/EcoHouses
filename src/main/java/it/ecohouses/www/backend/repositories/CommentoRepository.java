package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.Commento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentoRepository extends JpaRepository<Commento, Long> {
}
