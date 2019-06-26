package com.gxldcptrick.echoserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

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
    private static int APP_PORT;
    public static void main(String[] args) {
        var commandArgs = createArgumentsMap(args);
        if(!commandArgs.containsKey("port")) throw new IllegalArgumentException("Port must be passed in as a command line parameter.");
        APP_PORT = Integer.parseInt(commandArgs.get("port"));
        boolean isActive = true;
        try(var server = new ServerSocket(APP_PORT)) {
            System.out.println("Listening on Port " + APP_PORT);
            try(var clientConnection = server.accept()){
                System.out.println("Accepted Connection");
                do {
                   var messageToEcho =  readMessageFromClient(clientConnection.getInputStream());
                    if(!messageToEcho.isBlank()){
                        System.out.println("Received from client: '" + messageToEcho + "'");
                        System.out.println("Sending to client: '" + messageToEcho + "'");
                        messageToEcho += ".";
                        clientConnection.getOutputStream().write(messageToEcho.getBytes());
                    }else{
                        isActive = false;
                    }
                }while(isActive);
            }
        } catch (IOException e) {
            if(e instanceof SocketException){
                System.err.println("...Lost Connection To Client...");
            }else{
                e.printStackTrace();
            }
        }
    }

    private static String readMessageFromClient(InputStream inputStream) throws IOException {
        var clientMessage = new StringBuilder();
        char currentCharacter;
        while((currentCharacter = (char)inputStream.read()) != '.' && currentCharacter != 65535){
            clientMessage.append(currentCharacter);
        }
        return clientMessage.toString();
    }

    private static Map<String, String> createArgumentsMap(String[] args) {
        var emptyMap = new HashMap<String, String>();
        for (var arg: args) {
            var argPair = arg.split("=");
            emptyMap.put(argPair[0], argPair[1]);
        }
        return emptyMap;
    }
}
