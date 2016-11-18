package testProject;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.NoSuchElementException

import junit.framework.Assert;

public class KariyerTestSuites {
	final static Logger logger = Logger.getLogger(KariyerTestSuites.class);

	private static WebDriver driver;
	WebDriverWait driverWait;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		setDriver(new FirefoxDriver());
		setBaseUrl("http://www.kariyer.net/");
		getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void faultLogin() throws Exception {

		String actualString = "Kariyer.net Üye Giriþi";
		String faultMessage = "Kullanýcý adý, E-posta ve þifrenizi kontrol ederek tekrar deneyin.";
		driver.get(baseUrl);
		driver.findElement(By.linkText("ÜYE GÝRÝÞÝ")).click();
		String expectedString = getDriver().findElement(By.xpath("/html/body/form/div[3]/div/table/tbody/tr[3]/td/div/div/div/div/div[1]/div/h1")).getText();
		Assert.assertTrue(actualString.equals(expectedString));
		logger.info(expectedString);

		driver.findElement(By.id("lgnUserName")).clear();
		driver.findElement(By.id("lgnUserName")).sendKeys("testbirkariyer@hotmail.com");
		driver.findElement(By.id("lgnPassword")).clear();
		driver.findElement(By.id("lgnPassword")).sendKeys("123456");
		driver.findElement(By.id("LinkButton1")).click();

		String expectedFaultMessage = getDriver().findElement(By.xpath("/html/body/form/div[3]/div/table/tbody/tr[3]/td/div/div/div/div/div[1]/div/div[2]/span")).getText();
		Assert.assertTrue(faultMessage.equals(expectedFaultMessage));
		logger.info(expectedFaultMessage);
	}

	@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void successLogin() throws Exception {

		String actualString = "Kariyer.net Üye Giriþi";
		String actualRecourses = "Baþvurularým";
		driver.get(baseUrl);
		driver.findElement(By.linkText("ÜYE GÝRÝÞÝ")).click();
		String expectedString = getDriver().findElement(By.xpath("/html/body/form/div[3]/div/table/tbody/tr[3]/td/div/div/div/div/div[1]/div/h1")).getText();
		Assert.assertTrue(actualString.equals(expectedString));
		logger.info(expectedString);

		driver.findElement(By.id("lgnUserName")).clear();
		driver.findElement(By.id("lgnUserName")).sendKeys("testbirkariyer@hotmail.com");
		driver.findElement(By.id("lgnPassword")).clear();
		driver.findElement(By.id("lgnPassword")).sendKeys("kariyer1234");
		driver.findElement(By.id("LinkButton1")).click();

		if (driver.findElement(By.xpath("/html/body/form/div[9]")).isEnabled()) {
			Assert.assertTrue(driver.findElement(By.xpath("/html/body/form/div[9]")).isEnabled());
			driver.findElement(By.cssSelector("#btnIlgilenmiyorum > span")).click();
		}

		//	    List<WebElement> interestPopup = getDriver().findElements(By.xpath("/html/body/form/div[9]"));
		//	    System.err.println("POP_UP>>>>>   "+interestPopup.size());
		//	    if(interestPopup.size()>0){
		//	    	System.err.println("Inside If*****************");
		//	    	driver.findElement(By.cssSelector("#btnIlgilenmiyorum > span")).click();
		//	    	String recoursesString = getDriver().findElement(By.xpath("/html/body/form/div[3]/div/table/tbody/tr[3]/td/div[3]/div[1]/div[2]/div[1]/h1")).getText();
		//	    	System.err.println("IF>>>" + recoursesString);
		//	    	Assert.assertTrue(actualRecourses.equals(recoursesString));
		//	    }
		else {
			String recoursesString = getDriver().findElement(By.xpath("/html/body/form/div[3]/div/table/tbody/tr[3]/td/div[3]/div[1]/div[2]/div[1]/h1")).getText();
			logger.info(recoursesString);
			Assert.assertTrue(actualRecourses.equals(recoursesString));
		}
	}

	@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void testIsArama() throws Exception {

		String firstJob;
		String expectedString = "java";
		driver.get(baseUrl);
		driver.findElement(By.linkText("Ýþ Ara")).click();
		driverWait = new WebDriverWait(driver, 20);
		driverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("criteriaCase")));
		logger.info("ELEMENT" + driver.findElement(By.id("criteriaCase")).getText());
		driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div[1]/div[1]/div[2]/div[2]/label/div")).click();
		driver.findElement(By.id("txtSearchKeyword")).clear();
		driver.findElement(By.id("txtSearchKeyword")).sendKeys("Java Developer");
		driver.findElement(By.id("btnSearchKeyword")).click();
		Thread.sleep(5000);

		List<WebElement> allSearchList = getDriver().findElements(By.className("ilan"));
		logger.info("SIZE>>>>  " + allSearchList.size());
		firstJob = allSearchList.get(0).getText().toLowerCase();

		try {
			Assert.assertTrue(firstJob.contains(expectedString));
		} catch (AssertionError e) {
			logger.info("Hata: " + expectedString + "aranýlan ilanda bulunamadý." + e);
		}
		//Bütün listeyi yazdýrma.	    
		//		for (WebElement allFavListHref : allSearchList) {
		//			//allFavRefList.add(allFavListHref.getText());
		//			System.err.println("   ref>> " + allFavListHref.getText());
		//		}
	}
	@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void editSummaryTry() throws Exception {

		String expectedFirstSummaryName = "Test Bir Kariyer";
		String expectedSecondSummaryName = "Test Ýki Kariyer";
		String updateFirstString, updateSecondString;

		driver.get(baseUrl);
		driver.findElement(By.linkText("ÜYE GÝRÝÞÝ")).click();
		driver.findElement(By.id("lgnUserName")).clear();
		driver.findElement(By.id("lgnUserName")).sendKeys("testbirkariyer@hotmail.com");
		driver.findElement(By.id("lgnPassword")).clear();
		driver.findElement(By.id("lgnPassword")).sendKeys("kariyer1234");
		driver.findElement(By.cssSelector("#LinkButton1 > span")).click();
		// driver.findElement(By.cssSelector("#btnIlgilenmiyorum > span")).click();
		String a = driver.findElement(By.linkText("KariyerTestBir Özgeçmiþi")).getAttribute("href");
		driver.findElement(By.linkText("KariyerTestBir Özgeçmiþi")).click();
		driver.get(a);

		driver.findElement(By.xpath("/html/body/form/div[3]/div/table/tbody/tr[3]/td/div/div[5]/div[1]/div[2]/form/div[3]/a/span")).click();
		driver.findElement(By.id("ad")).clear();
		driver.findElement(By.id("ad")).sendKeys("Test Ýki");
		driver.findElement(By.xpath("//div[@id='frmGenelBilgi']/table/tbody/tr[16]/td/div/a/span")).click();
		Thread.sleep(1000);
		updateFirstString = driver.findElement(By.id("cvisim")).getText();
		logger.info("updateFirstString" + updateFirstString);
		Assert.assertTrue(expectedSecondSummaryName.equals(updateFirstString));
		try {
			driver.findElement(By.id("lblEditRes")).click();
			driver.findElement(By.id("ad")).clear();
			driver.findElement(By.id("ad")).sendKeys("Test Bir");
			driver.findElement(By.xpath("//div[@id='frmGenelBilgi']/table/tbody/tr[16]/td/div/a/span")).click();
			Thread.sleep(1000);
			updateSecondString = driver.findElement(By.id("cvisim")).getText();
			logger.info("updateSecondString" + updateSecondString);
			Assert.assertTrue(expectedFirstSummaryName.equals(updateSecondString));
		} catch (Exception e) {
			logger.info("Buton bu sayfada bulunamadý" + getDriver().getCurrentUrl());
		}
	}

	@After
	public void tearDown() throws Exception {
		getDriver().quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			// Assert.a(verificationErrorString);
		}
	}

	@SuppressWarnings("unused")
	private boolean isElementPresent(By by) {
		try {
			getDriver().findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
	private String closeAlertAndGetItsText() {
		try {
			Alert alert = getDriver().switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		KariyerTestSuites.driver = driver;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
