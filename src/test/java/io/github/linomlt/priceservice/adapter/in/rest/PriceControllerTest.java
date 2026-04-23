package io.github.linomlt.priceservice.adapter.in.rest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    private static final String APPLICABLE_PRICE_URL = "/api/v1/prices/applicable";
    private static final Long BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({
            "2020-06-14T10:00:00, 1, 35.50, 2020-06-14T00:00:00, 2020-12-31T23:59:59",
            "2020-06-14T16:00:00, 2, 25.45, 2020-06-14T15:00:00, 2020-06-14T18:30:00",
            "2020-06-14T21:00:00, 1, 35.50, 2020-06-14T00:00:00, 2020-12-31T23:59:59",
            "2020-06-15T10:00:00, 3, 30.50, 2020-06-15T00:00:00, 2020-06-15T11:00:00",
            "2020-06-16T21:00:00, 4, 38.95, 2020-06-15T16:00:00, 2020-12-31T23:59:59"
    })
    void shouldReturnApplicablePrice(
            String applicationDate,
            int expectedPriceList,
            BigDecimal expectedPrice,
            String expectedStartDate,
            String expectedEndDate
    ) throws Exception {

        mockMvc.perform(get(APPLICABLE_PRICE_URL)
                        .param("brandId", BRAND_ID.toString())
                        .param("productId", PRODUCT_ID.toString())
                        .param("applicationDate", applicationDate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(BRAND_ID))
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.price").value(expectedPrice.stripTrailingZeros()))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.startDate").value(expectedStartDate))
                .andExpect(jsonPath("$.endDate").value(expectedEndDate));
    }

    @Test
    void shouldReturnNotFoundWhenNoApplicablePriceExists() throws Exception {
        mockMvc.perform(get(APPLICABLE_PRICE_URL)
                        .param("brandId", BRAND_ID.toString())
                        .param("productId", PRODUCT_ID.toString())
                        .param("applicationDate", "2030-01-01T00:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    
    @Test
    void shouldReturnBadRequestWhenBrandIdMissing() throws Exception {
        mockMvc.perform(get(APPLICABLE_PRICE_URL)
                        .param("productId", PRODUCT_ID.toString())
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Missing Required Parameter"))
                .andExpect(jsonPath("$.parameter").value("brandId"));
    }

    @Test
    void shouldReturnBadRequestWhenProductIdIsMissing() throws Exception {
        mockMvc.perform(get(APPLICABLE_PRICE_URL)
                        .param("brandId", BRAND_ID.toString())
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Missing Required Parameter"))
                .andExpect(jsonPath("$.parameter").value("productId"));
    }

    @Test
    void shouldReturnBadRequestWhenApplicationDateIsMissing() throws Exception {
        mockMvc.perform(get(APPLICABLE_PRICE_URL)
                        .param("brandId", BRAND_ID.toString())
                        .param("productId", PRODUCT_ID.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Missing Required Parameter"))
                .andExpect(jsonPath("$.parameter").value("applicationDate"));
    }

    @Test
    void shouldReturnBadRequestWhenProductIdIsNotLong() throws Exception {
        mockMvc.perform(get(APPLICABLE_PRICE_URL)
                        .param("brandId", BRAND_ID.toString())
                        .param("productId", "not-a-long")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid Parameter Type"))
                .andExpect(jsonPath("$.parameter").value("productId"))
                .andExpect(jsonPath("$.expectedType").value("Long"));
    }

    @Test
    void shouldReturnBadRequestWhenApplicationDateHasInvalidFormat() throws Exception {
        mockMvc.perform(get(APPLICABLE_PRICE_URL)
                        .param("brandId", BRAND_ID.toString())
                        .param("productId", PRODUCT_ID.toString())
                        .param("applicationDate", "2020-06-14")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid Parameter Type"))
                .andExpect(jsonPath("$.parameter").value("applicationDate"))
                .andExpect(jsonPath("$.expectedType").value("LocalDateTime"));
    }

}