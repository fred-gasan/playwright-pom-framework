Manual Test Cases — SAT_20260630 | Datatable Config (Current Appropriateness Column)
URL: /rolematrix/mnepreviewqueue/

Prereq: Logged in as fg-manager1

TC-01 | Search field exists on Current Appropriateness column

Steps: Navigate to mnepreviewqueue → Scroll to tfoot → Locate Current Appropriateness column
Expected: Search input field is visible and enabled under Current Appropriateness column in tfoot


TC-02 | Search returns matching rows

Steps: Type "Inappropriate" in Current Appropriateness search field → Wait for table to filter
Expected: Only rows containing "Inappropriate" in Current Appropriateness column are displayed


TC-03 | Search is case-insensitive

Steps: Type "inappropriate" (all lowercase) in search field
Expected: Same rows returned as "Inappropriate" — filter is not case-sensitive


TC-04 | Search with no match shows empty table

Steps: Type "xyz123" in Current Appropriateness search field
Expected: Table shows "No matching records found" / 0 entries; no data rows visible


TC-05 | Clearing search restores all rows

Steps: Type "Inappropriate" → confirm filter → Clear the search field
Expected: All original rows return; table resets to full unfiltered state


TC-06 | Sorting is disabled on Current Appropriateness column

Steps: Click the Current Appropriateness column header once
Expected: Table does NOT sort; no sort arrow (▲▼) appears on header; row order unchanged


TC-07 | Sorting stays disabled on repeated clicks

Steps: Click Current Appropriateness column header 2–3 times
Expected: Still no sort applied on any click; header class remains sorting_disabled


TC-08 | Sorting still works on other columns

Steps: Click Mnemonic column header → then click again
Expected: First click sorts ascending (▲), second click sorts descending (▼); confirms only Current Appropriateness is restricted


TC-09 | Search does not cause timeout

Steps: Type "Inappropriate" in search field → observe page behavior for 5 seconds
Expected: Table filters and responds within 5 seconds; no spinner stuck; no timeout error on page


TC-10 | Search + sort interaction on other columns

Steps: Type "Inappropriate" in Current Appropriateness search → then click Mnemonic column to sort
Expected: Filtered rows remain (only "Inappropriate") AND sort by Mnemonic applies correctly on top of filter


TCPriorityTypeTC-01HighFunctionalTC-02HighFunctionalTC-03MediumFunctionalTC-04MediumNegativeTC-05MediumFunctionalTC-06HighFunctionalTC-07LowBoundaryTC-08HighFunctionalTC-09HighPerformanceTC-10MediumIntegration
