package com.dbtraining.reconx.dto;

import com.dbtraining.reconx.domain.Trade;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-03T17:44:06+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 25.0.3 (Microsoft)"
)
@Component
public class TradeMapperImpl implements TradeMapper {

    @Override
    public TradeResponse toResponse(Trade trade) {
        if ( trade == null ) {
            return null;
        }

        Long id = null;
        String tradeRef = null;
        BigDecimal quantity = null;
        BigDecimal price = null;
        LocalDate tradeDate = null;
        Instant createdAt = null;
        Instant modifiedAt = null;

        id = trade.getId();
        tradeRef = trade.getTradeRef();
        quantity = trade.getQuantity();
        price = trade.getPrice();
        tradeDate = trade.getTradeDate();
        createdAt = trade.getCreatedAt();
        modifiedAt = trade.getModifiedAt();

        Long counterpartyId = trade.getCounterparty() != null ? trade.getCounterparty().getId() : null;
        String counterpartyName = trade.getCounterparty() != null ? trade.getCounterparty().getName() : null;
        Long instrumentId = trade.getInstrument() != null ? trade.getInstrument().getId() : null;
        String instrumentSymbol = trade.getInstrument() != null ? trade.getInstrument().getSymbol() : null;
        String status = trade.getStatus() != null ? trade.getStatus().name() : null;
        String assetClass = null;
        String side = null;

        TradeResponse tradeResponse = new TradeResponse( id, tradeRef, instrumentId, instrumentSymbol, counterpartyId, counterpartyName, assetClass, side, quantity, price, tradeDate, status, createdAt, modifiedAt );

        return tradeResponse;
    }

    @Override
    public Trade toEntity(TradeRequest req) {
        if ( req == null ) {
            return null;
        }

        Trade trade = new Trade();

        trade.setTradeRef( req.tradeRef() );
        trade.setQuantity( req.quantity() );
        trade.setPrice( req.price() );
        trade.setTradeDate( req.tradeDate() );

        return trade;
    }
}
