package message;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.ChangedCharSetException;

public abstract class Pieces {
	private List<Item> itemList = new ArrayList<Item>();
	
	public Pieces(Item[] items)
	{
		for(Item item : items)
			itemList.add(item);
	}
	
	public int getLength()
	{
		int length = 0;
		for(Item item:itemList)
			length += item.getLength();
		return length;
	}
	
	public byte[] pack(String charsetName)
	{
		StringBuffer result = new StringBuffer();
		byte[] b = null;
		try {
			for(Item item : itemList) {
				PropertyDescriptor pd = new PropertyDescriptor(
						item.getName(), this.getClass());
				Method readMethod = pd.getReadMethod();
				Object gotVal = readMethod.invoke(this);
				String str = gotVal == null ? "":gotVal.toString();
				result.append(
						this.autoFill(str,item.getNullFlag(),
								item.getFillChar(),
								item.getAlignment(),
								item.getLength(),
								charsetName));
				
			}
			b = result.toString().getBytes(charsetName);
		} catch (Exception e) {  
            e.printStackTrace();  
        }  
		return b;
	}
	private int countBytes(byte[] b) {
		int i = 0;
		for (i = 0; i < b.length; i++) {
			if(b[i] == '\0')
				break;
		}
		return i;
	}
	public void unpack(byte[] msg,String charsetName) throws Exception{
		if(countBytes(msg) != this.getLength())
			throw new Exception("解消息出错，消息长度不合法！msg.length="
								+ msg.length + ",obj.length="
								+ this.getLength());
		int index = 0;
		try {
			for(Item item : itemList) {
				String value = new String(msg,index,item.getLength(),
						charsetName);
				value = this.getRealValue(value,item.getAlignment(),
						item.getFillChar());
				PropertyDescriptor pd = new PropertyDescriptor(item.getName(),
						this.getClass());
				Method setMethod = pd.getWriteMethod();
				if(pd.getPropertyType().equals(byte.class) ||
				   pd.getPropertyType().equals(Byte.class)) 
					setMethod.invoke(this, Byte.parseByte(value));
				
				else if (pd.getPropertyType().equals(short.class) || 
						pd.getPropertyType().equals(Short.class))   
					setMethod.invoke(this, Short.parseShort(value));  
				
				else if (pd.getPropertyType().equals(int.class) || 
						pd.getPropertyType().equals(Integer.class))   
					setMethod.invoke(this, Integer.parseInt(value));  
				
				else if (pd.getPropertyType().equals(long.class) || 
						pd.getPropertyType().equals(Long.class))   
					setMethod.invoke(this, Long.parseLong(value));
				
				else if (pd.getPropertyType().equals(float.class) || 
						pd.getPropertyType().equals(Float.class))   
					setMethod.invoke(this, Float.parseFloat(value)); 
				
				else if (pd.getPropertyType().equals(double.class) || 
						pd.getPropertyType().equals(Double.class))   
					setMethod.invoke(this, Double.parseDouble(value));  
				
				else if (pd.getPropertyType().equals(char.class) || 
						pd.getPropertyType().equals(Character.class))   
					setMethod.invoke(this, value.toCharArray()[0]);
				
				else if (pd.getPropertyType().equals(boolean.class) || 
						pd.getPropertyType().equals(Boolean.class))   
					setMethod.invoke(this, Boolean.valueOf(value));  
	            else   
	                setMethod.invoke(this, value);
				index += item.getLength();
			}
		} catch (Exception e) {
//			throw new Exception("解消息出错：" + e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	private String getRealValue(String value,Alignment alignment,
			char fillChar) {
		char[] chars = value.toCharArray();
		int index = 0;
		if(alignment == Alignment.LEFT) {
			for(int i = 0; i < chars.length; i++) {
				if(fillChar != chars[i]) {
					index++;
				}
				else
					break;
			}
		return value.substring(0,index);
		}else if(alignment == Alignment.RIGHT) {
			for(int i = 0; i < chars.length; i++) {
				if(fillChar == chars[i]) {
					index++;
				}
				else
					break;
			}
		}
		return value.substring(index);
	}
	
	private String autoFill(String value,boolean nullFlag,
			char fillChar,Alignment alignment,
			int maxLength,String charsetName) {
		try {
			if(value == null) {
				value = "";
				if(!nullFlag) {
					throw new IllegalArgumentException("该参数不能为空");
				}
			}
							StringBuffer sbuffBuffer = new StringBuffer();  
			if(value.getBytes(charsetName).length > maxLength) {
				byte[] data = new byte[maxLength];
				System.arraycopy(value.getBytes(charsetName), 0, 
						data, 0, maxLength);
				sbuffBuffer.append(new String(data, charsetName));
				return sbuffBuffer.toString();
			}
			if(alignment == message.Alignment.LEFT) {
				sbuffBuffer.append(value);
			}
			for(int i = value.getBytes(charsetName).length;
					i < maxLength; i++) {
				sbuffBuffer.append(fillChar);
			}
			if(alignment == message.Alignment.RIGHT) {
				sbuffBuffer.append(value);
			}
			return sbuffBuffer.toString();
		} catch(UnsupportedEncodingException e) {  
            throw new RuntimeException("系统不支持" + charsetName + "编码");  
		}
	}
	public static void main(String[] args) {	}

}
