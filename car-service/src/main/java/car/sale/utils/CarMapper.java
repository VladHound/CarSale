package car.sale.utils;

import car.sale.dto.request.CarRequest;
import car.sale.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    Car mapToCar(CarRequest request);
}
