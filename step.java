package ie.testing.ibank.acceptance.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;

import ie.testing.ibank.acceptance.pages.StandingOrderPage;


public class StandingOrderStep {

    private StandingOrderPage standingOrderPage = new StandingOrderPage();

    @When("^the user setup a new standing order to an External Account$")
    public void setupNewStandingOrder(){
        standingOrderPage.setupStandingOrder("NewAccount");
    }

    @Then("^the code card or card reader segment should be displayed in the Standing Order screen$")
    public void theCodeReaderShouldBeDisplayed(){
        standingOrderPage.verifyCodeAndCardDisplayedOrNot(true);
    }

    @When("^the user setup a new standing order to existing testiing account$")
    public void setupNewStandingOrderToExistingAccount(){
        standingOrderPage.setupStandingOrder("ExistingAccount");
    }

    @Then("^the code card or card reader segment should Not be displayed in the Standing Order screen$")
    public void theCodeReaderShouldNotBeDisplayed(){
        standingOrderPage.verifyCodeAndCardDisplayedOrNot(false);
    }

    @When("^the user confirm the standing order with existing testiing account details$")
    public void theUserConfirmStaningOrderWithExistingAccount(){
        standingOrderPage.confirmTheStandingOrderDetails("ExistingAccount");
    }

    @Then("^the code card or card reader segment should Not be displayed in the Confirm screen$")
    public void theCodeCardShouldNotBeDisplayed(){
        standingOrderPage.verifyCodeCardDisplayedOrNot(false);
    }

    @When("^the user confirm the standing order with testiing new account details$")
    public void theUserConfirmStaningOrderWithNewAccount(){
        standingOrderPage.confirmTheStandingOrderDetails("NewAccount");
    }

    @Then("^the code card or card reader segment should be displayed in the Confirm screen$")
    public void theCodeCardShouldBeDisplayed(){
        standingOrderPage.verifyCodeCardDisplayedOrNot(true);
    }

    @When("^the user setup a new standing order with a new payment option \"([^\"]*)\"$")
    public void createStandingOrderWithPaymentOptions(String payOptions){
        standingOrderPage.updatePaymentDetailsWithPayOptions(payOptions);
    }

    @Then("^the confirm details screen should be displayed$")
    public void theconfirmScreenShouldBeDisplayed(){
        standingOrderPage.verifyConfirmationPage();
    }

    @When("^the user setup a new standing order with a payment option \"([^\"]*)\"$")
    public void createStandingOrderWithPayments(String payOptions){
        standingOrderPage.enterPaymentOptions(payOptions);
    }

    @Then("^the remaining other options \"([^\"]*)\" \"([^\"]*)\" should be disabled$")
    public void verifyRemainingOtherOptionsShouldBeDisabled(String payOptions1, String payOptions2){
        standingOrderPage.verifyPaymentOptions(payOptions1);
        standingOrderPage.verifyPaymentOptions(payOptions2);
    }

    @And("^the user confirm the details on the confirm details screen$")
    public void userConfrimDetails(){
        standingOrderPage.clickOnConfirmButton();
    }

    @Then("^the confirmation details screen should be displayed$")
    public void verifyConfirmationScreen(){
        standingOrderPage.verifyConfirmationDetailScreen();
    }
}
