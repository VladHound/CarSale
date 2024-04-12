package car.sale.auth.model;

import lombok.Getter;

@Getter
public enum Role {
    USER("ROLE_USER"),
    SELLER("ROLE_SELLER"),
    ADMIN("ROLE_ADMIN");

    private final String key;

    Role(String key) {
        this.key = key;
    }

}