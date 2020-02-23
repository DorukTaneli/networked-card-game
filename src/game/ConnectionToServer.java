package game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import gameModel.Deck;


public class ConnectionToServer
{
    public static final String DEFAULT_SERVER_ADDRESS = "localhost";
    public static final int DEFAULT_SERVER_PORT = 4446;
    private Socket s;
    //private BufferedReader br;
    protected ObjectInputStream dataIn;
    protected ObjectOutputStream dataOut;

    protected String serverAddress;
    protected int serverPort;
    protected Deck myDeck = new Deck();
    /**
     *
     * @param address IP address of the server, if you are running the server on the same computer as client, put the address as "localhost"
     * @param port port number of the server
     */
    public ConnectionToServer(String address, int port)
    {
        serverAddress = address;
        serverPort    = port;
    }

    /**
     * Establishes a socket connection to the server that is identified by the serverAddress and the serverPort
     */
    public void Connect()
    {
        try
        {
            s=new Socket(serverAddress, serverPort);
            //br= new BufferedReader(new InputStreamReader(System.in));
            /*
            Read and write buffers on the socket
             */
            dataIn = new ObjectInputStream(s.getInputStream());
            dataOut = new ObjectOutputStream(s.getOutputStream());

            System.out.println("Successfully connected to " + serverAddress + " on port " + serverPort);
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.err.println("Error: no server has been found on " + serverAddress + "/" + serverPort);
        }
    }

    /**
     * sends the message String to the server and retrives the answer
     * @param message input message string to the server
     * @return the received server answer
     */
    public int[] SendForAnswer(int message)
    {
        int[] messageRecieved = new int[26];
        try
        {
        	/*
            Sends the message to the server via PrintWriter
             */
            dataOut.writeObject(message);
            dataOut.flush();
        	/*
            Reads a line from the server via Buffer Reader
             */
            messageRecieved = (int[]) dataIn.readObject();
            if(messageRecieved.length > 1)
            {
            	ArrayList<Integer> cardList = new ArrayList<Integer>();
            	for(int i=0; i<26; i++)
            	{
            		cardList.add(messageRecieved[i]);
            	}
            	myDeck.deck = cardList;
            }
            if(message == 2)
            {
            	dataOut.writeObject(myDeck.drawCard());
            	dataOut.flush();
            	
            }
            
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("ConnectionToServer. SendForAnswer. Socket read Error");
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return messageRecieved;
    }


    /**
     * Disconnects the socket and closes the buffers
     */
    public void Disconnect()
    {
        try
        {
            dataOut.close();
            dataIn.close();
            //br.close();
            s.close();
            System.out.println("ConnectionToServer. SendForAnswer. Connection Closed");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
