@ui @flightonly
Feature: Playwright with java scenarios for filght only End to End basic flows scenarios
  As a Western Region Customer
  I want to be able to do booking

  Scenario: Verifying each page and complete the booking
    Given a customer is on the TUI fly homepage
    #And they select to view content in English on the site
    And a customer selects data from search fields
    #And a customer made an outbound flight selection and return flight selection
    #When customer clicks on CONTINUE button on Search Results Page will be taken to the Flight Options page
    #Then they click on continue button in flight page
    #And they click on continue button from extras page
    #And they click on continue button in passenger details page
    #And they select any of the payment method and click on continue
    #And booking reference number should be displayed

  #Scenario: Should able to make booking by Baggage Upgrade on flight page
    #Given that a customer or agent is on the WR FO Flight Options page
    #And they select the luggage other than Okg
    #And Selected luggage should be displayed with the text
    #And Other luggage price also should be updated
    #When they click on continue button in flight page
    #Then they click on continue button from extras page
    #And they click on continue button in passenger details page
    #And they select any of the payment method and click on continue
    #And booking reference number should be displayed

  #Scenario: Customer should able to make the booking by adding the insurance
    #Given the customer is on the WR FO Extras page
    #When the Price Panel is before upgarding with the insurance
    #And they have selected who needed an insurance option
    #And they have selected an insurance option
    #Then the insurance should be added to the booking
    #And the selected insurance amount should get added to the total price
    #And they click on continue button in passenger details page after insurance selection
    #And they select any of the payment method and click on continue
    #And booking reference number should be displayed
