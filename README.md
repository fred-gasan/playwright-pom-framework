I approve normalizing line endings to Unix LF, but ONLY for these two files:

1. readyapi/ap-e2e-soapui-project.xml
2. readyapi/Scripts/VerifyAdHocPaymentDetails.groovy

Please follow these safety requirements:

1. Convert only CRLF to LF in these two files. Preserve all actual code, XML content, encoding, and functionality.
2. Do not normalize or modify any other files.
3. Do not change global or repository Git configuration.
4. After normalization, verify:
    * Both files use LF line endings.
    * The XML diff contains only the intended changes.
    * git diff –check reports no whitespace errors.
    * The XML remains valid.
    * The Groovy script still compiles.
5. Show the resulting diff statistics and any warnings.

If all checks pass, stage ONLY these two files using explicit paths.

Then run:

* git diff –cached –name-status
* git diff –cached –stat
* git diff –cached –check
* git status –short

Confirm that exactly two files are staged and all unrelated local changes remain untouched.

Do not modify .gitignore, delete any files, commit, push, or create a PR yet.

If normalization introduces unexpected content changes or any validation fails, stop before staging and report the issue.