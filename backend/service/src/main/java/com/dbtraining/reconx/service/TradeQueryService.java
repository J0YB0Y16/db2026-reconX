package com.dbtraining.reconx.service;

import com.dbtraining.reconx.domain.Trade; // Adjust package as needed based on your domain models
import com.dbtraining.reconx.repository.TradeRepository; // Assuming you have a repository
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TradeQueryService {

    private final TradeRepository tradeRepository;

    public TradeQueryService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> getTradeById(Long id) {
        return tradeRepository.findById(id);
    }
}