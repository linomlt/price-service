package io.github.linomlt.priceservice.config;

import io.github.linomlt.priceservice.adapter.out.persistence.PriceRepository;
import io.github.linomlt.priceservice.adapter.out.persistence.PriceJpaEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(PriceRepository repository) {
        return args -> {
            PriceJpaEntity p1 = new PriceJpaEntity();
            p1.setBrandId(1L);
            p1.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
            p1.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
            p1.setPriceList(1);
            p1.setProductId(35455L);
            p1.setPriority(0);
            p1.setPrice(new BigDecimal("35.50"));
            p1.setCurrency("EUR");

            PriceJpaEntity p2 = new PriceJpaEntity();
            p2.setBrandId(1L);
            p2.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0));
            p2.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0));
            p2.setPriceList(2);
            p2.setProductId(35455L);
            p2.setPriority(1);
            p2.setPrice(new BigDecimal("25.45"));
            p2.setCurrency("EUR");

            PriceJpaEntity p3 = new PriceJpaEntity();
            p3.setBrandId(1L);
            p3.setStartDate(LocalDateTime.of(2020, 6, 15, 0, 0, 0));
            p3.setEndDate(LocalDateTime.of(2020, 6, 15, 11, 0, 0));
            p3.setPriceList(3);
            p3.setProductId(35455L);
            p3.setPriority(1);
            p3.setPrice(new BigDecimal("30.50"));
            p3.setCurrency("EUR");

            PriceJpaEntity p4 = new PriceJpaEntity();
            p4.setBrandId(1L);
            p4.setStartDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0));
            p4.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
            p4.setPriceList(4);
            p4.setProductId(35455L);
            p4.setPriority(1);
            p4.setPrice(new BigDecimal("38.95"));
            p4.setCurrency("EUR");

            repository.saveAll(List.of(p1, p2, p3, p4));
        };
    }
}
