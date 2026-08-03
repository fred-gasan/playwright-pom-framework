Test Case ID	Test Case Title	Preconditions	Test Steps	Expected Result	Priority	Status
TC-01	Verify task log is created on successful upload to Non-Human table	User has access to /rolematrix/managenonhuman/; a valid upload file is prepared	"1. Navigate to /rolematrix/managenonhuman/
2. Upload a valid file with all records correctly formatted
3. Wait for processing to complete
4. Open the resulting task log"	Task log is created/updated for the upload and reflects that processing has completed	High	Not Run
TC-02	Verify task log shows status of processing completion	A file has been uploaded to the Non-Human table and processing has finished	"1. Upload a file
2. Wait for processing to complete
3. Review task log status field"	Task log clearly displays a status indicating the processing has completed	High	Not Run
TC-03	Verify task log records number of records added	A file with a known number of valid records is prepared	"1. Upload a file containing a known number of valid records (e.g., 10)
2. Wait for processing to complete
3. Review task log"	Task log records the correct count of records successfully added, matching the known input count	High	Not Run
TC-04	Verify task log records number of records failed	A file containing a mix of valid and invalid records is prepared	"1. Upload a file with a known number of invalid/failing records (e.g., 3 invalid, 7 valid)
2. Wait for processing to complete
3. Review task log"	Task log records the correct count of failed records, matching the known number of invalid records	High	Not Run
TC-05	Verify task log with all records failing	A file containing only invalid records is prepared	"1. Upload a file where all records are invalid
2. Wait for processing to complete
3. Review task log"	Task log shows 0 records added and the correct count of failed records; status reflects completion (with failures)	Medium	Not Run
TC-06	Verify task log with all records succeeding	A file containing only valid records is prepared	"1. Upload a file where all records are valid
2. Wait for processing to complete
3. Review task log"	Task log shows the correct count of records added and 0 failed records	Medium	Not Run
TC-07	Verify task log format is consistent with existing example (Prod task #11886)	Reference example task log (Prod task #11886) is available for comparison	"1. Upload a file and let it process
2. Compare new task log's structure/fields to the referenced example task log #11886"	New task log follows the same structure/format as the reference example, with added status and record count fields	Medium	Not Run
TC-08	Verify task log updates in real time / upon completion (not before)	A large file is prepared to allow observation of in-progress state	"1. Upload a large file
2. Check task log while processing is still in progress
3. Check task log again after processing completes"	Task log does not falsely show 'completed' status while processing is still running; updates correctly once finished	Medium	Not Run
TC-09	Regression - existing task log functionality still works	User has access to /rolematrix/managenonhuman/	"1. Perform a standard upload
2. Verify all previously existing task log fields/behavior (unrelated to this change) still function as expected"	No regression in existing task log fields or behavior; only new fields/status are added	Low	Not Run
TC-10	Cross-browser/device check	Access to at least 2 supported browsers (e.g., Chrome, Edge)	"1. Repeat TC-01 in Chrome
2. Repeat in Edge
3. Compare task log display"	Task log displays consistently across browsers	Low	Not Run<img width="1225" height="621" alt="image" src="https://github.com/user-attachments/assets/6c44d5f9-b84f-4f60-9342-f838770db02d" />
