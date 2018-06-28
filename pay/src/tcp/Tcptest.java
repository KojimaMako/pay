package tcp;

import message.TestHead;

public class Tcptest {

	public static void main(String[] args) {
		TcpCli cli = new TcpCli("127.0.0.1", 8888);
		TestHead tt = new TestHead();
		tt.setBodyLength(666);
		tt.setTxCode("790001");
		try {
			byte[] b = cli.send(tt.pack("GBK"));
			System.out.println(new String(b,"GBK"));
			tt.unpack(b, "GBK");
			System.out.println(tt.getTxCode() + "," + tt.getBodyLength());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
