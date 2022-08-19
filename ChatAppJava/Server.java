package ChatAppJava;
import java.io.*; // to import i/o classes (like BufferedReader, PrintWriter)
import java.net.*;  // to import networking classes (like ServerSocket, Socket)

class Server {


    ServerSocket server; 
    Socket socket; //using this socket to store the object from client Socket
    
    BufferedReader br; // to read
    PrintWriter out; //to write (need this to create i/o streams)
    //constructor..
    public Server(){
        try {
            server = new ServerSocket(7777); 
            //giving port 7777 to contact with perticlar program as we will use Port:7777 to run this server only

            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting...");

           socket=server.accept(); 
           //when server accepts the connection client will return the socket obj, 
           //so we are storing the socket obj
            
           br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           out = new PrintWriter(socket.getOutputStream());

           startReading(); //method to read
           startWriting(); //method to write


        } catch (Exception e) {
            e.printStackTrace(); //if one out of five methods in your code cause an exception, 
            //printStackTrace() will pinpoint the exact line in which the method raised the exception.
            
        }
    }


    //using the concept of multithreading in these two methods to send and read data simultaneously
    public void startReading(){
        //this thread is used to read data continuously

        Runnable r1 = ()->{ //using a lambda expression 

            System.out.println("Reader started..");

            while(true) //using while loop to read data continuously , so we assigned while true to run this loop infinite times
            {   
                try{
                String msg = br.readLine();
                if(msg.equals("exit"))
                {
                    System.out.println("Client terminated the chat");
                    break; // if client type exit it will stop the reader
                }

                System.out.println("Client : "+msg);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        //to start the thread
        new Thread(r1).start();
    }

    public void startWriting(){
        //this thread is used to take data from user and then send it to client

        Runnable r2 = ()->{
            while(true){
                try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in)); //using to  fetch the input from user
                    
                    String content = br1.readLine();

                    out.println(content);
                    out.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }


    public static void main(String args[]){
        System.out.println("This is server..going to start server");
        new Server(); //creating server object
    }
}
