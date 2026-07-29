package com.dbtraining.reconx.repository;

import com.dbtraining.reconx.dto.ReconResult;
import com.dbtraining.reconx.repository.entity.ReconResultEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReconResultRepositoryImpl implements ReconResultRepository {
    
    private final ReconResultJpaRepository jpaRepo;

    public ReconResultRepositoryImpl(ReconResultJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public void save(ReconResult result) {
        ReconResultEntity entity = new ReconResultEntity();
        entity.setTradeRef(result.tradeRef());
        entity.setStatus(result.status().name());
        entity.setDiscrepancyType(result.discrepancyType());
        entity.setDetails(result.details());
        jpaRepo.save(entity);
    }

    @Override
    public List<ReconResult> findAll() {
        return jpaRepo.findAll().stream().map(e -> new ReconResult(
                e.getTradeRef(),
                ReconResult.Status.valueOf(e.getStatus()),
                e.getDiscrepancyType(),
                e.getDetails()
        )).collect(Collectors.toList());
    }
}
