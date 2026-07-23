Test Case ID	Test Case Title	Preconditions	Test Steps	Expected Result	Priority	Status
TC-01	Verify updated header order displays correctly	User has access to /rolematrix/managenonhuman/	"1. Navigate to /rolematrix/managenonhuman/
2. Locate the upload instructions section"	Headers appear in exact order: Profile ID, Type of ID, Owner ID, Owner Back-Up ID, Entitlement, Mnemonic, High Risk Entitlement, SOD Concerns, Additional Controls/Reports, Purpose, Active Date, Inactive Date	High	Not Run
TC-02	Verify header names are unchanged	Story SAT_20260825 has been deployed	"1. Open upload instructions
2. Compare each header label against the original set"	No header text was renamed, added, or removed — only the order changed	Medium	Not Run
TC-03	Verify count of headers matches original	Upload instructions are visible	"1. Open upload instructions
2. Count total number of headers listed"	12 headers total, matching the pre-existing set	Low	Not Run
TC-04	Verify uploaded file template/sample matches new order	A downloadable template/sample file exists on the page	"1. Download the template/sample file from the page
2. Open the file and review column order"	Column order in template matches the new instruction order exactly	Medium	Not Run
TC-05	Verify successful upload using correctly-ordered file	Test file prepared with columns in the new order	"1. Prepare a test file with columns in the new order
2. Upload the file via /rolematrix/managenonhuman/"	Upload succeeds without column-mapping errors	High	Not Run
TC-06	Verify upload behavior with old (pre-fix) column order	Test file prepared using the previous incorrect column order	"1. Prepare a test file using the previous (old) column order
2. Upload the file"	Confirm expected behavior — file is either rejected with a clear error, or correctly mapped (clarify intended behavior with team)	Medium	Not Run
TC-07	Regression - no impact to other pages/instructions	Identify any other pages that reference the same shared instruction component	"1. Navigate to other pages referencing the same instruction set (if applicable)
2. Review header order on those pages"	No unintended header order changes on other pages	Low	Not Run
TC-08	Visual/UI formatting check	Upload instructions are visible	1. Review formatting (spacing, punctuation, line breaks) of the instructions section	No visual defects introduced by the reordering; text is clean and readable	Low	Not Run
TC-09	Cross-browser/device check	Access to at least 2 supported browsers (e.g., Chrome, Edge)	"1. View upload instructions in Chrome
2. View upload instructions in Edge
3. Compare rendering"	Header order and formatting render consistently across browsers	Low	Not Run![Uploading image.png…]()
