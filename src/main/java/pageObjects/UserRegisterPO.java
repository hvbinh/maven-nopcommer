package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import io.qameta.allure.Step;
import pageUIs.AbstractPageUI;
import pageUIs.UserRegisterPageUI;

public class UserRegisterPO extends AbstractPage{
	WebDriver driver;

	public UserRegisterPO(WebDriver driver) {
		super();
		this.driver = driver;
	}

	//@Step("Click to Gender Male radio button")
	public void clickToGenderMaleRadioButton() {
		waitToElementClickable(driver, UserRegisterPageUI.GENDER_MALE_RADIO);
		clickToElement(driver, UserRegisterPageUI.GENDER_MALE_RADIO);
		
	}
	//@Step("Input to Firstname textbox with value: {0}")
	public void inputToFirstnameTextBox(String firstName) {
		waitToElementVisible(driver, UserRegisterPageUI.FIRSTNAME_TEXTBOX);
		sendkeyToElement(driver, UserRegisterPageUI.FIRSTNAME_TEXTBOX, firstName);
		
	}
	//@Step("Input to Lastname textbox with value: {0}")
	public void inputToLastnameTextBox(String lastName) {
		waitToElementVisible(driver, UserRegisterPageUI.LASTNAME_TEXTBOX);
		sendkeyToElement(driver, UserRegisterPageUI.LASTNAME_TEXTBOX, lastName);
		
	}

	//@Step("Select Day dropdown")
	public void selectDayDropdown(String day) {
		waitToElementClickable(driver, UserRegisterPageUI.DAY_DROPDOWN);
		selectItemInDropdown(driver, UserRegisterPageUI.DAY_DROPDOWN, day);
		
	}
	//@Step("Select Month dropdown")
	public void selectMonthDropdown(String month) {
		waitToElementClickable(driver, UserRegisterPageUI.MONTH_DROPDOWN);
		selectItemInDropdown(driver, UserRegisterPageUI.MONTH_DROPDOWN, month);
		
	}

	//@Step("Select Year dropdown")
	public void selectYearDropdown(String year) {
		waitToElementClickable(driver, UserRegisterPageUI.YEAR_DROPDOWN);
		selectItemInDropdown(driver, UserRegisterPageUI.YEAR_DROPDOWN, year);
		
	}
	//@Step("Input to Email textbox with value: {0}")
	public void inputToEmailTextbox(String email) {
		waitToElementVisible(driver, UserRegisterPageUI.EMAIL_TEXTBOX);
		sendkeyToElement(driver, UserRegisterPageUI.EMAIL_TEXTBOX, email);
		
	}
	
	//@Step("Input to Company textbox with value: {0}")
	public void inputToCompanyTextbox(String companyName) {
		waitToElementVisible(driver, UserRegisterPageUI.COMPANY_TEXTBOX);
		sendkeyToElement(driver, UserRegisterPageUI.COMPANY_TEXTBOX, companyName);
		
	}

	//@Step("Input to Password textbox with value: {0}")
	public void inputToPasswordTextbox(String pass) {
		waitToElementVisible(driver, UserRegisterPageUI.PASSWORD_TEXTBOX);
		sendkeyToElement(driver, UserRegisterPageUI.PASSWORD_TEXTBOX, pass);
		
	}

	//@Step("Input to confirm Password textbox with value: {0}")
	public void inputToConfirmPasswordTextbox(String pass) {
		waitToElementVisible(driver, UserRegisterPageUI.CONFIRM_PASSWORD_TEXTBOX);
		sendkeyToElement(driver, UserRegisterPageUI.CONFIRM_PASSWORD_TEXTBOX, pass);
		
	}

	//@Step("Click to Register button")
	public UserRegisterPO clickToRegisterButton() {
		waitToElementClickable(driver, UserRegisterPageUI.REGISTER_BUTTON);
		clickToElement(driver, UserRegisterPageUI.REGISTER_BUTTON);
		return new UserRegisterPO(driver);
		
	}

	//@Step("Verify Register success message displayed")
	public String getRegisteredSuccessMessage() {
		waitToElementVisible(driver, UserRegisterPageUI.REGISTER_SUCCESS_MESSAGE);
		return getElementText(driver, UserRegisterPageUI.REGISTER_SUCCESS_MESSAGE);
	}

	//@Step("Click to Logout link and navigate to Home Page")
	public UserHomePO clickToLogoutLink() {
		waitToElementClickable(driver, UserRegisterPageUI.LOGOUT_LINK);
		clickToElement(driver, UserRegisterPageUI.LOGOUT_LINK);
		return PageGeneratorManager.getUserHomePage(driver);
		
	}

	public String getRegisteredErrorMessage(String errorName) {
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_ERROR_MESSAGE_BY_ID, errorName);
		
		return getElementText(driver, AbstractPageUI.DYNAMIC_ERROR_MESSAGE_BY_ID, errorName);
	}

	public String getRegisteredExistedErrorMessage() {
		waitToElementVisible(driver, UserRegisterPageUI.REGISTER_VALIDATE_EXISTED_ERROR_MESSAGE);
		return getElementText(driver, UserRegisterPageUI.REGISTER_VALIDATE_EXISTED_ERROR_MESSAGE);
	}

	public String getRegisteredPasswordLessThan6Characters() {
		waitToElementVisible(driver, UserRegisterPageUI.REGISTER_VALIDATE_PASSWORD_ERROR_MESSAGE);
		return getElementText(driver, UserRegisterPageUI.REGISTER_VALIDATE_PASSWORD_ERROR_MESSAGE);
	}

	public String getRegisteredNotMatchConfirmPassword() {
		waitToElementVisible(driver, UserRegisterPageUI.REGISTER_VALIDATE_CONFIRM_PASSWORD_ERROR_MESSAGE);
		return getElementText(driver, UserRegisterPageUI.REGISTER_VALIDATE_CONFIRM_PASSWORD_ERROR_MESSAGE);
	}





}
