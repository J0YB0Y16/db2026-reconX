package com.dbtraining.reconx.exception;
//ticket 62
import com.dbtraining.reconx.common.exception.DuplicateTradeRefException;
import com.dbtraining.reconx.common.exception.ReconException;
import com.dbtraining.reconx.common.exception.TradeNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TradeNotFoundException.class)
    public ProblemDetail handleNotFound(TradeNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setType(URI.create("https://reconx.dbtraining.com/errors/trade-not-found"));
        pd.setTitle("Trade not found");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(DuplicateTradeRefException.class)
    public ProblemDetail handleDuplicate(DuplicateTradeRefException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        pd.setType(URI.create("https://reconx.dbtraining.com/errors/duplicate-trade-ref"));
        pd.setTitle("Duplicate trade reference");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(ReconException.class)
    public ProblemDetail handleReconConflict(ReconException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        pd.setType(URI.create("https://reconx.dbtraining.com/errors/recon-failure"));
        pd.setTitle("Reconciliation failure");
        pd.setProperty("timestamp", Instant.now());
        if (ex.getReconBreakId() != null) {
            pd.setProperty("reconBreakId", ex.getReconBreakId());
        }
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
            .map(f -> f.getField() + ": " + f.getDefaultMessage())
            .collect(Collectors.joining("; "));
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, msg);
        pd.setType(URI.create("https://reconx.dbtraining.com/errors/validation-failed"));
        pd.setTitle("Validation failed");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraint(ConstraintViolationException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setType(URI.create("https://reconx.dbtraining.com/errors/constraint-violation"));
        pd.setTitle("Constraint violation");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleAny(Exception ex) {
        log.error("Unhandled exception", ex);
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred — please contact support with the correlationId");
        pd.setType(URI.create("https://reconx.dbtraining.com/errors/internal-server-error"));
        pd.setTitle("Internal server error");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }
}// Minor update for git tracking