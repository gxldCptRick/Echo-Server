package com.gxldcptrick.echoserver.client;

import java.io.IOException;
import java.net.Socket;

public class Main {
    /*
    Once the connection between your server and your client is established the client should do the following:
    1. Allow the user to type a single line string on the console.
    2. Read in the string from the user and print it out preceded by the text “Client says:”
    3. Send the string over the socket to the server.
    4. Wait for a response from the server.
    5. When the server responds print “Server says:” followed by the single line string received from the server.
    Go to 1.
    */
    private static int APP_PORT = 8080;
    public static void main(String[] args) {
        boolean isActive = true;
        try(var clientSocket = new Socket("localhost", APP_PORT)){
            do{
                // Do the server shit.
            }while(isActive);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
