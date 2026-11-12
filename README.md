Thanks for the implementation plan. I agree with the phased approach.

Before starting Phase 1, please verify the exact current implementation on the refactored dev branch.

Specifically:

1. Confirm where the active Ad Hoc Payment SOAP Groovy assertions are located.
2. Confirm whether we should extend the existing XML assertion or use an external Groovy script, following the current refactored architecture.
3. Verify the exact SOAP XML paths for payee, amount, currency, financial coding, and line items.
4. Confirm that the expected values come from the existing UAT Scenario 9 fixture rather than hardcoded assumptions.
5. Ensure that existing status, address, and attachment validations remain intact.
6. Identify any assertion that cannot be safely implemented without additional clarification.

Do not modify any files yet.

Once verified, show me the exact proposed changes and the affected files so I can review them before implementation.