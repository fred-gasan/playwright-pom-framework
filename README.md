Test Case ID	Test Case Title	Preconditions	Test Steps	Expected Result	Priority	Status
TC-01	Verify filters retained on Manager Certifications page after submit	User is logged in as a Manager with access to /sat/managerapproval/; work queue has multiple items	"1. Navigate to /sat/managerapproval/
2. Apply a filter on one or more columns
3. Decision (approve/reject) some items in the queue
4. Click Submit"	After submission, the previously applied column filters remain in place; filtered view is unchanged	High	Not Run
TC-02	Verify filters retained on App Owner Certifications page after submit	User is logged in as an App Owner with access to /sat/appownerapproval/; work queue has multiple items	"1. Navigate to /sat/appownerapproval/
2. Apply a filter on one or more columns
3. Decision some items in the queue
4. Click Submit"	After submission, the previously applied column filters remain in place; filtered view is unchanged	High	Not Run
TC-03	Verify multiple simultaneous column filters are retained	User is on Manager or App Owner Certifications page with a queue containing varied data	"1. Apply filters to two or more columns at once
2. Decision some filtered items
3. Click Submit"	All applied filters across multiple columns remain in place after submit, not just the last one applied	High	Not Run
TC-04	Verify filtered-out items remain hidden after submit	User is on Manager or App Owner Certifications page; filter applied that excludes some items	"1. Apply a filter that excludes certain rows
2. Decision the visible (filtered-in) items
3. Click Submit"	Filtered-out items remain hidden after submit; only the filtered-in item set is displayed	Medium	Not Run
TC-05	Verify filters retained when only some items in queue are decisioned	User is on Manager or App Owner Certifications page with a queue of 5+ items and a filter applied	"1. Apply a filter
2. Decision only a subset of the visible items
3. Click Submit"	Filters remain applied; remaining undecisioned items are still shown per the active filter	Medium	Not Run
TC-06	Verify filters are cleared/reset appropriately on manual clear	User is on Manager or App Owner Certifications page with a filter applied	"1. Apply a filter
2. Manually clear or remove the filter
3. Click Submit after deciding some items"	Filter remains cleared after submit (i.e., the fix does not force filters to persist when user explicitly removed them)	Low	Not Run
TC-07	Verify behavior on page refresh/navigation away and back	User is on Manager or App Owner Certifications page with a filter applied and items decisioned	"1. Apply a filter, decision some items, and submit
2. Navigate away from the page
3. Return to the same page"	Confirm expected behavior — clarify with team whether filters should persist across navigation or only across submit within the same session	Low	Not Run
TC-08	Regression - decisioning still functions correctly with filters retained	User is on Manager or App Owner Certifications page with a filter applied	"1. Apply a filter
2. Approve/reject items
3. Click Submit
4. Verify decisioned items are correctly processed (status updates, removed from queue, etc.)"	Decisions are processed correctly and reflected accurately; filter retention does not interfere with decision logic	High	Not Run
TC-09	Cross-browser/device check	Access to at least 2 supported browsers (e.g., Chrome, Edge)	"1. Repeat TC-01 or TC-02 in Chrome
2. Repeat in Edge
3. Compare filter retention behavior"	Filter retention behaves consistently across browsers	Low	Not Run<img width="1225" height="561" alt="image" src="https://github.com/user-attachments/assets/04f42828-429c-44e4-9416-98a076888563" />
