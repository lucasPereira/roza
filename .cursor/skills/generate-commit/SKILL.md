---
name: generate-commit
description: >-
  Roza (Java/Gradle): in the roza submodule repo root, build one subject line
  (no conventional type/slug prefix; first letter uppercase), stage with
  `git add -A`, show **`git status`** after staging and the **composed commit
  message** (one line; **hard cap 72 characters**—shorten until compliant), wait
  for explicit user approval, then run **`git commit -m "…"`**. Use when the user
  attaches this skill or asks to generate commit / mensagem de commit for Roza.
---

# Generate commit for Roza (single subject line)

## How to invoke

Attach this **`SKILL.md`** (or ask the agent to follow it). Do **not** rely on or add Cursor slash commands for this workflow unless the user explicitly requests them.

## Goal

1. Derive **one** commit message line: a **short subject** only (no body in this workflow).

   **Format (mandatory):**

   - **No** conventional prefix such as `feat:`, `fix:`, `chore:`, or scoped `docs-modern:` — the line is **only** the summary text.
   - **First character must be uppercase** (English: capitalize the first letter of the sentence; Portuguese: capitalize the first letter of the first word per normal orthography).
   - **Imperative mood**, present tense summary of **everything** not yet committed in **this** repo (same intent as before, without a slug).
   - **Length (non-negotiable):** the **entire** line must be **≤72 characters**. Treat **72** as a **hard** ceiling: if the draft is **73+**, shorten **before** the user ever sees it (see **Verify length** in Steps). Never depend on tools to truncate for you.

2. **Stage** everything not yet committed (`git add -A`), then show the user **only**: full **`git status`** output (after the add) **and** the **composed commit message line** (the same ≤72-character line you verified). Then **ask for explicit approval** before running **`git commit`**. If the user does not approve, **do not** commit; offer `git reset` to unstage if they want to undo staging. Do **not** show **`git diff --cached`** unless the user explicitly asks for a patch preview.

## Repo root (mandatory)

The Git repository is the **Roza submodule** (`roza/`), **not** the doctorate workspace root.

Run all Git commands **from `roza/`** (e.g. `cd roza` or `git -C <path-to-roza>`).

## Scope

The message must reflect the **full** tree vs `HEAD` before staging: staged, unstaged, and **untracked** paths (respecting `.gitignore` via normal Git rules).

## Steps

1. `cd` to the **roza** submodule root (or use `git -C <roza-root>`).
2. Run `git status` and `git diff HEAD` (and short/porcelain status if useful) to understand **all** pending changes.
3. If there is **nothing** to commit (clean tree), report that and **stop** — do not run `git add` or `git commit`.
4. Compose **one** subject line covering the full picture: **no** `type:` prefix; **first character uppercase**; imperative; **≤72** characters total.
5. **Verify length (mandatory):** count characters in the **full** string exactly as the user will see it. If the count is **greater than 72**, rewrite and re-count until **≤72**. Repeat until compliant.
6. Stage everything not yet committed: **`git add -A`** at the **roza** repo root (adds new files, stages modifications, stages removals; honors ignored files).
7. After staging, show the user (before asking approval):
   - **`git status`**: paste the **full** output (branch, ahead/behind if any, and the whole “Changes to be committed” section).
   - The **commit message** you composed: the single line (and optionally the exact `git commit -m "…"` command using that line).
8. **Stop and ask for explicit approval** (e.g. yes / ok / pode / aprovo) to run the commit. Do **not** run `git commit` until the user confirms.
9. After approval: run **`git commit -m "…"`** once with the exact composed subject line only (quote safely for the shell). Do **not** add a second `-m`, a here-doc, or an editor-driven multi-line message unless the user explicitly asks for that.
10. If the user declines approval, do **not** commit. Optionally offer **`git reset`** (mixed or keep their preference) to unstage if they want to revert the index.
11. If `git commit` fails (e.g. hook failure, GPG signing issues), report the error output; do not claim success.

## Subject line rules

- Prefer **English** unless the user asks for Portuguese.
- **No** `slug:` / `type:` prefix; **do** start with an **uppercase** letter.
- One line; **hard cap 72 characters** for the **entire** line—**no** “close enough” overruns. Avoid vague verbs (“Update stuff”). If changes are unrelated, compress into one umbrella summary **≤72** and warn in chat that bundling unrelated work is risky; offer to split only if the user asks.

### Long summaries and laundry lists

Comma-separated or “and also …” lists **will** blow the 72-character budget. **Compress** (stronger umbrella verb + one object) or **split scope** in your head—still keep **this** proposed line within 72 chars.

## Examples

```text
Move legacy loader fixtures to test/resources/legacy/loader
```

```text
Rename runRozaUi Gradle task to runLegacyRozaUi
```

```text
Document modern and legacy import isolation in modern docs
```
