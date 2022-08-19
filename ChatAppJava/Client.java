package ChatAppJava;
import java.net.*;
import java.io.*;

public class Client {

    Socket socket;

    BufferedReader br; // to read
    PrintWriter out; //to write (need this to create i/o streams)
    public Client()
    {
        try {
            System.out.println("Sending rqeuest to Server");
            socket = new Socket("127.0.0.1", 7777); //need to give ip of server and port, in our case ip is our system only
            System.out.println("connection done.");

             
           br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           out = new PrintWriter(socket.getOutputStream());

           startReading(); //method to read
           startWriting(); //method to write
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                    System.out.println("Server terminated the chat");
                    break; // if client type exit it will stop the reader
                }

                System.out.println("Server : "+msg);
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
            System.out.println("Writer started..");
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


    public static void main(String[] args) {
        System.out.println("This is client");
        new Client();
    }
}
