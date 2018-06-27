package tcp;
import message.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TcpCli {

	public static void main(String[] args) {
		try {
			Socket cli = new Socket("127.0.0.1",8888);
//			String str = new String("hello world!");
			TestHead tt = new TestHead();
			tt.setBodyLength(666);
			tt.setTxCode("790001");
			ObjectOutputStream output = new ObjectOutputStream(
					cli.getOutputStream());
			OutputStream op = cli.getOutputStream();
			InputStream ip = cli.getInputStream();
			ObjectInputStream input = new ObjectInputStream(
					cli.getInputStream());
//			output.writeObject(str);
//			output.writeObject(tt);
//			System.out.println((String)input.readObject());
			op.write(tt.pack("GBK"));
			byte[] b = new byte[1024];
			int len = ip.read(b);
			System.out.println(new String(b,0,len));
			tt.unpack(b, "GBK");
			System.out.println(tt.getTxCode() + "," + tt.getBodyLength());
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
