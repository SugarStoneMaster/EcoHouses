package it.ecohouses.www.backend.Utils;

import it.ecohouses.www.backend.services.PopulatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbPopulator implements ApplicationRunner {

    private final PopulatorService populatorService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
         {
            populatorService.populate();
        }
    }


}
