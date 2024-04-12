package car.sale.service.Impl;

import car.sale.dto.request.CarRequest;
import car.sale.dto.response.CarResponse;
import car.sale.dto.response.CarResponseProjection;
import car.sale.model.Car;
import car.sale.repository.CarRepository;
import car.sale.service.CarService;
import car.sale.service.CharacteristicService;
import car.sale.utils.CarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository repository;
    private final CharacteristicService characteristicService;
    private final CarMapper carMapper;

    @Override
    public Page<CarResponse> getByBrand(Long brandId, Pageable pageable) {
        Page<CarResponse> car = repository.getCarByBrand(brandId, pageable);

        return null;
    }

    @Override
    public Page<CarResponse> getByBrandAndModel(Long brandId, Long modelId, Pageable pageable) {
        Page<Car> car = repository.getCarByModel(brandId, modelId, pageable);
        return null;
    }

    @Override
    public Page<CarResponse> getByType(Long typeId, Pageable pageable) {
        Page<Car> car = repository.getCarByType(typeId, pageable);

        return null;
    }

    @Override
    public Page<CarResponseProjection> getAll(Pageable pageable) {
        Page<CarResponseProjection> car = repository.getCarWithJoins(pageable);

        System.out.println(car);

        return car;
    }

    @Override
    public Car getById(UUID carId) {
        Optional<Car> car = repository.getCar(carId);
        return null;
    }

    @Override
    public Long getCount() {
        Long numberOfCar = repository.getNumberOfCar().longValue();
        return null;
    }

    @Override
    public UUID createCar(UUID sellerId, CarRequest request) {
        Car car = carMapper.mapToCar(request);
        car.setSellerId(sellerId);
        car.setCarId(UUID.randomUUID());

        System.out.println(car);

        return repository.save(car).getCarId();
    }
}
