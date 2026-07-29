# ADR-0003 — Use GIN (`jsonb_path_ops`) instead of B-tree for JSONB metadata

**Status:** Accepted  
**Date:** 2026-07-29

## Context

ReconX analysts frequently search instrument metadata using JSON containment
queries, such as locating all options with a specific strike price or all bonds
issued by a particular issuer.

With approximately 91 million retained trades, sequential scans or B-tree
indexes on the entire JSON document would not scale.

## Decision

Create a PostgreSQL GIN index using the `jsonb_path_ops` operator class on the
`instruments.metadata` column.

```sql
CREATE INDEX idx_instruments_metadata
ON instruments
USING GIN (metadata jsonb_path_ops);
```

B-tree indexes remain reserved for scalar relational columns such as ISIN,
symbol, and trade reference.

## Consequences

### Positive

- Efficient containment (`@>`) queries over JSONB documents.
- Significantly faster metadata searches during reconciliation.
- Better storage efficiency than a generic GIN operator class for containment
  workloads.

### Negative

- Not suitable for ordering operations.
- Slightly slower inserts due to index maintenance.
- Additional B-tree indexes are still required for standard relational lookups.