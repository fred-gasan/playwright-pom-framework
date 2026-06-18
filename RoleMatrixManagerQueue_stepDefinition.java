package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Step definitions for CLOP-16993: Retain / Revoke buttons on the
 * Manager Business Justification view (/rolematrix/managerqueue/).
 *
 * NOTE: Locators below (row checkbox, justification cell, status cell,
 * cert-action cell, validation banner) are inferred from the page
 * structure visible in the captured screenshots and follow the same
 * row-by-entitlement xpath pattern already used in
 * SystemAccessTool_stepDefinition.java. Confirm exact column indices /
 * ids against the live DOM (or the page object, if one exists) before
 * merging, since the table column order can shift between releases.
 */
public class RoleMatrixManagerQueue_stepDefinition extends AbstractStepDefinitions {

    @When("we select the checkbox for entitlement {string}")
    public void weSelectTheCheckboxForEntitlement(String entitlement) throws InterruptedException {
        By checkboxLocator = By.xpath("//tr[td[normalize-space()='" + entitlement + "']]//input[@type='checkbox']");
        WebElement checkbox = state.getDriver().findElement(checkboxLocator);
        HighLightElement(state.getDriver(), checkbox);
        checkbox.click();
        System.out.println("Selected checkbox for entitlement: " + entitlement);
    }

    @Then("{string} button should be {string}")
    public void buttonShouldBeState(String buttonName, String expectedState) {
        String buttonId = buttonName.equalsIgnoreCase("Retain") ? "retain_btn" : "revoke_btn";
        WebElement button = state.getDriver().findElement(By.id(buttonId));
        boolean isDisabled = button.getAttribute("class").contains("disabled");

        if (expectedState.equalsIgnoreCase("disabled")) {
            Assert.assertTrue(isDisabled, buttonName + " button expected to be disabled but was enabled");
        } else if (expectedState.equalsIgnoreCase("enabled")) {
            Assert.assertFalse(isDisabled, buttonName + " button expected to be enabled but was disabled");
        } else {
            Assert.fail("Unknown expected state '" + expectedState + "' - use 'disabled' or 'enabled'");
        }
        System.out.println(buttonName + " button state verified as: " + expectedState);
    }

    @When("we click-custom on {string} button for entitlement")
    public void weClickCustomOnButtonForEntitlement(String buttonName) {
        String buttonId = buttonName.equalsIgnoreCase("Retain") ? "retain_btn" : "revoke_btn";
        WebElement button = state.getDriver().findElement(By.id(buttonId));
        HighLightElement(state.getDriver(), button);
        button.click();
        System.out.println("Clicked " + buttonName + " button");
    }

    @When("we enter-custom {string} in the Business Justification field for entitlement {string}")
    public void weEnterCustomInBusinessJustificationField(String text, String entitlement) {
        By justificationInput = By.xpath(
                "//tr[td[normalize-space()='" + entitlement + "']]//input[@name='businessJustification']");
        WebElement input = state.getDriver().findElement(justificationInput);
        input.clear();
        input.sendKeys(text);
        System.out.println("Entered Business Justification '" + text + "' for entitlement: " + entitlement);
    }

    @Then("the Manager Business Justification field for entitlement {string} should contain {string}")
    public void theManagerBusinessJustificationFieldShouldContain(String entitlement, String expectedText) {
        By justificationCellLocator = By.xpath(
                "//tr[td[normalize-space()='" + entitlement + "']]/td[contains(@class,'justification') or position()=9]");
        WebElement justificationCell = state.getDriver().findElement(justificationCellLocator);
        String actualText = justificationCell.getText();
        Assert.assertTrue(actualText.contains(expectedText),
                "Expected Business Justification to contain '" + expectedText + "' but found: " + actualText);
        System.out.println("Verified Business Justification for " + entitlement + ": " + actualText);
    }

    @Then("the record status for entitlement {string} should be {string}")
    public void theRecordStatusForEntitlementShouldBe(String entitlement, String expectedStatus) {
        By statusLocator = By.xpath(
                "//tr[td[normalize-space()='" + entitlement + "']]//*[contains(@class,'status')]");
        WebElement statusElement = state.getDriver().findElement(statusLocator);
        String actualStatus = statusElement.getText().trim();
        Assert.assertEquals(actualStatus, expectedStatus,
                "Expected status '" + expectedStatus + "' but found '" + actualStatus + "' for entitlement: " + entitlement);
        System.out.println("Verified status for " + entitlement + ": " + actualStatus);
    }

    @Then("the Cert Action for entitlement {string} should remain {string}")
    public void theCertActionForEntitlementShouldRemain(String entitlement, String expectedCertAction) {
        // Maps to the "Current Appropriateness" column loaded from the OIM upload.
        By certActionLocator = By.xpath(
                "//tr[td[normalize-space()='" + entitlement + "']]/td[contains(@class,'current-appropriateness') or position()=6]");
        WebElement certActionCell = state.getDriver().findElement(certActionLocator);
        String actual = certActionCell.getText().trim();
        Assert.assertEquals(actual, expectedCertAction,
                "Cert Action should not be overridden by a Revoke decision for entitlement: " + entitlement);
        System.out.println("Verified Cert Action unchanged for " + entitlement + ": " + actual);
    }

    @Then("we should see validation message {string}")
    public void weShouldSeeValidationMessage(String expectedMessage) {
        By validationLocator = By.xpath("//*[contains(@class,'error') or contains(@class,'validation')]");
        WebElement validationElement = state.getDriver().findElement(validationLocator);
        String actualMessage = validationElement.getText().trim();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Expected validation message '" + expectedMessage + "' but found: " + actualMessage);
        System.out.println("Validation message verified: " + actualMessage);
    }
}
