TC_12 — Verify End Date inline edit accepts today’s date

	•	Test Steps:
	1.	Navigate to Active tab on either view
	2.	Click End Date field to open inline edit
	3.	Enter today’s date
	4.	Save/confirm the change
	•	Expected Result: Today’s date is accepted and saved successfully
	•	Test Data: Today’s date

TC_13 — Verify End Date inline edit accepts a future date

	•	Test Steps:
	1.	Navigate to Active tab on either view
	2.	Click End Date field to open inline edit
	3.	Enter a date 30 days in the future
	4.	Save/confirm the change
	•	Expected Result: Future date is accepted and saved successfully
	•	Test Data: Today + 30 days

TC_14 — Verify End Date inline edit rejects a past date

	•	Test Steps:
	1.	Navigate to Active tab on either view
	2.	Click End Date field to open inline edit
	3.	Enter yesterday’s date
	4.	Attempt to save/confirm
	•	Expected Result: Error message displayed; past date is not accepted
	•	Test Data: Today - 1 day

TC_15 — Verify Inactive tab fields are NOT editable (read-only)

	•	Test Steps:
	1.	Navigate to /rolematrix/managethirdparty/ or /rolematrix/managenonhuman/
	2.	Go to Inactive tab
	3.	Click on any field (End Date, or any other field)
	•	Expected Result: No field enters edit mode; all fields are read-only
	•	Test Data: Any inactive record

TC_16 — Verify Start Date is NOT updated from upload file when record already exists

	•	Test Steps:
	1.	Note the existing Start Date of a record already in the system
	2.	Prepare an upload file for that same record with a different Start Date
	3.	Upload the file via /rolematrix/managenonhuman/ or /rolematrix/managethirdparty/
	4.	Check the record’s Start Date after upload
	•	Expected Result: Start Date remains unchanged from original; upload file Start Date value is ignored
	•	Test Data: Existing record + upload file with different Start Date

TC_17 — Verify upload rejects End Date prior to today

	•	Test Steps:
	1.	Prepare an upload file with End Date set to yesterday’s date
	2.	Upload the file
	3.	Check result/task log
	•	Expected Result: Record is rejected; error shown indicating End Date cannot be before today
	•	Test Data: Upload file with End Date = today - 1 day

TC_18 — Verify upload accepts End Date of today

	•	Test Steps:
	1.	Prepare an upload file with End Date set to today’s date
	2.	Upload the file
	3.	Check result/task log
	•	Expected Result: Record is accepted and saved successfully
	•	Test Data: Upload file with End Date = today

TC_19 — Verify upload accepts End Date in the future

	•	Test Steps:
	1.	Prepare an upload file with End Date set to a future date
	2.	Upload the file
	3.	Check result/task log
	•	Expected Result: Record is accepted and saved successfully
	•	Test Data: Upload file with End Date = today + 30 days

TC_20 — Verify new record upload sets Start Date correctly

	•	Test Steps:
	1.	Prepare an upload file for a brand new record (does not exist in system yet) with a Start Date
	2.	Upload the file
	3.	Check the newly created record’s Start Date
	•	Expected Result: Start Date is saved as provided in the upload file (rule only blocks update for existing records, not new ones)
	•	Test Data: Upload file with a new record and a Start Date