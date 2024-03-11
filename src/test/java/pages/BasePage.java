package pages;

import config.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// BasePage служит базовым классом для всех страниц тестового приложения
public class BasePage {
    protected static WebDriver driver;
    public static void setDriver(WebDriver webDriver){// Метод устанавливает значение поля driver в переданный экземпляр веб-драйвера.

        driver=webDriver;//Эта строка присваивает переданный экземпляр веб-драйвера переменной driver, что позволяет другим классам иметь доступ к этому веб-драйверу через метод getDriver()
    }




}
