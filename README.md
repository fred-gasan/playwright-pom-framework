Test Case ID	AC #	Test Case Title	Preconditions	Test Steps	Expected Result	Priority	Status
TC-01	AC1	Verify Add to Role Matrix/Add Exception buttons removed for Vendor employee type	A record exists with Employee Type = Representing Third Party: Vendor	"1. Navigate to the relevant screen for a Vendor-type record
2. Check for Add to Role Matrix and Add Exception buttons"	Neither button is displayed for Vendor employee type	High	Not Run
TC-02	AC1	Verify buttons removed for all Non-Human employee types	Records exist for System ID, Hybrid, Shared ID, Training, and Non-Human employee types	"1. Navigate to each Non-Human type record (System ID, Hybrid, Shared ID, Training, Non-Human)
2. Check for Add to Role Matrix and Add Exception buttons on each"	Neither button is displayed for any of the five Non-Human employee types	High	Not Run
TC-03	AC1	Regression - buttons still display for unaffected employee types	A record exists for an employee type not listed in AC1 (e.g., standard Human employee)	"1. Navigate to a standard employee type record
2. Check for Add to Role Matrix and Add Exception buttons"	Both buttons still display normally, confirming the removal is scoped only to the listed types	Medium	Not Run
TC-04	AC2	Verify redirect to AO Validation Queue after App Owner completes Validation Signoff	Logged in as App Owner with items pending Validation Signoff	"1. Complete a Validation Signoff as an App Owner
2. Observe the resulting page"	User is redirected to the AO Validation Queue	High	Not Run
TC-05	AC2	Verify existing action message displays at top of AO Validation Queue after redirect	App Owner has just completed a Validation Signoff	"1. Complete a Validation Signoff
2. Observe top of AO Validation Queue after redirect"	The existing action/confirmation message displays at the top of the queue	Medium	Not Run
TC-06	AC2	Verify signed-off mnemonic no longer appears in AO Validation Queue listing	A mnemonic has just been signed off by the App Owner	"1. Complete Validation Signoff for a specific mnemonic
2. Review the AO Validation Queue listing"	The signed-off mnemonic is no longer listed in the queue	High	Not Run
TC-07	AC3	Verify SAT Dashboard card/view for signed-off mnemonics exists	User has access to SAT Dashboard; some mnemonics have completed signoff	"1. Navigate to SAT Dashboard
2. Locate the new card/view for mnemonics signed off"	Card/view is present and lists mnemonics that have been signed off	High	Not Run
TC-08	AC3	Verify failed mnemonic in signoff task shows 'failed' status	A signoff task exists where a mnemonic failed	"1. Trigger/observe a signoff task failure for a mnemonic
2. Check the dashboard card"	The mnemonic displays a 'failed' status on the dashboard card	High	Not Run
TC-09	AC3	Verify failure email is sent to configured signoff_failure_email address	Constant signoff_failure_email is configured with a specific email address; a mnemonic will fail signoff	"1. Configure signoff_failure_email constant with a test email address
2. Trigger a signoff failure
3. Check inbox of configured address"	Email is sent to the address specified in the signoff_failure_email constant	High	Not Run
TC-10	AC3	Verify failure email falls back to SAT team email when constant is missing	Constant signoff_failure_email is NOT configured (blank/missing)	"1. Remove/leave blank the signoff_failure_email constant
2. Trigger a signoff failure
3. Check SAT team emailbox"	Failure email is sent to the SAT team's email box as fallback	Medium	Not Run
TC-11	AC3	Verify Signoff Task Status card displays required columns	SAT Dashboard is accessible with signoff task data available	"1. Navigate to SAT Dashboard
2. Open the Signoff Task Status card"	Card displays columns: Calendar ID, Mnemonic, Cert Month, Validation End Date, Signoff Activity, and Late	High	Not Run
TC-12	AC3	Verify Signoff Activity column shows correct status and opens task log on click	A mnemonic + Calendar ID combination has an associated signoff task	"1. Locate a row with an existing signoff task
2. Click on the status in the Signoff Activity column"	Status displays correctly and clicking it opens the associated task log	Medium	Not Run
TC-13	AC3	Verify Signoff Activity shows 'Has not been signed off' when no task exists	A mnemonic + Calendar ID combination has no associated signoff task	"1. Locate a row with no signoff task performed
2. Review Signoff Activity column"	Column displays 'Has not been signed off'	Medium	Not Run
TC-14	AC3	Verify Late column shows 'Yes' when first signoff instance is after validation end date	A signoff task's first instance date is after the record's Validation End Date	"1. Identify/create a record where signoff occurred after Validation End Date
2. Review Late column"	Late column displays 'Yes'	Medium	Not Run
TC-15	AC3	Verify Late column shows 'No' when signoff occurred on or before validation end date	A signoff task's first instance date is on or before the record's Validation End Date	"1. Identify/create a record where signoff occurred on/before Validation End Date
2. Review Late column"	Late column displays 'No'	Medium	Not Run
TC-16	AC4	Verify App Owner signoff email triggers when no items are available for processing	A review period exists where an App Owner has zero items available for processing	"1. Set up/identify a review period with no processable items for an App Owner
2. Allow the trigger method to run
3. Check App Owner's email"	App Owner signoff email is triggered and sent even though no items were available	Medium	Not Run
TC-17	AC5	Verify Manager name displays in Preview/Business Justification queue	A record exists in the Preview/Business Justification period with an assigned Manager	"1. Navigate to the Preview/Business Justification queue
2. Review the record listing"	The Manager's name is displayed for the record	Medium	Not Run
TC-18	AC5	Verify App Owner name displays in Preview/Business Justification queue	A record exists in the Preview/Business Justification period with an assigned App Owner	"1. Navigate to the Preview/Business Justification queue
2. Review the record listing"	The App Owner's name is displayed for the record	Medium	Not Run
TC-19	AC6	Verify Signoff Delegate priority is higher than Custodian when individual holds both roles	An individual is assigned as both Signoff Delegate and Custodian for the same record during Validation	"1. Identify/set up an individual with both roles on the same record
2. Review the record during Validation processing"	The individual's Signoff Delegate role/priority takes precedence (is 'higher') over the Custodian role	Medium	Not Run
TC-20	AC7	Verify Current Appropriateness shows 'Revoked' when record is marked revoked with a revoke request number	A record is marked as revoked and has a revoke request number entered	"1. Identify a record with revoked status and a revoke request number
2. Review the Current Appropriateness field"	Current Appropriateness displays 'Revoked'	High	Not Run
TC-21	AC7	Verify Current Appropriateness does NOT show 'Revoked' when revoke request number is missing	A record is marked as revoked but has NO revoke request number entered	"1. Identify a record marked revoked without a revoke request number
2. Review the Current Appropriateness field"	Current Appropriateness does not display 'Revoked' (confirm expected fallback value with team)	Medium	Not Run
TC-22	AC8	Verify Inappropriate Tab shows most recent Transfer Date within review period	A record has a Transfer Date that falls within the current review period	"1. Navigate to the Inappropriate Tab in Validation Views
2. Locate the record with a recent transfer"	Most recent Transfer Date is displayed for the record	Medium	Not Run
TC-23	AC8	Verify Termination Tab shows most recent Termination Date when greater than most recent Rehire Date	A record has a Termination Date that is later than its most recent Rehire Date	"1. Navigate to the Termination Tab in Validation Views
2. Locate the record"	Most recent Termination Date is displayed, since it is greater than the most recent Rehire Date	Medium	Not Run
TC-24	AC8	Verify Termination Tab does not show Termination Date when Rehire Date is more recent	A record has a Rehire Date that is later than its most recent Termination Date	"1. Navigate to the Termination Tab
2. Locate the record"	Termination Date is not shown / record is treated as active per business rule (confirm expected display with team)	Low	Not Run
TC-25	AC9	Verify Role Matrix Email Tracking view is available on SAT System Dashboard	User has access to the SAT System Dashboard	"1. Navigate to SAT System Dashboard
2. Locate the Role Matrix Email Tracking view"	View is present and displays role matrix email tracking data	Medium	Not Run
TC-26	AC10	Verify Validation remains in App Owner queue until signoff when Validation Start Date is present	A calendar has a Validation Start Date populated; App Owner has not yet signed off	"1. Identify a calendar with a Validation Start Date set
2. Confirm the Validation item remains in the App Owner queue prior to signoff"	Validation item stays in the App Owner queue until signoff occurs	High	Not Run
TC-27	AC10	Verify behavior when Validation Start Date is NOT present in the calendar	A calendar does NOT have a Validation Start Date populated	"1. Identify a calendar without a Validation Start Date
2. Observe App Owner queue behavior for that record"	Confirm expected behavior with team, since the retention rule is conditional on the Validation Start Date being present	Medium	Not Run
TC-28	General	Regression - existing Validation Signoff processing still functions correctly	Standard Validation Signoff scenario with no special conditions from ACs above	"1. Perform a standard Validation Signoff end-to-end
2. Verify no unexpected errors or behavior changes outside of the new enhancements"	Core Validation Signoff processing completes successfully with no regressions	High	Not Run<img width="1297" height="1701" alt="image" src="https://github.com/user-attachments/assets/8e5bec75-496e-4bb2-b3dd-a302892f2341" />
