package car.sale.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public interface CarResponseProjection {

    UUID getCarId();

    String getBrandCar();

    String getTypeCar();

    String getModelCar();

    String getIconPreview();

    Integer getYear();

    Integer getMileage();

    String getCondition();

    BigDecimal getPrice();

    String getSellerId();

    Double getEngineCapacity();

    String getColor();

    String getTransmission();

    String getAddress();

    Integer getHorsepower();

    Double getFuelTankCapacity();
}
