package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "utente")
public class Utente {

    @Id
    @NotBlank(message = "Il nickname non può essere vuoto")
    //@Size(max = 15, message = "Il nickname non può superare i 15 caratteri")
    @Column(nullable = false)
    private String nickname;

    @NotBlank(message = "L'email non può essere vuota")
    /*@Pattern(
            regexp = "^[^@\\s]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "L'email deve contenere almeno un punto dopo la '@' e un dominio valido"
    )*/
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "La password non può essere vuota")
   // @Size(min = 12, message = "La password deve contenere almeno 12 caratteri")
    @Column(nullable = false)
    private String password;

    @Column
    private String immagineProfilo;

    @Column(nullable = false)
    private boolean gestore;

    @ManyToOne
    private Abitazione abitazione;

    public Utente(String nickname, String email, String password, String immagineProfilo, boolean gestore) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.immagineProfilo = immagineProfilo;
        this.gestore = gestore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Utente utente = (Utente) o;
        return Objects.equals(nickname, utente.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    @Override
    public String toString() {
        return "Utente{" +
                "nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", immagineProfilo='" + immagineProfilo + '\'' +
                ", gestore=" + gestore +
                '}';
    }
}
