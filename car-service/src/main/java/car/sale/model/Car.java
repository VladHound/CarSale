package car.sale.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "car")
public class Car {

    @Id
    @SequenceGenerator(name = "products_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_sequence")
    private Long id;

    private UUID carId;
    private UUID brandId;
    private UUID typeId;
    private UUID modelId;

    private String iconPreview;

    private int year;
    private int mileage;

    private String condition;

    private BigDecimal price;
    private UUID sellerId;
    private double engineCapacity;

    private String color;
    private String transmission;
    private String address;
    private int horsepower;
    private double fuelTankCapacity;

    @CreationTimestamp
    private LocalDateTime creationDate;
}
