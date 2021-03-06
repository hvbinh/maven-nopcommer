package commons;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.UserAddressesPO;
import pageObjects.UserCustomerInforPO;
import pageObjects.UserOrdersPO;
import pageObjects.UserProductDetailPO;
import pageObjects.PageGeneratorManager;
import pageUIs.AbstractPageUI;
import pageUIs.UserAddressesPageUI;
import pageUIs.UserCustomerInforPageUI;
import pageUIs.UserOrdersPageUI;

public class AbstractPage {
	public void openPageUrl(WebDriver driver, String url) {
		driver.get(url);
	}

	public String getCurrentPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getCurrentPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getCurrentPageSource(WebDriver driver) {
		return driver.getPageSource();
	}

	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}

	public void cancelAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}

	public String getTextlAlert(WebDriver driver) {
		return driver.switchTo().alert().getText();
	}

	public void setTextlAlert(WebDriver driver, String value) {
		driver.switchTo().alert().sendKeys(value);
	}

	public void waitAlertPresence(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public void swithToWindowById(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}

	}

	public boolean closeAllWindowsWithoutParent(WebDriver driver, String idParent) {
		Set<String> allWindows = driver.getWindowHandles();

		for (String runWindow : allWindows) {

			if (!runWindow.equals(idParent)) {
				driver.switchTo().window(runWindow);
				driver.close();
			}
		}
		driver.switchTo().window(idParent);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	public void switchToWindowByTitle(WebDriver driver, String title) {
		Set<String> allWindows = driver.getWindowHandles();

		for (String runWindow : allWindows) {
			driver.switchTo().window(runWindow);
			String currentTitle = driver.getTitle();
			if (currentTitle.trim().equals(title)) {
				break;
			}
		}
	}

	public WebElement getElement(WebDriver driver, String locator) {
		return driver.findElement(getByXpath(locator));
	}

	public WebElement getElement(WebDriver driver, String locator, String... values) {
		return getElement(driver, getDynamicLocator(locator, values));
	}

	public List<WebElement> getElements(WebDriver driver, String locator) {
		return driver.findElements(getByXpath(locator));
	}

	public void clickToElement(WebDriver driver, String locator, String... values) {
		element = getElement(driver, getDynamicLocator(locator, values));
		
		if(driver.toString().toLowerCase().contains("internet explorer"))
		{
			clickToElementByJS(driver, locator, values);
			sleepInSecond(3);
		}
		else
		{
			element.click();
		}
	}

	public void clickToElement(WebDriver driver, String locator) {
		element = getElement(driver, locator);
		if(driver.toString().toLowerCase().contains("internet explorer"))
		{
			clickToElementByJS(driver, locator);
			sleepInSecond(3);
		}
		else
		{
			element.click();
		}
		
	}

	public void sendkeyToElement(WebDriver driver, String locator, String value, String... values) {
		element = getElement(driver, getDynamicLocator(locator, values));
		element.clear();
		element.sendKeys(value);
	}

	public void sendkeyToElement(WebDriver driver, String locator, String value) {
		element = getElement(driver, locator);
		element.clear();
		element.sendKeys(value);
	}

	public By getByXpath(String locator) {
		return By.xpath(locator);
	}

	public String getDynamicLocator(String locator, String... values) {
		locator = String.format(locator, (Object[]) values);
		return locator;
	}

	public void selectItemInDropdown(WebDriver driver, String locator, String itemValue) {
		element = getElement(driver, locator);
		select = new Select(element);
		select.selectByVisibleText(itemValue);
	}
	public void selectItemInDropdown(WebDriver driver, String locator, String itemValue,String... values) {
		String elementText = getElement(driver, getDynamicLocator(itemValue, values)).getText();
		element = getElement(driver, locator);
		select = new Select(element);
		select.selectByVisibleText(elementText);
	}

	public String getFirstSelectedTextInDropDown(WebDriver driver, String locator) {
		element = getElement(driver, locator);
		select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	public boolean isDropdownMultiple(WebDriver driver, String locator) {
		element = getElement(driver, locator);
		select = new Select(element);
		return select.isMultiple();
	}

	public void selectTheItemInCustomeDropdown(WebDriver driver, String parentXpath, String childXpath, String expectedText) {

		getElement(driver, parentXpath).click();
		sleepInSecond(1);
		explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childXpath)));

		elements = getElements(driver, childXpath);

		for (WebElement actualItem : elements) {
			if (actualItem.getText().trim().equals(expectedText)) {
				jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", actualItem);
				sleepInSecond(1);
				actualItem.click();
				sleepInSecond(1);
				break;
			}
		}

	}


	public void sleepInSecond(long second) {

		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public String getElementAtribute(WebDriver driver, String locator, String attributeName) {
		element = getElement(driver, locator);
		return element.getAttribute(attributeName);
	}

	public String getElementAtribute(WebDriver driver, String locator, String attributeName, String... values) {
		element = getElement(driver, getDynamicLocator(locator, values));
		return element.getAttribute(attributeName);
	}

	public String getElementText(WebDriver driver, String locator) {
		element = getElement(driver, locator);
		return element.getText();
	}

	public String getElementText(WebDriver driver, String locator, String... values) {
		element = getElement(driver, locator, values);
		return element.getText();
	}

	public int countElementSize(WebDriver driver, String locator) {
		return getElements(driver, locator).size();
	}

	public int countElementSize(WebDriver driver, String locator, String... values) {
		return getElements(driver, getDynamicLocator(locator, values)).size();
	}

	public void checkToCheckbox(WebDriver driver, String locator) {
		element = getElement(driver, locator);
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void checkToCheckbox(WebDriver driver, String locator, String... values) {
		element = getElement(driver, getDynamicLocator(locator, values));
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToCheckbox(WebDriver driver, String locator) {
		element = getElement(driver, locator);
		if (element.isSelected()) {
			element.click();
		}
	}

	public boolean isElementDisplayed(WebDriver driver, String locator) {
		return getElement(driver, locator).isDisplayed();
	}

	public boolean isElementDisplayed(WebDriver driver, String locator, String... values) {
		try {
			return getElement(driver, getDynamicLocator(locator, values)).isDisplayed();
		}catch(Exception e)
		{
			System.out.println("execption: "+e);
			return false;
		}
		
	}

	public boolean isElementEnabled(WebDriver driver, String locator) {
		return getElement(driver, locator).isEnabled();
	}

	public boolean isElementSelected(WebDriver driver, String locator) {
		return getElement(driver, locator).isSelected();
	}

	public void switchToFrame(WebDriver driver, String locator) {
		driver.switchTo().frame(getElement(driver, locator));
	}

	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public void doubleClickToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.doubleClick(getElement(driver, locator)).perform();
	}

	public void rightClickToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.contextClick(getElement(driver, locator)).perform();
	}

	public void hoverMouseToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.moveToElement(getElement(driver, locator)).perform();
	}
	public void hoverMouseToElement(WebDriver driver, String locator,String... values) {
		action = new Actions(driver);
		action.moveToElement(getElement(driver, getDynamicLocator(locator, values))).perform();
	}

	public void clickAndHoldToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.contextClick(getElement(driver, locator)).perform();
	}

	public void dragAndDropElement(WebDriver driver, String sourceLocator, String targetLocator) {
		action = new Actions(driver);
		action.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targetLocator)).perform();
	}

	public void sendKeyBoardToElement(WebDriver driver, String locator, Keys key) {
		action = new Actions(driver);
		action.sendKeys(getElement(driver, locator), key).perform();
	}

	public Object executeForBrowser(WebDriver driver, String javaScript) {
		jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		jsExecutor = (JavascriptExecutor) driver;
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(WebDriver driver, String url) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void highlightElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		element = getElement(driver, locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	public void clickToElementByJS(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		element = getElement(driver, locator);
		jsExecutor.executeScript("arguments[0].click();", element);
	}
	public void clickToElementByJS(WebDriver driver, String locator,String...values) {
		jsExecutor = (JavascriptExecutor) driver;
		element = getElement(driver, getDynamicLocator(locator, values));
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public void scrollToElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		element = getElement(driver, locator);
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
		sleepInSecond(1);
	}

	public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
		jsExecutor = (JavascriptExecutor) driver;
		element = getElement(driver, locator);
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}

	public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
		jsExecutor = (JavascriptExecutor) driver;
		element = getElement(driver, locator);
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", element);
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, 30);
		jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public boolean isImageLoaded(WebDriver driver, String locator) {
		element = getElement(driver, locator);
		jsExecutor = (JavascriptExecutor) driver;

		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", element);
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public void waitToElementVisible(WebDriver driver, String locator) {

		explicitWait = new WebDriverWait(driver, 30);

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));

	}

	public void waitToElementVisible(WebDriver driver, String locator, String... values) {
		explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(locator, values))));
	}

	public void waitToElementInvisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, GlobalConstants.SHORT_TIME);
		overrideImplicitWait(driver, GlobalConstants.SHORT_TIME);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
		overrideImplicitWait(driver, GlobalConstants.LONG_TIME);
	}

	public void waitToElementInvisible(WebDriver driver, String locator, String... values) {
		explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(getDynamicLocator(locator, values))));
	}

	public void waitToElementClickable(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
	}

	public void waitToElementClickable(WebDriver driver, String locator, String... values) {
		explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator, values))));
	}

	// 4 ham mo page
	public UserAddressesPO openAddressesPage(WebDriver driver) {
		waitToElementClickable(driver, AbstractPageUI.ADDRESSES_LINK);
		clickToElement(driver, AbstractPageUI.ADDRESSES_LINK);
		return PageGeneratorManager.getUserAddressesPage(driver);
	}

	/*
	 * public UserProductReviewsPO openMyProductReviewsPage(WebDriver driver) { waitToElementClickable(driver, AbstractPageUI.MY_PRODUCT_REVIEWS_LINK);
	 * clickToElement(driver, AbstractPageUI.MY_PRODUCT_REVIEWS_LINK); return PageGeneratorManager.getUserMyProductReviewsPage(driver); }
	 */

	public UserCustomerInforPO openCustomerInforPage(WebDriver driver) {
		waitToElementClickable(driver, AbstractPageUI.CUSTOMER_INFOR_LINK);
		clickToElement(driver, AbstractPageUI.CUSTOMER_INFOR_LINK);
		return PageGeneratorManager.getUserCustomerInforPage(driver);
	}

	// >10 page
	public void openLinkWithPageName(WebDriver driver, String pageName) {
		waitToElementClickable(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
		clickToElement(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
	}

	public UserOrdersPO openOrdersPage(WebDriver driver) {
		waitToElementClickable(driver, AbstractPageUI.ORDERS_LINK);
		clickToElement(driver, AbstractPageUI.ORDERS_LINK);
		return PageGeneratorManager.getUserOrdersPage(driver);
	}

	public AbstractPage openLinkByPageName(WebDriver driver, String pageName) {
		waitToElementClickable(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
		clickToElement(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
		switch (pageName) {
		case "Addresses":
			return PageGeneratorManager.getUserAddressesPage(driver);
		case "My product reviews":
			return PageGeneratorManager.getUserMyProductReviewsPage(driver);
		case "Customer info":
			return PageGeneratorManager.getUserCustomerInforPage(driver);
		default:
			return PageGeneratorManager.getUserOrdersPage(driver);
		}
	}

	public void waitAjaxLoadingInvisible(WebDriver driver) {
		waitToElementInvisible(driver, AbstractPageUI.LOADING_ICON);
	}
	/* nopcommer project */
	
	public void uploadFileByPanelID(WebDriver driver, String panelID, String... fileNames) {
		String filePath = GlobalConstants.UPLOAD_FOLDER;
		String fullFileName = "";
		for (String file : fileNames) {
			fullFileName = fullFileName + filePath + file + "\n";
		}
		fullFileName = fullFileName.trim();
		getElement(driver, getDynamicLocator(AbstractPageUI.UPLOAD_FILE_TYPE_BY_PANEL, panelID)).sendKeys(fullFileName);
	}

	public void clickToPlusIconByPanelID(WebDriver driver, String panelID) {
		waitToElementClickable(driver, AbstractPageUI.PLUS_ICON_PANEL, panelID);
		String iconAttributeValue = getElementAtribute(driver, AbstractPageUI.PLUS_ICON_PANEL, "class", panelID);
		if (iconAttributeValue.contains("fa-plus")) {
			clickToElement(driver, AbstractPageUI.PLUS_ICON_PANEL, panelID);
			sleepInSecond(1);
		}
	}

	public boolean isElementUndisplayed(WebDriver driver, String locator) {
		overrideImplicitWait(driver, GlobalConstants.SHORT_TIME);
		elements = getElements(driver, locator);
		overrideImplicitWait(driver, GlobalConstants.LONG_TIME);
		if (elements.size() == 0) {
			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}
	public void overrideImplicitWait(WebDriver driver, long timeSecond) 
	{
		driver.manage().timeouts().implicitlyWait(timeSecond, TimeUnit.SECONDS);
	}
	public void clickToRadioButtonByID(WebDriver driver,String radioButtonID)
	{
		waitToElementClickable(driver, AbstractPageUI.DYNAMIC_RADIO_BUTTON_BY_ID,radioButtonID);
		clickToElement(driver, AbstractPageUI.DYNAMIC_RADIO_BUTTON_BY_ID,radioButtonID);
	}
	public void inputToTexboxByID(WebDriver driver, String textBoxID,String value)
	{
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_TEXTBOX_BY_ID, textBoxID);
		sendkeyToElement(driver, AbstractPageUI.DYNAMIC_TEXTBOX_BY_ID, value, textBoxID);
	}
	public void clickToButtonByValue(WebDriver driver, String buttonValue)
	{
		waitToElementClickable(driver, AbstractPageUI.DYNAMIC_BUTTON_BY_VALUE,buttonValue);
		clickToElement(driver, AbstractPageUI.DYNAMIC_BUTTON_BY_VALUE, buttonValue);
	}
	public void selectDropdownByName(WebDriver driver, String dropdownName,String itemValue)
	{
		waitToElementClickable(driver, AbstractPageUI.DYNAMIC_DROPDOWN_BY_NAME, dropdownName);
		selectItemInDropdown(driver, AbstractPageUI.DYNAMIC_DROPDOWN_BY_NAME, itemValue, dropdownName);
	}
	public String getErrorMessageAtMandatoryByID(WebDriver driver, String fieldID)
	{
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_ERROR_MESSAGE_BY_ID, fieldID);
		return getElementText(driver, AbstractPageUI.DYNAMIC_ERROR_MESSAGE_BY_ID, fieldID);
	}

	public boolean isDataFloatSortedAssending(WebDriver driver,String locator)
	{
		ArrayList<Float> arrayList = new ArrayList<>();

		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		for(WebElement element : elementList)
		{
			arrayList.add(Float.parseFloat(element.getText().replace("$", "").trim()));
		}

		System.out.println("data in UI");

		for(Float name : arrayList)
		{
			System.out.println(name);
		}

		ArrayList<Float> sortedList = new ArrayList<>();
		for(Float child : arrayList)
		{
			sortedList.add(child);
		}

		Collections.sort(arrayList);


		System.out.println("data after sorting ASC");

		for(Float name : arrayList)
		{
			System.out.println(name);
		}

		return sortedList.equals(arrayList);

	}
	public ArrayList<String> getProductPriceList(WebDriver driver,String locator)
	{
		ArrayList<String> arrayList = new ArrayList<>();

		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		System.out.println("list--------------");
		for(WebElement element : elementList)
		{
			arrayList.add(element.getText().trim().replace("$", ""));
			System.out.println(element.getText());
		}
		return arrayList;
	}
	public ArrayList<String> getProductPriceListAfterProcessSortASC(WebDriver driver,String locator)
	{
		ArrayList<String> arrayList = new ArrayList<>();

		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		for(WebElement element : elementList)
		{
			arrayList.add(element.getText().trim().replace("$", ""));

		}
		Collections.sort(arrayList);

		System.out.println("data after sorting ASC------------");

		for(String name : arrayList)
		{
			System.out.println(name);
		}
		return arrayList;
	}
	public ArrayList<String> getProductPriceListAfterProcessSortDESC(WebDriver driver,String locator)
	{
		ArrayList<String> arrayList = new ArrayList<>();

		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		for(WebElement element : elementList)
		{
			arrayList.add(element.getText().trim().replace("$", ""));

		}
		Collections.sort(arrayList);
		Collections.reverse(arrayList);

		System.out.println("data after sorting DESC------------");

		for(String name : arrayList)
		{
			System.out.println(name);
		}
		return arrayList;
	}
	public ArrayList<String> getProductNameList(WebDriver driver,String locator)
	{
		ArrayList<String> arrayList = new ArrayList<>();

		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		System.out.println("list--------------");
		for(WebElement element : elementList)
		{
			arrayList.add(element.getText().trim());
			System.out.println(element.getText());
		}
		return arrayList;
	}
	public ArrayList<String> getProductNameListAfterProcessSortASC(WebDriver driver,String locator)
	{
		ArrayList<String> arrayList = new ArrayList<>();

		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		for(WebElement element : elementList)
		{
			arrayList.add(element.getText().trim());
		}

		Collections.sort(arrayList);

		System.out.println("data after sorting ASC------------");

		for(String name : arrayList)
		{
			System.out.println(name);
		}
		return arrayList;
	}
	public ArrayList<String> getProductNameListAfterProcessSortDESC(WebDriver driver,String locator)
	{
		ArrayList<String> arrayList = new ArrayList<>();

		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		for(WebElement element : elementList)
		{
			arrayList.add(element.getText().trim());
		}
		Collections.sort(arrayList);
		Collections.reverse(arrayList);

		System.out.println("data after sorting ASC------------");

		for(String name : arrayList)
		{
			System.out.println(name);
		}
		return arrayList;
	}

	public boolean isDataStringSortedAssending(WebDriver driver,String locator)
	{
		ArrayList<String> arrayList = new ArrayList<>();

		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		for(WebElement element : elementList)
		{
			arrayList.add(element.getText().trim());
		}

		System.out.println("product name in UI which have not sorted");

		for(String productName : arrayList)
		{
			System.out.println(productName);
		}

		ArrayList<String> sortedList = new ArrayList<>();
		for(String child : arrayList)
		{
			sortedList.add(child);
		}

		Collections.sort(arrayList);


		System.out.println("data after sorting ASC");

		for(String name : arrayList)
		{
			System.out.println(name);
		}

		return sortedList.equals(arrayList);

	}
	public boolean isDataFloatSortedDescending(WebDriver driver,String locator)
	{
		ArrayList<Float> arrayList = new ArrayList<>();

		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		for(WebElement element : elementList)
		{
			arrayList.add(Float.parseFloat(element.getText().replace("$", "").trim()));
		}

		System.out.println("data in UI");

		for(Float name : arrayList)
		{
			System.out.println(name);
		}

		ArrayList<Float> sortedList = new ArrayList<>();
		for(Float child : arrayList)
		{
			sortedList.add(child);
		}

		Collections.sort(arrayList);



		System.out.println("data after sorting ASC");

		for(Float name : arrayList)
		{
			System.out.println(name);
		}

		Collections.reverse(arrayList);


		System.out.println("data after sorting DESC");

		for(Float name : arrayList)
		{
			System.out.println(name);
		}

		return sortedList.equals(arrayList);

	}

	
	private WebDriverWait explicitWait;
	private WebElement element;
	private JavascriptExecutor jsExecutor;
	private Actions action;
	private Select select;
	private List<WebElement> elements;

}
