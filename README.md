Stage 1 Safety Verification & Conflict Report (Pre-Branch Creation Stop)

  In strict accordance with your instruction ("If switching would overwrite or risk any existing local changes, STOP and report the conflict. Do
  not automatically stash, reset, clean, restore, discard, or overwrite files"), I have stopped before creating or switching branches.

  ---

  1. Verification of origin/dev and PR #237 Squash-Merge

   - Fetch Status: Succeeded via git fetch origin dev.
   - Target Remote Branch Commit: 45e8091a87ec230917fe87d641468fea74da276c (origin/dev).
   - Squash-Merge Verification: Confirmed. Commit 45e8091 on origin/dev contains the squash-merged PR #237 ([FPOAP-165] Add ReadyAPI Ad-Hoc Payment
     validations for UAT 7–10 (#237)), which includes:
     - readyapi/Scripts/VerifyAdHocPaymentDetails.groovy (+204 lines)
     - readyapi/ap-e2e-soapui-project.xml (+4, -52 lines)
   - Branch Namespace Verification: Neither feature/fpoap-182 nor feature/fpoap-182/dev exists locally or remotely. There are zero ref-locking
     namespace conflicts.

  ---

  2. The Conflict Detected Between Working Tree and origin/dev

  We compared the 11 modified working tree files against the 53 files changed between our current HEAD (dfd0218) and the target commit origin/dev
  (45e8091).

  Overlapping File in Conflict:
   - readyapi/Scripts/AllureListener.groovy

  Why a Switch Conflict Occurs:
   1. On origin/dev: In an upstream merge (PR #226 / FPOAP-156 duplicate checks), line 124 of readyapi/Scripts/AllureListener.groovy was updated to
      add baseline ad-hoc step names ('01h2-Verify-Baseline-AdHoc-SOAP', '04b-Verify-Baseline-AdHoc-Integrity').
   2. In Local Working Tree: readyapi/Scripts/AllureListener.groovy is currently dirty due to Windows CRLF line endings from checkout.
   3. Git's Safety Rule: Because AllureListener.groovy has unstaged working tree modifications and differs between HEAD and origin/dev, Git will
      refuse to switch or branch directly to origin/dev, aborting with:
     error: Your local changes to the following files would be overwritten by checkout: readyapi/Scripts/AllureListener.groovy.

  ---

  3. Current Working Tree & Developer Artifact Status

   - Current Branch: feature/fpoap-165/dev
   - Current HEAD SHA: dfd0218
   - Substantive Local Edits: scripts/run_local_server.py (+46 lines for local Uvicorn/Windows compatibility).
   - Untracked Local Configs: alembic.local.ini, readyapi/view_allure_report.bat.
   - Checkout Line-Ending Differences: 10 tracked files (including AllureListener.groovy).
   - Status: 100% preserved. No files have been stashed, reset, restored, cleaned, or modified.

  ---

  4. Recommended Resolution to Safely Branch to feature/fpoap-182/dev

  To safely create feature/fpoap-182/dev from origin/dev without risking any work:

   - The Only Necessary Action: Normalize the line endings on readyapi/Scripts/AllureListener.groovy or restore that single file to HEAD so it is
     clean (since it has zero local code changes, only line endings):
     - Once readyapi/Scripts/AllureListener.groovy matches HEAD, git switch -c feature/fpoap-182/dev origin/dev will succeed seamlessly.
     - Your local enhancements to scripts/run_local_server.py, alembic.local.ini, and view_allure_report.bat will carry over completely unharmed,
       because origin/dev does not touch any of those files.

  Please let me know if you approve restoring or normalizing readyapi/Scripts/AllureListener.groovy so we can proceed with creating
  feature/fpoap-182/dev.
