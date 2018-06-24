package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpCli {

	public static void main(String[] args) {
		try {
			Socket cli = new Socket("127.0.0.1",8888);
			String str = new String("hello world!");
			ObjectOutputStream output = new ObjectOutputStream(
					cli.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(
					cli.getInputStream());
			output.writeObject(str);
			System.out.println((String)input.readObject());
			input.close();
			output.close();
			cli.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
