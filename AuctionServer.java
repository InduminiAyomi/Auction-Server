

/*  E/13/057 - K.I.A. De Silva
 *  Lab 09: Auction Server
 */

// import packages
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AuctionServer implements Runnable{   

    public static final int BASE_PORT = 2000;   // define base port

    private static ServerSocket serverSocket; 
    private static int socketNumber; 

    private Socket connectionSocket; 
    static Details d;
    private String clientName = null;

    static FileWriter writer;
    static File file;

    // this constructor get socket number
    public AuctionServer(int socket) throws IOException { 
        serverSocket = new ServerSocket(socket); 
        socketNumber = socket; 
    }

    // this constructor get connection socket
    public AuctionServer(Socket socket) { 
        this.connectionSocket = socket; 
    }

    public void run(){

        synchronized(this){

            try{

                BufferedReader in = new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(this.connectionSocket.getOutputStream())); 

                int count=0;     // count number of inputs
                String line;  // used to read line by line of the csv file

                for(line = in.readLine(); line != null && !line.equals("quit"); line = in.readLine()) {
                   
                    if(count == 0){      // when count=0, clients first input (his name)
                        this.clientName = line;    
                        System.out.println(this.clientName + ": "+line);
                        out.println(line);
                        out.print("Enter symbol: ");
                        out.flush();  
                    }
                    else{
                        if(count%2 == 1){    // enter symbol of the security
                            System.out.println(this.clientName + ": "+line);   // print on sever window
                            // write on csv file
                            writer.append("\n");
                            writer.append(this.clientName);
                            writer.append(",");
                            writer.append(line);
                            writer.append(",");

                            if(d.check(line) == -1){
                                // write on csv file
                                writer.append("Invalid Symbol");
                                out.println("-1");  // print on client window
                                out.print("Enter Correct symbol: ");
                                count = 0;
                            }
                            else{        // show current prize of the symbol 
                                out.print("Current cost: ");
                                out.println(d.getPrice(line));
                                writer.append(d.getPrice(line));
                                writer.append(",");
                                out.print("Your bid: ");
                            }
                            out.flush(); 

                        } else if(count%2 == 0){   // enter user's bid
                            d.resetPrice(line);
                            out.print("Enter symbol: ");
                            System.out.println(this.clientName + ": "+line);
                            writer.append(line); 
                            writer.append(",");
                            out.flush();
                        }
                        writer.flush();
                    }
                    
                    count++;
                }
                
                writer.close();
                
            } catch(Exception e){  // catch exceptions
                System.out.println(e.toString());
            }

            try {       
                this.connectionSocket.close(); 
            } catch(IOException e) {    // catch IOExceptions
                 System.out.println(e.toString());
            }

        }

        
    }

    public void server_loop() throws IOException {

        while(true) { 
            Socket socket = serverSocket.accept();      
            Thread worker = new Thread(new AuctionServer(socket)); 
            worker.start();         
        }

    }

  
    public static void main(String[] args) throws Exception {

        // make a Details object and map all the initial values to a hashmap
        d = new Details("stocks.csv");   

        file =new File("Changes.csv");    // make csv file to track changes to the stock item
        try{
            writer = new FileWriter(file);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
		Display dis = new Display(d);
        writer.append("Name");  // track name
        writer.append(",");
        writer.append("Symbol");   // track symbol item
        writer.append(",");
        writer.append("Current Price");   // track current price
        writer.append(","); 
        writer.append("Bid Price");   // track bid
        writer.append("\n");
    
        AuctionServer server = new AuctionServer(BASE_PORT);
        server.server_loop();
    }

}
