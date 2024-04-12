package car.sale.dto.response;

import car.sale.model.BrandCar;
import car.sale.model.ModelCar;

import java.math.BigDecimal;

public class CarPreviewResponse {
    private BrandCar brandCar;
    private ModelCar modelCar;
    private String iconPreview;
    private int year;
    private int mileage;
    private BigDecimal price;
    private int horsepower;
}