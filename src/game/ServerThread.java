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

import gameModel.Deck;

class ServerThread extends Thread
{
    protected BufferedReader is;
    protected BufferedReader dataIn;
    protected PrintWriter dataOut;
    protected Socket s;
    private String line = new String();
    private int clientCount;
    private Deck halfDeck = new Deck();
    

    /**
     * Creates a server thread on the input socket
     *
     * @param s input socket to create a thread on
     */
    public ServerThread(Socket s, int clientCounter, Deck deck)
    {
        this.s = s;
        this.clientCount = clientCounter;
        halfDeck = deck;
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
            line = is.readLine();
            while (line.compareTo("QUIT") != 0)
            {

                
                if( line.compareTo("0") == 0)
                {
                	String deck = String.format("%s,", halfDeck.deck.get(0));
                	System.out.println(halfDeck.deck.toString());
                	for(int i = 0; i<halfDeck.deckSize(); i++)
                	{
                		deck += String.format(",%s", halfDeck.deck.get(i));
                	}
                	dataOut.println(deck);;
                	dataOut.flush();
                	System.out.println("Client " + s.getRemoteSocketAddress() + " got their deck");
                }
                else{
                	int card = Integer.parseInt(line);
                }
               
                line = is.readLine();
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