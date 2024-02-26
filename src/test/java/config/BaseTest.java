package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.BasePage;

import java.time.Duration;

public class BaseTest { // Эта строка объявляет начало определения класса BaseTest. Класс является шаблоном или чертежом для создания объектов.
    private static final ThreadLocal<WebDriver> driverThreadLocal
            = new ThreadLocal<>(); //Эта строка объявляет статическое приватное поле driverThreadLocal, которое является объектом класса ThreadLocal. Он используется для хранения объектов типа WebDriver в потоке исполнения.
    // ThreadLocal - это класс в Java, который позволяет создавать локальные переменные, специфичные для каждого потока.
    // Каждый поток имеет свою собственную копию переменной, хранящейся в ThreadLocal, и доступ может быть получен только из соответствующего потока.
    // Это полезно, когда вам нужно создать объект, который будет уникальным для каждого потока, и при этом изолировать состояние между потоками.

    public static WebDriver getDriver(){

        return driverThreadLocal.get();
    }//Этот метод возвращает экземпляр WebDriver из объекта driverThreadLocal. Он используется для получения веб-драйвера, установленного для текущего потока


    // Эти строки аннотируют метод setUp как метод, который должен быть выполнен перед каждым тестовым методом (@BeforeMethod), и указывают, что он принимает параметр browser из файла конфигурации.
    // Аннотация @Optional("firefox") означает, что значение по умолчанию для browser - это "firefox".
    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser){
        // Этот блок кода проверяет, является ли значение параметра browser равным "chrome".
        // Если да, то он настраивает ChromeDriver и добавляет опции для запуска браузера на английском языке.
        if(browser.equalsIgnoreCase("chrome")){
            /**
             * Для использования Хрома версий выше 114, можно попробовать использовать
             * // WebDriverManager.chromedriver().browserVersion("121.0.6167.185").setup();
             * Вместо WebDriverManager.chromedriver().setup(); - Закоментируйте строку, а вместо "121.0.6167.185" подставте версию вашего браузера.
             *
             */

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--lang=en"); //lang for browser
            //options.addArguments("--headless"); //testing bez otobrazheniya
            driverThreadLocal.set(new ChromeDriver(options));

            // Аналогично предыдущему блоку, но если browser равен "firefox",
            // то настраивается FirefoxDriver и добавляются опции для запуска браузера на английском языке.
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("intl.accept_languages", "en");
            driverThreadLocal.set(new FirefoxDriver(options));
        } else if (browser.equalsIgnoreCase("safari")) {
            SafariOptions options = new SafariOptions();
            options.setCapability("language", "en");
            driverThreadLocal.set(new SafariDriver());
        } else if (browser.equalsIgnoreCase("edge")) {
            // Настройки для Edge
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.setCapability("language", "en");
            //options.addArguments("--headless");
            driverThreadLocal.set(new EdgeDriver(options));
        } else {
            throw new IllegalArgumentException("Invalid browser " + browser);
        }

        // Этот блок кода получает веб-драйвер с помощью метода getDriver(), максимизирует окно браузера,
        // устанавливает время ожидания загрузки страницы и неявного ожидания, а затем устанавливает этот драйвер для BasePage.
        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(20000)); // Здесь устанавливается время ожидания для загрузки страницы. Если страница не загружается в течение указанного времени (в данном случае, 20 секунд), будет сгенерировано исключение.
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(20000));
        BasePage.setDriver(driver);// Этот вызов используется для установки экземпляра драйвера в базовом классе страницы. это полезно т.к. есть базовый класс для всех ваших страниц, который управляет инициализацией драйвера.
    }

    @AfterMethod
    public void tearDown(){
        WebDriver driver = getDriver();// Получаем текущий экземпляр WebDriver с помощью метода getDriver().
        if (driver != null){// Проверяем, что экземпляр драйвера не равен null.
            driver.quit();// Если драйвер не равен null, то закрываем браузер с помощью метода quit().
            driverThreadLocal.remove();// Удаляем текущий экземпляр WebDriver из объекта driverThreadLocal.
        }
    }
}
