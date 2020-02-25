package game;

import java.util.Scanner;

public class ClientConnection {

    public static void main(String args[])
    {
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("Please enter your name: ");
    	String name = scanner.nextLine();
        ConnectionToServer connectionToServer = new ConnectionToServer(ConnectionToServer.DEFAULT_SERVER_ADDRESS, ConnectionToServer.DEFAULT_SERVER_PORT, name);
        connectionToServer.Connect();
        System.out.println("Do you want a new game? (Press 0 to start a game)");
        int message = Integer.parseInt(scanner.nextLine());
        while (message != 9)
        {
        	
            System.out.println("Response from server: " + connectionToServer.SendForAnswer(message));
            message = Integer.parseInt(scanner.nextLine());
        }
        connectionToServer.Disconnect();
        scanner.close();
    }


}
