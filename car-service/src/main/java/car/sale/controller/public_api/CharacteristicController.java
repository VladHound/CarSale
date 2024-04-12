package car.sale.controller.public_api;

import car.sale.dto.response.BrandPreview;
import car.sale.model.ModelCar;
import car.sale.model.TypeCar;
import car.sale.service.CharacteristicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static car.sale.util.HttpUtils.PUBLIC_API_VI;

@RestController
@RequiredArgsConstructor
@RequestMapping(PUBLIC_API_VI + "/car")
public class CharacteristicController implements CharacteristicApi {

    private final CharacteristicService characteristicService;

    @GetMapping("/brands")
    public List<BrandPreview> getAllBrands() {
        return characteristicService.getAllBrands();
    }

    @GetMapping("/brands/{brandId}/models")
    public List<ModelCar> getModelsByBrand(@PathVariable UUID brandId) {
        return characteristicService.getModelsByBrand(brandId);
    }

    @GetMapping("/types")
    public List<TypeCar> getTypes() {
        return characteristicService.getTypes();
    }
}
