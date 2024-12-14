package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.DispositivoIoT;
import it.ecohouses.www.backend.repositories.AbitazioneRepository;
import it.ecohouses.www.backend.repositories.DispositivoIoTRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IoTService {

    private final DispositivoIoTRepository dispositivoIoTRepository;
    private final AbitazioneRepository abitazioneRepository;

    @Autowired
    public IoTService(DispositivoIoTRepository dispositivoIoTRepository, AbitazioneRepository abitazioneRepository) {
        this.dispositivoIoTRepository = dispositivoIoTRepository;
        this.abitazioneRepository = abitazioneRepository;
    }

    @Transactional
    public DispositivoIoT aggiungiDispositivoIoT(DispositivoIoT dispositivoIoT) {

        if (dispositivoIoTRepository.existsByNumeroSerie(dispositivoIoT.getNumeroSerie())) {
            throw new IllegalArgumentException("Dispositivo già registrato.");
        }
        if (dispositivoIoTRepository.existsByNomeDispositivo(dispositivoIoT.getNomeDispositivo())) {
            throw new IllegalArgumentException("Utilizza un nome differente");
        }
        if (dispositivoIoT.getAbitazione().getIdAbitazione() == null) {
            throw new IllegalArgumentException("Il dispositivo deve essere associato ad un'abitazione");
        }

        if (!abitazioneRepository.existsByIdAbitazione(dispositivoIoT.getAbitazione().getIdAbitazione())) {
            throw new IllegalArgumentException("l'abitazione non esiste");
        }

        if ("SmartMeter".equals(dispositivoIoT.getTipoDispositivo())) {
            List<DispositivoIoT> dispositiviEsistenti = dispositivoIoTRepository.findDispositiviByAbitazione(dispositivoIoT.getAbitazione().getIdAbitazione());
            for (DispositivoIoT dispositivo : dispositiviEsistenti) {
                if ("SmartMeter".equals(dispositivo.getTipoDispositivo())) {
                    throw new IllegalArgumentException("Esiste già uno SmartMeter per questa abitazione.");
                }
            }
        }

        return dispositivoIoTRepository.save(dispositivoIoT);
    }

    @Transactional
    public List<DispositivoIoT> retriveDispositivi(String nickname){
        List<DispositivoIoT> listaDispositivi = dispositivoIoTRepository.findDispositiviByNickname(nickname);
        return listaDispositivi;
    }

}
