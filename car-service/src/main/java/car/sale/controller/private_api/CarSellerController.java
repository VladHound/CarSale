package car.sale.controller.private_api;

import car.sale.dto.request.CarRequest;
import car.sale.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static car.sale.util.HttpUtils.PRIVATE_API_VI;

@RestController
@RequiredArgsConstructor
@RequestMapping(PRIVATE_API_VI + "/car/sellers")
public class CarSellerController {
    // TODO: необходимо требовать токен

    @Qualifier("carServiceImpl")
    private final CarService service;

    @PostMapping
    @RequestMapping("/{sellerId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "createCar", summary = "Создание нового товара",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Продукт успешно создан",
            content = @Content(mediaType = "application/json"))
    @PreAuthorize(value = """
            @permissionService.hasPermission(
                #sellerId,
                T(car.sale.auth.model.Role).USER,
                T(car.sale.auth.util.ClientAttributes).USER_ID
            )
            """
    )
    public UUID createNewCar(
            @Parameter(name = "sellerId", description = "Идентификатор продавца", required = true)
            @PathVariable("sellerId") UUID sellerId,

            @Parameter(name = "createCarRequest", description = "Данные необходимые для добавления автомобиля",
                    required = true)
            @RequestBody CarRequest request) {
        return service.createCar(sellerId, request);
    }
}
