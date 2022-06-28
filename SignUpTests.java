import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class SignUpTests {
        private WebDriver driver;

    @BeforeClass
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void navigate(){
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void zipCodeTest() throws InterruptedException {

        WebElement zipCodeInput = driver.findElement(By.name("zip_code"));
            zipCodeInput.sendKeys("12345");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
            continueButton.click();
        Assert.assertFalse(driver.findElement(By.name("zip_code")).isDisplayed(), "zip code shouldn't be displayed");
        Assert.assertTrue(driver.findElement(By.name("first_name")).isDisplayed(), "First name input should be displayed");
        Thread.sleep(3000);

     }
     @Test
    public void doNotEnterZipCodeTestNegative() throws InterruptedException { //когда мы ничего не вводим в поле 'zip code'

        WebElement zipCodeInput = driver.findElement(By.name("zip_code"));
        zipCodeInput.sendKeys("");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
         Assert.assertTrue(driver.findElement(By.name("zip_code")).isDisplayed(), "zip code should be displayed");
         Thread.sleep(3000);
        // Why did the test fail when I added such a check?
         //Assert.assertFalse(driver.findElement(By.name("last_name")).isDisplayed(), "Last name input shouldn't be displayed");
    }
@Test
    public void enterLettersZipCodeTestNegative() throws InterruptedException{   //когда мы вводим буквы в поле 'zip code'

        WebElement zipCodeInput = driver.findElement(By.name("zip_code"));
        zipCodeInput.sendKeys("asdfh");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
        Assert.assertTrue(driver.findElement(By.name("zip_code")).isDisplayed(), "zip code should be displayed");
    Thread.sleep(3000);
    }
@Test
    public void enterLettersAndNumbersZipCodeTestNegative() { //когда мы вводим буквы и цифры в поле 'zip code'

        WebElement zipCodeInput = driver.findElement(By.name("zip_code"));
        zipCodeInput.sendKeys("as25g");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
        Assert.assertTrue(driver.findElement(By.xpath("(//span[text() = 'Oops, error on page. ZIP code should have 5 digits'])")).isDisplayed(), "This message should appear:Oops, error on page. ZIP code should have 5 digits");

    }
@Test
    public void signUpTestPositive() { //регистрация, всё ОК

        WebElement zipCodeInput = driver.findElement(By.name("zip_code"));
        zipCodeInput.sendKeys("12345");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
        WebElement firstNameInput = driver.findElement(By.name("first_name"));
          firstNameInput.sendKeys("Leo");
        WebElement lastNameInput  = driver.findElement(By.name("last_name"));
          lastNameInput.sendKeys("Messi");
        WebElement emailInput  = driver.findElement(By.name("email"));
          emailInput.sendKeys("sergei.G@gmail.com");
        WebElement passwordInput = driver.findElement(By.cssSelector("[name='password1']"));
          passwordInput.sendKeys("88888");
        WebElement cofirmPasswordInput = driver.findElement(By.cssSelector("[name='password2']"));
          cofirmPasswordInput.sendKeys("88888");
        WebElement registerButton = driver.findElement(By.cssSelector("[value='Register']"));
          registerButton.click();
          Assert.assertTrue(driver.findElement(By.cssSelector("[class='confirmation_message']")).isDisplayed() , "This message should appear:Account is created!");


    }
@Test
    public void doNotFillFieldsSignUpTestNegative() { //Не заполнять поля и нажать кнопку‘Зарегистрироваться’.

        WebElement zipCodeInput = driver.findElement(By.name("zip_code"));
        zipCodeInput.sendKeys("12345");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
        WebElement firstNameInput = driver.findElement(By.name("first_name"));
        firstNameInput.sendKeys("");
        WebElement lastNameInput  = driver.findElement(By.name("last_name"));
        lastNameInput.sendKeys("");
        WebElement emailInput  = driver.findElement(By.name("email"));
        emailInput.sendKeys("");
        WebElement passwordInput = driver.findElement(By.cssSelector("[name='password1']"));
        passwordInput.sendKeys("");
        WebElement cofirmPasswordInput = driver.findElement(By.cssSelector("[name='password2']"));
        cofirmPasswordInput.sendKeys("");
        WebElement registerButton = driver.findElement(By.cssSelector("[value='Register']"));
        registerButton.click();
        Assert.assertTrue(driver.findElement(By.xpath("(//span[text()='Oops, error on page. Some of your fields have invalid data or email was previously used'])")).isDisplayed() , "This message should appear:Oops, error on page. Some of your fields have invalid data or email was previously used");


    }
@Test
    public void enterPasswordSignUpTestNegative() { //вводим в поля 'пароль' и 'подтверждение пароля' РАЗНЫЕ значения

        WebElement zipCodeInput = driver.findElement(By.name("zip_code"));
        zipCodeInput.sendKeys("12345");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
        WebElement firstNameInput = driver.findElement(By.name("first_name"));
        firstNameInput.sendKeys("Leo");
        WebElement lastNameInput  = driver.findElement(By.name("last_name"));
        lastNameInput.sendKeys("Messi");
        WebElement emailInput  = driver.findElement(By.name("email"));
        emailInput.sendKeys("sergei.G@gmail.com");
        WebElement passwordInput = driver.findElement(By.cssSelector("[name='password1']"));
        passwordInput.sendKeys("88888");
        WebElement cofirmPasswordInput = driver.findElement(By.cssSelector("[name='password2']"));
        cofirmPasswordInput.sendKeys("11111");
        WebElement registerButton = driver.findElement(By.cssSelector("[value='Register']"));
        registerButton.click();
        WebElement element = driver.findElement(By.cssSelector(".confirmation_message"));
        String actualResult = element.getText();
        String expectedResult = "Oops, error on page. ZIP code should have 5 digits";
        Assert.assertEquals(actualResult, expectedResult, "The test dropped!");
//fixed errors in SignUpTests's file
    }
}
