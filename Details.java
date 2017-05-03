/*  Detail.java
 *  Used to put and get items from a hashmap
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.io.*;

public class Details{

    // HashMap gets the string symbol as the key
    // HashMAp gets a Value object as the value
    // Value object is contained security name and the initial price of each symbol 
    HashMap<String,Value> hm = new HashMap<>();
    
    BufferedReader br;
    static String symbol;
    private String csvFile;

    // constructor of the Details.java  class
    // get the csv file which is wanted to put to the hashmap
    public Details(String csvFile){

        this.csvFile = csvFile;   // get csv file

        String line;
        try{

            br = new BufferedReader(new FileReader(this.csvFile));

            while ((line = br.readLine()) != null) {     // read until null line

                String[] list = line.split(",");   // splitted by a comma

                Value v = new Value();

                v.setSecurityName(list[1]);       // set values to the Value object
                v.setPrice(list[2]);

                hm.put(list[0],v);       // give Symbol as the key
            }

        } catch (Exception e) {          // catch the exception

            e.printStackTrace();
        }
    }

    // check whether the user input symbol valid or invalid
    // if it is a valid symbol return 1
    // if it is invalid symbol return -1
    public int check(String input){

        try{

            Value val = hm.get(input);      // search the values for given key
            if(val == null)
                return -1;
            else
                return 1;
        }
        catch(Exception e){       // catch the exception and terminate the program
            return 0;
        }
    }

    // get current price of a symbol from the hashmap
    // return price as a String
    public String getPrice(String input) throws IOException {

        try{
            Value val = hm.get(input);
            symbol = input;
            return val.getPrice();
        }
        catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }

    // replace the price with the new bid
    public void resetPrice(String price) throws IOException{
        try{
            Value val = hm.get(symbol);
            val.setPrice(price);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

    // used to get data for the table 
    public String getRawData(String symbol,String type){

        Value val = hm.get(symbol);
        if(type == "SecurityName"){
            return val.getSecurityName();
        }
        else {
            return val.getPrice();
        }
    }
}