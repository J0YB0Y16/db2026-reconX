package com.dbtraining.reconx.common.exception;

public class ReconException extends RuntimeException {
    private final Long reconBreakId;

    public ReconException(String message) {
        super(message);
        this.reconBreakId = null;
    }

    public ReconException(String message, Long reconBreakId) {
        super(message);
        this.reconBreakId = reconBreakId;
    }

    public Long getReconBreakId() {
        return reconBreakId;
    }
}