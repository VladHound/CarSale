package car.sale.service.Impl;

import car.sale.dto.response.BrandPreview;
import car.sale.model.ModelCar;
import car.sale.model.TypeCar;
import car.sale.repository.BrandCarRepository;
import car.sale.repository.ModelCarRepository;
import car.sale.repository.TypeCarRepository;
import car.sale.service.CharacteristicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {

    private final TypeCarRepository typeRepository;
    private final BrandCarRepository brandRepository;
    private final ModelCarRepository modelRepository;


    @Override
    public List<BrandPreview> getAllBrands() {
        return brandRepository.getBrands();
    }

    @Override
    public List<ModelCar> getModelsByBrand(UUID brandId) {
        return modelRepository.getModelsByBrand(brandId);
    }

    @Override
    public List<TypeCar> getTypes() {
        return typeRepository.getTypes();
    }
}
