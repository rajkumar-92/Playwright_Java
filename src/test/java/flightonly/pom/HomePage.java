package flightonly.pom;

import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.Random;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import factory.DriverFactory;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;

public class HomePage {
	private Page page;

	private static final Logger logger = LoggerFactory.getLogger(HomePage.class);
	
	private String loginButtonLocators = "[name='submit-button'], [type='button']";

	private FlightOnlyPageNavigation foNavigation;
	
//	FlightOnlyPageNavigation foNavigation = new FlightOnlyPageNavigation(DriverFactory.getPage());
	
	public HomePage(Page page) {
			this.page = page;
			foNavigation = new FlightOnlyPageNavigation(page);
		}

		public void closePrivacyPopUp() {
			//page.waitForSelector("[id='cmCloseBanner']");
			//page.locator("[id='cmCloseBanner']").click();
			page.waitForSelector("[class=\"cmButtons\"] #cmCloseBanner");
			page.locator("[class=\"cmButtons\"] #cmCloseBanner").click();
		}

		public void loginToRetailThirdParty() {
			page.locator("[name='agentNumber'], [name='userName'], [name*='user'], #userNameInput").fill("605502");
			page.locator("[name='agentPassword'], [name='userPassword'], [name*='pass'], #passwordInput")
					.fill("Retail@1234");
			page.locator("[name='agentReference'], [name*='agent']").fill("AAA");
			;
		}

		public void loginButton() {
			Locator cookieButton1Locator = page.locator("#ensCloseBanner");
			if (isElementPresent("#ensCloseBanner")) {
				cookieButton1Locator.click();
			}
			Locator loginButtonLocator = page
					.locator("[name='submit-button'], .ThirdPartyLogin__loginButton, #submitButton");
			loginButtonLocator.click();
			List<ElementHandle> loginButtonElements = page.locator(loginButtonLocators).elementHandles();
		if (loginButtonElements.size() == 1) {
			assertFalse("LoginId and Password invalid", loginButtonLocator.isVisible());
		} else {
			logger.info(() -> "Agent is navigated to Retail TUI Home Page");
		}
	}

	public void waitForShadowRootSelector(Page page, String parentSelector, String shadowSelector) {
		String jsScript = "document.querySelector('" + parentSelector + "').shadowRoot.querySelector('" + shadowSelector
				+ "')";
		page.waitForSelector(parentSelector);
		page.evaluate(jsScript);
	}
	
	public void explicitWait() {
		try {
			// Pause the script execution for 5 seconds
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}

	public void selectDepartureAirport() {

		ElementHandle parentElement = page.querySelector("tui-flight-search-bar");
		ElementHandle shadowRootHandle = parentElement.evaluateHandle("element => element.shadowRoot").asElement();
//		explicitWait();
		waitForShadowRootSelector(page, "tui-flight-search-bar", "#searchField-airport-outbound");
		// Query elements within the shadow root
		ElementHandle departureAirport = shadowRootHandle.querySelector("#searchField-airport-outbound");
		departureAirport.click();
		
//		explicitWait();
		
		departureAirport.fill("BRU");
//		explicitWait();
		page.waitForSelector("[id='BRU']", new Page.WaitForSelectorOptions().setTimeout(4000));
		ElementHandle departureAirportselection = shadowRootHandle.querySelector("[id='BRU']");
		departureAirportselection.click();
	}

	public void selectArrivalAirport() {

		ElementHandle parentElement = page.querySelector("tui-flight-search-bar");
		ElementHandle shadowRootHandle = parentElement.evaluateHandle("element => element.shadowRoot").asElement();

		waitForShadowRootSelector(page, "tui-flight-search-bar", "#searchField-airport-inbound");

		// Query elements within the shadow root
		ElementHandle arrivalAirport = shadowRootHandle.querySelector("#searchField-airport-inbound");
		arrivalAirport.click();
//		explicitWait();
		arrivalAirport.type("AGA");
		page.waitForSelector("[id='allAirports'] a", new Page.WaitForSelectorOptions().setTimeout(4000));
		ElementHandle arrivalAirportSelection = shadowRootHandle.querySelector("[id='allAirports'] a");
		arrivalAirportSelection.click();
	}

	public void selectFromDate() {

		ElementHandle parentElement = page.querySelector("tui-flight-search-bar");
		ElementHandle shadowRootHandle = parentElement.evaluateHandle("element => element.shadowRoot").asElement();
//        explicitWait();
		// Query elements within the shadow root
		page.waitForSelector("#searchField-date-outbound", new Page.WaitForSelectorOptions().setTimeout(4000));
		ElementHandle departureDate = shadowRootHandle.querySelector("#searchbar div div:nth-child(4) i");
		departureDate.hover();
//		departureDate.click();
//		departureDate.click();
		departureDate.dblclick();
//		explicitWait();
		page.waitForSelector("#calendarItems-outbound:nth-child(3) [class='entry__6a711 day__6a711 available__6a711']", new Page.WaitForSelectorOptions().setTimeout(4000));
		List<ElementHandle> availableOutboundDates = shadowRootHandle.querySelectorAll(
				"#calendarItems-outbound:nth-child(3) [class='entry__6a711 day__6a711 available__6a711']");
		//if (availableOutboundDates.size() >= 3) {
		//	ElementHandle date = availableOutboundDates.get(1);
		//	date.scrollIntoViewIfNeeded();
		//	date.click();
		//}
		for (int i = 0; i < availableOutboundDates.size(); i++) {
			String actualDate = availableOutboundDates.get(i).textContent();
			if (actualDate.equals("2")) {
				String attribute = availableOutboundDates.get(i).getAttribute("class");
				// await expect(attribute).to.contain("available");
				if (attribute.contains("available")) {
					availableOutboundDates.get(i).click();
					break;
				}
				else {
					for (++i; i < availableOutboundDates.size(); i++) {
						attribute = availableOutboundDates.get(i).getAttribute("class");
						if (attribute.contains("available")) {
							availableOutboundDates.get(i).click();
							break;
						}
					}
					break;
				}
			}
		}

//		explicitWait();
		// Query elements within the shadow root
		page.waitForSelector("#searchField-date-inbound", new Page.WaitForSelectorOptions().setTimeout(4000));
		ElementHandle arrivalDate = shadowRootHandle.querySelector("#searchField-date-inbound");
		arrivalDate.click();
//		explicitWait();		
		page.waitForSelector("#calendarItems-inbound:nth-child(3) [class='entry__6a711 day__6a711 available__6a711']", new Page.WaitForSelectorOptions().setTimeout(4000));
		List<ElementHandle> availableInboundDates = shadowRootHandle.querySelectorAll(
				"#calendarItems-inbound:nth-child(3) [class='entry__6a711 day__6a711 available__6a711']");
		
			//ElementHandle dateSelect = availableInboundDates.get(3);
			//dateSelect.scrollIntoViewIfNeeded();
			//dateSelect.click();
			//explicitWait();
		for (int i = 0; i < availableInboundDates.size(); i++) {
			String actualDate = availableInboundDates.get(i).textContent();
			if (actualDate.equals("20")) {
				String attribute = availableInboundDates.get(i).getAttribute("class");
				// await expect(attribute).to.contain("available");
				if (attribute.contains("available")) {
					availableInboundDates.get(i).click();
					break;
				}
				else {
					for (++i; i < availableInboundDates.size(); i++) {
						attribute = availableInboundDates.get(i).getAttribute("class");
						if (attribute.contains("available")) {
							availableInboundDates.get(i).click();
							break;
						}
					}
					break;
				}
			}
		}
	}

	public void selectPassenger() {

		ElementHandle parentElement = page.querySelector("tui-flight-search-bar");
		ElementHandle shadowRootHandle = parentElement.evaluateHandle("element => element.shadowRoot").asElement();
//		explicitWait();
		page.waitForSelector("#searchField-pax", new Page.WaitForSelectorOptions().setTimeout(4000));
		waitForShadowRootSelector(page, "tui-flight-search-bar", "#searchField-pax");
		// Query elements within the shadow root
		ElementHandle passengerField = shadowRootHandle.querySelector("#searchField-pax");
		passengerField.click();
//		explicitWait();
		ElementHandle selectAdults = page.querySelector("#travelPartySelectAdults");
		selectAdults.selectOption("2");

		ElementHandle selectChildrens = page.querySelector("#travelPartySelectChildren");
		selectChildrens.selectOption("2");

		List<ElementHandle> selectChildAges = page.querySelectorAll("[class='child-age-sel']");
		selectChildAges.get(0).selectOption("0");
		selectChildAges.get(1).selectOption("4");
	}

	public void selectButton() {

		ElementHandle parentElement = page.querySelector("tui-flight-search-bar");
		ElementHandle shadowRootHandle = parentElement.evaluateHandle("element => element.shadowRoot").asElement();
//		explicitWait();
		waitForShadowRootSelector(page, "tui-flight-search-bar", "#searchButton");
		// Query elements within the shadow root
		ElementHandle searchButton = shadowRootHandle.querySelector("#searchButton");
		searchButton.click();
	}

	public void navigateToSearchResultPage() {
		selectPassenger();
		selectFromDate();
		selectDepartureAirport();
		selectArrivalAirport();
//		selectFromDate();
//		selectPassenger();
		selectButton();
		page.waitForLoadState(LoadState.NETWORKIDLE);
	}
	
	public void navigateToHomePage() {
		foNavigation.navigationUrl();
		closePrivacyPopUp();
		loginToRetailThirdParty();
		loginButton();
		foNavigation.changeLanguageFrench();
		foNavigation.languageDropdown();
	}
	
	public void navigateToSearchResultPages() {
		navigateToHomePage();
		selectPassenger();
		selectFromDate();
		selectDepartureAirport();
		selectArrivalAirport();
//		selectFromDate();
//		selectPassenger();
		selectButton();
		page.waitForLoadState(LoadState.NETWORKIDLE);
	}
	
	public void navigateToFlightPage() {
		navigateToSearchResultPages();
		foNavigation.selectOutboundFlight();
		foNavigation.selectInboundFlight();
		foNavigation.ContinueToFlightButton();
		page.waitForLoadState(LoadState.NETWORKIDLE);
	}
	
	public void navigateToExtrasPage() {
		navigateToFlightPage();
		foNavigation.ContinueToExtrasButton();
		page.waitForLoadState(LoadState.NETWORKIDLE);
	}
	
	private boolean isElementPresent(String string) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
