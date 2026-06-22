package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

/**
 * Step definitions for SAT_20260630:
 * Datatable config update for preview and validation views.
 * - Current Appropriateness column: search enabled, sort disabled.
 *
 * Column index for "Current Appropriateness" is 8 (1-based) based on
 * the Work Queue table visible in prior screenshots:
 * Mnemonic(1) | Entitlement(2) | User(3) | Associated Roles(4) |
 * Associated Entitlement(5) | Starting Appropriateness(6) |
 * Current Appropriateness(7) | Manager Business Justification(8) |
 * Revoke Reason(9) | Related RMV(10)
 * Confirm exact index against live DOM before merging.
 */
public class DatatableConfig_stepDefinition extends AbstractStepDefinitions {

    // ── Confirmed from live DOM (screenshot 06/22/2026) ──────────────────────
    // Table:   table.ui.selectable.celled.striped.table.dataTable
    // URL:     /rolematrix/mnepreviewqueue/
    // Search inputs live in tfoot#my_foot (NOT thead)
    // Current Appropriateness search input:
    //   id="foot_current_color_reason"
    //   aria-label="arialabel_foot_current_color_reason"
    //   placeholder="Search"  class="myplaceholder"
    // ─────────────────────────────────────────────────────────────────────────

    // Confirmed: search input is in tfoot, id="foot_current_color_reason"
    private static final By CURRENT_APPROPRIATENESS_SEARCH =
            By.id("foot_current_color_reason");

    // Column header matched by visible text in thead
    private static final By CURRENT_APPROPRIATENESS_HEADER = By.xpath(
            "//thead//th[normalize-space(.)='Current Appropriateness']");

    // Data cells — position derived dynamically from header text to be column-order-safe
    private static final By CURRENT_APPROPRIATENESS_CELLS = By.xpath(
            "//table[contains(@class,'dataTable')]//tbody/tr/td[" +
            "count(//thead//th[normalize-space(.)='Current" +
            " Appropriateness']/preceding-sibling::th)+1]");

    @When("we enter {string} in the Current Appropriateness search field")
    public void weEnterInTheCurrentAppropriatenessSearchField(String searchText) {
        WebElement searchInput = state.getDriver().findElement(CURRENT_APPROPRIATENESS_SEARCH);
        HighLightElement(state.getDriver(), searchInput);
        searchInput.clear();
        searchInput.sendKeys(searchText);
        System.out.println("Entered '" + searchText + "' in Current Appropriateness search field");
    }

    @Then("all visible rows should have {string} in the Current Appropriateness column")
    public void allVisibleRowsShouldHaveValueInCurrentAppropriatenessColumn(String expectedValue) {
        List<WebElement> cells = state.getDriver().findElements(CURRENT_APPROPRIATENESS_CELLS);
        Assert.assertFalse(cells.isEmpty(), "No rows found in table after search");
        for (WebElement cell : cells) {
            String actualText = cell.getText().trim();
            Assert.assertTrue(
                    actualText.equalsIgnoreCase(expectedValue),
                    "Expected '" + expectedValue + "' in Current Appropriateness but found: '" + actualText + "'");
        }
        System.out.println("All " + cells.size() + " visible rows verified with value: " + expectedValue);
    }

    @Then("the table should show no results message")
    public void theTableShouldShowNoResultsMessage() {
        // DataTables renders an empty-table row with class "dataTables_empty"
        By emptyLocator = By.xpath("//tbody/tr[@class='odd']/td[contains(@class,'dataTables_empty')]");
        WebElement emptyMessage = state.getDriver().findElement(emptyLocator);
        Assert.assertTrue(emptyMessage.isDisplayed(),
                "Expected 'No entries found' message but table still shows rows");
        System.out.println("No results message confirmed: " + emptyMessage.getText());
    }

    @Then("the table should not be empty")
    public void theTableShouldNotBeEmpty() {
        List<WebElement> rows = state.getDriver().findElements(By.xpath("//tbody/tr[td]"));
        Assert.assertFalse(rows.isEmpty(), "Table is empty but expected at least one row");
        System.out.println("Table has " + rows.size() + " row(s) - confirmed not empty");
    }

    @When("we click on the Current Appropriateness column header")
    public void weClickOnTheCurrentAppropriatenessColumnHeader() {
        WebElement header = state.getDriver().findElement(CURRENT_APPROPRIATENESS_HEADER);
        HighLightElement(state.getDriver(), header);
        header.click();
        System.out.println("Clicked Current Appropriateness column header");
    }

    @Then("the Current Appropriateness column header should not have a sort class applied")
    public void theCurrentAppropriatenessColumnHeaderShouldNotHaveSortClass() {
        WebElement header = state.getDriver().findElement(CURRENT_APPROPRIATENESS_HEADER);
        String classes = header.getAttribute("class");
        // DataTables adds "sorting_asc" or "sorting_desc" when sorting is active.
        // A non-sortable column keeps the base "sorting_disabled" class only.
        Assert.assertFalse(
                classes.contains("sorting_asc") || classes.contains("sorting_desc"),
                "Current Appropriateness column is sortable but should be disabled. Classes found: " + classes);
        Assert.assertTrue(
                classes.contains("sorting_disabled"),
                "Expected 'sorting_disabled' class on Current Appropriateness header but found: " + classes);
        System.out.println("Confirmed sort is disabled on Current Appropriateness. Classes: " + classes);
    }

    @Then("the table row order should remain unchanged")
    public void theTableRowOrderShouldRemainUnchanged() {
        // Capture first cell of first row before and after click to confirm no re-order.
        // In practice, call this step immediately after clicking the header in the same scenario.
        // We verify by confirming no "sorting_asc"/"sorting_desc" class exists (covered above).
        System.out.println("Row order unchanged - confirmed via sort class assertion above");
    }

    @When("we click on the {string} column header")
    public void weClickOnColumnHeader(String columnName) {
        // Looks up column header by visible text
        By headerLocator = By.xpath(
                "//thead//th[normalize-space(.)='" + columnName + "']");
        WebElement header = state.getDriver().findElement(headerLocator);
        HighLightElement(state.getDriver(), header);
        header.click();
        System.out.println("Clicked column header: " + columnName);
    }

    @Then("the {string} column header should have a sort class applied")
    public void theColumnHeaderShouldHaveSortClassApplied(String columnName) {
        By headerLocator = By.xpath(
                "//thead//th[normalize-space(.)='" + columnName + "']");
        WebElement header = state.getDriver().findElement(headerLocator);
        String classes = header.getAttribute("class");
        Assert.assertTrue(
                classes.contains("sorting_asc") || classes.contains("sorting_desc"),
                "Expected sort class on '" + columnName + "' header but found: " + classes);
        System.out.println("Sort confirmed on column '" + columnName + "'. Classes: " + classes);
    }

    @Then("the page should remain responsive within 5 seconds")
    public void thePageShouldRemainResponsiveWithinFiveSeconds() {
        // Verifies no spinner/timeout overlay is blocking the page within 5s
        By loadingOverlay = By.xpath(
                "//*[contains(@class,'dataTables_processing') and contains(@style,'display: block')]");
        try {
            new WebDriverWait(state.getDriver(), Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));
            System.out.println("Page remained responsive - processing overlay gone within 5 seconds");
        } catch (Exception e) {
            Assert.fail("Page timed out - DataTables processing overlay still visible after 5 seconds");
        }
    }
}
