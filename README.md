I approve resolving the AllureListener.groovy line-ending conflict and creating the new FPOAP-182 feature branch, subject to the following safeguards.

1. Run:
    git diff –ignore-space-at-eol HEAD – readyapi/Scripts/AllureListener.groovy
2. Independently verify that the local file differs from HEAD only by CRLF/LF line endings, with no substantive code or whitespace changes.
3. Confirm that the file has no staged changes.
4. If the differences are strictly line-ending related, restore ONLY this specific file:
    git restore –source=HEAD –worktree – readyapi/Scripts/AllureListener.groovy
5. Verify that the file is clean after restoration.
6. Confirm that all other local modifications and untracked files remain unchanged.
7. Create the new feature branch from the latest origin/dev:
    git switch -c feature/fpoap-182/dev origin/dev
8. Verify:
    * Current branch is feature/fpoap-182/dev.
    * HEAD matches origin/dev.
    * FPOAP-165 squash-merged changes are present.
    * AllureListener.groovy contains the latest upstream changes.
    * All unrelated local modifications remain intact.
    * No unintended files are staged.

IMPORTANT:

* If any substantive local code changes are detected in AllureListener.groovy, stop immediately.
* Do not restore any other files.
* Do not use git reset, git clean, git stash, or force checkout.
* Do not change global Git configuration or line-ending settings.
* Do not modify credentials or local configuration.
* Do not commit, push, or run live tests.
* Do not begin FPOAP-182 implementation yet.

If any unexpected conflict occurs, stop and report it.

Return the final branch name, HEAD SHA, working tree status, and confirmation that all unrelated local changes were preserved.