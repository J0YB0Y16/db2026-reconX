package com.dbtraining.reconx.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TradeRequest(
    @NotBlank(message = "Trade reference cannot be blank")
    String tradeRef,

    @NotNull(message = "Counterparty ID cannot be null")
    Long counterpartyId,

    @NotNull(message = "Instrument ID cannot be null")
    Long instrumentId,

    @NotNull(message = "Quantity cannot be null")
    @DecimalMin(value = "0.0001", message = "Quantity must be greater than zero")
    BigDecimal quantity,

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0001", message = "Price must be greater than zero")
    BigDecimal price,

    @NotNull(message = "Trade date cannot be null")
    @PastOrPresent(message = "Trade date cannot be in the future")
    LocalDate tradeDate
) {}