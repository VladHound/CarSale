package car.sale.controller.public_api;

import car.sale.dto.response.CarResponse;
import car.sale.dto.response.CarResponseProjection;
import car.sale.model.Car;
import car.sale.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static car.sale.util.HttpUtils.PUBLIC_API_VI;

@RestController
@RequiredArgsConstructor
@RequestMapping(PUBLIC_API_VI + "/car")
public class CarController implements CarApi{

    private final CarService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CarResponseProjection> getCar(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/brands/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CarResponse> getCarByBrand(@PathVariable Long brandId, Pageable pageable) {
        return service.getByBrand(brandId, pageable);
    }

    @GetMapping("/brands/{brandId}/models/{modelId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CarResponse> getCarByBrandAndModel(@PathVariable Long brandId, @PathVariable Long modelId, Pageable pageable) {
        return service.getByBrandAndModel(brandId, modelId, pageable);
    }

    @GetMapping("/types/{typeId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CarResponse> getCarByType(@PathVariable Long typeId, Pageable pageable) {
        return service.getByType(typeId, pageable);
    }

    @GetMapping("/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public Car getCarById(@PathVariable UUID carId) {
        return service.getById(carId);
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public Long getNumbersOfCar() {
        return service.getCount();
    }
}
