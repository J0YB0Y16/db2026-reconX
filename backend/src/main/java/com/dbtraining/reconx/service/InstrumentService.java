package com.dbtraining.reconx.service;

import com.dbtraining.reconx.exception.InvalidTradeException;
import com.dbtraining.reconx.repository.InstrumentRepository;
import com.dbtraining.reconx.repository.entity.Instrument;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InstrumentService {

    private final InstrumentRepository repo;

    public InstrumentService(InstrumentRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "instruments", key = "#symbol")
    public Instrument findBySymbol(String symbol) {

        log.info("DB hit for {}", symbol);

        return repo.findBySymbol(symbol)
                .orElseThrow(() ->
                        new InvalidTradeException(
                                "Unknown instrument symbol: " + symbol
                        ));
    }
}