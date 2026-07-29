# ADR-0001 — Partition the `trades` table by `trade_date`

**Status:** Accepted  
**Date:** 2026-07-29

## Context

ReconX stores approximately 50,000 new trades every day and retains data for
five years, resulting in roughly 91 million trade records. Nearly every
reconciliation workflow filters trades by business date, while compliance
requires efficient archival after the retention period.

Maintaining a single table would increase index size, slow maintenance
operations, and make retention deletes expensive.

## Decision

Partition the `trades` table using PostgreSQL RANGE partitioning on
`trade_date`, with one partition per calendar month.

The primary key includes `trade_date` to satisfy PostgreSQL partitioning rules.
Monthly partitions are created automatically 12 months ahead.

A `trades_default` partition captures unexpected inserts outside the configured
range, with operational alerts raised whenever it receives data.

## Consequences

### Positive

- Partition pruning reduces scanned data for date-filtered reconciliation jobs.
- Five-year archival is performed using `DETACH PARTITION` instead of deleting
  millions of rows.
- Smaller partition indexes reduce maintenance overhead.

### Negative

- Composite primary keys increase JPA mapping complexity.
- Global uniqueness constraints require additional handling.
- Scheduled partition creation becomes an operational responsibility.