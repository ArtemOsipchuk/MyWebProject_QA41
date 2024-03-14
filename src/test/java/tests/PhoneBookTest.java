package tests;

import config.BaseTest;
import helpers.*;
import io.qameta.allure.Allure;
import jdk.jfr.Description;
import model.Contact;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactsPage;
import pages.LoginPage;
import pages.MainPage;

import java.time.Duration;
import java.util.List;

public class PhoneBookTest  extends BaseTest{

    @Test(description = "this test verifies that the contact has been deleted")
    @Parameters ("browser")
    public void deleteContact(@Optional("chrome") String browser){
        Allure.description("this test verifies that the contact has been deleted");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("registered user authorisation");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        loginPage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword")).clickByLoginButton();
        Allure.step("adding a new contact");
        mainPage.openTopMenu(TopMenuItem.ADD.toString());
        AddPage addPage = new AddPage(getDriver());
        Contact newContact = new Contact(
                NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(10,5,3),
                AddressGenerator.generateAddress(),
                "new description");
        newContact.toString();
        addPage.fillFormAndSave(newContact);
        Allure.step("deleting a new contact");
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Allure.step("verifying that a contact has been deleted");
        Assert.assertTrue(contactsPage.deleteContact(newContact));

    }

    @Test(description = "The test checks the empty field warning declaration.")
    @Parameters("browser")
    public void loginOfAnExistingUserAddContact(@Optional("chrome") String browser) throws InterruptedException {

        Allure.description("User already exist. Login and add contact.!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        loginPage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword")).clickByLoginButton();
        Allure.step("Step 3");
        mainPage.openTopMenu(TopMenuItem.ADD.toString());
        AddPage addPage = new AddPage(getDriver());
        Contact newContact = new Contact(
                NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(10,5,3),
        AddressGenerator.generateAddress(),
        "new description");
        newContact.toString();
        addPage.fillFormAndSave(newContact);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.getDataFromContactList(newContact));
        TakeScreen.takeScreenshot("screen");
        Thread.sleep(3000);

    }

    @Test(description = "Positive registration")
    @Parameters("browser")
    public void positiveUserRegistration(@Optional("chrome") String browser) throws InterruptedException {
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        loginPage.fillEmailField(EmailGenerator.generateEmail(6,5,3))
                .fillPasswordField(PasswordStringGenerator.generateString()).clickByRegistrationButton();
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.messageIsDisplayed("No Contacts here!"));

        }

//    @Test
//    @Description("Successful Registration")
//    public void successfulRegistration(){
//        Allure.description("Successful Registration test.");
//        MainPage mainPage = new MainPage(getDriver());
//        Allure.step("Open LOGIN menu");
//        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
//        lpage.fillEmailField(EmailGenerator.generateEmail(5,5,3))
//                .fillPasswordField(PasswordStringGenerator.generateString());
//        Alert alert =  lpage.clickByRegistartionBUtton();
//        if (alert==null){
//            ContactsPage contactsPage = new ContactsPage(getDriver());
//            Assert.assertTrue( contactsPage. isElementPersist(getDriver()
//                    .findElement(By.xpath("//button[contains(text(),'Sign Out')]"))));
//        }else {
//            TakeScreen.takeScreenshot("Successful Registration");}
//    }

}
