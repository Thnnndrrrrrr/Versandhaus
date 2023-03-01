package woche_04;


public class Customer {
	
	private static final String SPLIT_SIGN = ",";
	private int customerID;
	private String name;
	private String firstname;
	private String street;
	private String housenumber;
	private String zipcode;
	private String location;
	
	public Customer() {
	    
	}
	/**
	 * Creates a customer object
	 * @param csvString Expects a string with comma seperated values in the following order: customerID, name, firstname, street, housenumber, zipcode, location
	 *
	 */
	public Customer(String csvString) {

        this(csvString.split(SPLIT_SIGN));
    }
	
	public Customer(String[] customersAttributes) {
	    this(Integer.parseInt(customersAttributes[0]), customersAttributes[1], customersAttributes[2], customersAttributes[3], customersAttributes[4], customersAttributes[5], customersAttributes[6]);
	}
	   
	   
	public Customer(String name, String firstname, String street, String housenumber, String zipcode, String location) {
		this(-1, name, firstname, street, housenumber, zipcode, location);
	   
	}
	
	public Customer(int customerID, String name, String firstname, String street, String housenumber, String zipcode, String location) {
		this.customerID = customerID;
		this.name = name;
		this.firstname = firstname;
		this.street = street;
		this.housenumber = housenumber;
		this.zipcode = zipcode;
		this.location = location;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHousenumber() {
		return housenumber;
	}

	public void setHousenumber(String housenummer) {
		this.housenumber = housenummer;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCustomerID() {
		return this.customerID;
	}
	
	public void setCustomerID(int customerID) {
		this.customerID=customerID;
	}
	
	public String toString() {
		return "CustomerID: " + this.customerID +"\nFirstname: "+this.firstname +"\nName: " + 
				this.name +  "\nStreet: " + this.street +"\nHousenumber: "+this.housenumber + "\nZipcode: " + this.zipcode + "\nLocation: " + this.location;
	}
	/**
	 * @return returns a String with all instance variables seperated by a ","
	 */
	public String getCustomerForCSV() {
		return this.customerID + SPLIT_SIGN + this.name + SPLIT_SIGN + this.firstname + SPLIT_SIGN + this.street + SPLIT_SIGN + this.housenumber 
				+ SPLIT_SIGN + this.zipcode + SPLIT_SIGN + this.location;
	}
	
}
