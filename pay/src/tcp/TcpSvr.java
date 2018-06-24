package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpSvr {
	public static void main(String[] args) {
		ServerSocket svr = null;
		try {
			svr = new ServerSocket(8888);
			Socket cli = svr.accept();
			ObjectOutputStream output = new ObjectOutputStream(
					cli.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(
					cli.getInputStream());
			String str = (String)input.readObject();
			str = str.toUpperCase();
			output.writeObject(str);
			input.close();
			output.close();
			cli.close();					
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (svr != null) {		
				try {svr.close();} 
				catch (IOException e) {e.printStackTrace();}
			}
	}
}


