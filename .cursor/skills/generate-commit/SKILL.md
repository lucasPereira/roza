---
name: generate-commit
description: Generate a Róża commit proposal from pending Git changes and commit it only after user approval of a verified ≤72-character subject.
disable-model-invocation: true
---

# Generate commit for Róża

This skill prepares one Róża commit with a single subject line. Follow [Repository scope](#repository-scope), [Commit subject](#commit-subject), and [Approval workflow](#approval-workflow) in order.

## Repository scope

Run Git commands from the Róża repository root, wherever that checkout lives. The message must reflect the full tree versus `HEAD`: staged changes, unstaged changes, and untracked files, respecting `.gitignore`.

**Inspect before staging.** Run `git status` and `git diff HEAD` to understand all pending changes before composing the subject. If the tree is clean, report that and stop without running `git add` or `git commit`.

## Commit subject

The commit message is a single short subject line that summarizes every pending change in the Róża repository.

**Use one line only.** Do not add a body. Do not use conventional prefixes, scoped prefixes, or slugs such as `feat:`, `fix:`, `chore:`, or `docs-modern:`.

**Start uppercase and use imperative mood.** Prefer English unless the user asks for Portuguese. The first character must be uppercase.

**Enforce the 72-character maximum length.** Count the exact visible characters in the full subject before showing it to the user. If it is 73 characters or more, rewrite and recount until it is 72 characters or fewer. Never depend on tools to truncate the message.

**Compress unrelated changes carefully.** If pending changes are unrelated, still propose one umbrella subject within 72 characters and warn that bundling unrelated work is risky. Offer to split only if the user asks.

## Approval workflow

These steps preserve the staging, approval, decline, and failure gates.

1. Compose and verify the subject using [Commit subject](#commit-subject).
2. Run `git add -A` at the repository root.
3. Paste the full `git status` output after staging.
4. Paste the single subject line.
5. Do not paste `git diff --cached` unless the user asks for a patch preview.
6. Ask for explicit approval: `yes`, `ok`, `pode`, `aprovo`, or equivalent.
7. Do not run `git commit` before approval.
8. If approved, run one `git commit -m "…"` with the exact subject.
9. Do not add a second `-m`, here-doc, or editor body unless the user asks.
10. If the user declines, do not commit; offer `git reset` to unstage.
11. If `git commit` fails, paste the error output and do not claim success.

## Examples

See [examples.md](examples.md) for sample valid subject lines.
