Test Case ID
Test Case Title	Preconditions	Test Steps	Expected Result	Priority	Status
TC-01	
Verify error is shown when Validation Start Date is before Manager Start Date (new calendar)
User has access to calendar creation screen; a calendar with a defined Manager Start Date is being created
1. Begin creating a new calendar
2. Enter a Manager Start Date
3. Enter a Validation Start Date that is prior to the Manager Start Date
4. Attempt to save/submit
System blocks the save and displays an error indicating the Validation Start Date must be after the Manager Start Date
High	Not Run
TC-02	
Verify Validation Start Date equal to Manager Start Date is handled correctly
A calendar with a defined Manager Start Date is being created
1. Enter a Manager Start Date
2. Enter a Validation Start Date equal to the Manager Start Date
3. Attempt to save/submit
Confirm expected behavior with team — either accepted (if 'after' is inclusive) or blocked with error (if strictly after is required)
Medium	Not Run
TC-03	
Verify Validation Start Date after Manager Start Date is accepted
A calendar with a defined Manager Start Date is being created
1. Enter a Manager Start Date
2. Enter a Validation Start Date after the Manager Start Date
3. Save/submit
Calendar saves successfully with no error	High	Not Run
TC-04	
Verify editing existing calendar enforces the same validation
An existing calendar is available to edit
1. Open an existing calendar for editing
2. Change the Validation Start Date to a date prior to the Manager Start Date
3. Attempt to save
System blocks the save and displays an error indicating the Validation Start Date must be after the Manager Start Date
High	Not Run
TC-05	
Verify changing Manager Start Date triggers re-validation of existing Validation Start Date
An existing calendar has both a Manager Start Date and Validation Start Date set
1. Open an existing calendar for editing
2. Change the Manager Start Date to a date after the current Validation Start Date
3. Attempt to save
System blocks the save and displays an error, since the Validation Start Date is now prior to the updated Manager Start Date
Medium	Not Run
TC-06	Verify error message is clear and specific	
A calendar is being created/edited with an invalid Validation Start Date
1. Trigger the validation error per TC-01
2. Review the error message text
Error message clearly states the issue (Validation Start Date must be after Manager Start Date) and is not a generic/unclear error
Medium	Not Run
TC-07	
Verify validation applies per calendar (not cross-calendar)
Two or more calendars exist with different Manager Start Dates
1. Create/edit Calendar A with a Validation Start Date valid for Calendar A but invalid relative to Calendar B's Manager Start Date
2. Save Calendar A
Calendar A saves successfully; validation is scoped only to its own associated Manager Start Date, not other calendars
Low	Not Run
TC-08	
Regression - calendar save/edit still functions for valid data
A calendar with valid Manager Start Date and Validation Start Date values
1. Create or edit a calendar with all valid, correctly ordered dates
2. Save
Calendar saves successfully with no unexpected errors; no regression introduced by the new validation
High	Not Run
TC-09	
Verify blank/missing Manager Start Date does not break validation
A new calendar is being created without a Manager Start Date entered yet
1. Leave Manager Start Date blank
2. Enter a Validation Start Date
3. Attempt to save
System handles gracefully — either prompts for required Manager Start Date first or applies appropriate validation without erroring unexpectedly
Low	Not Run
TC-10	Cross-browser/device check	
Access to at least 2 supported browsers (e.g., Chrome, Edge)
1. Repeat TC-01 in Chrome
2. Repeat in Edge
3. Compare error handling behavior
Validation and error message behave consistently across browsers	Low	Not Run