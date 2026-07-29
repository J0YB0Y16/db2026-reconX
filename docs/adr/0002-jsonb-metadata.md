# ADR-0002 — Store instrument metadata in JSONB

**Status:** Accepted  
**Date:** 2026-07-29

## Context

ReconX reconciles trades across multiple asset classes. Instrument-specific
attributes vary significantly between Equities, FX, Bonds, ETFs, and
Derivatives.

Adding nullable relational columns for every possible attribute would create a
wide, sparsely populated table that requires schema migrations whenever new
instrument types are introduced.

## Decision

Store variable instrument attributes in a PostgreSQL `JSONB` column named
`metadata`.

Core business identifiers (ISIN, symbol, exchange, currency) remain relational
columns to preserve integrity and joins, while optional asset-specific fields
are stored inside `metadata`.

Examples include coupon rate, maturity date, strike price, option style, and
issuer-specific attributes.

## Consequences

### Positive

- Supports new instrument types without schema migrations.
- Reduces nullable columns.
- Allows flexible indexing for frequently queried metadata.

### Negative

- JSON schema is enforced by the application instead of PostgreSQL.
- Complex JSON queries are less readable than relational queries.
- Excessive JSON usage could reduce consistency if core attributes migrate into
  metadata.