Thanks for the proposed assertions.

Before implementing anything, please provide a concise review of the exact code changes, including the Groovy code for all five new assertions.

Also verify that:

* The assertions use the actual Workday SOAP read-back response.
* All existing assertions remain unchanged.
* The XML namespace handling and field extraction follow the current framework conventions.
* The financial coding assertions validate the correct line-item fields.
* Missing SOAP fields cause meaningful test failures rather than silently passing.
* The existing fixture values are preserved.

Please identify any potential risks or dependencies.

Do not modify any files yet. I want to review the implementation before approving it.