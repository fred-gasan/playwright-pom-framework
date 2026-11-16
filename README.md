Thanks for the detailed gap analysis. The proposed approach looks aligned with our goal of extending the existing UAT automation without changing the current scenarios.

Before implementation, please prepare the complete proposed Groovy script and the exact XML diff for review.

Make sure all new validations use scenario-specific fixture values rather than hardcoded values, and that the script works correctly across UAT scenarios 7, 8, 9, and 10.

Preserve all existing assertions, fixtures, and test workflows. Keep rental dates and rental agreement reference validations pending until we receive business clarification.

Do not modify any files yet. Let’s review the proposed code first.