package game;

import java.io.*;
import java.net.*;

public class FTP_Server
{

	public static final String DEFAULT_SERVER_ADDRESS = "localhost";
	public static final int FTP_PORT = 6000;
	ServerSocket server; // create a server.....
	Socket con= null; 		// to establish a connection
	ObjectOutputStream out;
	ObjectInputStream in;
	String filename = "";
	String contents = "";
	File file=null;
	void start() throws UnknownHostException,IOException ,FileNotFoundException
	{
		try
		{
			server = new ServerSocket(FTP_PORT);
      System.out.println("Server - listening on port " + FTP_PORT);
     	System.out.println("Server - Waiting for the Client.....");
			 con = server.accept();
			 System.out.println("Server - Connection received from " + con.getInetAddress().getHostName());
			 out = new ObjectOutputStream(con.getOutputStream());
			 in = new ObjectInputStream(con.getInputStream());
			 serverMessage("Server - Connection to Client Failed.....");

			 do
			 {
				try
				{
				 filename = (String) in.readObject();
 System.out.println("Server > Got filename From Client -> " + filename);
				 File file = new File(filename);
				 FileInputStream fin = new FileInputStream(file);
				 BufferedReader din = new BufferedReader(new InputStreamReader(fin));
				 while((contents = din.readLine()) != null)
				 {
					 System.out.println("Inside" + contents);
					 serverMessage(contents);  //sending file contents.....
               			  }
				 if(filename.equals("bye"))
				      	serverMessage("bye");
				}
				catch(ClassNotFoundException e){
					System.out.println(e.getMessage());
				}
			 }while(!filename.equals("bye"));
		}
		catch(IOException e){}
		 finally
            		{
                try
                {
                    in.close();
                    out.close();
                    server.close();
                }
                catch(IOException e)
                {
                   	System.out.println(e.getMessage());
                }
          		 }
	}
	void serverMessage(String str) throws IOException
	{
		try
		{
			 out.writeObject(str); // WRITING TO SERVER'S OUTPUT BUFFER.....
			 out.flush(); // clearing the output buffer.....
 System.out.println("Server -writing Message to Server's output buffer....." + str);
		}
		catch(IOException ioe){	}
	}

/*
public static void main(String[] args) throws UnknownHostException
 ,IOException ,NullPointerException
	{
		FTP_Server server = new FTP_Server();
        		while(true)
     	  	 {
         			  try
       	    {
        	    server.start();
      	     }
        	   catch(NullPointerException e){}
      	 }
	}
*/
}
