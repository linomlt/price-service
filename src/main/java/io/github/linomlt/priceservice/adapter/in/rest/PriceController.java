package io.github.linomlt.priceservice.adapter.in.rest;

import io.github.linomlt.priceservice.domain.model.Price;
import io.github.linomlt.priceservice.application.port.in.GetApplicablePriceUseCase;
import io.github.linomlt.priceservice.application.port.in.PriceQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/prices")
@Tag(
        name = "Prices",
        description = "Operations to retrieve the applicable price for a product, brand and application date"
)
public class PriceController {

    private final GetApplicablePriceUseCase getApplicablePriceUseCase;

    @GetMapping("/applicable")
    @Operation(
            summary = "Get applicable price",
            description = "Returns the applicable price for the given brand, product and application date"
    )
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @Parameter(
                    description = "Brand identifier",
                    example = "1",
                    required = true
            )
            @RequestParam Long brandId,
            @Parameter(
                    description = "Product identifier",
                    example = "35455",
                    required = true
            )
            @RequestParam Long productId,
            @Parameter(
                    description = "Application date-time in ISO-8601 format",
                    example = "2020-06-14T10:00:00",
                    required = true
            )
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime applicationDate) {

        PriceQuery priceQuery = new PriceQuery(brandId, productId, applicationDate);
        Price price = getApplicablePriceUseCase.execute(priceQuery);
        PriceResponse priceResponse = PriceResponse.from(price);
        return ResponseEntity.ok(priceResponse);
    }
}
