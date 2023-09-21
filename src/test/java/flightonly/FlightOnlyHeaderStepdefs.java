package flightonly;

import factory.DriverFactory;
import flightonly.pom.FlightOnlyPageNavigation;
import flightonly.pom.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FlightOnlyHeaderStepdefs {

	FlightOnlyPageNavigation foNavigation = new FlightOnlyPageNavigation(DriverFactory.getPage());

	HomePage homePage = new HomePage(DriverFactory.getPage());
	
	@Given("a customer is on the TUI fly homepage")
	public void a_customer_is_on_the_tui_fly_homepage() {
		foNavigation.navigationUrl();
		homePage.closePrivacyPopUp();
		homePage.loginToRetailThirdParty();
		homePage.loginButton();
	}

	@And("they select to view content in English on the site")
	public void they_select_to_view_content_in_english_on_the_site() {
		foNavigation.changeLanguageFrench();
		foNavigation.languageDropdown();
	}

	@And("a customer selects data from search fields")
	public void a_customer_selects_data_from_search_fields() {
		homePage.navigateToSearchResultPage();
	}

	@And("a customer made an outbound flight selection and return flight selection")
	public void a_customer_made_an_outbound_flight_selection_and_return_flight_selection() {
		foNavigation.selectOutboundFlight();
		foNavigation.selectInboundFlight();
	}
	
	@When("customer clicks on CONTINUE button on Search Results Page will be taken to the Flight Options page")
	public void customer_clicks_on_continue_button_on_search_results_page_will_be_taken_to_the_flight_options_page() {
		foNavigation.ContinueToFlightButton();
	}

	@And("they click on continue button in flight page")
	public void they_click_on_continue_button_in_flight_page() {
		foNavigation.ContinueToExtrasButton();
	}
	
	@And("they click on continue button from extras page")
	public void they_click_on_continue_button_from_extras_page() {
		foNavigation.ContinueToPassengerButton();
	}
	
	@And("they click on continue button in passenger details page")
	public void they_click_on_continue_button_in_passenger_details_page() {
		foNavigation.fillThePassengerDetails();
	}

	@And("they select any of the payment method and click on continue")
	public void they_select_any_of_the_payment_method_and_click_on_continue() {
		foNavigation.selectContinueBooking();
	}
	
	@And("booking reference number should be displayed")
	public void booking_reference_number_should_be_displayed() {
		foNavigation.bookingReference();
	}
	
	@Given("that a customer or agent is on the WR FO Flight Options page")
	public void that_a_customer_or_agent_is_on_the_wr_fo_flight_options_page() {
		homePage.navigateToFlightPage();
	}
	
	@And("they select the luggage other than Okg")
	public void they_select_the_luggage_other_than_okg() {
		foNavigation.getTotalPriceValue();
		foNavigation.selectLuggage();
	}

	@And("Selected luggage should be displayed with the text")
	public void selected_luggage_should_be_displayed_with_the_text() {
		foNavigation.verifyLuggageSelected();
	}
	
	@And("Other luggage price also should be updated")
	public void other_luggage_price_also_should_be_updated() {
		foNavigation.verifyPriceUpdated();
	}
	
	@Given("the customer is on the WR FO Extras page")
	public void the_customer_is_on_the_wr_fo_extras_page() {
		homePage.navigateToExtrasPage();
	}
	
	@When("the Price Panel is before upgarding with the insurance")
	public void the_price_panel_is_before_upgarding_with_the_insurance() {
		foNavigation.getTotalPriceValue();
	}
	
	@And("they have selected who needed an insurance option")
	public void they_have_selected_who_needed_an_insurance_option() {
		foNavigation.selectPaxForInsurance();
	}
	
	@And("they have selected an insurance option")
	public void they_have_selected_an_insurance_option() {
		foNavigation.chooseInsurance();
	}
	
	@Then("the insurance should be added to the booking")
	public void the_insurance_should_be_added_to_the_booking() {
		foNavigation.reviewInsurance();
	}
	
	@And("the selected insurance amount should get added to the total price")
	public void the_selected_insurance_amount_should_get_added_to_the_total_price() {
		foNavigation.totalPriceadded();
	}
	
	@And("they click on continue button in passenger details page after insurance selection")
	public void they_click_on_continue_button_in_passenger_details_page_after_insurance_selection() {
		foNavigation.fillThePassengerDetailsWithInsurance();
	}

}
