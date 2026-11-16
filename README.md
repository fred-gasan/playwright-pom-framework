Line-Ending Verification Report (Pre-Staging Stop)

  In strict accordance with Instruction 2 ("Before staging, verify that the XML file does not contain unrelated CRLF/LF changes. If it does, stop and report the
  issue rather than staging unrelated changes"), I have stopped before staging any files.

  ---

  1. Issue Discovered: Repository Line-Ending (CRLF vs. LF) Mismatch

   - Git Tracking Reality: The repository upstream (origin/dev) standardizes on Unix LF line endings (\n) (established in commit 336f4b7: "normalize entrypoint.sh
     and groovy scripts to Unix LF line endings").
   - Local Windows Working Tree: On Windows (win32), readyapi/ap-e2e-soapui-project.xml has Windows CRLF line endings (\r\n).
   - Diff Comparison:
     - With Whitespace Ignored (git diff --ignore-space-at-eol):

   1     readyapi/ap-e2e-soapui-project.xml | 56 +++-----------------------------------
   2     1 file changed, 4 insertions(+), 52 deletions(-)
      (Only the intended 4 insertions and 52 deletions).
     - Raw Git Diff (git diff without flags):

   1     readyapi/ap-e2e-soapui-project.xml | 2804 ++++++++++++++++++------------------
   2     1 file changed, 1378 insertions(+), 1426 deletions(-)
     - git diff --check Warning:
      Every line in readyapi/ap-e2e-soapui-project.xml and readyapi/Scripts/VerifyAdHocPaymentDetails.groovy triggers a trailing whitespace warning due to the
  carriage return character (\r).

  ---

  2. Impact if Staged Now

  If readyapi/ap-e2e-soapui-project.xml were staged right now without normalizing line endings:
   1. It would introduce 2,804 lines of unrelated CRLF/LF churn across the entire project file.
   2. It would fail CI/CD linting and merge conflict checks.
   3. It would violate the repository hygiene mandate.

  ---

  3. Recommended Resolution

  Before running git add, we should normalize both files to Unix LF (\n):
   1. Convert readyapi/ap-e2e-soapui-project.xml line endings from CRLF to LF.
   2. Convert readyapi/Scripts/VerifyAdHocPaymentDetails.groovy line endings from CRLF to LF.
   3. Verify that git diff --check returns 0 warnings.
   4. Verify that git diff --stat readyapi/ap-e2e-soapui-project.xml reflects only the 56 substantive lines.
   5. Then proceed with staging only those two files.

  ---

  4. Current Status
   - Staged Files: 0 files staged (git diff --cached is empty).
   - Working Tree: All local changes remain preserved.
   - Awaiting Approval: Please confirm if you would like me to normalize the line endings to Unix LF on these two files before performing selective staging.
