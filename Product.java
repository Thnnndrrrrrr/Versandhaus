package woche_04;

public class Product {
	
	private static final String SPLIT_SIGN = ",";
	
	private int productCounter;
    private int productNumber;
    private String productName;
    private double productPrice;
    private String productDescription;
    private double vatRate;
    private static int counter=1;
    
    
    public Product(String csvString) {

        this(csvString.split(SPLIT_SIGN));
    }
    
    public Product(String[] productsAttributes) {
        this(Integer.parseInt(productsAttributes[0]), productsAttributes[1], Double.parseDouble(productsAttributes[2]), productsAttributes[3], Double.parseDouble(productsAttributes[4]));
    }    
    
    public Product(int productNumber, String productName, double productPrice, String productDescription, double vatRate) {
    	this.productCounter=counter++;
    	this.productNumber=productNumber;
    	this.productName=productName;
    	this.productPrice=productPrice;
    	this.productDescription=productDescription;
    	this.vatRate=vatRate;
    }

    
	public int getPos() {
		return productCounter;
	}


	public void setPos(int pos) {
		this.productCounter = pos;
	}


	public double round(double value){
        return  Math.round(value*100.0)/100.0;
    } 
    
    public double grossPrice(int value){
        return round(value * this.productPrice);
    }

    public double netPrice(double netPrice){
        return  round(netPrice - ( this.vatRate * netPrice));
    }
   
    public double VATRate(double netPrice) {
    	return  round(netPrice * this.vatRate);
    }
    public int getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(int productnumber) {
		this.productNumber = productnumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productname) {
		this.productName = productname;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productprice) {
		this.productPrice = productprice;
	}

	public String getProductDiscription() {
		return productDescription;
	}

	public void setProductDiscription(String ProductDiscription) {
		this.productDescription = ProductDiscription;
	}
	
	public double getVATrate() {
		return this.vatRate;
	}
	
	public void setVATrate(double VATrate) {
		this.vatRate = VATrate;
	}
	
	public String getProductForCSV() {
		return this.productNumber + SPLIT_SIGN + this.productName + SPLIT_SIGN + this.productPrice + SPLIT_SIGN + this.productDescription 
				+ SPLIT_SIGN + this.vatRate;
	}


}
