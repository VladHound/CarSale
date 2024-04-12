package car.sale.userservice.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {
    private String fullName;

    private String lastName;

    private String firstName;

    private String patronymic;

    public UserCredentials(String lastName, String firstName, String patronymic) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.fullName = lastName + " " + firstName + " " + patronymic;
    }
}

