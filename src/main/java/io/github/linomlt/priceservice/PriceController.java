package io.github.linomlt.priceservice;

import io.github.linomlt.priceservice.dto.PriceResponse;
import io.github.linomlt.priceservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping("api/prices")
    public ResponseEntity<PriceResponse> prices(
            @RequestParam("brandId") Long brandId,
            @RequestParam("productId") Long productId,
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {
        return priceService.getEffectivePrice(brandId, productId, applicationDate)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
