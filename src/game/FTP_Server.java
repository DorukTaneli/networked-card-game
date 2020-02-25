package game;

import java.io.*;
import java.net.*;

public class FTP_Server
{

	public static final String DEFAULT_SERVER_ADDRESS = "localhost";
	public static final int FTP_PORT = 6000;
	private ServerSocket server;
	private Socket connection;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	String filename = "";
	String lineRead = "";
	File file = null;
	void start() throws UnknownHostException,IOException ,FileNotFoundException
	{
		try
		{
			server = new ServerSocket(FTP_PORT);
			System.out.println("Listening on: " + FTP_PORT);
			connection = server.accept();
			System.out.println("Connected.");
			oos = new ObjectOutputStream(connection.getOutputStream());
			ois = new ObjectInputStream(connection.getInputStream());
			oos.writeObject("FTP connection failed."); // WRITING TO SERVER'S OUTPUT BUFFER.....
			oos.flush();

			/*
			 if() // data change protocol, check game states
			 {
				try
				{
				 filename = (String) ois.readObject();
				 File file = new File(filename);
				 FileInputStream fis = new FileInputStream(file);
				 BufferedReader b = new BufferedReader(new InputStreamReader(fis));
				 while((lineRead = b.readLine()) != null)
				 {
					 System.out.println("Inside" + lineRead);
					 serverMessage(contents);
               			  }
				}
				catch(ClassNotFoundException e){
					System.out.println(e.getMessage());
				}
			 }
			 */
		}
		catch(IOException e){}
		finally
		{
			try
			{
				ois.close();
				oos.close();
				server.close();
			}
			catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}


}

