package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage extends BasePage {
    @FindBy(xpath = "//input[@name='email']") // Эта строка использует аннотацию @FindBy для поиска веб-элемента на веб-странице с помощью XPath-выражения. В данном случае, элемент найден по XPath, который ищет <input> элемент с атрибутом name, равным "email".
    WebElement emailField;// Найденный элемент сохраняется в переменной emailField типа WebElement.

    @FindBy(xpath = "//button[@name='registration']")
    WebElement registrationButton;

    @FindBy(xpath = "//input[@name='password']")
    WebElement passwordField;

    @FindBy(xpath = "//button[@name='login']")
    WebElement loginButton;

    public LoginPage(WebDriver driver){
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public LoginPage fillEmailField(String email){// Этот метод заполняет поле электронной почты на веб-странице.
        // Он принимает строку email, переданную в качестве аргумента, и использует метод sendKeys(),
        // чтобы ввести эту строку в поле emailField.
        emailField.sendKeys(email);
        return this;// Затем метод возвращает объект LoginPage, что позволяет использовать этот метод в цепочке вызовов
    }
    public LoginPage clickByRegistrationButton(){// Этот метод кликает по кнопке регистрации на веб-странице.
        // Он вызывает метод click() для registrationButton.
        registrationButton.click();
       return this;// Затем он также возвращает объект LoginPage, чтобы этот метод также можно было использовать в цепочке вызовов.
    }

    public LoginPage fillPasswordField(String password){
        passwordField.sendKeys(password);
        return this;
    }

    public LoginPage clickByLoginButton(){
        loginButton.click();
        return this;
    }

}
