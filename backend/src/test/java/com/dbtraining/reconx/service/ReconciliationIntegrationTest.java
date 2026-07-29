package com.dbtraining.reconx.service;

import com.dbtraining.reconx.dto.ReconResult;
import com.dbtraining.reconx.model.Trade;
import com.dbtraining.reconx.repository.ReconResultRepository;
import com.dbtraining.reconx.repository.TradeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class ReconciliationIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("reconx")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", postgres::getJdbcUrl);
        r.add("spring.datasource.username", postgres::getUsername);
        r.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TradeRepository tradeRepo;

    @Autowired
    private ReconResultRepository reconResultRepo;

    @Autowired
    private ReconciliationService reconciliationService;

    @Test
    void containerIsRunning() {
        // sanity: if this passes, all your wiring is correct.
    }

    @Test
    void insertedTradesAreReconciledAndPersisted() {
        // given — two matching trades, one in each repo
        Trade internal = new Trade();
        internal.setTradeRef("TRD-INT-1");
        internal.setCounterpartyId(1L);
        internal.setInstrumentId(1L);
        internal.setAssetClass("EQUITY");
        internal.setPrice(new BigDecimal("245.50"));
        internal.setQuantity(new BigDecimal("100"));
        internal.setTradeDate(LocalDate.now());
        internal.setSide("BUY");
        
        // Just simulating the external trade with another Trade entity for this test
        Trade external = new Trade();
        external.setTradeRef("TRD-INT-1");
        external.setCounterpartyId(1L);
        external.setInstrumentId(1L);
        external.setAssetClass("EQUITY");
        external.setPrice(new BigDecimal("245.50"));
        external.setQuantity(new BigDecimal("100"));
        external.setTradeDate(LocalDate.now());
        external.setSide("BUY");

        tradeRepo.save(internal);
        tradeRepo.save(external);

        // when
        reconciliationService.runRecon(
                List.of(internal),
                List.of(external));

        // then — exactly one MATCHED row landed in recon_results
        List<ReconResult> persisted = reconResultRepo.findAll();
        assertThat(persisted).hasSize(1);
        assertThat(persisted.get(0).status()).isEqualTo(ReconResult.Status.MATCHED);
        assertThat(persisted.get(0).tradeRef()).isEqualTo("TRD-INT-1");
    }
}
