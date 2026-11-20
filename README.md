We are starting a new Jira ticket: FPOAP-182.

The previous ticket FPOAP-165 has been completed, and PR #237 was squash-merged into dev.

New ticket:
FPOAP-182 — [QE] Add ReadyAPI End-to-End Coverage for Ad-Hoc Payments Search and Warnings (GET & POST /wd-adhoc-payments)

Related implementation tickets:

* FPOAP-113 — GET /wd-ad-hoc-payments for duplicate detection
* FPOAP-169 — Add warnings to Ad-Hoc Payment creation

We need to follow the same careful workflow we used for FPOAP-165.

PHASE 1 — REPOSITORY AND REQUIREMENTS ANALYSIS ONLY

1. Inspect the current Git branch, working tree, and remote tracking configuration.
2. Verify that PR #237 was squash-merged into dev and identify the resulting merge commit.
3. Check whether local dev is up to date with origin/dev.
4. Identify all unrelated uncommitted or untracked changes and preserve them.
5. Recommend a safe way to create a new feature branch from the latest origin/dev without losing, stashing, resetting, cleaning, or overwriting local changes.
6. Use the required branch naming convention ending in /dev. Proposed name: feature/fpoap-182/dev. Check for local and remote namespace conflicts before recommending it.

PHASE 2 — FUNCTIONAL ANALYSIS

7. Inspect the current ReadyAPI project, InvoiceToWorkday test case, existing Ad-Hoc Payment scenarios UAT 7–10, fixture CSV, Groovy scripts, and API configuration.
8. Locate the actual GET Ad-Hoc Payments implementation and POST warnings handling in the repository.
9. Identify the actual supported GET query parameters and response JSON schema.
10. Investigate the discrepancy between FPOAP-182 and FPOAP-113:

* FPOAP-182 mentions payment_reference, supplier_id, from_date, to_date, limit, and offset.
* FPOAP-113 mentions payee_name, payment_date_on_or_after, and payment_date_on_or_before.
    Determine which parameters are implemented and supported by the current API contract. Do not assume either ticket is correct without verifying the implementation.

11. Inspect how POST /wd-adhoc-payments returns the warnings array, including populated and empty cases.
12. Determine whether pagination is currently implemented and what behavior is supported.
13. Review existing ReadyAPI test patterns and identify reusable scripts, assertions, and fixture structures.
14. Identify dependencies, blockers, and any missing API behavior that must be clarified with Liliana or the implementation team.

PHASE 3 — TEST DESIGN

15. Propose a test coverage matrix mapping every FPOAP-182 acceptance criterion to ReadyAPI steps, fixtures, assertions, and expected results.
16. Include positive, negative, empty-result, warning, search-filter, and pagination scenarios where supported.
17. Propose the minimal file changes required.
18. Explain how to test locally without creating unnecessary persistent Workday Sandbox transactions.
19. Recommend the implementation sequence and validation strategy.

IMPORTANT SAFETY RULES:

* Do not modify any files yet.
* Do not switch branches, fetch, pull, merge, rebase, reset, stash, or clean yet.
* Do not create a new branch yet.
* Do not run live API calls or create Workday transactions.
* Do not stage, commit, or push.
* Do not modify credentials or environment configuration.
* Preserve all existing unrelated local changes.
* Do not assume the old feature branch is a safe starting point.
* Do not assume GET endpoint parameters from the Jira description without checking the actual implementation.

Return:
A. Git and branch readiness report.
B. FPOAP-182 requirements analysis.
C. FPOAP-113 and FPOAP-169 implementation findings.
D. API contract discrepancies.
E. Proposed test coverage matrix.
F. Recommended new branch creation strategy.
G. Blockers and questions requiring clarification.

Stop after analysis and wait for my approval before making any changes.