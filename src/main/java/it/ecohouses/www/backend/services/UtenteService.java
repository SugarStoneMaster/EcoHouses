package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Utente RegistrazioneUtente(String nickname, String email, String password, String immagineProfilo, boolean gestore) {
        if (utenteRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("L'email è già in uso.");
        }
        if (utenteRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("Il nickname è già in uso.");
        }

        String hashedPassword = passwordEncoder.encode(password);
        Utente utente = new Utente(nickname, email, hashedPassword, immagineProfilo, gestore);

        return utenteRepository.save(utente);
    }

    public Utente AutenticazioneUtente(String email, String password) {
        Optional<Utente> utenteOpt = utenteRepository.findByEmail(email);
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            if (passwordEncoder.matches(password, utente.getPassword())) {
                return utente;
            } else {
                throw new IllegalArgumentException("Password errata.");
            }
        } else {
            throw new IllegalArgumentException("Utente con questa email non trovato.");
        }
    }

}
