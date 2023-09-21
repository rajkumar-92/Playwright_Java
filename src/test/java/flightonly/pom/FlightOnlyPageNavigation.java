package flightonly.pom;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.ElementHandle.SelectOptionOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import factory.DriverFactory;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import junit.framework.Assert;

public class FlightOnlyPageNavigation {
	private Page page;

	private static final Logger logger = LoggerFactory.getLogger(FlightOnlyPageNavigation.class);
	
	private String totalPriceBeforeUpgrade;

	public FlightOnlyPageNavigation(Page page) {
		this.page = page;
	}

	private String ChangeLanguageButton = "[role='button']";

	public void navigationUrl() {
		//this.page.navigate("https://retailagents.tuiprjuat.be/retail/thirdpartyagent/login");
		this.page.navigate("https://www.tuifly.be/flight/");
	}

	public ElementHandle getCountrySwitcherLink() {
		return page.waitForSelector(".LanguageCountrySelector__languageText");
	}

	public void changeLanguageFrench() {
		ElementHandle countrySwitcherLink = getCountrySwitcherLink();
		countrySwitcherLink.click();
	}

	public void languageDropdown() {
		page.locator("div[class='SelectDropdown__large SelectDropdown__button']").click();
		page.locator("div[class='SelectDropdown__menuOpen']>ul>li:nth-child(3)").click();
		List<ElementHandle> ChangeLanguageButtonElements = page.locator(ChangeLanguageButton).elementHandles();
		ChangeLanguageButtonElements.get(0).click();
	}

	public void selectOutboundFlight() {
		List<ElementHandle> searchCardsOutbound = page
				.querySelectorAll("(//div[@class='FlightCardsList__flightCardsList'])[1]//button");
		searchCardsOutbound.get(0).click();
	}

	public void selectInboundFlight() {
		List<ElementHandle> searchCardsInbound = page
				.querySelectorAll("(//div[@class='FlightCardsList__flightCardsList'])[2]//button");
		searchCardsInbound.get(0).click();
	}

	public void ContinueToFlightButton() {
//		try {
//			// Pause the script execution for 5 seconds
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}	
		page.waitForSelector("button[class*='continueButton']", new Page.WaitForSelectorOptions().setTimeout(4000));
		ElementHandle continueButton = page.querySelector("button[class*='continueButton']");
		continueButton.click();
	}

	public void ContinueToExtrasButton() {
		ElementHandle continueButtonExtras = page.querySelector("[class*='continue'] a");
		continueButtonExtras.click();
	}

	public void ContinueToPassengerButton() {
		ElementHandle continueButtonPassenger = page.querySelector("[class*='ContinueButton'] button");
		continueButtonPassenger.click();
	}

	private String generateRandomString(int length) {
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(characters.length());
			sb.append(characters.charAt(randomIndex));
		}
		return sb.toString();
	}

	public void setPaxDetails() {
		String selector = "input[name^='paxInfoFormBean'][name$='.firstName'][aria-label='FIRST NAME']";
		page.waitForSelector(selector);
		List<ElementHandle> firstNameElements = page.querySelectorAll(selector);
		for (ElementHandle element : firstNameElements) {
			element.type(generateRandomString(8));
		}

		List<ElementHandle> surName = page
				.querySelectorAll("//input[contains(@name,'lastName') or contains(@name,'surName')]");
		System.out.println(surName);
		for (ElementHandle element : surName) {
			element.type(generateRandomString(8));
		}
		List<ElementHandle> selectElement = page.querySelectorAll("select[aria-label='Gender']");
		selectElement.get(0).selectOption("MALE");
		selectElement.get(1).selectOption("FEMALE");
		selectElement.get(2).selectOption("MALE");
		selectElement.get(3).selectOption("FEMALE");

		page.querySelector("[name='mobileNum']").fill("98489848");

		page.querySelector("[name='stayHomemobileNum']").fill("98485687");

		page.fill("[id='EMAILADDRESSADULT1']", generateRandomString(8) + ".s@sonata-software.com");
//		try {
//			// Pause the script execution for 5 seconds
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

	public void enterPaxDOB() {
		List<ElementHandle> inputDayElement = page.querySelectorAll("input[aria-label='day']");
		inputDayElement.get(0).type("10");
		inputDayElement.get(1).type("02");
		inputDayElement.get(2).type("10");
		inputDayElement.get(3).type("02");
//		try {
//			// Pause the script execution for 5 seconds
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		List<ElementHandle> inputMonthElement = page.querySelectorAll("input[aria-label='month']");
		inputMonthElement.get(0).type("09");
		inputMonthElement.get(1).type("12");
		inputMonthElement.get(2).type("09");
		inputMonthElement.get(3).type("12");
//		try {
//			// Pause the script execution for 5 seconds
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		List<ElementHandle> inputYearElement = page.querySelectorAll("input[aria-label='year']");
		inputYearElement.get(0).type("1996");
		inputYearElement.get(1).type("1997");
		inputYearElement.get(2).type("2018");
		inputYearElement.get(3).type("2022");
	}

	public void selectImportantInfoCheckbox() {
		ElementHandle selectInfoCheckbox = page
				.querySelector("[aria-label='important information'] [aria-label='checkbox']");
		selectInfoCheckbox.click();
	}

	public void selectContinueBooking() {
		ElementHandle continueButton = page.querySelector(".ContinueButton__continue button");
		continueButton.click();
	}

	public void fillThePassengerDetails() {
		setPaxDetails();
		enterPaxDOB();
		selectImportantInfoCheckbox();
	}

	public ElementHandle getBookRefId() {
		return page.querySelector("span[class*='referenceID']");
	}

	public void bookingReference() {
		waitForFive();
		page.waitForLoadState(LoadState.NETWORKIDLE);
		ElementHandle bookRefElement = getBookRefId();
		String bookRefId = bookRefElement.innerText();
		System.out.println("Booking reference number: " + bookRefId);
		logger.info(() -> "Booking reference number: " + bookRefId);
		page.close();
	}

	public void selectLuggage() {
		ElementHandle parentElement = page.querySelector("tui-fo-special-service-luggage");
		ElementHandle shadowRootHandle = parentElement.evaluateHandle("element => element.shadowRoot").asElement();

		List<ElementHandle> luggageSelection = shadowRootHandle
				.querySelectorAll("[class='button__27b5c6 luggageSelectBtn__af46ad']");
		luggageSelection.get(3).click();
		luggageSelection.get(8).click();
		luggageSelection.get(13).click();
	}

	public void verifyLuggageSelected() {
		ElementHandle parentElement = page.querySelector("tui-fo-special-service-luggage");
		ElementHandle shadowRootHandle = parentElement.evaluateHandle("element => element.shadowRoot").asElement();

		List<ElementHandle> luggageSelected = shadowRootHandle.querySelectorAll("[class='selectedTitle__9f1f86']");

		for (ElementHandle element : luggageSelected) {
			String textContent = element.getProperty("textContent").jsonValue().toString();
			System.out.println("Text content: " + textContent);
			Assert.assertEquals("Selected", textContent);
		}
	}

	public void getTotalPriceValue() {
		ElementHandle parentElement = page.querySelector("tui-fo-price-summary");
		ElementHandle shadowRootHandle = parentElement.evaluateHandle("element => element.shadowRoot").asElement();

		ElementHandle totalPrice = shadowRootHandle.querySelector("[class='price'] span");
		totalPriceBeforeUpgrade = totalPrice.getProperty("textContent").jsonValue().toString().replace("�", "")
				.replace(",", "");
		System.out.println("totalPriceBeforeUpgrade: " + totalPriceBeforeUpgrade);
	}

	public void verifyPriceUpdated() {
		ElementHandle parentElement = page.querySelector("tui-fo-price-summary");
		ElementHandle shadowRootHandle = parentElement.evaluateHandle("element => element.shadowRoot").asElement();
		waitForFive();
		page.waitForSelector("[class='price'] span", new Page.WaitForSelectorOptions().setTimeout(4000));
		ElementHandle totalPriceUpdated = shadowRootHandle.querySelector("[class='price'] span");
		String totalPriceAfterUpgrade = totalPriceUpdated.getProperty("textContent").jsonValue().toString()
				.replace("�", "").replace(",", "");
		System.out.println("totalPriceAfterUpgrade: " + totalPriceAfterUpgrade);

		double totalPriceAfter = Double.parseDouble(totalPriceAfterUpgrade);
		double totalPriceBefore = Double.parseDouble(totalPriceBeforeUpgrade);

		if (totalPriceAfter > totalPriceBefore) {
			System.out.println("totalPriceAfterUpgrade is greater than " + totalPriceBeforeUpgrade);
		} else {
			System.out.println("totalPriceAfterUpgrade is not greater than " + totalPriceBeforeUpgrade);
		}
	}

	public void selectPaxForInsurance() {
		ElementHandle selectPaxForInsurance = page.querySelector("[class='GetQuoteV2__selectAll']");
		selectPaxForInsurance.click();
		enterPaxDOB();
	}

	public void chooseInsurance() {
		ElementHandle chooseInsurance = page.querySelector(".GetQuoteV2__getQuote [role='button']");
		chooseInsurance.click();
		List<ElementHandle> chooseAccordion = page.querySelectorAll("[class*='AccordionToggle__accordionToggle']");
		chooseAccordion.get(2).click();	
		chooseInsurance.click();
		waitForFive();
		List<ElementHandle> selectInsurance = page.querySelectorAll("[class='PerPersonSelectList__checkbox']");
		selectInsurance.get(12).click();	
		selectInsurance.get(13).click();	
		selectInsurance.get(14).click();	
		selectInsurance.get(15).click();	
	}
	
	public void reviewInsurance() {
		page.querySelector("[class='buttons__button buttons__primary buttons__fill ReviewInsurancePricesButtons__button']").click();
		page.waitForSelector("[class='buttons__button buttons__primary buttons__fill buttons__fixed-pad Modal__applyButton ']", new Page.WaitForSelectorOptions().setTimeout(4000));
		page.querySelector("[class='buttons__button buttons__primary buttons__fill buttons__fixed-pad Modal__applyButton ']").click();
	}
	
	public void totalPriceadded() {
//		verifyPriceUpdated();
	}
	
	public void fillThePassengerDetailsWithInsurance() {
		page.waitForLoadState(LoadState.NETWORKIDLE);
		setPaxDetails();
		selectImportantInfoCheckbox();
	}
	
	public void waitForThree() {
		try {
			// Pause the script execution for 5 seconds
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void waitForFive() {
		try {
			// Pause the script execution for 5 seconds
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}