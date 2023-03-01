package woche_04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.Scanner;


/**
 *
 * @author Tim
 * @author Max
 * @author Leon
 * @author Cenk
 *
 * This class contains the methods to operate the marketplace application.
 * The user is able to Login using an existing Login ID or can create a new UserAccount and enter his data.
 * User data is saved in an array and lost upon closing the application.
 * After logging in, the user can choose between 4 products to purchase. These products are also saved in an array.
 * After choosing 1 product, the amount to buy can be determined by the user.
 * The application prints out an invoice for the order to the terminal.
 */
public class Marketplace {

	private Customer[] customerArray;
	private Customer loggedInCustomer;
	private Product[] productArray;
	private Product currentProduct;
	private Product boughtProduct;
	private double grossPrice;
	private double netPrice;
	private double vatRate;
	private int orderQuantity;
    private int productIDUserInput = 0;
    private boolean validProductSelected = false;
    private String stateMachine = "APPLICATION_START";

	/**
	 * starts Marketplace application
	 *
	 * 1. Creates Example Customers and Products and saves them to Arrays
	 * 2. User Login or Registration
	 * 3. Product Selection and Order Quantity
	 * 4. Generates an Invoice
	 */
	public void startMarketplace() {

		boolean isProgramActive = true;
		MarketPlaceUtils.createProducts(productArray);
		String userDecisionInput;

		java.util.Scanner userInputScanner = new java.util.Scanner(System.in);
		readCustomerDataFromLocalCSV(this);


		while (isProgramActive ) {

			switch (stateMachine) {
				case "APPLICATION_START":
					MarketPlaceUtils.showWelcomeScreen();
					stateMachine = "LOGIN_ROUTINE";
					break;
				case "LOGIN_ROUTINE":
					userDecisionInput = getUserInputString("Einloggen? Mit Enter bestätigen oder \"ESC\" zum Beenden", userInputScanner);
					if (userDecisionInput.equals("ESC")) {
                        isProgramActive = false;
                        System.out.println("Programm beendet");
					} else {
					    startLogin(userInputScanner);
					    stateMachine = "MAIN_MENU";
					}
					break;
				case "MAIN_MENU":
					userDecisionInput = getUserInputString("\nMit ENTER die Produkte aufrufen oder logout + ENTER zum Ausloggen, \"change\" zum Ändern der Kundendaten, \"ESC\" zum Beenden ", userInputScanner);
					if (userDecisionInput.equals("logout")) {
						loggedInCustomer = null;
						stateMachine = "APPLICATION_START";
						System.out.println("Sie wurden ausgeloggt!");
					} else if (userDecisionInput.equals("")) {
						stateMachine = "SHOPPING";
					} else if (userDecisionInput.equals("change")) {
						stateMachine = "CHANGE_CUSTOMER_DATA";
					} else if (userDecisionInput.equals("ESC")) {
						isProgramActive = false;
						System.out.println("Programm beendet");
					}
					break;
				case "CHANGE_CUSTOMER_DATA":
					changeCustomerData(userInputScanner);
					stateMachine = "MAIN_MENU";
					break;
				case "SHOPPING":
				       doShoppingRoutine(userInputScanner);
					break;
			}
		}
		userInputScanner.close();
		saveCustomerDataToLocalCSV();
		saveProductDataToLocalCSV();
	}




	private void doShoppingRoutine(Scanner userInputScanner) {
        productdataOutput();

        while (!validProductSelected) {
            try {
                productIDUserInput = Integer.parseInt(getUserInputString("\nWaehlen sie ein Produkt aus", userInputScanner));
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (isProductNumberInProductsArray(productIDUserInput)) {
                this.currentProduct = getCurrentProduct(productIDUserInput);
                productdataOutput(this.currentProduct);
                validProductSelected = true;
            }
        }

        orderQuantity = Integer.parseInt(
                getUserInputString("\nWie viel möchten Sie bestellen? Zahl eingeben und mit Enter bestätigen", userInputScanner));

        calculatePrice();

        getUserInputString("Mit ENTER jetzt kostenpflichtig bestellen", userInputScanner);
        this.boughtProduct=this.currentProduct;
        printInvoice();

        validProductSelected = false;
        stateMachine = "MAIN_MENU";
	}
	/**
	 * Checks if the file  CustomerData.csv exists in the programm directory
	 * When the file is found each line will be read. The method addCustomersToCustomerArray is called.
	 * A new customer object will be handed over to the method by calling constructor of customer
	 * with the read string in CSV format as an argument.
	 * When there is no such file the method createExampleCustomers is called
	 */
	private void readCustomerDataFromLocalCSV(Marketplace m) {
	    Path pathToCustomerData = Paths.get("CustomerData.csv");
	    File fileCustomerData = new File("CustomerData.csv");
	    if (fileCustomerData.isFile()) {
	        try (BufferedReader reader = Files.newBufferedReader(pathToCustomerData)) {


	            String readString = "";
	            while ((readString = reader.readLine()) != null) {
	                addCustomersToCustomerArray(new Customer(readString));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        MarketPlaceUtils.createExampleCustomers(this);
	    }
    }
	private void saveCustomerDataToLocalCSV() {

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("CustomerData.csv"))) {

			for (Customer customer : this.customerArray) {
				writer.write(customer.getCustomerForCSV());
				writer.newLine();
			}
			writer.flush();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

   private void saveProductDataToLocalCSV() {


        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("ProductData.csv"))) {

            for (Product product : this.productArray) {
                writer.write(product.getProductForCSV());
                writer.newLine();
            }
            writer.flush();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



	private void changeCustomerData(Scanner userInputScanner) {

		boolean isChangeDataActive=true;
		String userDecisionInput;
		String stateMachineCustomerData = "START";

		while(isChangeDataActive){
			switch(stateMachineCustomerData){
				case "START":
					System.out.println(this.loggedInCustomer);
					userDecisionInput = getUserInputString("Wollen Sie Ihre Daten ändern?\"ESC\" zum Abbrechen \"Adresse\" zum ändern der Adresse und \"Name\" zum ändern des Namens" , userInputScanner);
					if (userDecisionInput.equals("ESC")) {
						isChangeDataActive = false;
					} else if (userDecisionInput.equals("Adresse")) {
						stateMachineCustomerData = "CHANGE_ADDRESS";
					} else if (userDecisionInput.equals("Name")) {
						stateMachineCustomerData = "CHANGE_NAMES";
					}
					break;
				case "CHANGE_NAMES":
					this.loggedInCustomer.setName(getUserInputString("Nachname erneut festlegen: ", userInputScanner));
					this.loggedInCustomer.setFirstname(getUserInputString("Vorname erneut festlegen: ", userInputScanner));
					stateMachineCustomerData = "START";
					System.out.println("Name erfolgreich geändert.");
					break;
				case "CHANGE_ADDRESS":
					this.loggedInCustomer.setStreet(getUserInputString("Straße erneut festlegen: ", userInputScanner));
					this.loggedInCustomer.setHousenumber(getUserInputString("Hausnummer erneut festlegen: ", userInputScanner));
					this.loggedInCustomer.setZipcode(getUserInputString("Postleitzahl erneut festlegen: ", userInputScanner));
					this.loggedInCustomer.setLocation(getUserInputString("Stadt erneut festlegen: ", userInputScanner));
					stateMachineCustomerData = "START";
					System.out.println("Addresse erfolgreich geändert.");
					break;



			}


		}



	}
	/**
	 * Calculates the price including the vat rate, gross price and net price from the class variables.
	 * Proceeds to print them to the terminal.
	 */
	private void calculatePrice() {
		this.grossPrice = this.currentProduct.grossPrice(orderQuantity);
		this.netPrice = this.currentProduct.netPrice(this.grossPrice);
		this.vatRate = this.currentProduct.VATRate(this.grossPrice);

		System.out.println("Der Bruttopreis ist: " + this.grossPrice + "€");
		System.out.println("Der Nettopreis ist: " + this.netPrice + "€");
		System.out.println("Enthaltene Mehrwertsteuer: " + this.vatRate + "€");

	}
	/**
	 * Used for matching regex patterns in the user inputs
	 *
	 * @param stringToCheck
	 * @param pattern
	 * @param userInputScanner
	 * @return checked and correct input
	 */
	private String patternMatcher(String stringToCheck, Pattern pattern, Scanner userInputScanner) {
		while (!pattern.matcher(stringToCheck).matches()) {
			System.err.println("Bitte Eingabe prüfen und wiederholen.");
			stringToCheck = getUserInputString("", userInputScanner);
		}
		return stringToCheck;
	}




	/**
	 * Prints the @param to the console and waits for input which it returns
	 *
	 * @param inputInformation to print with the input prompt
	 * @return the userInput as String
	 */
	private String getUserInputString(String inputInformation, Scanner userInputScanner) {
		String userInput = "";
		try {
			System.out.println(inputInformation);
			userInput = userInputScanner.nextLine();
		} catch (NumberFormatException e) {
			System.out.println("Falsche Eingabe");
		}
		return userInput;
	}



	/**
	 * This method tests, if there is a product with a specific product number in productArray. It returns a boolean.
	 *
	 * @param productNumber Expects the Product Number you are looking for as an integer.
	 * @return	True: There is a product with the given number in productArray
	 */
	private boolean isProductNumberInProductsArray(int productNumber) {

		for (Product currentProduct : this.productArray) {
			if (currentProduct.getPos() == productNumber) {
				//this.boughtProduct = e;
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is used to get the product object with a certain ID. The ID is passed as an argument.
	 * @param currentProductIndex productID of the product object you want to get
	 * @return	Returns the product with the given ID. If no such ID exists, null is returned.
	 */
	private Product getCurrentProduct(int currentProductIndex) {
		for (Product currentProduct: this.productArray) {
			if (currentProduct.getPos() == currentProductIndex) {
				return currentProduct;
			}
		}
		return null;
	}
	/**
	 * Checks if a value is present in the array
	 *
	 * @param input to check
	 * @return if value is in the array: true
	 */
	private boolean isCustomerIDinCustomerArray(int input) {

		for (Customer customer : this.customerArray) {
			if (customer != null && customer.getCustomerID() == input) {
				this.loggedInCustomer = customer;
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds any amount of Customers to the array and expands it by creating a new array of the added sizes of
	 * customerArray and the varargs parameter. The content of the existing customerArray is saved to a
	 * temporary variable and copied to the new array starting at [0]. The new customers are added to the new array
	 * after that.
	 * This method also creates random id numbers for new customers and saves them to the array.
	 * @param newCustomersToAddToArray Expects an amount of 0 to n (varargs) customer objects that will be added to the customerArray
	 */
	void addCustomersToCustomerArray(Customer... newCustomersToAddToArray) {

		if (newCustomersToAddToArray != null) {
			int newCustomerNumber = 0;

			Customer[] oldCustomer;

			if(this.customerArray!=null) {
				oldCustomer = this.customerArray;
				this.customerArray = new Customer[customerArray.length + newCustomersToAddToArray.length];

				for (int i = 0; i < oldCustomer.length; i++) {
					this.customerArray[i] = oldCustomer[i];
				}
				for (int i = oldCustomer.length; i < this.customerArray.length; i++) {
				    if (newCustomersToAddToArray[i-oldCustomer.length].getCustomerID() == -1) {
				        while (isCustomerIDinCustomerArray(newCustomerNumber =MarketPlaceUtils.generateRandomNumber(4))) {
				        }
				        newCustomersToAddToArray[i - oldCustomer.length].setCustomerID(newCustomerNumber);
				    }
					this.customerArray[i] = newCustomersToAddToArray[i - oldCustomer.length];
				}
			}
			else {
				this.customerArray = newCustomersToAddToArray;
				for (int i = 0; i < newCustomersToAddToArray.length; i++) {
					while (isCustomerIDinCustomerArray(newCustomerNumber = MarketPlaceUtils.generateRandomNumber(4))) {
					}
					this.customerArray[i].setCustomerID(newCustomerNumber);
				}
			}

		}
	}



	/**
	 * Prints the Productdata to the console
	 */

	private void productdataOutput() {
		for (Product productData : this.productArray) {
				System.out.println(productData.getPos() + ". " + "Product Name: " + productData.getProductName() + "\tProduct Price: "
						+ productData.getProductPrice() + "€");
		}
	}

	/**
	 * Prints the Productdata to the console for the given product object
	 * @param product Product Object whose product parameters are supposed to be printed.
	 */

	private void productdataOutput(Product product) {

		System.out.println("Product Name: " + product.getProductName() + "\tProduct Price: " + product.getProductPrice() + "€");

	}



	/**
	 * Prints the reciept to the console using the product Object which is accessed by the class variable boughtProduct
	 * and the customer object which is accessed by the class variable loggedInCustomer
	 */
	private void printInvoice() {

		System.out.println(loggedInCustomer.getFirstname() + " " + loggedInCustomer.getName());
		System.out.println(loggedInCustomer.getStreet() + " " + loggedInCustomer.getHousenumber());
		System.out.println(loggedInCustomer.getZipcode() + " " + loggedInCustomer.getLocation() + "\n\n");
		System.out.printf("Vielen Dank für Ihren Einkauf %s %s\n", loggedInCustomer.getFirstname(), loggedInCustomer.getName());
		System.out.println("Folgende Positionen stellen wir in Rechnung:");
		System.out.println(
				"\n---------------------------------------------------------------------------------------------------------------------------");

		System.out.printf("\n%-10s %-20s %-50s %-20s %-15s", "Pos.", "Bezeichnung", "Menge", "Preis", "Gesamtpreis");
		String productPriceInEuro = this.boughtProduct.getProductPrice()+ "€";
		String grossPriceInEuro = this.grossPrice + "€";
		System.out.printf("\n%-10d %-20s %-50d %-20s %-15s", this.boughtProduct.getPos(),
				this.boughtProduct.getProductName(), orderQuantity, productPriceInEuro,
				grossPriceInEuro);
		System.out.printf("\n\n%s", this.boughtProduct.getProductDiscription());
		System.out.println(
				"\n\n---------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("\n%70s %-30.2f€", "Gesamt netto:", this.netPrice);
		System.out.printf("\n\n%70s %.0f%s %-30.2f€\n", "USt.: ", this.boughtProduct.getVATrate() * 100, "%",
				this.grossPrice - this.netPrice);

	}
	/**
	 * starts the login function and prompts the user inputs for login or registration
	 * @param userInputScanner
	 */
	private void startLogin(Scanner userInputScanner) {

		int customerNumberInput = 0;
		while (loggedInCustomer == null) {
			String input = getUserInputString("Einloggen (E) oder neu registrieren (R)", userInputScanner);
			if (input.equals("E")) { /*
										 * Hinweis: bei input=="E" vergleicht er nicht "E" mit dem Inhalt des Strings
										 * input, sondern mit der Referenz auf den Speicher/ der Speicheradresse - das
										 * verhält sich identisch für alle nicht primitiven Datentypen Frage: Warum
										 * funktioniert das bei case ...?
										 */
				try {
					customerNumberInput = Integer
							.parseInt(getUserInputString("Bitte geben Sie ihre Kundennummer ein: ", userInputScanner));
				} catch (NumberFormatException e) {
					System.out.println(e);
				}

				if (isCustomerIDinCustomerArray(customerNumberInput)) {
					System.out.print("Herzlich Willkommen: " + this.loggedInCustomer.getFirstname() + "\n\n\n");
				}

			} else if (input.equals("R")) {
					registerNewCustomer(userInputScanner);
			}
		}
	}
		/**
		 * Starts the registration process for a new user
		 * expands the array with the new user
		 * requests the inputs for the user data
		 * matches the inputs with the regex pattern
		 * saves them them to the user object
		 *
		 * @param userInputScanner
		 */
	private void registerNewCustomer(Scanner userInputScanner) {



		String newUserDataInput = "";

		System.out.print("Geben sie noch folgende Daten an\n");

		newUserDataInput = getUserInputString("Vorname: ", userInputScanner);
		Pattern pattern = Pattern.compile("[a-z]+\\s?[a-z]*\\s?[a-z]*\\s?[a-z]*", Pattern.CASE_INSENSITIVE);

		String firstName = patternMatcher(newUserDataInput, pattern, userInputScanner);

		newUserDataInput = getUserInputString("Nachname: ", userInputScanner);

		String lastName = patternMatcher(newUserDataInput, pattern, userInputScanner);

		pattern = Pattern.compile("[a-z]+\\s*[a-z]*\\s*[a-z]*\\s*[a-z]*\\s*[a-z]*\\s*[a-z]*", Pattern.CASE_INSENSITIVE);
		newUserDataInput = getUserInputString("Straße (Ohne Hausnummer): ", userInputScanner);
		String street = patternMatcher(newUserDataInput, pattern, userInputScanner);

		pattern = Pattern.compile("[0-9]+\\s*[a-z]*", Pattern.CASE_INSENSITIVE);
		newUserDataInput = getUserInputString("Hausnummer: ", userInputScanner);
		String houseNumber = patternMatcher(newUserDataInput, pattern, userInputScanner);

		pattern = Pattern.compile("[0-9]{5}", Pattern.CASE_INSENSITIVE);
		newUserDataInput = getUserInputString("PLZ: ", userInputScanner);
		String zipCode = patternMatcher(newUserDataInput, pattern, userInputScanner);

		pattern = Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE);
		newUserDataInput = getUserInputString("Ort: ", userInputScanner);

		String location = patternMatcher(newUserDataInput, pattern, userInputScanner);

		Customer newCustomer = new Customer(firstName, lastName, street, houseNumber, zipCode, location);
		addCustomersToCustomerArray(newCustomer);
		System.out.print("Für Sie wurde eine Kundennummer erstellt: " + newCustomer.getCustomerID() + "\n\n\n");
	}
}
