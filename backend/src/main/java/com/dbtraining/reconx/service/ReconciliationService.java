package com.dbtraining.reconx.service;

import com.dbtraining.reconx.dto.ReconResult;
import com.dbtraining.reconx.model.ReconciliationRule;
import com.dbtraining.reconx.model.TradeType;
import com.dbtraining.reconx.repository.ReconResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReconciliationService {

    private final ReconciliationEngine engine;
    private final ReconResultRepository reconResultRepo;

    public ReconciliationService(ReconciliationEngine engine, ReconResultRepository reconResultRepo) {
        this.engine = engine;
        this.reconResultRepo = reconResultRepo;
    }

    public void runRecon(List<? extends TradeType> internal, List<? extends TradeType> external) {
        runRecon(internal, external, ReconciliationRule.EXACT);
    }

    @SuppressWarnings("unchecked")
    public void runRecon(List<? extends TradeType> internal, List<? extends TradeType> external, ReconciliationRule rule) {
        List<ReconResult> results = engine.reconcile((List<TradeType>) internal, (List<TradeType>) external, rule);
        for (ReconResult result : results) {
            reconResultRepo.save(result);
        }
    }
}
