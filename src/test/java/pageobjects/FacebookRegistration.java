package pageobjects;

import library.selenium.exec.BasePO;
import org.openqa.selenium.*;


public class FacebookRegistration extends BasePO {

    private final By register = By.xpath("//a[text()='Create New Account']");
    private final By searchByPinBtn = By.xpath("//div[text()='Search by PIN']");
    private final By searchByPinInput = By.xpath("//input[@placeholder='Enter your PIN']");
    private final By searchBtn = By.xpath("//button[normalize-space()='Search']");
    private final By username = By.xpath("//input[@name='email']");
    private final By password = By.xpath("//input[@id='pass']");
    private final By firstname = By.name("firstname");
    private final By lastname = By.name("lastname");
    private final By mobile = By.xpath("//input[@aria-label='Mobile number or email address']");
    private final By day = By.xpath("//select[@title='Day']");
    private final By month = By.xpath("//select[@title='Month']");
    private final By year = By.xpath("//select[@title='Year']");
    private final By sex = By.name("sex");
    private final By radio = By.xpath("//span[@data-type='radio']//input");
    private final By tableLoc = By.xpath("//div[@class='ui-grid-contents-wrapper']");
    private final By headerLoc = By.xpath("//div/span[@class='ui-grid-header-cell-label ng-binding']");
    private final By rowLoc = By.xpath("//div[@class='ui-grid-canvas']/div");
    private final By colLoc = By.xpath("//div[@role='gridcell']");

    public By getRegister() {
        return register;
    }

    public By getSearchByPinBtn() {
        return searchByPinBtn;
    } public By getSearchBtn() {
        return searchBtn;
    }

    public By getSearchByPinInput() {
        return searchByPinInput;
    }

    public By getUsername() {
        return username;
    }

    public By getPassword() {
        return password;
    }

    public By getFirstname() {
        return firstname;
    }

    public By getLastname() {
        return lastname;
    }

    public By getMobile() {
        return mobile;
    }

    public By getDay() {
        return day;
    }

    public By getMonth() {
        return month;
    }

    public By getYear() {
        return year;
    }

    public By getSex() {
        return sex;
    }

    public By getRadio() {
        return radio;
    }

    public By getTableLoc() {
        return tableLoc;
    }

    public By getHeaderLoc() {
        return headerLoc;
    }

    public By getRowLoc() {
        return rowLoc;
    }

    public By getColLoc() {
        return colLoc;
    }


}
