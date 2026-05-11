# Generate commit (Roza)

Follow **`.cursor/skills/generate-commit/SKILL.md`** end-to-end.

- Git runs **only** in the **`roza/`** repository root (this submodule), not the parent workspace root when that is doctorate.
- Inspect the full uncommitted tree, compose **`slug: commit message`**, then run **`git add -A`** and **`git commit -m "…"`** with that line.
- If the working tree is already clean, stop without committing.
