package car.sale.repository;

import car.sale.dto.response.CarResponse;
import car.sale.dto.response.CarResponseProjection;
import car.sale.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    /*    @Query(
                value = """
                            SELECT
                              car.id AS carId,
                              car.brand_id AS brandCarId,
                              brand.name AS brandCar,
                              car.type_id AS typeCarId,
                              type.name AS typeCar,
                              car.model_id AS modelCarId,
                              model.name AS modelCar,
                              car.icon_preview,
                              car.year,
                              car.mileage,
                              car.condition,
                              car.price,
                              car.seller_id,
                              car.engine_capacity,
                              car.color,
                              car.transmission,
                              car.address,
                              car.horsepower,
                              car.fuel_tank_capacity
                            FROM car car
                            INNER JOIN brands brand ON car.brand_id = brand.id
                            INNER JOIN types type ON car.type_id = type.id
                            INNER JOIN models model ON car.model_id = model.id
                            WHERE brand.id = :brandId
                            ORDER BY car.id
                        """,
                nativeQuery = true
    //            ,
    //            countQuery = """
    //                        SELECT COUNT(*)
    //                        FROM car car
    //                        INNER JOIN brands brand ON car.brand_id = brand.id
    //                        INNER JOIN types type ON car.type_id = type.id
    //                        INNER JOIN models model ON car.model_id = model.id
    //                        WHERE brand.id = :brandId
    //                    """
        )*/
    @Query(value = "SELECT c FROM car c WHERE c.brand_car_id=:brandId", nativeQuery = true)
    Page<CarResponse> getCarByBrand(@Param("brandId") Long brandId, Pageable pageable);

    /*     @Query(
             value = """
                         SELECT
                           car.id AS carId,
                           car.brand_id AS brandCarId,
                           brand.name AS brandCar,
                           car.type_id AS typeCarId,
                           type.name AS typeCar,
                           car.model_id AS modelCarId,
                           model.name AS modelCar,
                           car.icon_preview,
                           car.year,
                           car.mileage,
                           car.condition,
                           car.price,
                           car.seller_id,
                           car.engine_capacity,
                           car.color,
                           car.transmission,
                           car.address,
                           car.horsepower,
                           car.fuel_tank_capacity
                         FROM car car
                         INNER JOIN brands brand ON car.brand_id = brand.id
                         INNER JOIN types type ON car.type_id = type.id
                         INNER JOIN models model ON car.model_id = model.id
                         WHERE brand.id = :brandId AND model.id = modelId
                         ORDER BY car.id
                         OFFSET :pageable.offset
                         LIMIT :pageable.pageSize;
                     """,
             nativeQuery = true
     )*/
    @Query(value = "SELECT c FROM car c WHERE c.brand_car_id=:brandId AND model_car_id=:modelId", nativeQuery = true)
    Page<Car> getCarByModel(@Param("brandId") Long brandId, @Param("modelId") Long modelId, Pageable pageable);

    /*    @Query(
                value = """
                            SELECT
                              car.id AS carId,
                              car.brand_id AS brandCarId,
                              brand.name AS brandCar,
                              car.type_id AS typeCarId,
                              type.name AS typeCar,
                              car.model_id AS modelCarId,
                              model.name AS modelCar,
                              car.icon_preview,
                              car.year,
                              car.mileage,
                              car.condition,
                              car.price,
                              car.seller_id,
                              car.engine_capacity,
                              car.color,
                              car.transmission,
                              car.address,
                              car.horsepower,
                              car.fuel_tank_capacity
                            FROM car car
                            INNER JOIN brands brand ON car.brand_id = brand.id
                            INNER JOIN types type ON car.type_id = type.id
                            INNER JOIN models model ON car.model_id = model.id
                            WHERE type.id = :typeId
                            ORDER BY car.id
                            OFFSET :pageable.offset
                            LIMIT :pageable.pageSize;
                        """,
                nativeQuery = true
        )*/
    @Query(value = "SELECT c FROM car c WHERE c.type_car_id=:typeId", nativeQuery = true)
    Page<Car> getCarByType(@Param("typeId") Long typeId, Pageable pageable);

    @Query("""
       SELECT
           c.carId AS carId,
           b.name AS brandCar,
           t.name AS typeCar,
           m.name AS modelCar,
           c.iconPreview AS iconPreview,
           c.year AS year,
           c.mileage AS mileage,
           c.condition AS condition,
           c.price AS price,
           c.sellerId AS sellerId,
           c.engineCapacity AS engineCapacity,
           c.color AS color,
           c.transmission AS transmission,
           c.address AS address,
           c.horsepower AS horsepower,
           c.fuelTankCapacity AS fuelTankCapacity
       FROM Car c
       INNER JOIN ModelCar m ON c.modelId = m.modelId
       INNER JOIN BrandCar b ON c.brandId = b.brandId
       INNER JOIN TypeCar t ON c.typeId = t.typeId
       """)
    Page<CarResponseProjection> getCarWithJoins(Pageable pageable);


    @Query("SELECT c FROM Car c WHERE c.id = :carId")
    Optional<Car> getCar(@Param("carId") UUID carId);

    @Query(value = "SELECT COUNT(*) FROM Car")
    BigInteger getNumberOfCar();
}
