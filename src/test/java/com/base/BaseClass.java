package com.base;

import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * 
 * @author ELCOT
 *@see Used to maintain all resuable methods
 *@Date 19-01-2023
 */
public class BaseClass {
	public static WebDriver driver;
	
	RequestSpecification reqSpec;
	Response response;
	
	public void addHeader(String key,String value) {
		
		 reqSpec = RestAssured.given().header(key,value);
	}
	
	public void addHeaders(Headers headers) {

		 reqSpec = RestAssured.given().headers(headers);
	}
	
	public void addBasicAuth(String userName,String password) {

		reqSpec=reqSpec.auth().preemptive().basic(userName, password);
		
	}
	
	public void addQueryParam(String key,String value) {

		reqSpec =reqSpec.queryParam(key, value);
	}
	
	public void addPathParam(String key,String value) {

		reqSpec =reqSpec.pathParam(key, value);
	}
	
	public void addBody(String body) {

		reqSpec =reqSpec.body(body);
		
	}
	
	public void addBody(Object body) {

		reqSpec =reqSpec.body(body);
		
	}
	
	
	public Response requestType(String type,String endPoint) {

		switch (type) {
		case "GET":
			 response = reqSpec.log().all().get(endPoint);
			
			break;
		case "POST":
			 response = reqSpec.log().all().post(endPoint);
			
			break;
		case "PUT":
			 response = reqSpec.log().all().put(endPoint);
			
			break;
		case "DELETE":
			 response = reqSpec.log().all().delete(endPoint);
			
			break;

		default:
			break;
		}
		return response;
	}
	
	public int getResponseCode(Response response) {

		int statusCode = response.getStatusCode();
		return statusCode;
		
		
	}
	
	public String getResponseBodyAsString(Response response) {

		String asString = response.asString();
		return asString;
	}
	
	public String getResponseBodyAsPrettyString(Response response) {

		String asPrettyString = response.asPrettyString();
		return asPrettyString;
	}

	
	/**
	 * 
	 * @param sheetname
	 * @param rownum
	 * @param cellnum
	 * @return String
	 * @throws IOException
	 * @see Used to read the value from excel
	 */

	public String getCellValue(String sheetname, int rownum, int cellnum) throws IOException {

		String res = "";
		File file = new File("C:\\Users\\ELCOT\\eclipse-workspace\\FrameWorkClass\\excel\\AdactinHotel.xlsx");

		FileInputStream stream = new FileInputStream(file);

		Workbook workbook = new XSSFWorkbook(stream);

		Sheet sheet = workbook.getSheet(sheetname);

		Row row = sheet.getRow(rownum);

		Cell cell = row.getCell(cellnum);

		CellType type = cell.getCellType();

		switch (type) {
		case STRING:
			res = cell.getStringCellValue();

			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				Date dateCellValue = cell.getDateCellValue();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				res = dateFormat.format(dateCellValue);

			} else {
				double numericCellValue = cell.getNumericCellValue();
				long check = Math.round(numericCellValue);
				if (check == numericCellValue) {
					res = String.valueOf(numericCellValue);
				} else {
					res = String.valueOf(check);
				}

			}

			break;

		default:
			break;
		}
		return res;

	}

	/**
	 * 
	 * @returnbyte[]
	 * @see Used to take screen shot
	 */
	public byte[] screenShot() {

		TakesScreenshot screenshot=(TakesScreenshot) driver;
		
		byte[] bs = screenshot.getScreenshotAs(OutputType.BYTES);
		return bs;
		
		
	}
	
	
	/**
	 * 
	 * @param sheetname
	 * @param rownum
	 * @param cellnum
	 * @param oldData
	 * @param newData
	 * @throws IOException
	 * @see Used to update the value in excel sheet
	 */

	public void updateCellData(String sheetname, int rownum, int cellnum, String oldData, String newData)
			throws IOException {

		File file = new File("C:\\Users\\ELCOT\\eclipse-workspace\\FrameWorkClass\\excel\\Adactin.xlsx");

		FileInputStream stream = new FileInputStream(file);

		Workbook workbook = new XSSFWorkbook(stream);

		Sheet sheet = workbook.getSheet(sheetname);

		Row row = sheet.getRow(rownum);

		Cell cell = row.getCell(cellnum);

		String value = cell.getStringCellValue();

		if (value.equals(oldData)) {

			cell.setCellValue(newData);
		}
		FileOutputStream out = new FileOutputStream(file);

		workbook.write(out);

	}

	/**
	 * 
	 * @param sheetname
	 * @param rownum
	 * @param cellnum
	 * @param data
	 * @throws IOException
	 * @see Used to write the excel sheet
	 */
	public void writeCellData(String sheetname, int rownum, int cellnum, String data) throws IOException {

		File file = new File("C:\\Users\\ELCOT\\eclipse-workspace\\FrameWorkClass\\excel\\AdactinHotel.xlsx");

		FileInputStream stream = new FileInputStream(file);

		Workbook workbook = new XSSFWorkbook(stream);

		Sheet sheet = workbook.getSheet(sheetname);

		Row row = sheet.getRow(rownum);

		Cell createCell = row.createCell(cellnum);

		createCell.setCellValue(data);

		FileOutputStream out = new FileOutputStream(file);

		workbook.write(out);

	}

	/**
	 * 
	 * @param browserType
	 * @see Used to launch the browser
	 */
	public static void getDriver(String browserType) {

		switch (browserType) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

			break;
		case "ie":
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();

			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();

			break;

		default:
			break;
		}

	}
	
	/**
	 * 
	 * @return String
	 * @see Used to get  project path
	 */
	
	public static String getProjectPath() {
		
		String path = System.getProperty("user.dir");
		return path;

		
	}
	
	/**
	 * 
	 * @param key
	 * @return String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @see Used to read the value from property file
	 */
	public static String  getPropertyFileValue(String key) throws FileNotFoundException, IOException {

		Properties properties=new Properties();
		
		properties.load(new FileInputStream(getProjectPath()+"\\config\\config.properties"));
		
		Object object = properties.get(key);
		
		String value=(String) object;
		return value;
	}


	/**
	 * 
	 * @param url
	 * @see Used to launch the url
	 */
	public static void enterUrl(String url) {

		driver.get(url);
	}

	/**
	 * @see Used to maximize the window
	 */
	public static void maximizeWindow() {

		driver.manage().window().maximize();
	}

	/**
	 * 
	 * @param element
	 * @param text
	 * @see pass the parameters in textbox
	 */
	public void elementSendkeys(WebElement element, String text) {

		element.sendKeys(text);
	}
	
	/**
	 * 
	 * @param element
	 * @see Used to click the particular element
	 */

	public void elementClick(WebElement element) {

		element.click();
	}

	/**
	 * @see Used to accept the alert
	 */
	public void acceptInAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	/**
	 * @see Used to cancel the alert
	 */
	public void dissmissInAlert() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();

	}

	/**
	 * 
	 * @param element
	 * @return String
	 * @see get the text from webpage
	 */
	public String elementGetText(WebElement element) {

		String text = element.getText();
		return text.trim();
	}

	/**
	 * 
	 * @param element
	 * @return String
	 * @see get the value from webpage
	 */
	public String elementGetAttribute(WebElement element) {

		String attribute = element.getAttribute("value");
		return attribute;

	}
	/**
	 * @see Used to close the  window
	 */

	public static void quitWindow() {

		driver.quit();
	}
	
	/**
	 * @see Used to close the current window
	 */

	public void closeCurrentWindow() {
		driver.close();
	}
	/**
	 * 
	 * @return String
	 * @see get the title from web page
	 */

	public String getApplicationTittle() {

		String title = driver.getTitle();
		return title;
	}

	/**
	 * 
	 * @return String
	 * @see Used to get the current url
	 */
	public String getCurrentUrl() {

		String currentUrl = driver.getCurrentUrl();
		return currentUrl;
	}

	/**
	 * 
	 * @param element
	 * @param text
	 * @see select the options from dropdown using text
	 */
	public void selectOptionByText(WebElement element, String text) {

		Select select = new Select(element);
		select.selectByVisibleText(text);
	}
	
	/**
	 * 
	 * @param element
	 * @param attributevalue
	 * @see select the options from dropdown using value
	 */

	public void selectOptionByAttribute(WebElement element, String attributevalue) {

		Select select = new Select(element);
		select.selectByValue(attributevalue);
	}
	
	/**
	 * 
	 * @param element
	 * @param index
	 * @see select the options from dropdown using index
	 */

	public void selectOptionByIndex(WebElement element, int index) {

		Select select = new Select(element);
		select.selectByIndex(index);
	}
	
	/**
	 * 
	 * @param element
	 * @param text
	 * @see pass the parameters in text box sing javascipt excecutor
	 */

	public void sendkeysByJs(WebElement element, String text) {

		JavascriptExecutor jk = (JavascriptExecutor) driver;

		jk.executeScript("arguments[0].setAttribute('value','" + text + "')", element);
	}
	
	/**
	 * 
	 * @param element
	 * @see click the particular webelment using javascript excecutor
	 */

	public void clickByJs(WebElement element) {

		JavascriptExecutor jk = (JavascriptExecutor) driver;

		jk.executeScript("arguments[0].click())", element);
	}

	/**
	 * @see Used to switch the child window
	 */
	public void switchToChildWindow() {

		Set<String> handles = driver.getWindowHandles();

		for (String childwindow : handles) {
			driver.switchTo().window(childwindow);
		}
	}

	/*
	 * @see Used switch to frame by using index
	 */
	public void switchFrameByIndex(int index) {

		driver.switchTo().frame(index);
	}

	/**
	 * 
	 * @param frameElement
	 * @see Used switch to frame by using id
	 */
	public void switchToFrameById(WebElement frameElement) {

		driver.switchTo().frame(frameElement);
	}

	/**
	 * 
	 * @param text
	 * @return WebElement
	 * @see Used to find the locator by using id
	 */
	public WebElement findElementById(String text) {

		WebElement element = driver.findElement(By.id(text));
		return element;
	}

	/**
	 * 
	 * @param text
	 * @return WebElement
	 * @see Used to find the locator by using name
	 */
	public WebElement findElementByName(String text) {

		WebElement element = driver.findElement(By.name(text));
		return element;
	}
/**
 * 
 * @param text
 * @return WebElement
 * @see Used to find the locator by using classname
 */
	public WebElement findElementByClassName(String text) {

		WebElement element = driver.findElement(By.className(text));
		return element;
	}
	
	/**
	 * 
	 * @param xpathvalue
	 * @return WebElement
	 * @see Used to find the locator by using xpath
	 */

	public WebElement findElementByXpath(String xpathvalue) {

		WebElement element = driver.findElement(By.xpath(xpathvalue));
		return element;
	}
	
	/**
	 * 
	 * @param element
	 * @see Used to get all options in dropdown
	 */

	public void getOptionsByGetText(WebElement element) {

		Select select = new Select(element);

		List<WebElement> options = select.getOptions();

		for (WebElement x : options) {
			String text = x.getText();
		}
	}
	
	/**
	 * 
	 * @param element
	 * @param text
	 * @see Used to get options in dropdown by using attribute value
	 */

	public void getoptionsByAttributeValue(WebElement element, String text) {

		Select select = new Select(element);

		List<WebElement> options = select.getOptions();

		for (WebElement x : options) {
			String attribute = x.getAttribute(text);
		}
	}
	
	/**
	 * 
	 * @param element
	 * @return WebElement
	 * @see Used to select the first option in dropdown
	 */

	public WebElement firstSelectedOption(WebElement element) {

		Select select = new Select(element);
		WebElement firstSelectedOption = select.getFirstSelectedOption();
		return firstSelectedOption;
	}
	
	/**
	 * 
	 * @param element
	 * @return boolean
	 * @see Used to check the multiple selection in checkboxs
	 */

	public boolean checkMultipleSelection(WebElement element) {

		Select select = new Select(element);
		boolean multiple = select.isMultiple();
		return multiple;
	}
	
	/**
	 *@see maintains the implicitywait in seconds
	 */

	public static Timeouts implicityWait(long time) {

		Timeouts implicitlyWait = driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
		return implicitlyWait;
	}

	/**
	 * 
	 * @param time
	 * @param text
	 * @return WebElement
	 * @see maintains the explicitywait in seconds
	 */
	public WebElement explicityWait(long time, String text) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));

		WebElement until = wait.until(ExpectedConditions.elementToBeClickable(By.id(text)));
		return until;
	}
	
	/**
	 * 
	 * @param element
	 * @param index
	 * @see Deselect the option by dropdown using index
	 */

	public void deselectByIndex(WebElement element, int index) {

		Select select = new Select(element);

		select.deselectByIndex(index);
	}

	/**
	 * 
	 * @param element
	 * @param attributevalue
	 * @see Deseleect the option by dropdown using attribute value
	 */
	public void deselectByAttribute(WebElement element, String attributevalue) {

		Select select = new Select(element);

		select.deselectByValue(attributevalue);
	}

	/**
	 * 
	 * @param element
	 * @param text
	 * @see Deselect the option by dropdown using text
	 */
	public void DeselectByText(WebElement element, String text) {

		Select select = new Select(element);

		select.deselectByVisibleText(text);
	}
/**
 * 
 * @param element
 * @see Deselect the all option by dropdown using desele
 */
	public void deselectByAll(WebElement element) {

		Select select = new Select(element);

		select.deselectAll();
	}
	
	/**
	 * 
	 * @return String
	 * @see switch to parent window
	 */

	public String getTheParentWindow() {

		String windowHandle = driver.getWindowHandle();
		return windowHandle;
	}
	
	/**
	 * 
	 * @return Set<String>
	 * @see Used to get all windows 
	 */

	public Set<String> getAllWindows() {

		Set<String> windowHandles = driver.getWindowHandles();
		return windowHandles;
	}

	/**
	 * 
	 * @param element
	 * @see clear the text from text box
	 */
	public void clearTheText(WebElement element) {

		element.clear();
	}

	/**
	 * 
	 * @return File
	 * @see Used to take the screenshot
	 */
	public File takesScreenShot() {

		TakesScreenshot tk = (TakesScreenshot) driver;
		File screenshotAs = tk.getScreenshotAs(OutputType.FILE);
		return screenshotAs;
	}

	/**
	 * 
	 * @param element
	 * @return Actions
	 * @see Used to mouse over actions
	 */
	public Actions moveToElement(WebElement element) {

		Actions actions = new Actions(driver);
		Actions moveToElement = actions.moveToElement(element);
		return moveToElement;

	}

	/**
	 * 
	 * @param sourceElement
	 * @param destiElement
	 * @return String
	 * @see Used to move element one place to another place
	 */
	public Actions dragAndDrop(WebElement sourceElement, WebElement destiElement) {

		Actions actions = new Actions(driver);
		Actions dragAndDrop = actions.dragAndDrop(sourceElement, destiElement);
		return dragAndDrop;
	}

	/**
	 * 
	 * @param element
	 * @return Actions
	 * @see Used to rightclick the element
	 */
	public Actions contextClick(WebElement element) {

		Actions actions = new Actions(driver);
		Actions contextClick = actions.contextClick(element);
		return contextClick;
	}
	
/**
 * 
 * @param element
 * @return Actions 
 * @see Used to double click the elemeent
 */
	public Actions dooubleClick(WebElement element) {
		Actions actions = new Actions(driver);
		Actions doubleClick = actions.doubleClick(element);
		return doubleClick;
	}

	/**
	 * 
	 * @param element
	 * @param text
	 * @see Used to insert the values intextbox and procced
	 */
	public void insertValueAndEnter(WebElement element, String text) {

		element.sendKeys(text + Keys.ENTER);
	}

	/**
	 * @see Used to navigate forward page
	 */
	public void moveToForwardPage() {

		driver.navigate().forward();
	}

	/**
	 * @see Used to navigate back page
	 */

	public void moveToBack() {

		driver.navigate().back();
	}
	

	/**
	 * @see Used to navigate refresh the page
	 */

	public void pageToRefresh() {

		driver.navigate().refresh();
	}

	/**
	 * 
	 * @param element
	 * @see Used to scroll down the webpage using javascipt excecutor
	 */
	public void scrollDownByJs(WebElement element) {

		JavascriptExecutor jk = (JavascriptExecutor) driver;

		jk.executeScript("arguments[0].scrollIntoView(true)", element);

	}
	/**
	 * 
	 * @param element
	 * @see Used to scroll up the webpage using javascipt excecutor
	 */
	public void scrollupByJs(WebElement element) {

		JavascriptExecutor jk = (JavascriptExecutor) driver;

		jk.executeScript("arguments[0].scrollIntoView(false)", element);
	}

}
