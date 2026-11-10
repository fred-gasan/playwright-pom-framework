I received clarification from Daniel Hanks regarding the Ad Hoc Payment rental car reimbursement scenario.

Daniel confirmed that Workday Ad Hoc Payments do not have a dedicated rental period field. He suggested that rental dates would likely belong in the payment memo.

He also explained that supporting references are generally handled through attachments, such as receipts or invoices, and that Ad Hoc Payments require supporting documentation based on their type.

However, these are not yet confirmed scenario-specific requirements.

Daniel recommended checking with Qurrent or Nick, who have more experience with the existing Ad Hoc Payment automation.

For now:

* Preserve the existing fixture and payee.
* Do not add rental dates or new reference fields yet.
* Keep the existing attachment validations.
* Continue reviewing the proposed missing SOAP assertions for payee, amount, currency, and financial coding.
* Do not modify any files until the remaining requirements are clarified.

Please update the implementation plan based on this information and identify which validations can be implemented independently of these open questions.


Hi, I’m working on the Ad Hoc Payment automation ticket for the AAA-caused vehicle damage rental car reimbursement scenario.

I’ve reviewed the existing UAT Scenario 9 and wanted to clarify a couple of things before making changes.

Daniel mentioned that Workday Ad Hoc Payments don’t have a dedicated rental period field and that supporting references are typically handled through attachments.

Could you please confirm:

1. Should the rental period be included in the payment memo for this scenario?
2. Are there specific supporting documents or reference details (such as a claim number or repair order number) that need to be included or validated?
3. Is there an existing Ad Hoc Payment test scenario or fixture that already covers these requirements and that I should follow?

I want to make sure the automation aligns with the existing implementation and business requirements.

Thanks for your help!


Got it, thanks Daniel! That makes sense. I’ll follow up with Qurrent or Nick to confirm the scenario-specific requirements. Appreciate your help!
