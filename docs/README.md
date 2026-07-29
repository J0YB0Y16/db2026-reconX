# Architecture Decision Records (ADR)

This directory contains Architecture Decision Records (ADRs) for the ReconX
Enterprise Trade Reconciliation Platform.

Each ADR follows the Michael Nygard format:

1. Title
2. Status
3. Context
4. Decision
5. Consequences

Every ADR should be generated (or drafted) using the following prompt template
so reviewers understand the assumptions and reasoning used.

---

## ADR Prompt Template

You are an enterprise software architect. Write an Architecture Decision Record
(ADR) in the Michael Nygard format (Title, Status, Context, Decision,
Consequences) for the following decision.

System: ReconX, a near-production trade reconciliation platform.

Stack:
- PostgreSQL 16
- Spring Boot 3
- Apache Kafka
- React

Scale:
- ~50,000 trades/day
- ~5-year retention
- ~91 million trade records
- 10 concurrent reconciliation analysts
- Daily reconciliation batches with intraday trade updates

Decision to record:

<ONE LINE DESCRIBING THE DECISION>

Alternatives we considered:

- <Alternative 1>
- <Alternative 2>
- <Alternative 3>

Constraints / forces:

- <Constraint 1>
- <Constraint 2>
- <Constraint 3>

Requirements:

- Markdown
- Michael Nygard ADR format
- Maximum 300 words
- No generic architecture language
- Mention ReconX-specific scale where appropriate
- State concrete trade-offs
- Include

Status: Accepted | Date: <YYYY-MM-DD>