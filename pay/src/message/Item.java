package message;

public class Item {
	private int length;
	private boolean nullFlag;
	private char fillChar;
	private ItemType type;
	private Alignment alignment;
	private Object value;
	private String name;
	
	public Item(String name,int length,char fillChar,ItemType type,
			boolean nullFlag,Alignment alignment)
	{
		this.name = name;
		this.length = length;
		this.nullFlag = nullFlag;
		this.alignment = alignment;
		this.type = type;
		this.fillChar = fillChar;
	}
	public static void main(String[] args) {}
}


