Test Case ID	Test Case Title	Preconditions	Test Steps	Expected Result	Priority	Status
TC-01	Verify revoke email triggers for standard Certification processing (regression)	A standard Certification instance exists with an item that will result in a revoke decision	"1. Process a standard Certification
2. Decision an item as 'Revoke'
3. Complete processing
4. Check that the revoke email is sent"	Revoke email is triggered and sent, consistent with existing (pre-change) behavior	High	Not Run
TC-02	Verify revoke email triggers for Off-Cycle review	An Off-Cycle review instance exists with an item that will result in a revoke decision	"1. Initiate/process an Off-Cycle review
2. Decision an item as 'Revoke'
3. Complete processing
4. Check that the revoke email is sent"	Revoke email is triggered and sent for the Off-Cycle review revoke, same as standard certification	High	Not Run
TC-03	Verify revoke email triggers for Validation Processing	A Validation Processing instance exists with an item that will result in a revoke decision	"1. Initiate/process a Validation Processing certification
2. Decision an item as 'Revoke'
3. Complete processing
4. Check that the revoke email is sent"	Revoke email is triggered and sent for the Validation Processing revoke	High	Not Run
TC-04	Verify revoke email content is correct across all certification types	Revoke emails have been triggered for standard, Off-Cycle, and Validation Processing instances (TC-01 to TC-03)	"1. Open the revoke email received for each certification type
2. Compare content (recipient, subject, item details) across types"	Revoke email content is accurate and consistent in format regardless of which certification type triggered it	Medium	Not Run
TC-05	Verify no revoke email is sent when item is not revoked (Off-Cycle)	An Off-Cycle review instance exists with an item that will be approved (not revoked)	"1. Process an Off-Cycle review
2. Decision an item as 'Approve'
3. Complete processing
4. Check inbox for revoke email"	No revoke email is sent, since the item was not revoked	Medium	Not Run
TC-06	Verify no revoke email is sent when item is not revoked (Validation Processing)	A Validation Processing instance exists with an item that will be approved (not revoked)	"1. Process a Validation Processing certification
2. Decision an item as 'Approve'
3. Complete processing
4. Check inbox for revoke email"	No revoke email is sent, since the item was not revoked	Medium	Not Run
TC-07	Verify correct recipient(s) receive the revoke email for each type	Revoke scenarios set up for Off-Cycle and Validation Processing	"1. Trigger a revoke in Off-Cycle review
2. Trigger a revoke in Validation Processing
3. Confirm who received each email"	The correct recipient(s) (e.g., owner, manager, or app owner as applicable) receive the revoke email for each certification type	Medium	Not Run
TC-08	Verify multiple revokes in a single Off-Cycle/Validation batch each trigger an email	An Off-Cycle or Validation Processing instance with multiple items to be revoked	"1. Decision multiple items as 'Revoke' in the same batch
2. Complete processing
3. Check emails received"	A revoke email is triggered for each revoked item (or a consolidated email if that's the existing pattern — confirm with team which is expected)	Low	Not Run
TC-09	Regression - other email triggers unaffected	Certification instances with a mix of decisions (approve, revoke, etc.)	"1. Process a certification with a mix of decisions
2. Verify that non-revoke email triggers (e.g., approval notifications, reminders) still function as before"	No regression in other existing email trigger types; only revoke email trigger scope has been expanded	Low	Not Run<img width="1261" height="561" alt="image" src="https://github.com/user-attachments/assets/ccfe608e-8578-453f-80b8-ee6cd7c5bc6b" />
