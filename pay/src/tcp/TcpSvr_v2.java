package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import message.*;

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
			byte[] b = new byte[1024];
			output = new ObjectOutputStream(
					cli.getOutputStream());
			OutputStream op = cli.getOutputStream();
			ObjectInputStream input = new ObjectInputStream(
					cli.getInputStream());
			InputStream ip = cli.getInputStream();
//			String str = (String)input.readObject();
			int len = ip.read(b);
			System.out.println("read"+new String(b,0,len));
			TestHead tt = new TestHead();
			tt.unpack(b, "GBK");
			tt.setTxCode("888");
			b = tt.pack("GBK");
//			str = str.toUpperCase();
//			output.writeObject(str);
			op.write(b);
			System.out.println(getId());
			op.close();
			ip.close();
			input.close();
			output.close();
			cli.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

