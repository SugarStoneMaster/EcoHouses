package it.ecohouses.www.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SmartMeter extends DispositivoIoT {

    private String email;
    private String password;

}
