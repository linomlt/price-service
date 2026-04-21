package io.github.linomlt.priceservice.application.domain.model;

import io.github.linomlt.priceservice.application.domain.exception.NegativePriceException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Price {

    private final Long brandId;
    private final Long productId;
    private final Money money;
    private final Integer priority;
    private final Integer priceList;
    private final DateRange validity;

    public Price(Long brandId, Long productId, Money money, Integer priority, Integer priceList, DateRange validity) {
        Objects.requireNonNull(brandId, "Brand ID cannot be null");
        Objects.requireNonNull(productId, "Product ID cannot be null");
        Objects.requireNonNull(priceList, "Price List cannot be null");
        Objects.requireNonNull(priority, "Priority cannot be null");
        Objects.requireNonNull(money, "Money cannot be null");
        Objects.requireNonNull(validity, "Validity period cannot be null");

        if (money.isNegative()) {
            throw new NegativePriceException(money);
        }

        this.brandId = brandId;
        this.productId = productId;
        this.money = money;
        this.priority = priority;
        this.priceList = priceList;
        this.validity = validity;
    }

    public Long getBrandId() {
        return brandId;
    }

    public Long getProductId() {
        return productId;
    }

    public Money getMoney() {
        return money;
    }

    public Integer getPriority() {
        return priority;
    }

    public Integer getPriceList() {
        return priceList;
    }

    public DateRange getValidity() {
        return validity;
    }

    public boolean isApplicable(LocalDateTime date) {
        return validity.isWithin(date);
    }

}
