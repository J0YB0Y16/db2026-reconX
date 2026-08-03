package com.dbtraining.reconx.repository;

import com.dbtraining.reconx.domain.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    void shouldFindTradesByDateRangeFilter() {
        LocalDate from = LocalDate.now().minusDays(30);
        LocalDate to = LocalDate.now().plusDays(30);

        Page<Trade> result = tradeRepository.findByFilters(
            from, to, null, null, PageRequest.of(0, 10)
        );

        assertThat(result).isNotNull();
    }
}