package message;

import java.io.UnsupportedEncodingException;

public class Test {
	static TestHead head = new TestHead();
	static TestHead head2 = new TestHead();
	static public TestHead getMgs() {
		return head;
	}
	public Test(){
		head.setTxCode("790001");
		head.setBodyLength(666);
	}
	public static void main(String[] args) {
		String in = "  7900010000000666";
		try {
			System.out.println(new String(head.pack("GBK"),"GBK"));
			System.out.println(head.getLength());
			System.out.println("----------");
			head2.unpack(in.getBytes("UTF-8"), "UTF-8");
			System.out.println(head2.getTxCode() + "," + head2.getBodyLength()
							   + "," + head2.getLength());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
