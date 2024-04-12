package car.sale.controller.public_api;

import car.sale.model.ErrorResponse;
import car.sale.dto.response.BrandPreview;
import car.sale.model.ModelCar;
import car.sale.model.TypeCar;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Tag(name = "characteristics")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid data provided to the server", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        }),
        @ApiResponse(responseCode = "404", description = "Resource not found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        })
})
public interface CharacteristicApi {

    @Operation(
            summary = "Get all brands",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of brands",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = List.class)
                            )
                    }
            )
    )
    List<BrandPreview> getAllBrands();

    @Operation(
            summary = "Get models by brand",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of models by brand",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = List.class)
                            )
                    }
            )
    )
    List<ModelCar> getModelsByBrand(
            @Parameter(name = "brandId", description = "Brand ID", required = true) UUID brandId
    );

    @Operation(
            summary = "Get types",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of types",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = List.class)
                            )
                    }
            )
    )
    List<TypeCar> getTypes();
}
