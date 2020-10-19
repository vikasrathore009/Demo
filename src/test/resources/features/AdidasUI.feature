@UITest
Feature: To implement the Web automated checks over DEMO ONLINE SHOP

  Scenario: Customer navigation through product categories: Phones, Laptops and Monitors 
 Given User navigates to "URL" via "chrome" browser
 When User "Add to cart" a laptop "Sony vaio i5" from category "Laptops"
 And user navigates to "Home"
 And User "Add to cart" a laptop "Dell i7 8gb" from category "Laptops"
  And user "move" on "Cart"
  And user "Delete" the item "Dell i7 8gb" from cart
  And user fetches info for item "Sony vaio i5" from cart
   And user "click" on "Place Order"
   And user fills the place order form using following details
   |Name|Country|City|Card|Month|Year|
   |Name|Country|City|12345|12|2020|
   And user "click" on "Purchase"
   Then user fetches the purchase info
   And user verifies "laptopAmount"
   And user quits the browser
      