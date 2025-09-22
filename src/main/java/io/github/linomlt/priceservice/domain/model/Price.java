package io.github.linomlt.priceservice.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Table(name = "prices")
@Getter
@Setter
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "price_list")
    private Long priceList;

    @Column(name = "priority")
    private Long priority;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "curr")
    private Currency currency; // TODO Review

}
