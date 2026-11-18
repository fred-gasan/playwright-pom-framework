I approve Option A: feature/fpoap-165/dev.

Please proceed with the following steps:

1. Verify that the current branch is feature/fpoap-165-adhoc-payment-validations and HEAD is commit dfd0218.
2. Confirm the new branch name feature/fpoap-165/dev does not already exist locally or remotely.
3. Create the new local branch from the existing commit dfd0218:
    git switch -c feature/fpoap-165/dev
4. Verify the new branch points to dfd0218 and contains both the original FPOAP-165 implementation and the latest validation fixes.
5. Push ONLY feature/fpoap-165/dev to origin and configure upstream tracking.
6. Verify the remote branch exists and points to the expected commit.
7. Check whether GCP Cloud Build CI was actually triggered for the new branch.
8. If Cloud Build is running or completed, report its build ID, status, and any failures.

Important:

* Do not create another commit.
* Do not stage unrelated files.
* Do not modify source code or fixtures.
* Do not delete or rename the original branch.
* Do not close or modify PR #234.
* Do not create the replacement PR yet.
* Do not run additional live E2E tests.
* If any command fails, stop and report the exact error without destructive recovery.

After the push, report the new branch name, commit SHA, remote push status, Cloud Build status, and whether we are ready to create the replacement PR against dev.