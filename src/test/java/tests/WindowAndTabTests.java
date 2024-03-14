package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class WindowAndTabTests {

    public static void main(String[] args) throws InterruptedException {
        // switchTab();
        sliderTest();
    }
    @Test
    public static  void sliderTest() throws InterruptedException { // Указание throws InterruptedException означает, что метод может выбрасывать исключение InterruptedException.


        WebDriver driver = new FirefoxDriver(); // Создание экземпляра веб-драйвера Firefox. Этот драйвер будет использоваться для управления браузером.
        driver.get("https://demoqa.com/slider");
        driver.manage().window().maximize(); // Максимизация окна браузера для полноэкранного просмотра.
        WebElement slider = driver.findElement(By.xpath("//input[@type='range']")); // Нахождение элемента слайдера на веб-странице с помощью XPath.
        Actions action = new Actions(driver); // Создание объекта класса Actions, который используется для выполнения действий с мышью или клавиатурой на странице.
        action.dragAndDropBy(slider, 30, 0).build().perform(); // Выполнение действия перетаскивания ползунка слайдера на 30 пикселей вправо (горизонтальное перемещение). Затем метод perform() применяет это действие.
        Thread.sleep(3000);

/*
        WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
        slider.sendKeys(Keys.ARROW_RIGHT); Thread.sleep(1000);
        slider.sendKeys(Keys.ARROW_RIGHT); Thread.sleep(1000);
        slider.sendKeys(Keys.ARROW_RIGHT); Thread.sleep(1000);
        slider.sendKeys(Keys.ARROW_RIGHT); Thread.sleep(1000);
        slider.sendKeys(Keys.ARROW_RIGHT); Thread.sleep(1000);*/
        driver.quit(); // Завершение сеанса веб-драйвера. Этот шаг очищает ресурсы, освобождает браузер и завершает сеанс, что важно для правильной работы и завершения теста.
    }
    public static void switchTab(){
        WebDriver driver = new FirefoxDriver();
        driver.get("https://demoqa.com/browser-windows");
        driver.manage().window().maximize();

        String mainWindowHandler = driver.getWindowHandle(); // Получает идентификатор текущего (главного) окна браузера и сохраняет его в переменной mainWindowHandler.
        WebElement newButton = driver.findElement(By.xpath("//button[@id='tabButton']")); //Находим кнопку
        newButton.click(); // и жмем на нее, чтобы открыть новую вкладку.

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));// Создает объект WebDriverWait, который будет ожидать на протяжении 5 секунд, чтобы выполнить следующую команду.
        wait.until(ExpectedConditions.numberOfWindowsToBe(2)); // Ожидает, пока количество открытых окон в браузере не станет равным 2.
        Set<String> allWindowHandles = driver.getWindowHandles();// Получает все идентификаторы окон, открытых в браузере, и сохраняет их в коллекции типа Set<String>.

        String newWindowHandler = ""; // Объявляет переменную newWindowHandler, которая будет использоваться для хранения идентификатора нового окна или вкладки.
        for (String windowHandle : allWindowHandles){ // Пробегаемся по всем идентификаторам окон, найденным на предыдущем шаге.
            if(!windowHandle.equals(mainWindowHandler)){ // Проверяем, является ли текущий идентификатор окна отличным от идентификатора главного окна, сохраненного на первом шаге.
                newWindowHandler = windowHandle;// Если идентификатор окна отличается от главного, сохраняем этот идентификатор в переменной newWindowHandler и завершаем цикл.
                break;
            }
        }
        driver.switchTo().window(newWindowHandler); // Переключаемся на новую вкладку, используя его идентификатор.
        WebElement newPageElement = driver.findElement(By.xpath("//h1[@id='sampleHeading']"));//  пытаемся найти элемент на новой вкладке.
        newPageElement.click();
        driver.quit();

    }
    @Test
    public static void dragAndDropTest() throws InterruptedException {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        driver.manage().window().maximize();

        WebElement elementToDrop = driver.findElement(By.xpath("//div[@id='column-a']"));
        WebElement targetElement = driver.findElement(By.xpath("//div[@id='column-b']"));

        Actions actions = new Actions(driver);
        actions.dragAndDrop(elementToDrop, targetElement).build().perform();
        Thread.sleep(5000);
        driver.quit();

    }
}
