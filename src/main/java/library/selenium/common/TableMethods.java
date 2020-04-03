package library.selenium.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class TableMethods extends CommonMethods implements MethodObjects {

    public WebElement getMatchingCellElement(String textToFind, String type, String access_name) throws Exception {
        WebElement table = getDriver().findElement(getObjectBy(type, access_name));
        List<WebElement> table_rows = table.findElements(By.tagName("tr"));
        int rows_count = table_rows.size();
        int row = 0;
        int column = 0;
        int columns_count = 0;
        List<WebElement> columns_row = null;
        first:
        for (row = 0; row < rows_count; row++) {
            columns_row = table_rows.get(row).findElements(By.tagName("td"));
            columns_count = columns_row.size();
            for (column = 0; column < columns_count; column++) {
                if (columns_row.get(column).getText().equals(textToFind)) {
                    break first;
                }
            }
        }
        if (columns_row != null) {
            return columns_row.get(column);
        } else {
            return null;
        }
    }

    public WebElement getMatchingCellElement(String textToFind, String columnName, String type, String access_name) throws Exception {
        WebElement table = getDriver().findElement(getObjectBy(type, access_name));
        List<WebElement> table_headers = table.findElements(By.xpath("//th | //div/span[@class='ui-grid-header-cell-label ng-binding']"));
        List<WebElement> table_rows = table.findElements(By.xpath("//tr | //div[@class='ui-grid-canvas']/div"));
        int col;
        List<WebElement> columns = new ArrayList<>();
        First:
        for (col = 1; col <= table_headers.size(); col++) {
            if (table_headers.get(col).getText().trim().equals(columnName))
                for (int row = 1; row <= table_rows.size(); row++) {
                    columns = getDriver().findElements(By.xpath("//tr" + "[" + row + "]//td | " +
                            "//div[@class='ui-grid-canvas']/div" + "[" + row + "]//div[@role='gridcell']"));
                    if (columns.get(col).getText().equals(textToFind)) {
                        break First;
                    }
                }
        }


        if (columns.get(col)!=null){
            return columns.get(col);
        }else {
            return null;
        }

    }

    public WebElement getMatchingCellElement(String textToFind, String columnName, String tableLocator, String headerLocator, String rowLocator, String columLocator, String pageName) throws Throwable {
        WebElement table = getDriver().findElement(getObject(tableLocator, pageName));
        List<WebElement> table_headers = table.findElements(getObject(headerLocator, pageName));
        List<WebElement> table_rows = table.findElements(getObject(rowLocator, pageName));
        int col;
        List<WebElement> columns = new ArrayList<>();
        First:
        for (col = 1; col <= table_headers.size(); col++) {
            if (table_headers.get(col).getText().trim().equals(columnName))
                for (int row = 1; row <= table_rows.size(); row++) {
            columns = getDriver().findElements(By.xpath(getObject(rowLocator, pageName).toString().split(":")[1] + "[" + row + "]" + getObject(columLocator, pageName).toString().split(":")[1]));
                    if (columns.get(col).getText().equals(textToFind)) {
                        break First;
                    }
                }
        }

        if (columns.get(col)!=null){
            return columns.get(col);
        }else {
            return null;
        }

    }
}


