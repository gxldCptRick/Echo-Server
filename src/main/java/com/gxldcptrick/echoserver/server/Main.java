package com.gxldcptrick.echoserver.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    /*
    Once the connection is established the server does the following:
    1. Wait for a message from the client.
    2. Read in a single line string message from the client.
    3. Print out “Received from client:” followed by the string from the client.
    4. Print out “Sending to client:” followed by the string from the client.
    5. Write the single line string received from the client back out to the client (echoing what the client said).
    Go to 1.
    */
    private static int APP_PORT = 8080;
    public static void main(String[] args) {
        boolean isActive = true;
        try(var server = new ServerSocket(APP_PORT)) {
            try(var clientConnection = server.accept()){
                do {
                    //Do server stuff
                }while(isActive);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
