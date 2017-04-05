package ie.testiing.ibank.acceptance.pages;

import org.joda.time.LocalDate;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StandingOrderPage {

    private static final String NEW_ACCOUNT = "NewAccount";
    private static final String EXISTING_ACCOUNT = "ExistingAccount";
    private static final String FINAL_PAY_DATE_OPTION = "EndDate";
    private static final String NO_OF_PAYMNET_OPTION = "NoOfPayments";
    private static final String INDEFINITY_OPTION = "Indefinitely";

    @FindBy(id = "transfersandpayments_button_id")
    private WebElement payAndTransferOption;

    @FindBy(id = "standingorderlist_button_id")
    private WebElement standingOrderLink;

    @FindBy(id = "inactive_button_id")
    private WebElement inactiveStandingOrderTab;

    @FindBy(xpath = "//button[contains(.,'Set up a Standing Order')]")
    private WebElement setUpStandingOrder;

    @FindBy(id = "standingOrderSenderAccountIndex_id")
    private WebElement fromAccount;

    @FindBy(id = "standingOrder.senderReference_id")
    private WebElement yourMessage;

    @FindBy(xpath = "//label[contains(.,'A new account')]")
    private WebElement newAccountType;

    @FindBy(xpath = "//label[contains(.,'An existing account')]")
    private WebElement existingAccountType;

    @FindBy(id = "standingOrderReceiverAccountIndex_id")
    private WebElement toAccount;

    @FindBy(id = "receiverPaymentDetails.IBAN_id")
    private WebElement iBAN;

    @FindBy(id = "standingOrder.receiverName_id")
    private WebElement receiverName;

    @FindBy(id = "standingOrder.receiverReference_id")
    private WebElement receiverMessage;

    @FindBy(xpath = "//button[contains(.,'Next')]")
    private WebElement nextButton;

    @FindBy(id = "standingOrder.nextAmount.euro_id")
    private WebElement amount;

    @FindBy(id = "standingOrder.paymentFrequency_id")
    private WebElement paymentFrequency;

    @FindBy(id = "pickDate_day")
    private WebElement startDateDay;

    @FindBy(id = "pickDate_month")
    private WebElement startDateMonth;

    @FindBy(id = "pickDate_year")
    private WebElement startDateYear;

    @FindBy(xpath = "//a[contains(.,'Code Card')]")
    private WebElement codeCardLink;

    @FindBy(xpath = "//a[contains(.,'Card Reader')]")
    private WebElement cardReaderLink;

    @FindBy(id = "codecard_confirm_field_id")
    private WebElement codeCardConfirm;

    @FindBy(id = "pickEndDate_day")
    private WebElement finalDateDay;

    @FindBy(id = "pickEndDate_month")
    private WebElement finalDateMonth;

    @FindBy(id = "pickEndDate_year")
    private WebElement finalDateYear;

    @FindBy(id = "standingOrder.numPayments_id")
    private WebElement noOfPayments;

    @FindBy(id = "checkBoxLabel")
    private WebElement indefinityOption;

    @FindBy(xpath = "//h2[contains(.,'Confirm Details')]")
    private WebElement confirmationDetailScreen;

    @FindBy(xpath = "//button[contains(.,'Confirm')]")
    private WebElement confirmButton;

    @FindBy(xpath = "//h3[contains(.,'Confirmation')]")
    private WebElement confirmationScreen;

    public StandingOrderPage(){PageFactory.initElements(getWebDriver(), this);}

    public void clickPayAndTransfer() { $(payAndTransferOption).hover();}

    public void nagivateToStandingOrderPage(){
        clickPayAndTransfer();
        $(standingOrderLink).click();
    }

    public void setupStandingOrder(String accountType){
        nagivateToStandingOrderPage();
        $(setUpStandingOrder).click();

        Select fromAccountType = new Select($(fromAccount));
        fromAccountType.selectByVisibleText(fromAccountType.getOptions().get(1).getText());
        $(yourMessage).setValue("AutomationTesting");
        $(receiverName).setValue("Testign Team");

        if(NEW_ACCOUNT.contains(accountType)){
            $(newAccountType).click();
            $(iBAN).setValue("IE07BOFI90097373678330");
        }else if(EXISTING_ACCOUNT.contains(accountType)){
            $(existingAccountType).click();
            Select toAccountType = new Select($(toAccount));
            toAccountType.selectByVisibleText(toAccountType.getOptions().get(2).getText());
        }

        $(receiverMessage).setValue("test");
    }

    public void updatePaymentDetails(){
        enterAmountDetails();
        $(noOfPayments).setValue("3");
        $(nextButton).click();
    }

    public void confirmTheStandingOrderDetails(String accountType){
        setupStandingOrder(accountType);
        $(nextButton).click();
        updatePaymentDetails();
    }

    public void verifyCodeAndCardDisplayedOrNot(Boolean fieldStatus){
        assertThat($(codeCardLink).isDisplayed(), is(fieldStatus));
        assertThat($(cardReaderLink).isDisplayed(), is(fieldStatus));
    }

    public void verifyCodeCardDisplayedOrNot(Boolean fieldStatus){
        assertThat($(codeCardConfirm).isDisplayed(), is(fieldStatus));
    }

    public void updatePaymentDetailsWithPayOptions(String payOptions){
        enterPaymentOptions(payOptions);
        $(nextButton).click();
    }

    public void enterPaymentOptions(String payOptions){

        setupStandingOrder("ExistingAccount");
        $(nextButton).click();
        enterAmountDetails();

        switch (payOptions){
            case FINAL_PAY_DATE_OPTION:
                DecimalFormat formatter = new DecimalFormat("00");
                LocalDate today = new LocalDate();
                today = today.plusDays(11);

                $(finalDateDay).click();
                $(finalDateDay).setValue(formatter.format(today.getDayOfMonth()));
                $(finalDateMonth).setValue(formatter.format(today.getMonthOfYear()));
                $(finalDateYear).setValue(Integer.toString(today.getYear()));
                break;
            case NO_OF_PAYMNET_OPTION:
                $(noOfPayments).click();
                $(noOfPayments).setValue("3");
                break;
            case INDEFINITY_OPTION:
                $(indefinityOption).click();
                $(indefinityOption).click();
                break;
        }

    }

    public void verifyPaymentOptions(String payOptions) {

        switch (payOptions){
            case FINAL_PAY_DATE_OPTION:
                assertThat($(finalDateDay).isDisplayed(), is(false));
                assertThat($(finalDateMonth).isDisplayed(), is(false));
                assertThat($(finalDateYear).isDisplayed(), is(false));
                break;
            case NO_OF_PAYMNET_OPTION:
                assertThat($(noOfPayments).isDisplayed(), is(false));
                break;
            case INDEFINITY_OPTION:
                assertThat($(indefinityOption).isDisplayed(), is(false));
                break;
        }
    }

    public void enterAmountDetails(){
        $(amount).setValue("1");

        Select paymentFrequencyType = new Select($(paymentFrequency));
        paymentFrequencyType.selectByVisibleText(paymentFrequencyType.getOptions().get(1).getText());

        DecimalFormat formatter = new DecimalFormat("00");
        LocalDate today = new LocalDate();
        today = today.plusDays(10);

        $(startDateDay).setValue(formatter.format(today.getDayOfMonth()));
        $(startDateMonth).setValue(formatter.format(today.getMonthOfYear()));
        $(startDateYear).setValue(Integer.toString(today.getYear()));
    }

    public void verifyConfirmationPage(){
        assertThat($(confirmationDetailScreen).isDisplayed(), is(true));
    }

    public void clickOnConfirmButton(){$(confirmButton).click();}

    public void verifyConfirmationDetailScreen(){
        assertThat($(confirmationScreen).isDisplayed(), is(true));
    }
}
