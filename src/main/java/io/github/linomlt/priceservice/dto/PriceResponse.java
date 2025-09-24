package io.github.linomlt.priceservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Schema(description = "Respuesta con información del precio efectivo de un producto")
public record PriceResponse(
        @Schema(description = "Identificador de la marca", example = "1")
        Long brandId,
        
        @Schema(description = "Identificador del producto", example = "35455")
        Long productId,
        
        @Schema(description = "Lista de precios aplicable", example = "1")
        Long priceList,
        
        @Schema(description = "Fecha y hora de inicio de vigencia", example = "2020-06-14T00:00:00")
        LocalDateTime startDate,
        
        @Schema(description = "Fecha y hora de fin de vigencia", example = "2020-12-31T23:59:59")
        LocalDateTime endDate,
        
        @Schema(description = "Precio del producto", example = "35.50")
        BigDecimal price,
        
        @Schema(description = "Divisa del precio", example = "EUR")
        Currency currency
) {
}