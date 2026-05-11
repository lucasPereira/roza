---
name: generate-commit
description: >-
  Roza (Java/Gradle): in the roza submodule repo root, build one line
  `slug: commit message` from the full uncommitted tree, stage everything not yet
  committed (`git add -A`), and run `git commit` with that message. Triggers:
  generate commit, /generate-commit, mensagem de commit, or when this skill is
  attached for Roza commits.
---

# Generate commit for Roza (`slug: commit message`)

## How to invoke

- **Slash command:** **`/generate-commit`** via `.cursor/commands/generate-commit.md` (workspace root or `roza/` depending on what you opened in Cursor).
- **Skill:** attach this `SKILL.md` for the same workflow.

## Goal

1. Derive **one** commit message line:

```text
slug: commit message
```

- **slug**: short `kebab-case` (topic or conventional style, e.g. `feat`, `fix`, `docs`, `chore`, optionally scoped).
- **commit message**: imperative summary of **everything** that was not yet committed in **this** repo.

2. **Stage** all changes that are not committed yet (staged, unstaged, and untracked) and **create the commit** with that line as the message.

## Repo root (mandatory)

The Git repository is the **Roza submodule** (`roza/`), **not** the doctorate workspace root.

Run all Git commands **from `roza/`** (e.g. `cd roza` or `git -C <path-to-roza>`).

## Scope

The message must reflect the **full** tree vs `HEAD` before staging: staged, unstaged, and **untracked** paths (respecting `.gitignore` via normal Git rules).

## Steps

1. `cd` to the **roza** submodule root (or use `git -C <roza-root>`).
2. Run `git status` and `git diff HEAD` (and short/porcelain status if useful) to understand **all** pending changes.
3. If there is **nothing** to commit (clean tree), report that and **stop** — do not run `git commit`.
4. Compose **one** line `slug: commit message` covering the full picture. Show that line to the user, then proceed (or proceed and echo it in the final summary).
5. Stage everything not yet committed: **`git add -A`** at the **roza** repo root (adds new files, stages modifications, stages removals; honors ignored files).
6. Commit: **`git commit -m "slug: commit message"`** using the exact composed line (quote safely for the shell).
7. If `git commit` fails (e.g. empty commit after add, hook failure, GPG signing issues), report the error output; do not claim success.

## Slug and message

- Prefer **English** unless the user asks for Portuguese.
- One line; no vague slugs (`update`, `fix-stuff`). If changes are unrelated, reflect the main change and briefly note the rest, or warn that bundling unrelated work is risky and offer to split only if the user asks.

## Examples

```text
chore-roza: move legacy loader fixtures under test/resources/legacy/loader
```

```text
gradle-roza: rename runRozaUi application task to runLegacyRozaUi
```

```text
docs-modern-roza: document bidirectional modern/legacy import isolation
```
