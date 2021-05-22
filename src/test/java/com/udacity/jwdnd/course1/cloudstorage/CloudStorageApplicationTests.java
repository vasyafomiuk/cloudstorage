package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private UserService userService;
    @Autowired
    private NotesService notesService;
    @Autowired
    private CredentialsService credentialsService;
    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(1)
    @DisplayName("Verify that an unauthorized user can only access the login and signup pages.")
    public void verifyUnauthorizedAccess() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(0)
    @DisplayName("Signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.")
    public void asignUpAndLogout() throws InterruptedException {
        String firstName = "Mark";
        String lastName = "Twen";
        String username = "mtwen";
        String password = "user1234567";

        User user = userService.getUser(username);
        if (user != null) {
            userService.delete(user);
        }

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        driver.findElement(By.id("inputFirstName")).sendKeys(firstName);
        driver.findElement(By.id("inputLastName")).sendKeys(lastName);
        driver.findElement(By.id("inputUsername")).sendKeys(username);
        driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);

        driver.findElement(By.linkText("Back to Login")).click();

        driver.findElement(By.id("inputUsername")).sendKeys(username);
        driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);

        Thread.sleep(2000);

        Assertions.assertEquals("Home", driver.getTitle());

        driver.findElement(By.xpath("//*[text()='Logout']")).click();

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(3)
    @DisplayName("Create a note, and verify it is displayed.")
    public void createNoteTest() throws InterruptedException {
        String firstName = "Mark";
        String lastName = "Twen";
        String username = "mtwen";
        String password = "user1234567";
        String noteTitle = "Selenium Notes";
        String noteDescription = "some random description";

        List<Note> noteList = notesService.findAllByNoteTitle(noteTitle);
        noteList.forEach(n -> notesService.delete(n.getNoteId()));

        User user = userService.getUser(username);
        driver.get("http://localhost:" + this.port + "/login");

        if (user == null) {
            driver.get("http://localhost:" + this.port + "/signup");
            Assertions.assertEquals("Sign Up", driver.getTitle());
            driver.findElement(By.id("inputFirstName")).sendKeys(firstName);
            driver.findElement(By.id("inputLastName")).sendKeys(lastName);
            driver.findElement(By.id("inputUsername")).sendKeys(username);
            driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);
            driver.findElement(By.linkText("Back to Login")).click();
        }

        driver.findElement(By.id("inputUsername")).sendKeys(username);
        driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);

        Thread.sleep(1000);
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("[onclick=\"showNoteModal()\"]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("note-title")).sendKeys(noteTitle);
        driver.findElement(By.id("note-description")).sendKeys(noteDescription);
        driver.findElement(By.xpath("//*[contains(@onclick,'noteSubmit')]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        List<WebElement> notesTitle = driver.findElements(By.xpath("//td[text()='" + noteTitle + "']"));
        List<WebElement> notesDescription = driver.findElements(By.xpath("//td[text()='" + noteDescription + "']"));

        Assertions.assertTrue(notesTitle.size() > 0);
        Assertions.assertTrue(notesDescription.size() > 0);
    }

    @Test
    @Order(4)
    @DisplayName("Create a note, edit and verify it is displayed.")
    public void editNoteTest() throws InterruptedException {
        String firstName = "Mark";
        String lastName = "Twen";
        String username = "mtwen";
        String password = "user1234567";
        String noteTitle = "Selenium Notes";
        String editedNoteTitle = "EDITED Selenium Note";
        String noteDescription = "some random description";

        List<Note> noteList = notesService.findAllByNoteTitle(noteTitle);
        noteList.forEach(n -> notesService.delete(n.getNoteId()));

        List<Note> editedNoteList = notesService.findAllByNoteTitle(editedNoteTitle);
        editedNoteList.forEach(n -> notesService.delete(n.getNoteId()));

        User user = userService.getUser(username);
        driver.get("http://localhost:" + this.port + "/login");

        if (user == null) {
            driver.get("http://localhost:" + this.port + "/signup");
            Assertions.assertEquals("Sign Up", driver.getTitle());
            driver.findElement(By.id("inputFirstName")).sendKeys(firstName);
            driver.findElement(By.id("inputLastName")).sendKeys(lastName);
            driver.findElement(By.id("inputUsername")).sendKeys(username);
            driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);
            driver.findElement(By.linkText("Back to Login")).click();
        }

        driver.findElement(By.id("inputUsername")).sendKeys(username);
        driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);

        Thread.sleep(1000);
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("[onclick=\"showNoteModal()\"]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("note-title")).sendKeys(noteTitle);
        driver.findElement(By.id("note-description")).sendKeys(noteDescription);
        driver.findElement(By.xpath("//*[contains(@onclick,'noteSubmit')]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//td[text()='" + noteTitle + "']/preceding-sibling::td//button[contains(text(),'Edit')]")).click();
        Thread.sleep(1000);

        driver.findElement(By.id("note-title")).clear();
        driver.findElement(By.id("note-title")).sendKeys(editedNoteTitle);
        driver.findElement(By.xpath("//*[contains(@onclick,'noteSubmit')]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        List<WebElement> notesTitle = driver.findElements(By.xpath("//td[text()='" + editedNoteTitle + "']"));
        List<WebElement> notesDescription = driver.findElements(By.xpath("//td[text()='" + noteDescription + "']"));
        Thread.sleep(1000);

        Assertions.assertTrue(notesTitle.size() > 0);
        Assertions.assertTrue(notesDescription.size() > 0);
    }

    @Test
    @Order(5)
    @DisplayName("Create a note, delete and verify it is not displayed.")
    public void deleteNoteTest() throws InterruptedException {
        String firstName = "Mark";
        String lastName = "Twen";
        String username = "mtwen";
        String password = "user1234567";
        String noteTitle = "Selenium Notes";
        String noteDescription = "some random description";

        List<Note> noteList = notesService.findAllByNoteTitle(noteTitle);
        noteList.forEach(n -> notesService.delete(n.getNoteId()));

        User user = userService.getUser(username);
        driver.get("http://localhost:" + this.port + "/login");

        if (user == null) {
            driver.get("http://localhost:" + this.port + "/signup");
            Assertions.assertEquals("Sign Up", driver.getTitle());
            driver.findElement(By.id("inputFirstName")).sendKeys(firstName);
            driver.findElement(By.id("inputLastName")).sendKeys(lastName);
            driver.findElement(By.id("inputUsername")).sendKeys(username);
            driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);
            driver.findElement(By.linkText("Back to Login")).click();
        }

        driver.findElement(By.id("inputUsername")).sendKeys(username);
        driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);

        Thread.sleep(1000);
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("[onclick=\"showNoteModal()\"]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("note-title")).sendKeys(noteTitle);
        driver.findElement(By.id("note-description")).sendKeys(noteDescription);
        driver.findElement(By.xpath("//*[contains(@onclick,'noteSubmit')]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//td[text()='" + noteTitle + "']/preceding-sibling::td//*[contains(text(),'Delete')]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        List<WebElement> notesTitle = driver.findElements(By.xpath("//td[text()='" + noteTitle + "']"));
        List<WebElement> notesDescription = driver.findElements(By.xpath("//td[text()='" + noteDescription + "']"));
        Thread.sleep(1000);

        Assertions.assertEquals(0, notesTitle.size());
        Assertions.assertEquals(0, notesDescription.size());
    }

    @Test
    @Order(6)
    @DisplayName("Create credentials, and verify it is displayed.")
    public void createCredentialsTest() throws InterruptedException {
        String firstName = "Mark";
        String lastName = "Twen";
        String username = "mtwen";
        String password = "user1234567";
        String urlCredentials = "https://google.com";
        String usernameCredentials = "maxpayne";
        String passwordCredentials = "testabx123";

        List<Credential> noteList = credentialsService.getCredentials(usernameCredentials);
        noteList.forEach(n -> credentialsService.delete(n.getCredentialId()));

        User user = userService.getUser(username);
        driver.get("http://localhost:" + this.port + "/login");

        if (user == null) {
            driver.get("http://localhost:" + this.port + "/signup");
            Assertions.assertEquals("Sign Up", driver.getTitle());
            driver.findElement(By.id("inputFirstName")).sendKeys(firstName);
            driver.findElement(By.id("inputLastName")).sendKeys(lastName);
            driver.findElement(By.id("inputUsername")).sendKeys(username);
            driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);
            driver.findElement(By.linkText("Back to Login")).click();
        }

        driver.findElement(By.id("inputUsername")).sendKeys(username);
        driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);

        Thread.sleep(1000);
        driver.findElement(By.id("nav-credentials-tab")).click();
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("[onclick=\"showCredentialModal()\"]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("credential-url")).sendKeys(urlCredentials);
        driver.findElement(By.id("credential-username")).sendKeys(usernameCredentials);
        driver.findElement(By.id("credential-password")).sendKeys(passwordCredentials);

        driver.findElement(By.xpath("//*[contains(@onclick,'credentialSubmit')]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("nav-credentials-tab")).click();
        Thread.sleep(1000);

        List<WebElement> credentialsUrl = driver.findElements(By.xpath("//td[text()='" + urlCredentials + "']"));
        List<WebElement> credentialsUsername = driver.findElements(By.xpath("//td[text()='" + usernameCredentials + "']"));

        Assertions.assertTrue(credentialsUrl.size() > 0);
        Assertions.assertTrue(credentialsUsername.size() > 0);
    }


    @Test
    @Order(7)
    @DisplayName("Edit credentials, and verify it is displayed.")
    public void editCredentialsTest() throws InterruptedException {
        String firstName = "Mark";
        String lastName = "Twen";
        String username = "mtwen";
        String password = "user1234567";
        String urlCredentials = "https://google.com";
        String urlCredentialsEdited = "https://facebook.com";
        String usernameCredentials = "maxpayne";
        String usernameCredentialsEdited = "maxpayne2";
        String passwordCredentials = "testabx1q";
        String passwordCredentialsEdited = "testabx123123";

        List<Credential> credentialList = credentialsService.getCredentials(usernameCredentials);
        credentialList.forEach(credential -> credentialsService.delete(credential.getCredentialId()));

        List<Credential> credentialListEdited = credentialsService.getCredentials(usernameCredentialsEdited);
        credentialListEdited.forEach(credential -> credentialsService.delete(credential.getCredentialId()));

        User user = userService.getUser(username);
        driver.get("http://localhost:" + this.port + "/login");

        if (user == null) {
            driver.get("http://localhost:" + this.port + "/signup");
            Assertions.assertEquals("Sign Up", driver.getTitle());
            driver.findElement(By.id("inputFirstName")).sendKeys(firstName);
            driver.findElement(By.id("inputLastName")).sendKeys(lastName);
            driver.findElement(By.id("inputUsername")).sendKeys(username);
            driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);
            driver.findElement(By.linkText("Back to Login")).click();
        }

        driver.findElement(By.id("inputUsername")).sendKeys(username);
        driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);

        Thread.sleep(1000);
        driver.findElement(By.id("nav-credentials-tab")).click();
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("[onclick=\"showCredentialModal()\"]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("credential-url")).sendKeys(urlCredentials);
        driver.findElement(By.id("credential-username")).sendKeys(usernameCredentials);
        driver.findElement(By.id("credential-password")).sendKeys(passwordCredentials);

        driver.findElement(By.xpath("//*[contains(@onclick,'credentialSubmit')]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("nav-credentials-tab")).click();
        Thread.sleep(1000);


        driver.findElement(By.id("nav-credentials-tab")).click();

        driver.findElement(By.xpath("//td[text()='" + urlCredentials + "']/preceding-sibling::td//button[contains(text(),'Edit')]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("credential-url")).clear();
        driver.findElement(By.id("credential-url")).sendKeys(urlCredentialsEdited);
        driver.findElement(By.id("credential-username")).clear();
        driver.findElement(By.id("credential-username")).sendKeys(usernameCredentialsEdited);
        driver.findElement(By.id("credential-password")).clear();
        driver.findElement(By.id("credential-password")).sendKeys(passwordCredentialsEdited);
        driver.findElement(By.xpath("//*[contains(@onclick,'credentialSubmit')]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("nav-credentials-tab")).click();
        Thread.sleep(1000);

        List<WebElement> credentialsUrl = driver.findElements(By.xpath("//td[text()='" + urlCredentialsEdited + "']"));
        List<WebElement> credentialsUsername = driver.findElements(By.xpath("//td[text()='" + usernameCredentialsEdited + "']"));

        Assertions.assertTrue(credentialsUrl.size() > 0);
        Assertions.assertTrue(credentialsUsername.size() > 0);

    }

    @Test
    @Order(8)
    @DisplayName("Delete existing set of credentials and verifies that the credentials are no longer displayed.")
    public void deleteCredentialsTest() throws InterruptedException {
        String firstName = "Mark";
        String lastName = "Twen";
        String username = "mtwen";
        String password = "user1234567";
        String urlCredentials = "https://google.com";
        String usernameCredentials = "maxpayne";
        String passwordCredentials = "testabx123";

        List<Credential> noteList = credentialsService.getCredentials(usernameCredentials);
        noteList.forEach(n -> credentialsService.delete(n.getCredentialId()));

        User user = userService.getUser(username);
        driver.get("http://localhost:" + this.port + "/login");

        if (user == null) {
            driver.get("http://localhost:" + this.port + "/signup");
            Assertions.assertEquals("Sign Up", driver.getTitle());
            driver.findElement(By.id("inputFirstName")).sendKeys(firstName);
            driver.findElement(By.id("inputLastName")).sendKeys(lastName);
            driver.findElement(By.id("inputUsername")).sendKeys(username);
            driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);
            driver.findElement(By.linkText("Back to Login")).click();
        }

        driver.findElement(By.id("inputUsername")).sendKeys(username);
        driver.findElement(By.id("inputPassword")).sendKeys(password, Keys.ENTER);

        Thread.sleep(1000);
        driver.findElement(By.id("nav-credentials-tab")).click();
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("[onclick=\"showCredentialModal()\"]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("credential-url")).sendKeys(urlCredentials);
        driver.findElement(By.id("credential-username")).sendKeys(usernameCredentials);
        driver.findElement(By.id("credential-password")).sendKeys(passwordCredentials);

        driver.findElement(By.xpath("//*[contains(@onclick,'credentialSubmit')]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("nav-credentials-tab")).click();
        Thread.sleep(1000);


        driver.findElement(By.xpath("//td[text()='" + urlCredentials + "']/preceding-sibling::td//*[contains(text(),'Delete')]")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("nav-credentials-tab")).click();
        Thread.sleep(1000);

        List<WebElement> urlCredentialsList = driver.findElements(By.xpath("//td[text()='" + urlCredentials + "']"));
        List<WebElement> usernameCredentialsList = driver.findElements(By.xpath("//td[text()='" + usernameCredentials + "']"));
        Thread.sleep(1000);

        Assertions.assertEquals(0, urlCredentialsList.size());
        Assertions.assertEquals(0, usernameCredentialsList.size());

    }


}
