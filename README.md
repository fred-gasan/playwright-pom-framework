I received clarification from my QA teammate, Liliana, regarding UAT Scenario 9.

Here is the feedback:

1. Payee Name: We can keep the existing synthetic payee name. The exact name is not important as long as it represents a synthetic payee.
2. Rental Period: Including rental dates in the payment memo may be acceptable. However, we need to confirm the functional purpose and whether the ticket actually requires this information to be stored and validated. Do not add a dedicated rental period field without confirmation.
3. Supporting References: We need clarification from Daniel Hanks or Laksh about whether claim/repair order references should be stored in the memo or mapped to dedicated Workday fields.
4. Existing Coverage: Liliana mentioned that approximately 90% of this ticket was already implemented as part of another ticket. We should focus on identifying genuinely missing validations rather than rebuilding existing functionality.

Please update the implementation plan based on this feedback.

For now:

* Preserve the existing payee name.
* Do not add rental dates or supporting reference fields without clarification.
* Identify which missing assertions can safely be implemented using the current fixture and existing Workday SOAP response.
* Avoid unnecessary fixture modifications.
* Do not modify any files yet.

Please recommend the smallest safe next step and wait for my confirmation.



Thanks, Liliana! That makes sense. I’ll keep the existing payee name and focus on the missing validations.

I’ll also follow up with Daniel or Laksh to clarify whether the rental period and supporting references need to be included in the payment memo or mapped to specific Workday fields.

Thanks for your help!



Hi Daniel, I’m working on the Ad Hoc Payment automation ticket for the rental car reimbursement scenario, and I wanted to clarify a couple of requirements.

1. Rental period: The ticket mentions including the rental period. Is it sufficient to include the rental dates in the payment memo, or is there a specific Workday field where this information should be stored?
2. Supporting references: The ticket also mentions claim/repair order references. Should these be included in the payment memo, or does Workday have dedicated fields for them? Is there a specific format we should follow?

I want to make sure the automation validates these details correctly.

Thanks for your help!