package woche_04;

import java.util.Random;

public class MarketPlaceUtils {
	private MarketPlaceUtils() {
	}

	/**
	 * shows the welcome screen by printing out a slogan on the terminal
	 */
	public static void showWelcomeScreen() {
		System.out.println("Willkommen bei UBAY-Großanzeigen \n");
	}
	/**
	 * Prints the Customerdata to the console
	 */
	public static void customerdataOutputDebug(Customer[] customerArray) {
		for (Customer customerData : customerArray) {
			if (customerData != null)
				System.out.println("Name: " + customerData.getName() + "\tCID: " + customerData.getCustomerID());
			}
		}
	/**
	 * This method generates a random number and returns it as an integer.
	 * The number of digits can be adjusted by using the parameter customerNumberLength.
	 * @param customerNumberLenght expects an integer that determines the number of digits of the generated number
	 */
	public static int generateRandomNumber(int customerNumberLenght) {
		Random ran = new Random();
		int multiplier = 1;
		for(int i = 1; i < customerNumberLenght; i++) {
			multiplier *= 10;
		}
		return ran.nextInt(9*multiplier)+multiplier;
	}
	/**
	 * Creates 3 example customers and saves them to the array customerArray
	 * by calling the method addCustomersToCustomerArray
	 */
	static void createExampleCustomers(Marketplace m) {

		Customer exampleCustomer1 = new Customer("Steidl", "Tim", "Schulenburgallee", "22", "38228", "Wolfsburg");
		Customer exampleCustomer2 = new Customer("Soffner", "Leon", "Küchenteichstraße", "5", "38442", "Wolfsburg");
		Customer exampleCustomer3 = new Customer("Trauer", "Maximilian", "Lichtwerkallee", "2", "38106", "Braunschweig");
		Customer exampleCustomer4 = new Customer("Deveci", "Cenk", "Schulenburgallee", "22", "38228", "Wolfsburg");
		m.addCustomersToCustomerArray(exampleCustomer1, exampleCustomer2, exampleCustomer3, exampleCustomer4);
	}
	//TODO move to utility
	/**
	 * Creates a new productArray with the size of 4.
	 * For each Array index, an example product is added by using the product constructor
	 */
	static void createProducts(Product[] productArray) {

		productArray = new Product[4];
		productArray[0] = new Product(6443931, "Kinder Maxi King 3er", 1.49,
				"Spezialität mit Milchcreme- (45 %) und Milchkaramellfüllung (11 %) in Waffel (4 %) umhüllt von Milchschokolade und Haselnuss.",
				0.19);
		productArray[1] = new Product(64, "Kladde", 13.63, "Auch gut zum Tischtennisspielen", 0.19);
		productArray[2] = new Product(3931, "DeLonghi Kaffeemaschine", 420.69, "Feinster Kaffee in Barista-Qualität",
				0.19);
		productArray[3] = new Product(64931, "PlayStation 5", 499.99,
				"CPU: Achtkern Zen 2 mit 3.5 Ghz Taktung GPU: 10.28 Teraflops; 36 CUs (RDNA 2) Arbeitsspeicher: 16GB GDDR6 Festplatte: 825GB SSD (verfügbar sind nur 667.2 GB) Erw. Speicher: SSD-Slot Laufwerk: 4K UHD Blu-Ray-Drive.",
				0.19);
	}

}