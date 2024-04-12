package car.sale.service;

import car.sale.dto.request.CarRequest;
import car.sale.dto.response.CarResponse;
import car.sale.dto.response.CarResponseProjection;
import car.sale.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface CarService {

    /**
     * Получить автомобили по бренду.
     *
     * @param brandId ID бренда.
     * @param pageable Страница.
     * @return Страница автомобилей.
     */
    Page<CarResponse> getByBrand(Long brandId, Pageable pageable);

    /**
     * Получить автомобили по бренду и модели.
     *
     * @param brandId ID бренда.
     * @param modelId ID модели.
     * @param pageable Страница.
     * @return Страница автомобилей.
     */
    Page<CarResponse> getByBrandAndModel(Long brandId, Long modelId, Pageable pageable);

    /**
     * Получить автомобили по типу.
     *
     * @param typeId ID типа.
     * @param pageable Страница.
     * @return Страница автомобилей.
     */
    Page<CarResponse> getByType(Long typeId, Pageable pageable);

    /**
     * Получить все автомобили.
     *
     * @param pageable Страница.
     * @return Страница автомобилей.
     */
    Page<CarResponseProjection> getAll(Pageable pageable);

    /**
     * Получить автомобиль по ID.
     *
     * @param carId ID автомобиля.
     * @return Автомобиль.
     */
    Car getById(UUID carId);

    /**
     * Получить количество автомобилей.
     *
     * @return Количество автомобилей.
     */
    Long getCount();

    UUID createCar(UUID sellerId, CarRequest request);
}
