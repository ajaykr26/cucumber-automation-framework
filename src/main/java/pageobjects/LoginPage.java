package pageobjects;

import org.openqa.selenium.*;

import java.util.List;


public class LoginPage extends BasePage {


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

    private By username = By.xpath("//input[@name='email']");
    private By password = By.xpath("//input[@name='pass']");
    private By firstname = By.name("firstname");
    private By lastname = By.name("lastname");
    private By mobile = By.xpath("//input[@aria-label='Mobile number or email address']");
    private By day = By.xpath("//select[@title='Day']");
    private By month = By.xpath("//select[@title='Month']");
    private By year = By.xpath("//select[@title='Year']");
    private By sex = By.name("sex");
    private By radio = By.xpath("//span[@data-type='radio']//input");
    private By tableLoc = By.xpath("//div[@class='ui-grid-contents-wrapper']");
    private By headerLoc = By.xpath("//div/span[@class='ui-grid-header-cell-label ng-binding']");

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

    private By rowLoc = By.xpath("//div[@class='ui-grid-canvas']/div");
    private By colLoc = By.xpath("//div[@role='gridcell']");


}
