package message;

public class TestHead extends Pieces {
	private String txCode;
	private int bodyLength;
	
	private static final Item[] items = new Item[] {
			new Item("txCode",8,' ',ItemType.STRING,
					false,Alignment.RIGHT),
			new Item("bodyLength",10,'0',ItemType.INT,
					false,Alignment.RIGHT)
	};
	public TestHead() {
		super(items);
	}
	
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public void setBodyLength(int length) {
		this.bodyLength = length;
	}
	public String getTxCode()
	{
		return txCode;
	}
	public int getBodyLength()
	{
		return bodyLength;
	}
	public String toString() {
		return new String(txCode + bodyLength);
	}
	public static void main(String[] args) {};

}
