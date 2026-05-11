---
name: generate-commit
description: >-
  Roza (Java/Gradle): in the roza submodule repo root, build one line
  `slug: commit message` from the full uncommitted tree, stage with `git add -A`,
  show **`git status`** after staging and the **composed commit message** (one
  line; **hard cap 72 characters** for the entire string including `slug: ` and
  spaces—shorten until compliant), wait for explicit user approval, then run
  `git commit`. Use when the user attaches this skill or asks to generate commit
  / mensagem de commit for Roza.
---

# Generate commit for Roza (`slug: commit message`)

## How to invoke

Attach this **`SKILL.md`** (or ask the agent to follow it). Do **not** rely on or add Cursor slash commands for this workflow unless the user explicitly requests them.

## Goal

1. Derive **one** commit message line:

```text
slug: commit message
```

- **slug**: short `kebab-case` (topic or conventional style, e.g. `feat`, `fix`, `docs`, `chore`, optionally scoped such as `docs-modern` for modern-Roza docs). **Do not** add a redundant **`roza`** segment to the slug (e.g. avoid `chore-roza`); commits already live in the Roza repo.
- **commit message**: imperative summary of **everything** that was not yet committed in **this** repo.
- **Length (non-negotiable):** the **entire** single line—`slug`, `:`, spaces, and message—must be **≤72 characters**. Treat **72** as a **hard** ceiling, not a guideline: if the draft is **73+**, shorten **before** the user ever sees it (see **Verify length** in Steps). Never depend on tools to truncate for you.

2. **Stage** everything not yet committed (`git add -A`), then show the user **only**: full **`git status`** output (after the add) **and** the **composed `slug: commit message` line** (the same ≤72-character line you verified). Then **ask for explicit approval** before running **`git commit`**. If the user does not approve, **do not** commit; offer `git reset` to unstage if they want to undo staging. Do **not** show **`git diff --cached`** unless the user explicitly asks for a patch preview.

## Repo root (mandatory)

The Git repository is the **Roza submodule** (`roza/`), **not** the doctorate workspace root.

Run all Git commands **from `roza/`** (e.g. `cd roza` or `git -C <path-to-roza>`).

## Scope

The message must reflect the **full** tree vs `HEAD` before staging: staged, unstaged, and **untracked** paths (respecting `.gitignore` via normal Git rules).

## Steps

1. `cd` to the **roza** submodule root (or use `git -C <roza-root>`).
2. Run `git status` and `git diff HEAD` (and short/porcelain status if useful) to understand **all** pending changes.
3. If there is **nothing** to commit (clean tree), report that and **stop** — do not run `git add` or `git commit`.
4. Compose **one** line `slug: commit message` covering the full picture.
5. **Verify length (mandatory):** count characters in the **full** string exactly as the user will see it—the entire line including `slug`, the colon, every space, and the message. If the count is **greater than 72**, rewrite (compress wording, drop minor clauses, or narrow the summary) and re-count until **≤72**. **No exceptions** for “almost” or “just one word over”; Git and UIs do **not** safely truncate first lines—**you** must deliver a compliant line before any presentation. Repeat this verify/rewrite loop until compliant.
6. Stage everything not yet committed: **`git add -A`** at the **roza** repo root (adds new files, stages modifications, stages removals; honors ignored files).
7. After staging, show the user (before asking approval):
   - **`git status`**: paste the **full** output (branch, ahead/behind if any, and the whole “Changes to be committed” section).
   - The **commit message** you composed: the single line `slug: commit message` (and optionally the exact `git commit -m "…"` command using that line).
8. **Stop and ask for explicit approval** (e.g. yes / ok / pode / aprovo) to run the commit. Do **not** run `git commit` until the user confirms.
9. After approval: **`git commit -m "slug: commit message"`** using the exact composed line (quote safely for the shell).
10. If the user declines approval, do **not** commit. Optionally offer **`git reset`** (mixed or keep their preference) to unstage if they want to revert the index.
11. If `git commit` fails (e.g. hook failure, GPG signing issues), report the error output; do not claim success.

## Slug and message

- Prefer **English** unless the user asks for Portuguese.
- One line; **hard cap 72 characters** for the **entire** line (`slug: commit message`), **including** the colon and spaces—**no** “close enough” overruns. No vague slugs (`update`, `fix-stuff`). **Never** use a `-roza` suffix (or `something-roza`) in the slug — it is redundant. If changes are unrelated, reflect the main change in the line (compressed so the **whole line** stays ≤72) and warn in chat that bundling unrelated work is risky; offer to split only if the user asks.

### Long summaries and laundry lists

Comma-separated or “and also …” lists that read like a changelog **will** blow the 72-character budget. **Compress** (stronger umbrella verb + one object, drop redundant qualifiers) or **split scope** in your head and commit the dominant theme in one focused line **≤72**—do **not** rely on Git, hosts, or mailers to truncate; they produce ugly or misleading previews. If the tree truly mixes unrelated concerns, say so in chat and suggest separate commits—still keep **this** proposed line within 72 chars.

## Examples

```text
chore: move legacy loader fixtures to test/resources/legacy/loader
```

```text
gradle: rename runRozaUi task to runLegacyRozaUi
```

```text
docs-modern: document modern/legacy import isolation
```
