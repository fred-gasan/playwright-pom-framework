Important update before PR preparation:

I have NOT created a feature branch yet. All my current work has been done directly on the local dev branch.

I also have some local files and configurations that may need to be excluded through .gitignore. I am not sure which ones should be ignored, and I want to make sure we do not accidentally include unrelated files, credentials, or local environment changes in the PR.

Please perform a READ-ONLY Git and repository hygiene audit.

1. Confirm the current branch and its tracking/upstream configuration.
2. Run git status and identify all modified, untracked, and ignored files.
3. Review the existing .gitignore rules.
4. Identify which files belong to the UAT 7–10 automation implementation.
5. Identify any unrelated local changes, generated files, logs, caches, credentials, or environment-specific configuration files.
6. Check whether any sensitive files are already tracked by Git, without displaying secret values.
7. Review the current diff for the two intended files.
8. Check whether the local dev branch is ahead of or behind origin/dev using existing local Git metadata. Do not fetch or pull yet.
9. Recommend which files should be committed, which should remain local, and which should be added to .gitignore.
10. Recommend the safest way to create a feature branch while preserving all current work.

Important restrictions:

* Do NOT modify .gitignore yet.
* Do NOT delete, clean, reset, restore, or stash any files.
* Do NOT create or switch branches yet.
* Do NOT stage or commit anything.
* Do NOT fetch, pull, push, or create a PR.
* Do NOT print credentials, tokens, passwords, or secret values.

Please provide a clear file-by-file audit report and a safe step-by-step plan before making any changes.