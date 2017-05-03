
/**
 *  Value.java
 *  Used to put the value as the hashmap
 *  Inclued security name and the price of the item
 */

public class Value {

    private String securityName;
    private String price;

    public void setSecurityName(String securityName){
        this.securityName = securityName;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getSecurityName() {
        return this.securityName;
    }

    public String getPrice() {
        return this.price;
    }

}