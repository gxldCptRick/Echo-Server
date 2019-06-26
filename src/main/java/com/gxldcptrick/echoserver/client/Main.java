package com.gxldcptrick.echoserver.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    private static int APP_PORT;
    public static void main(String[] args) {
        var commandArgs = createArgumentsMap(args);
        if(!commandArgs.containsKey("port")) throw new IllegalArgumentException("Port must be passed in as a command line argument.");
        APP_PORT = Integer.parseInt(commandArgs.get("port"));
        if(!commandArgs.containsKey("host")) throw new IllegalArgumentException("Host must be passed in as a command line argument.");
        var host = commandArgs.get("host");
        boolean isActive;
        try(var clientSocket = new Socket(host, APP_PORT)){
            System.out.println("..Connected to Server..");
            do{
               var input = getUserInput("Give Me a Message");
                System.out.println("Client Says: " + input);
                input += ".";
               clientSocket.getOutputStream().write(input.getBytes());
               var echoedMessage = readInputFromServer(clientSocket.getInputStream());
                System.out.println("Server Says: " + echoedMessage);
                isActive = getUserInput("Would you like to continue?(y/n)").equalsIgnoreCase("y");
            }while(isActive);
        }catch(IOException e){
            if(e instanceof SocketException){
                System.err.println("...Lost Connection To Host...");
            }else{
                e.printStackTrace();
            }
        }
    }

    private static Map<String, String> createArgumentsMap(String[] args) {
        var emptyMap = new HashMap<String, String>();
        for (var arg: args) {
            var argPair = arg.split("=");
            emptyMap.put(argPair[0], argPair[1]);
        }
        return emptyMap;
    }

    private static String readInputFromServer(InputStream inputStream) throws IOException {
        var serverMessage = new StringBuilder();
        char currentCharacter;
        while((currentCharacter = (char)inputStream.read()) != '.' && currentCharacter != 65535){
            serverMessage.append(currentCharacter);
        }
        return serverMessage.toString();
    }

    public static String getUserInput(String message) throws IOException {
        var buffy  = new BufferedReader( new InputStreamReader(System.in));
        System.out.print(message + ": ");
        return buffy.readLine().replaceAll("\\.", ";");
    }
}
