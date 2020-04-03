package library.selenium.common;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ProgressMethods extends CommonMethods implements MethodObjects
{

	/** Method to wait
	 * @param time : String : Time to wait
	 * @throws NumberFormatException
	 * @throws InterruptedException
	 */
	public void wait(String time) throws NumberFormatException, InterruptedException
	{
		//sleep method takes parameter in milliseconds
		Thread.sleep(Integer.parseInt(time)*1000);
	}

	/**Method to Explicitly wait for element to be displayed
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 * @param duration : String : Time to wait for element to be displayed
	 */
	public void waitForElementToDisplay(String accessType,String accessName,String duration)
	{
		By byEle = getObjectBy(accessType, accessName);
		WebDriverWait wait = (new WebDriverWait(getDriver(),Integer.parseInt(duration)*1000));
		wait.until(ExpectedConditions.visibilityOfElementLocated(byEle));
	}

	/** Method to Explicitly wait for element to be enabled=click
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 * @param duration : String : Time to wait for element to be clickable
	 */
	public void waitForElementToClick(String accessType,String accessName,String duration)
	{
		By byEle = getObjectBy(accessType, accessName);
		WebDriverWait wait = (new WebDriverWait(getDriver(),Integer.parseInt(duration)*1000));
		wait.until(ExpectedConditions.elementToBeClickable(byEle));
	}
	/**Method to Explicitly wait for element to be displayed
	 * @param objectName : String : Locator type (id, name, class, xpath, css)
	 * @param pageName : String : Locator value
	 * @param duration : String : Time to wait for element to be displayed
	 */
	public void waitForElementToDisplay$(String objectName,String pageName,String duration) throws Throwable {
		WebDriverWait wait = (new WebDriverWait(getDriver(),Integer.parseInt(duration)*1000));
		wait.until(ExpectedConditions.visibilityOfElementLocated(getObject(objectName, pageName)));
	}
		
	/** Method to Explicitly wait for element to be enabled=click
	 * @param objectName : String : objectName
	 * @param pageName : String : pageName
	 * @param duration : String : Time to wait for element to be clickable
	 */
	public void waitForElementToClick$(String objectName,String pageName,String duration) throws Throwable {
		WebDriverWait wait = (new WebDriverWait(getDriver(),Integer.parseInt(duration)*1000));
		wait.until(ExpectedConditions.elementToBeClickable(getObject(objectName, pageName)));
	}
	
}
