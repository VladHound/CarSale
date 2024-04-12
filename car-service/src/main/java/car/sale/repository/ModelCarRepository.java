package car.sale.repository;

import car.sale.model.ModelCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModelCarRepository extends JpaRepository<ModelCar, Long> {

    @Query("SELECT m FROM ModelCar m WHERE m.brandId = :brandId")
    List<ModelCar> getModelsByBrand(@Param("brandId") UUID brandId);
}
