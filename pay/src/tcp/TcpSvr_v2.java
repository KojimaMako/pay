package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpSvr_v2 {

	public static void main(String[] args) {
		ServerSocket svr = null;
		Socket cli = null;
		try {
			svr = new ServerSocket(8888);
			while(true) {
				cli = svr.accept();
				AccepedCli ac = new AccepedCli(cli);
				ac.start();
			}		
		} catch (IOException e) {
				e.printStackTrace();}
			if (svr != null) {		
				try {svr.close();} 
				catch (IOException e) {e.printStackTrace();}
			}
		}
}

class AccepedCli extends Thread
{
	private Socket cli = null;
	public AccepedCli(Socket cli) {
		this.cli = cli;
	}
	public void run()
	{
		ObjectOutputStream output;
		try {
			output = new ObjectOutputStream(
					cli.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(
					cli.getInputStream());
			String str = (String)input.readObject();
			str = str.toUpperCase();
			output.writeObject(str);
			System.out.println(getId());
			input.close();
			output.close();
			cli.close();	
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}

