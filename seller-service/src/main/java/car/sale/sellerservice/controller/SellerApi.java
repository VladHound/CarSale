package car.sale.sellerservice.controller;

import car.sale.sellerservice.dto.SellerRegistration;
import car.sale.sellerservice.dto.SellerRequestDto;
import car.sale.sellerservice.dto.SellerResponseDto;
import car.sale.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Tag(name = "Seller")
@ApiResponses({
        @ApiResponse(
                responseCode = "400",
                description = "На сервер переданы неверные данные",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )),
        @ApiResponse(
                responseCode = "401",
                description = "Продавец не авторизован",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )),
        @ApiResponse(
                responseCode = "403",
                description = "У продавца нет доступа к ресурсу",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )),
        @ApiResponse(
                responseCode = "404",
                description = "Ресурс не был найден по id",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                ))
})
public interface SellerApi {

/*    @Operation(summary = "Создание нового продавца",
            responses = {@ApiResponse(responseCode = "201",
                    description = "Новый продавец создан",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))),
                    @ApiResponse(responseCode = "409", description = "Ошибка при создании нового продавца")
            })
    UUID createSeller(@RequestBody @Valid SellerRegistration sellerRegistration);*/

    @Operation(operationId = "getSellerBySellerId", summary = "Получение продавца по sellerId")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Продавец успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SellerResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Такого продавца не существует")
    })
    @PreAuthorize(value = """
            @permissionService.hasPermission(
                #sellerId,
                T(car.sale.auth.model.Role).SELLER,
                T(car.sale.auth.util.ClientAttributes).SELLER_ID
            )
            """
    )
    SellerResponseDto getSellerBySellerId(
            @Parameter(name = "sellerId", description = "Идентификатор продавца")
            @PathVariable("sellerId") UUID sellerId);

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Продавец успешно обновлён",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SellerResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Такого продавца не существует")
    })
    @Operation(operationId = "обновлениеПродавца",
            summary = "Поиск по продавца Id и обновление с помощью Dto",
            description = "Обновление продавца на основе входящего объекта Dto и продавца UUID")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = """
            @permissionService.hasPermission(
                #sellerId,
                T(car.sale.auth.model.Role).SELLER,
                T(car.sale.auth.util.ClientAttributes).SELLER_ID
            )
            """
    )
    SellerResponseDto updateSeller(
            @Parameter(name = "sellerId", description = "Индификатор продавца")
            @PathVariable UUID sellerId,
            @RequestBody SellerRequestDto sellerRequestDto);

    @Operation(summary = "Удаление продавца", responses = {
            @ApiResponse(responseCode = "200", description = "Продавец успешно удален"),
            @ApiResponse(responseCode = "404", description = "Такого продавца не существует")
    })
    @PreAuthorize(value = """
            @permissionService.hasPermission(
                #sellerId,
                T(car.sale.auth.model.Role).SELLER,
                T(car.sale.auth.util.ClientAttributes).SELLER_ID
            )
            """
    )
    void deleteSeller(@Parameter(name = "sellerId", description = "Идентификатор продавца")
                      @PathVariable UUID sellerId);
}
