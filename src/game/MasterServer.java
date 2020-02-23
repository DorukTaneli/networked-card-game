package game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import gameModel.Deck;


public class MasterServer
{
    private ServerSocket serverSocket;
    public static final int DEFAULT_SERVER_PORT = 4446;
    public int clientCounter = 0;
    public Deck deck = new Deck();
    /**
     * Initiates a server socket on the input port, listens to the line, on receiving an incoming
     * connection creates and starts a ServerThread on the client
     * @param port
     */
    public MasterServer(int port, Deck deck)
    {
        try
        {
            serverSocket = new ServerSocket(port);
            this.deck = deck;
            System.out.println("Oppened up a server socket on " + Inet4Address.getLocalHost());
            while (true)
            {
                ListenAndAccept();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Server class.Constructor exception on oppening a server socket");
        }
       
    }
    
    public static void main(String[] args){
    	   System.out.println("Start");
    	   
    	           while(true)
    	          {
    	               try
    	           {
    	            FTP_Server ftpServer = new FTP_Server();
					ftpServer.start();
    	            }
    	            catch(NullPointerException e){} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	        }
    	 }


    /**
     * Listens to the line and starts a connection on receiving a request from the client
     * The connection is started and initiated as a ServerThread object
     */
    private void ListenAndAccept()
    {
        Socket s;
        try
        {
            s = serverSocket.accept();
            if(s.isConnected())
            {
            	clientCounter++;
            	Deck halfDeck = new Deck();
            	if(this.deck.deckSize() > 26){
            		ArrayList<Integer> temp = new ArrayList<Integer>(deck.deck.subList(0, 25));
            		ArrayList<Integer> temp2 = new ArrayList<Integer>(deck.deck.subList(26, 51));
            		System.out.println(deck.deck.subList(0, 25).toString());
            		halfDeck.deck = temp;
            		deck.deck = temp2;
            	}
            	if(this.deck.deckSize() <= 26)
            	{
            		halfDeck.deck = deck.deck;
            	}
            	
                System.out.println("A connection was established with a client on the address of " + s.getRemoteSocketAddress());
                ServerThread st = new ServerThread(s, clientCounter, halfDeck);
                st.start();
            	
            }
            
        }

        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Server Class.Connection establishment error inside listen and accept function");
        }
    }

}

