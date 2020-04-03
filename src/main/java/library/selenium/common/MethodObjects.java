package library.selenium.common;

public interface MethodObjects {
	MiscMethods miscmethodObj = new MiscMethods();
	TableMethods tableObject = new TableMethods();
	NavigateMethods navigationObj = new NavigateMethods();
	AssertionMethods assertionObj = new AssertionMethods();
	ClickElementsMethods clickObj = new ClickElementsMethods();
	ConfigurationMethods configObj = new ConfigurationMethods();
	InputMethods inputObj = new InputMethods();
	ProgressMethods progressObj = new ProgressMethods();
	JavascriptHandlingMethods javascriptObj = new JavascriptHandlingMethods();
	ScreenShotMethods screenshotObj = new ScreenShotMethods();
}
