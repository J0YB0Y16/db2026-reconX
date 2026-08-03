package com.dbtraining.reconx.dto;

import com.dbtraining.reconx.domain.Trade;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradeMapper {

    @Mapping(target = "counterpartyId", expression = "java(trade.getCounterparty() != null ? trade.getCounterparty().getId() : null)")
    @Mapping(target = "counterpartyName", expression = "java(trade.getCounterparty() != null ? trade.getCounterparty().getName() : null)")
    @Mapping(target = "instrumentId", expression = "java(trade.getInstrument() != null ? trade.getInstrument().getId() : null)")
    @Mapping(target = "instrumentSymbol", expression = "java(trade.getInstrument() != null ? trade.getInstrument().getSymbol() : null)")
    @Mapping(target = "status", expression = "java(trade.getStatus() != null ? trade.getStatus().name() : null)")
    TradeResponse toResponse(Trade trade);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "counterparty", ignore = true)
    @Mapping(target = "instrument", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Trade toEntity(TradeRequest req);
}