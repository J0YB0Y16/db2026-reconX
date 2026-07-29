package com.dbtraining.reconx.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recon_results")
public class ReconResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trade_ref", nullable = false, length = 30)
    private String tradeRef;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "discrepancy_type", length = 30)
    private String discrepancyType;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String details;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTradeRef() { return tradeRef; }
    public void setTradeRef(String tradeRef) { this.tradeRef = tradeRef; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDiscrepancyType() { return discrepancyType; }
    public void setDiscrepancyType(String discrepancyType) { this.discrepancyType = discrepancyType; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
