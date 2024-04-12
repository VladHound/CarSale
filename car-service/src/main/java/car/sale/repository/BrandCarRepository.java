package car.sale.repository;

import car.sale.dto.response.BrandPreview;
import car.sale.model.BrandCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandCarRepository extends JpaRepository<BrandCar, Long> {

    @Query(value = """
    SELECT
    b.brand_id AS brandId,
    b.name AS name
    FROM brands AS b
    ORDER BY b.name
    """,
    nativeQuery = true
    )
    List<BrandPreview> getBrands();
}
