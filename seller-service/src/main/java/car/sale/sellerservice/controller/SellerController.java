package car.sale.sellerservice.controller;

import car.sale.sellerservice.dto.SellerRegistration;
import car.sale.sellerservice.dto.SellerRequestDto;
import car.sale.sellerservice.dto.SellerResponseDto;
import car.sale.sellerservice.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static car.sale.util.HttpUtils.PUBLIC_API_VI;

@Slf4j
@RestController
@RequestMapping(PUBLIC_API_VI + "/sellers")
//@RequestMapping("public/api/v1" + )
@RequiredArgsConstructor
public class SellerController implements SellerApi {

    private final SellerService sellerService;

/*    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createSeller(@RequestBody @Valid SellerRegistration sellerRegistration) {
        return sellerService.createSeller(sellerRegistration);
    }*/

    @PostMapping("/from/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createSeller(String json) {
        return sellerService.createSellerAutoWithKafka(json);
    }

    @GetMapping("/{sellerId}")
    @ResponseStatus(HttpStatus.OK)
    public SellerResponseDto getSellerBySellerId(
            @PathVariable("sellerId") UUID sellerId) {
        return sellerService.getSellerBySellerId(sellerId);
    }

    @PutMapping("/{sellerId}")
    @ResponseStatus(HttpStatus.OK)
    @Deprecated
    public SellerResponseDto updateSeller(
            @PathVariable UUID sellerId,
            @RequestBody @Valid SellerRequestDto sellerRequestDto) {
        return sellerService.updateSeller(sellerId, sellerRequestDto);
    }

    @DeleteMapping("/{sellerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSeller(@PathVariable UUID sellerId) {
        sellerService.deleteSellerBySellerId(sellerId);
    }
}