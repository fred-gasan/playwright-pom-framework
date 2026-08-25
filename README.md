Test Case ID	Scenario Branch	Test Case Title	Preconditions / Test Data	Test Steps	Expected Result	Priority	Status
TC-01	OUT_OF_SCOPE Not Blank	Verify record with OUT_OF_SCOPE not blank is treated as Out of Scope (Certification calendar)	OIM upload file: OUT_OF_SCOPE = populated; CERT_ACTION = any value; Certification calendar	"1. Prepare upload file with OUT_OF_SCOPE populated
2. Upload via OIM process
3. Check the record's Decision and placement"	Record is treated as Out of Scope; Decision shown as whatever is in CERT_ACTION field; other fields (CERTIFIER_ID, Remediated #/Comments) are ignored	High	Not Run
TC-02	OUT_OF_SCOPE Not Blank	Verify record with OUT_OF_SCOPE not blank shows as 'Not Required' on Off-Cycle calendar	OIM upload file: OUT_OF_SCOPE = populated; Off-Cycle calendar	"1. Prepare upload file with OUT_OF_SCOPE populated
2. Upload against an Off-Cycle calendar
3. Check the Decision value"	Decision is shown as 'Not Required' regardless of CERT_ACTION value	High	Not Run
TC-03	OUT_OF_SCOPE Not Blank	Verify out-of-scope record appears on Out of Scope tab during Validation	OIM upload with OUT_OF_SCOPE populated; Validation period is active	"1. Upload record with OUT_OF_SCOPE populated
2. Navigate to Validation view
3. Check which tab the record appears on"	Record is shown on the Out of Scope tab during Validation	High	Not Run
TC-04	OUT_OF_SCOPE Not Blank	Verify out-of-scope record appears on validation output document	OIM upload with OUT_OF_SCOPE populated; validation output document generated	"1. Upload record with OUT_OF_SCOPE populated
2. Generate/review the validation output document"	Record appears on the Out of Scope section of the validation output document	Medium	Not Run
TC-05	Remediated Not Blank – Certified	Verify Certified + Remediated: record added to Revoke Complete tab using CERTIFIER_ID	OIM upload: OUT_OF_SCOPE = blank; Remediated #/Comments = populated; CERT_ACTION = Certified; CERTIFIER_ID = valid UserID	"1. Prepare upload file with above values
2. Upload via OIM process
3. Check Revoke Complete tab"	Record appears on Revoke Complete tab with CERTIFIER_ID UserID as request info details	High	Not Run
TC-06	Remediated Not Blank – Certified	Verify Certified + Remediated: CERTIFIER_ID blank causes upload rejection	OIM upload: Remediated #/Comments = populated; CERT_ACTION = Certified; CERTIFIER_ID = blank	"1. Prepare upload file with CERTIFIER_ID blank
2. Upload
3. Check upload result/task log"	Record is rejected during upload due to blank CERTIFIER_ID; error shown	High	Not Run
TC-07	Remediated Not Blank – Certified	Verify Certified + Remediated: Manager Decision = Certified	OIM upload: Remediated = populated; CERT_ACTION = Certified; CERTIFIER_ID = valid	"1. Upload record
2. Review Manager Decision field for the record"	Manager Decision displays 'Certified'	High	Not Run
TC-08	Remediated Not Blank – Certified	Verify Certified + Remediated: AO Decision = Revoke	OIM upload: Remediated = populated; CERT_ACTION = Certified; CERTIFIER_ID = valid	"1. Upload record
2. Review AO Decision field"	AO Decision displays 'Revoke'	High	Not Run
TC-09	Remediated Not Blank – Certified	Verify Certified + Remediated: Current Appropriateness = Revoked	OIM upload: Remediated = populated; CERT_ACTION = Certified; CERTIFIER_ID = valid	"1. Upload record
2. Review Current Appropriateness field"	Current Appropriateness displays 'Revoked'	High	Not Run
TC-10	Remediated Not Blank – Certified	Verify Certified + Remediated: record on Remediated tab of validation output doc	OIM upload: Remediated = populated; CERT_ACTION = Certified; CERTIFIER_ID = valid	"1. Upload record
2. Generate/review validation output document"	Record appears on the Remediated tab of the validation output document	Medium	Not Run
TC-11	Remediated Not Blank – Certified	Verify Certified + Remediated: instance flagged as Manager Error for Analytics	OIM upload: Remediated = populated; CERT_ACTION = Certified; CERTIFIER_ID = valid	"1. Upload record
2. Review Analytics/reporting for Manager Error flag"	Instance is flagged as Manager Error in Analytics data	Medium	Not Run
TC-12	Remediated Not Blank – Revoked	Verify Revoked + Remediated: record added to Revoke Complete tab using CERTIFIER_ID	OIM upload: Remediated = populated; CERT_ACTION = Revoked; CERTIFIER_ID = valid UserID	"1. Prepare upload file with above values
2. Upload
3. Check Revoke Complete tab"	Record appears on Revoke Complete tab with CERTIFIER_ID UserID as request info details	High	Not Run
TC-13	Remediated Not Blank – Revoked	Verify Revoked + Remediated: CERTIFIER_ID blank causes upload rejection	OIM upload: Remediated = populated; CERT_ACTION = Revoked; CERTIFIER_ID = blank	"1. Prepare upload with CERTIFIER_ID blank
2. Upload
3. Check task log"	Record is rejected during upload	High	Not Run
TC-14	Remediated Not Blank – Revoked	Verify Revoked + Remediated: Manager Decision = Revoked and Current Appropriateness = Revoked	OIM upload: Remediated = populated; CERT_ACTION = Revoked; CERTIFIER_ID = valid	"1. Upload record
2. Review Manager Decision and Current Appropriateness fields"	Manager Decision = 'Revoked'; Current Appropriateness = 'Revoked'	High	Not Run
TC-15	Remediated Not Blank – Revoked	Verify Revoked + Remediated: record on Remediated tab of validation output doc	OIM upload: Remediated = populated; CERT_ACTION = Revoked; CERTIFIER_ID = valid	"1. Upload record
2. Check validation output document"	Record appears on Remediated tab of validation output document	Medium	Not Run
TC-16	Remediated Not Blank – Not Required	Verify Not Required + Remediated: record added to Revoke Complete tab using Current Manager	OIM upload: Remediated = populated; CERT_ACTION = Not Required; CERTIFIER_ID = any (ignored)	"1. Prepare upload file
2. Upload
3. Check Revoke Complete tab request info details"	Record appears on Revoke Complete tab using Current Manager (not CERTIFIER_ID) for request info	High	Not Run
TC-17	Remediated Not Blank – Not Required	Verify Not Required + Remediated: CERTIFIER_ID is ignored regardless of value	OIM upload: Remediated = populated; CERT_ACTION = Not Required; CERTIFIER_ID = blank or populated	"1. Upload with CERTIFIER_ID blank
2. Upload again with CERTIFIER_ID populated
3. Compare results"	Both uploads succeed; CERTIFIER_ID value makes no difference to the outcome	Medium	Not Run
TC-18	Remediated Not Blank – Not Required	Verify Not Required + Remediated: Manager Decision = Not Required; AO Decision = Revoke	OIM upload: Remediated = populated; CERT_ACTION = Not Required	"1. Upload record
2. Review Manager Decision and AO Decision fields"	Manager Decision = 'Not Required'; AO Decision = 'Revoke'	High	Not Run
TC-19	Remediated Not Blank – Not Required	Verify Not Required + Remediated: Current Appropriateness = Revoked; record on Remediated tab	OIM upload: Remediated = populated; CERT_ACTION = Not Required	"1. Upload record
2. Review Current Appropriateness and validation output document"	Current Appropriateness = 'Revoked'; record on Remediated tab in output doc	Medium	Not Run
TC-20	Remediated Not Blank – No Decision	Verify No Decision + Remediated: record added to Revoke Complete tab using CERTIFIER_ID	OIM upload: Remediated = populated; CERT_ACTION = No Decision; CERTIFIER_ID = valid	"1. Upload
2. Check Revoke Complete tab"	Record on Revoke Complete tab with CERTIFIER_ID as request info details	High	Not Run
TC-21	Remediated Not Blank – No Decision	Verify No Decision + Remediated: CERTIFIER_ID blank causes upload rejection	OIM upload: Remediated = populated; CERT_ACTION = No Decision; CERTIFIER_ID = blank	"1. Upload with blank CERTIFIER_ID
2. Check task log"	Record rejected during upload	High	Not Run
TC-22	Remediated Not Blank – No Decision	Verify No Decision + Remediated: Manager Decision = No Decision; AO Decision = Revoke	OIM upload: Remediated = populated; CERT_ACTION = No Decision; CERTIFIER_ID = valid	"1. Upload
2. Review Manager Decision and AO Decision fields"	Manager Decision = 'No Decision'; AO Decision = 'Revoke'; Current Appropriateness = 'Revoked'	High	Not Run
TC-23	Remediated Not Blank – No Decision	Verify No Decision + Remediated: record on Remediated tab and flagged as Manager Error	OIM upload: Remediated = populated; CERT_ACTION = No Decision; CERTIFIER_ID = valid	"1. Upload
2. Check validation output document and Analytics"	Record on Remediated tab in output doc; flagged as Manager Error in Analytics	Medium	Not Run
TC-24	Remediated Blank – Certified	Verify Certified + Remediated blank: Manager Decision = Certified	OIM upload: OUT_OF_SCOPE = blank; Remediated = blank; CERT_ACTION = Certified; CERTIFIER_ID = valid	"1. Upload
2. Review Manager Decision"	Manager Decision = 'Certified'	High	Not Run
TC-25	Remediated Blank – Certified	Verify Certified + Remediated blank: CERTIFIER_ID blank causes rejection	OIM upload: Remediated = blank; CERT_ACTION = Certified; CERTIFIER_ID = blank	"1. Upload with blank CERTIFIER_ID
2. Check task log"	Record rejected during upload	High	Not Run
TC-26	Remediated Blank – Certified	Verify Certified + Remediated blank: Current Appropriateness uses green/yellow/red method	OIM upload: Remediated = blank; CERT_ACTION = Certified; CERTIFIER_ID = valid	"1. Upload
2. Review Current Appropriateness field"	Current Appropriateness reflects standard green/yellow/red calculation (NOT forced to Revoked)	High	Not Run
TC-27	Remediated Blank – Certified	Verify Certified + Remediated blank: record on assigned tab in validation output doc	OIM upload: Remediated = blank; CERT_ACTION = Certified; CERTIFIER_ID = valid	"1. Upload
2. Review validation output document"	Record on the assigned tab (per green/yellow/red method), NOT on Remediated tab	Medium	Not Run
TC-28	Remediated Blank – Revoked	Verify Revoked + Remediated blank: record is rejected during upload	OIM upload: Remediated = blank; CERT_ACTION = Revoked	"1. Prepare upload file with CERT_ACTION = Revoked and Remediated blank
2. Upload
3. Check task log"	Record is rejected during upload; cannot upload a Revoked record without Remediated # populated	High	Not Run
TC-29	Remediated Blank – Not Required	Verify Not Required + Remediated blank: CERTIFIER_ID should be blank	OIM upload: Remediated = blank; CERT_ACTION = Not Required	"1. Upload with CERTIFIER_ID blank
2. Check result"	Upload succeeds with blank CERTIFIER_ID	Medium	Not Run
TC-30	Remediated Blank – Not Required	Verify Not Required + Remediated blank: Manager Decision = Not Required	OIM upload: Remediated = blank; CERT_ACTION = Not Required; CERTIFIER_ID = blank	"1. Upload
2. Review Manager Decision field"	Manager Decision = 'Not Required' based on upload value	High	Not Run
TC-31	Remediated Blank – Not Required	Verify Not Required + Remediated blank: Current Appropriateness uses green/yellow/red method	OIM upload: Remediated = blank; CERT_ACTION = Not Required	"1. Upload
2. Review Current Appropriateness and validation output"	Current Appropriateness uses standard green/yellow/red method; record on assigned tab in output	Medium	Not Run
TC-32	Remediated Blank – No Decision	Verify No Decision + Remediated blank: CERTIFIER_ID blank causes rejection	OIM upload: Remediated = blank; CERT_ACTION = No Decision; CERTIFIER_ID = blank	"1. Upload with blank CERTIFIER_ID
2. Check task log"	Record rejected during upload	High	Not Run
TC-33	Remediated Blank – No Decision	Verify No Decision + Remediated blank: Manager Decision = No Decision	OIM upload: Remediated = blank; CERT_ACTION = No Decision; CERTIFIER_ID = valid	"1. Upload
2. Review Manager Decision field"	Manager Decision = 'No Decision' based on upload value	High	Not Run
TC-34	Remediated Blank – No Decision	Verify No Decision + Remediated blank: Current Appropriateness uses green/yellow/red method	OIM upload: Remediated = blank; CERT_ACTION = No Decision; CERTIFIER_ID = valid	"1. Upload
2. Review Current Appropriateness and validation output doc"	Current Appropriateness uses standard green/yellow/red method; record on assigned tab in output	Medium	Not Run
TC-35	CERTIFIER_ID Validation	Verify CERTIFIER_ID validation applies across all applicable CERT_ACTION values	OIM upload files: one per scenario (Certified, Revoked, No Decision) all with blank CERTIFIER_ID	"1. For each of Certified, Revoked, No Decision with Remediated blank: upload with CERTIFIER_ID blank
2. Check result for each"	All three are rejected during upload; task log shows CERTIFIER_ID blank as the rejection reason	High	Not Run
TC-36	CERTIFIER_ID Validation	Verify CERTIFIER_ID is ignored/not required for Not Required CERT_ACTION	OIM upload: CERT_ACTION = Not Required; Remediated = blank; CERTIFIER_ID = blank	"1. Upload with CERTIFIER_ID blank
2. Verify upload succeeds"	Upload is accepted; CERTIFIER_ID blank does not cause rejection for Not Required	Medium	Not Run
TC-37	Regression	Verify non-OIM uploaded records are unaffected by this change	Records entered/processed through standard (non-OIM) certification process	"1. Process a standard certification (non-OIM)
2. Verify all decisions, tabs, and output documents behave as before"	No change in behavior for non-OIM records; no regression introduced	High	Not Run
TC-38	Regression	Verify task log reflects correct accepted/rejected counts after OIM upload with mixed records	OIM upload file with mix of valid and invalid records across different CERT_ACTION/Remediated combinations	"1. Upload a mixed file
2. Review the task log"	Task log correctly records number of records accepted and number rejected, matching the file composition	Medium	Not Run<img width="1441" height="2301" alt="image" src="https://github.com/user-attachments/assets/70d546ad-b784-4607-88ae-73c0e52e64ce" />
