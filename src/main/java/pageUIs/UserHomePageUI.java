package pageUIs;

public class UserHomePageUI {
	public static final String REGISTER_LINK = "//a[@class='ico-register']";
	public static final String LOG_IN_LINK = "//div[@class='header-links']//a[@class='ico-login']";
	public static final String LOG_OUT_LINK = "//a[@class='ico-logout']";
	public static final String MY_ACCOUNT_LINK = "//a[@class='ico-account']";
	public static final String SHOPPING_CART_NO_ITEM_TOOLTIP = "//div[@class='count' and text()='You have no items in your shopping cart.']";
	public static final String SEARCH_LINK = "//a[text()='Search']";
	
	public static final String DYNAMIC_HEADER_MENU = "//ul[@class='top-menu notmobile']//a[contains(text(),'%s')]";
	public static final String DYNAMIC_SUB_MENU = "//ul[@class='top-menu notmobile']//a[contains(text(),'%s')]";
	
	public static final String DYNAMIC_PRODUCT_TITLE = "//a[text()='Build your own computer']";

}
