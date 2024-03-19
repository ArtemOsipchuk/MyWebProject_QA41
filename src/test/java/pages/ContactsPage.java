package pages;

import config.BaseTest;
import model.Contact;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ContactsPage extends BasePage {

    @FindBy(xpath = "//button[contains(text(),'Sign Out']")
    WebElement signOutButton;


    public ContactsPage(WebDriver driver){
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public LoginPage clickBySignOutButton(){
        signOutButton.click();
        return new LoginPage(driver);
    }

    public boolean isElementPersist(WebElement element){
        return isElementPresent(element);
    }

    public int deleteContactByPhoneNumberOrName(String phoneNumberOrName) {
        List<WebElement> contactsList = getContactsList();
        int initSize = contactsList.size();
        try {
            for (WebElement contact : contactsList) {
                WebElement phoneNumberData = contact.findElement(By
                        .xpath("//h2[text()='"+phoneNumberOrName+"'] | //h3[text()='"+phoneNumberOrName+"']"));
                if (phoneNumberData.isDisplayed()) {
                    phoneNumberData.click();
                    clickRemoveButton();
                    break; // Для прекращения цикла после удаления контакта
                }
            }}catch (NoSuchElementException exception){exception.fillInStackTrace();
            System.out.println("Item with phone number "+phoneNumberOrName+" was not found. ");}
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions
                .numberOfElementsToBe(By.xpath("//div[@class='contact-item_card__2SOIM']"), initSize - 1));

        return contactsList.size();
    }

    protected List<WebElement> getContactsList(){
        return driver.findElements(By.xpath("//div[@class='contact-item_card__2SOIM']"));
    }

    public int getContactsListSize(){
        return getContactsList().size();
    }

    public void clickRemoveButton(){
        WebElement removeButton = driver.findElement(By.xpath("//button[text()='Remove']"));
        removeButton.click();
    }

    public boolean messageIsDisplayed(String text){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'No Contacts here')]")));
        String actualRes = message.getText().trim();
        boolean res = actualRes.equals(text);
        return res;

    }

    public List<WebElement> getDataFromContactsList(By locator){
        return BaseTest.getDriver().findElements(locator);
    }

    public boolean deleteContact(Contact contact){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));// Создается объект класса WebDriverWait,
        // который ожидает видимости элемента на странице в течение 5 секунд.

        WebElement nameInContact = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), '"+contact.getName().toString()+"')]")));
        nameInContact.click();

        WebElement removeButton = driver.findElement(By.xpath("//button[contains(text(),'Remove')]"));
        removeButton.click();
       return nameInContact.isDisplayed();
    }


    /**
     * Этот метод предназначен для получения данных о контакте из списка контактов
     * на веб-странице и сравнения полученных данных с данными переданным объектом Contact.
     * @param contact
     * @return boolean
     */
    public boolean getDataFromContactList(Contact contact){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));// Создается объект класса WebDriverWait,
        // который ожидает видимости элемента на странице в течение 5 секунд.

        WebElement nameInContact = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), '"+contact.getName().toString()+"')]")));
        nameInContact.click();
        WebElement editButton = driver.findElement(By.xpath("//button[contains(text(),'Edit')]"));
        editButton.click();

        WebElement elementName = driver.findElement(By.xpath("//input[@placeholder='Name']"));
        String elementNameValue = elementName.getAttribute("value");

        WebElement elementLastName = driver.findElement(By.xpath("//input[@placeholder='Last Name']"));
        String elementLastNameValue = elementLastName.getAttribute("value");

        WebElement elementPhone = driver.findElement(By.xpath("//input[@placeholder='Phone']"));
        String elementPhoneValue = elementPhone.getAttribute("value");

        WebElement elementEmail = driver.findElement(By.xpath("//input[@placeholder='email']"));
        String elementEmailValue = elementEmail.getAttribute("value");

        WebElement elementAddress = driver.findElement(By.xpath("//input[@placeholder='Address']"));
        String elementAddressValue = elementAddress.getAttribute("value");

        WebElement elementDescription = driver.findElement(By.xpath("//input[@placeholder='desc']"));
        String elementDescriptionValue = elementDescription.getAttribute("value");

        Contact listContact = new Contact();
        listContact.setName(elementNameValue);
        listContact.setLastName(elementLastNameValue);
        listContact.setPhone(elementPhoneValue);
        listContact.setEmail(elementEmailValue);
        listContact.setAddress(elementAddressValue);
        listContact.setDescription(elementDescriptionValue);

        boolean result = listContact.equals(contact);
        return result;
    }


}
