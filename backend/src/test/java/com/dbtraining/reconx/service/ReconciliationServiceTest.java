package com.dbtraining.reconx.service;

import com.dbtraining.reconx.dto.ReconResult;
import com.dbtraining.reconx.model.EquityTrade;
import com.dbtraining.reconx.model.ReconciliationRule;
import com.dbtraining.reconx.model.Side;
import com.dbtraining.reconx.model.TradeRef;
import com.dbtraining.reconx.repository.ReconResultRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ReconciliationServiceTest {

    @Test
    void testReconcile_savesResultWithMatchedStatus() {
        // given
        ReconResultRepository repo = mock(ReconResultRepository.class);
        ReconciliationEngine engine = new ReconciliationEngine();
        ReconciliationService svc = new ReconciliationService(engine, repo);

        EquityTrade internal = EquityTrade.builder()
                .tradeRef(TradeRef.of("TRD-1"))
                .counterpartyId(1L)
                .instrumentSymbol("SAP.DE")
                .price(new BigDecimal("100.00"))
                .quantity(new BigDecimal("1000"))
                .currency("EUR")
                .side(Side.BUY)
                .tradeDate(LocalDate.now())
                .build();
        
        EquityTrade external = EquityTrade.builder()
                .tradeRef(TradeRef.of("TRD-1"))
                .counterpartyId(1L)
                .instrumentSymbol("SAP.DE")
                .price(new BigDecimal("100.00"))
                .quantity(new BigDecimal("1000"))
                .currency("EUR")
                .side(Side.BUY)
                .tradeDate(LocalDate.now())
                .build();

        // when
        svc.runRecon(List.of(internal), List.of(external), ReconciliationRule.EXACT);

        // then
        ArgumentCaptor<ReconResult> captor = ArgumentCaptor.forClass(ReconResult.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().tradeRef()).isEqualTo("TRD-1");
        assertThat(captor.getValue().status()).isEqualTo(ReconResult.Status.MATCHED);
    }
}
