package it.ecohouses.www.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SmartMeter extends DispositivoIoT {

    private String email;
    private String password;

    public SmartMeter(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }
}
