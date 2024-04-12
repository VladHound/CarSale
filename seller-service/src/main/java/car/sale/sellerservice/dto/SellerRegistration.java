package car.sale.sellerservice.dto;

import car.sale.sellerservice.validator.annotation.SellerEmailConstraint;
import car.sale.sellerservice.validator.annotation.SellerPasswordConstraint;
public record SellerRegistration(
        @SellerEmailConstraint
        String email,
        @SellerPasswordConstraint
        String password
) {
}