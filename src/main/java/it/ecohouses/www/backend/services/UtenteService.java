package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.AbitazioneRepository;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final AbitazioneRepository abitazioneRepository;

    @Autowired
    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder, AbitazioneRepository abitazioneRepository) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.abitazioneRepository = abitazioneRepository;
    }

    @Transactional
    public Utente registrazioneUtente(Utente utente) {
        if (utenteRepository.existsByNickname(utente.getNickname())) {
            throw new IllegalArgumentException("Nickname già utilizzato.");
        }
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new IllegalArgumentException("Email già utilizzata.");
        }

        // Criptazione della password
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));

        return utenteRepository.save(utente);
    }

    @Transactional
    public Utente registrazioneGestore(Utente gestore) {
        if (utenteRepository.existsByNickname(gestore.getNickname())) {
            throw new IllegalArgumentException("Nickname già utilizzato.");
        }
        if (utenteRepository.existsByEmail(gestore.getEmail())) {
            throw new IllegalArgumentException("Email già utilizzata.");
        }

        // Criptazione della password
        gestore.setPassword(passwordEncoder.encode(gestore.getPassword()));
        System.out.println("Abitazione gestore = " + gestore.getAbitazione());
        gestore.setAbitazione(gestore.getAbitazione());
        return utenteRepository.save(gestore);
    }

    public Utente autenticazioneUtente(String email, String password) {
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
