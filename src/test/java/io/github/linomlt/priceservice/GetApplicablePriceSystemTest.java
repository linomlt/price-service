package io.github.linomlt.priceservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetApplicablePriceSystemTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldReturnHighestPriorityPrice_WhenMultiplePricesExist() {
        given()
            .param("applicationDate", "2020-06-14T16:00:00")
            .param("productId", 35455)
            .param("brandId", 1)
        .when()
            .get("/api/v1/prices/applicable")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("priceList", is(2)) // El de mayor prioridad
            .body("price", is(25.45f));
    }

}
