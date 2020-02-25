package game;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import database.MongoDB;
import gameModel.Deck;
import gameModel.Player;

class ServerThread extends Thread
{
    protected BufferedReader is;
    protected BufferedReader dataIn;
    protected PrintWriter dataOut;
    protected Socket s;
    private String line = new String();
    private int clientCount;
    private int turnCount = 0;
    private Deck halfDeck = new Deck(26);
    public Player player;
    private static List<Player> playerList = new LinkedList<Player>();
    private static MongoDB db = new MongoDB();

    /**
     * Creates a server thread on the input socket
     *
     * @param s input socket to create a thread on
     */
    public ServerThread(Socket s, int clientCounter, Deck deck)
    {
        this.s = s;
        this.clientCount = clientCounter;
        Deck.copyDeck(halfDeck, deck.deck);
    }

    /**
     * The server thread, echos the client until it receives the QUIT string from the client
     */
    public void run()
    {
        try
        {
        	dataIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            dataOut = new PrintWriter(s.getOutputStream());

        }
        catch (IOException e)
        {
            System.err.println("Server Thread. Run. IO error in server thread");
        }

        try
        {
            line = dataIn.readLine();
            while (line.compareTo("QUIT") != 0)
            {

                
                if( line.charAt(0) == '0')
                {
                	String[] parser = line.split(",");
                	String name = parser[1];
                	player = new Player(name);
                	String deck = Integer.toString((halfDeck.deck.get(0)));
                	System.out.println(halfDeck.deck.toString());
                	System.out.println(deck);
                	for(int i = 0; i<halfDeck.deckSize(); i++)
                	{
                		deck += ',' + halfDeck.deck.get(i);
                	}
                	System.out.println(deck);
                	dataOut.println(deck);;
                	dataOut.flush();
                	player.giveDeck(halfDeck);
                	this.playerList.add(player);
                	System.out.println("Client " + player.getName() + " got their deck");
                }
                else{
                	int card = Integer.parseInt(line);
                }
                
                if(clientCount == 2){
                	System.out.println("Game starting");
                	while(turnCount < 26){
                		db.insert(s.getLocalPort(),playerList.get(clientCount).getName(),playerList.get(clientCount+1).getName(),
                			turnCount, turnCount, playerList.get(clientCount).getScore(), playerList.get(clientCount+1).getScore());
                	turnCount++;
                	}
                }
               
                line = dataIn.readLine();
            }
        }
        catch (IOException e)
        {
            line = this.getName(); //reused String line for getting thread name
            System.err.println("Server Thread. Run. IO Error/ Client " + line + " terminated abruptly");
        }
        catch (NullPointerException e)
        {
            line = this.getName(); //reused String line for getting thread name
            System.err.println("Server Thread. Run.Client " + line + " Closed");
        } finally
        {
            try
            {
                System.out.println("Closing the connection");
                if (dataIn != null)
                {
                    dataIn.close();
                    System.err.println(" Socket Input Stream Closed");
                }

                if (dataOut != null)
                {
                	dataOut.close();
                    System.err.println("Socket Out Closed");
                }
                if (s != null)
                {
                    s.close();
                    System.err.println("Socket Closed");
                }

            }
            catch (IOException ie)
            {
                System.err.println("Socket Close Error");
            }
        }//end finally
    }
}