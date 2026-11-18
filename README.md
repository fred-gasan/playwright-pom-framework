I approve creating the replacement Pull Request with the proposed title and description.

Please proceed with the following safeguards:

1. Create a new PR in aaa-ncnu/accounts-payable-invoice-processing.
2. Base branch: dev
3. Head branch: feature/fpoap-165/dev
4. Title: [FPOAP-165] Add ReadyAPI Ad-Hoc Payment validations for UAT 7–10
5. Use the proposed PR description, including the reference to PR #234, local E2E results (4/4 PASS, 203/203 assertions), and successful Cloud Build check.
6. Explicitly retain the limitation that Workday Approved status for UAT 7 remains unverified at the SOAP layer.
7. Also clarify that when Workday omits the payee descriptor, the test verifies payee reference integrity but does not independently verify the expected payee name.
8. Verify the PR contains exactly the intended two files:
    * readyapi/Scripts/VerifyAdHocPaymentDetails.groovy
    * readyapi/ap-e2e-soapui-project.xml
9. Verify that the PR contains the original implementation commit and the latest correction commit dfd0218.
10. Confirm the PR targets dev and that CI checks are successful.

Important:

* Do not modify source files.
* Do not create additional commits.
* Do not run live E2E tests again.
* Do not delete branches.
* Do not close or modify PR #234 yet.
* Do not merge the new PR.
* Preserve all unrelated local changes.

After creating the PR, provide the new PR number, URL, base/head branches, commit list, changed-file summary, and CI status.