package car.sale.service;

import car.sale.dto.response.BrandPreview;
import car.sale.model.ModelCar;
import car.sale.model.TypeCar;

import java.util.List;
import java.util.UUID;

public interface CharacteristicService {
    /**
     * Получить все бренды.
     *
     * @return Список брендов.
     */
    List<BrandPreview> getAllBrands();

    /**
     * Получить модели для определённого бренда.
     *
     * @param brandId ID бренда.
     * @return Список моделей.
     */
    List<ModelCar> getModelsByBrand(UUID brandId);

    /**
     * Получить типы автомобилей.
     *
     * @return Список типов.
     */
    List<TypeCar> getTypes();
}
