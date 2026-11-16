The branch name is now correct: feature/fpoap-165-adhoc-payment-validations.

Let’s proceed with selective staging for FPOAP-165.

Please:

1. Stage ONLY these two files:
    * readyapi/Scripts/VerifyAdHocPaymentDetails.groovy
    * readyapi/ap-e2e-soapui-project.xml
2. Before staging, verify that the XML file does not contain unrelated CRLF/LF changes. If it does, stop and report the issue rather than staging unrelated changes.
3. After staging, run:
    * git diff –cached –name-status
    * git diff –cached –stat
    * git diff –cached –check
    * git status –short
4. Review the staged diff and confirm:
    * Exactly two files are staged.
    * The new Groovy script contains the intended UAT 7–10 validations.
    * The XML changes are limited to registering and invoking the new script.
    * No credentials, secrets, local configuration, generated files, or unrelated changes are staged.
    * Existing XPath assertions remain unchanged.
5. Confirm all other local changes remain untouched and unstaged.

Do not modify .gitignore, delete files, commit, push, or create a PR yet.

If you discover any unexpected changes or sensitive content, stop and report them before proceeding.

Please provide the exact staged file list, diff summary, and any warnings.