---
name: roza-modern-development
description: Maintains requirements, acceptance criteria, decisions, and implementation knowledge for the modern Roza architecture. Use only when work explicitly targets modern Roza, core.modern, docs/roza-modern, or requirements for the modern architecture; do not use for legacy Roza work unless comparing it with modern Roza.
---

# Roza Modern Development

## Quick Start

Before answering or implementing work about modern Roza or `core.modern`:

1. Read `docs/roza-modern/requirements.md` as the user-driven source of truth: what the user said or confirmed modern Roza must do, what constraints it must satisfy, which acceptance criteria define done, and which requirement questions need the user's decision.
2. Read `docs/roza-modern/knowledge.md` as the agent-maintained understanding: current vocabulary, design rationale, architectural decisions, implementation discoveries, model knowledge, hypotheses, and interview backlog.
3. Classify the user's message:
   - Update `requirements.md` only when the user states or confirms required behavior, constraints, acceptance criteria, or a question that must be answered before requirements are complete.
   - Update `knowledge.md` when the agent records terminology, interpretation, design rationale, architectural decisions, implementation discoveries, model understanding, hypotheses, or interview backlog.
   - Update both files only when the user-confirmed requirement also teaches something durable about how the agent should understand or implement the model.
4. Then proceed with design, implementation, validation, or interview questions.

## Documentation Contract

Maintain these files:

- `docs/roza-modern/requirements.md`: user-driven, normative requirements, constraints, acceptance criteria, and open questions that need the user's decision.
- `docs/roza-modern/knowledge.md`: agent-maintained, non-normative architectural knowledge, model vocabulary, implementation discoveries, decisions, hypotheses, and interview backlog.

Use stable IDs in requirements:

- `REQ-###` for functional requirements.
- `NFR-###` for non-functional requirements or architectural constraints.
- `AC-###` for acceptance criteria.
- `Q-###` for open questions.

Preserve history unless the user explicitly supersedes a requirement. When a requirement changes, update the requirement text and add a short note explaining the user-confirmed refinement. Do not promote agent assumptions from `knowledge.md` into requirements without user confirmation.

## Requirement Capture Rules

Treat user statements as candidate requirements when they are explicitly scoped to modern Roza and use language such as:

- "deve", "precisa", "é importante", "sempre", "nunca"
- "modern Roza", "core.modern", "pipeline", "etapa", "interface"
- extensibility expectations for languages, frameworks, refactorings, or implementations

Do not treat generic Roza, legacy Roza, pipeline, or automated test-code refactoring discussion as modern Roza requirements unless the user explicitly connects it to modern Roza or `core.modern`.

For each candidate requirement, decide whether to:

- add a new requirement,
- refine an existing requirement,
- add or refine acceptance criteria,
- record an open question,
- or store implementation knowledge in `knowledge.md`.

Only add or refine requirements when the user states or confirms them. If the requirement is ambiguous, ask a question or record the uncertainty as an open question; put any agent interpretation in `knowledge.md` as non-normative context.

## Implementation Guidance

When implementing `core.modern`:

- Model each pipeline stage as an interface unless an existing requirement says otherwise.
- Keep stages independent. A stage should depend on input/output contracts, not concrete implementations of other stages.
- Support interchangeable implementations for each stage.
- Prefer extension points over language-, framework-, or refactoring-specific assumptions.
- Do not use or reuse classes from legacy Roza. Modern Roza implementation must be written from scratch.
- Validate that new code does not couple unrelated stages directly.

## Interview Workflow

When the user asks to define or discover modern Roza requirements:

1. Summarize the known requirements briefly.
2. Ask focused questions that reduce architectural uncertainty.
3. Prefer questions about contracts, stage boundaries, extensibility, error handling, and acceptance criteria.
4. After the user answers, update `requirements.md` for user-confirmed requirements and `knowledge.md` for the agent's resulting understanding before implementing.

## Final Response Checklist

When finishing modern Roza work, mention:

- which requirement or knowledge files were updated,
- whether implementation was changed,
- which validation was run,
- and which open questions remain relevant.
