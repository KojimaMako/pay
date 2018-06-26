package message;

public class TestHead extends Pieces {
	private String txCode;
	private int length;
	
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
	public void setLength(int length) {
		this.length = length;
	}
	public String getTxCode()
	{
		return this.txCode;
	}
	public int getLength()
	{
		return length;
	}
	public static void main(String[] args) {};

}
