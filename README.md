Test Case ID	Test Case Title	Preconditions	Test Steps	Expected Result	Priority	Status
TC-01	Verify current page is retained after bulk change on Manager Approval page	User is logged in as a Manager with access to /sat/managerapproval/; work queue spans multiple pages (pagination)	"1. Navigate to /sat/managerapproval/
2. Navigate to page 2 (or any page other than page 1) of the certification queue
3. Select multiple items on that page
4. Perform a bulk change/decision action"	After the bulk change completes, the user remains on the same page (e.g., page 2) instead of being reset to page 1	High	Not Run
TC-02	Verify current page is retained after bulk change on App Owner Approval page	User is logged in as an App Owner with access to /sat/appownerapproval/; work queue spans multiple pages (pagination)	"1. Navigate to /sat/appownerapproval/
2. Navigate to page 2 (or any page other than page 1) of the certification queue
3. Select multiple items on that page
4. Perform a bulk change/decision action"	After the bulk change completes, the user remains on the same page (e.g., page 2) instead of being reset to page 1	High	Not Run
TC-03	Verify page retention when bulk-changing items on page 1	User is on Manager or App Owner Approval page, queue has multiple pages	"1. Stay on page 1 of the queue
2. Select multiple items
3. Perform a bulk change action"	User remains on page 1 after the bulk change (baseline/sanity check)	Medium	Not Run
TC-04	Verify page retention when bulk-changing all items on the current page	User is on a page (e.g., page 2) where all visible items are selected	"1. Navigate to page 2
2. Select all items on that page
3. Perform a bulk change action"	User remains on page 2 (or is handled gracefully if that page becomes empty — confirm expected behavior with team)	Medium	Not Run
TC-05	Verify queue data reflects updated status after bulk change without losing page position	User is on Manager or App Owner Approval page, not on page 1	"1. Navigate to a non-first page
2. Perform a bulk decision on some items
3. Observe the queue after the action completes"	Decisioned items reflect their updated status (or are removed from the queue as expected) while the page position remains unchanged	High	Not Run
TC-06	Verify behavior on the last page of results	User is on Manager or App Owner Approval page, queue has multiple pages	"1. Navigate to the last page of the queue
2. Select and bulk-change items
3. Observe result"	User remains on the last page after the bulk change, unless that page becomes empty due to the change (confirm expected fallback behavior)	Medium	Not Run
TC-07	Verify single-item decision still works and retains page position	User is on Manager or App Owner Approval page, not on page 1	"1. Navigate to a non-first page
2. Decision a single item (not a bulk action)
3. Observe result"	Page position is retained after a single-item decision as well, consistent with bulk-change behavior	Low	Not Run
TC-08	Regression - bulk change functionality still processes correctly	User is on Manager or App Owner Approval page with multiple items	"1. Perform a bulk change (approve/reject) on multiple items
2. Verify all selected items are processed correctly"	All selected items are correctly decisioned/processed; page retention fix does not break bulk action logic	High	Not Run
TC-09	Cross-browser/device check	Access to at least 2 supported browsers (e.g., Chrome, Edge)	"1. Repeat TC-01 or TC-02 in Chrome
2. Repeat in Edge
3. Compare page retention behavior"	Page retention behaves consistently across browsers	Low	Not Run<img width="1237" height="561" alt="image" src="https://github.com/user-attachments/assets/aa3f61a7-d364-4d8c-89f9-cb92aee107c9" />
