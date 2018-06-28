package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Org_svr {

	public static void main(String[] args) {
		ServerSocket ser;
		try {
			ser = new ServerSocket(8888);
			Socket cli = ser.accept();
			OutputStream op = cli.getOutputStream();
			InputStream  ip = cli.getInputStream();
			byte[] b = new byte[1024];
			ip.read(b);
			String str = new String(b);
			System.out.println(str);
			str = str.toUpperCase();
			op.write(str.getBytes());
			op.flush();
			ip.close();
			op.close();
			cli.close();
			ser.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
