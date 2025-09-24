package io.github.linomlt.priceservice;

import io.github.linomlt.priceservice.dto.PriceResponse;
import io.github.linomlt.priceservice.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Tag(name = "Precios", description = "API para consulta de precios de productos")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @Operation(
            summary = "Obtiene el precio efectivo de un producto",
            description = "Devuelve el precio aplicable para un producto dado, marca y fecha"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Precio encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PriceResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un precio efectivo para los parámetros indicados",
                    content = @Content
            )
    })
    @GetMapping("api/prices")
    public ResponseEntity<PriceResponse> prices(
            @Parameter(description = "ID de la marca", example = "1") @RequestParam("brandId") Long brandId,
            @Parameter(description = "ID del producto", example = "35455") @RequestParam("productId") Long productId,
            @Parameter(description = "Fecha y hora de aplicación en formato ISO-8601", example = "2020-06-14T10:00:00")
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {
        return priceService.getEffectivePrice(brandId, productId, applicationDate)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
